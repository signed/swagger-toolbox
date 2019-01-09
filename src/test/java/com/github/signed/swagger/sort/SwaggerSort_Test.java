package com.github.signed.swagger.sort;

import com.github.signed.swagger.essentials.*;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.RefParameter;
import io.swagger.util.Yaml;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.github.signed.swagger.essentials.ParameterMatcher.parameterNamed;
import static com.github.signed.swagger.essentials.ParameterMother.*;
import static com.github.signed.swagger.essentials.PathMother.anyPath;
import static com.github.signed.swagger.essentials.TagMatcher.tagNamed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class SwaggerSort_Test {

    private final List<String> unordered = Arrays.asList("zebra", "ape", "Ant", "aaa");
    private final List<String> ordered = Arrays.asList("aaa", "Ant", "ape", "zebra");
    private final SwaggerBuilder builder = SwaggerMother.emptyApiDefinition();

    @Test
    void sort_tags_by_case_insensitive_name() {
        unordered.forEach(builder::defineTag);
        Iterator<Tag> tags = sort().getTags().iterator();

        ordered.forEach(tag -> assertThat(tags.next(), is(tagNamed(tag))));
    }

    @Test
    void sort_model_definitions_by_case_insensitive_identifier() {
        unordered.forEach(builder::withModelDefinition);
        Iterator<String> definitions = sort().getDefinitions().keySet().iterator();

        ordered.forEach(identifier -> assertThat(definitions.next(), is(identifier)));
    }

    @Test
    void sort_parameter_definitions_by_case_insensitive_identifier() {
        unordered.forEach((parameterIdentifier) -> builder.withParameterDefinition(parameterIdentifier, anyParameter()));
        Iterator<String> parameterDefinitions = sort().getParameters().keySet().iterator();

        ordered.forEach(identifier -> assertThat(parameterDefinitions.next(), is(identifier)));
    }

    @Test
    void sort_shared_parameters_in_path_definitions_by_case_insensitive_name() {
        String path = anyPath();
        PathBuilder pathBuilder = builder.withPath(path);
        unordered.forEach(parameterName -> pathBuilder.withParameterForAllOperations(parameterName, anyParameterThatCanOccurMultipleTimesInASingleOperation()));
        Iterator<Parameter> parameters = sort().getPath(path).getParameters().iterator();

        ordered.forEach(parameterName -> assertThat(parameters.next(), parameterNamed(parameterName)));
    }

    @Test
    void sort_parameters_in_operations_by_case_insensitive_name() {
        String path = anyPath();
        OperationBuilder operationBuilder = builder.withPath(path).withPost();
        unordered.forEach(parameterName -> operationBuilder.withParameter(parameterName, anyParameterThatCanOccurMultipleTimesInASingleOperation()));
        Iterator<Parameter> parameters = sort().getPath(path).getPost().getParameters().iterator();

        ordered.forEach(parameterName -> assertThat(parameters.next(), parameterNamed(parameterName)));
    }

    @Test
    void sort_ref_parameters_by_the_parameter_they_are_referencing() {
        String parameterIdentifier = anyParameterIdentifier();
        builder.withParameterDefinition(parameterIdentifier, anyParameter().withName("a"));

        String path = anyPath();

        PathBuilder pathBuilder = builder.withPath(path);
        pathBuilder.withParameterForAllOperations("z", anyParameterThatCanOccurMultipleTimesInASingleOperation());
        pathBuilder.withParameterForAllOperations(anyParameterReferencingParameterDefinition(parameterIdentifier));

        List<Parameter> parameters = sort().getPath(path).getParameters();

        assertThat(parameters.get(0), instanceOf(RefParameter.class));
        assertThat(parameters.get(1), parameterNamed("z"));
    }

    private Swagger sort() {
        Swagger unsorted = builder.build();
        Yaml.prettyPrint(unsorted);
        Swagger sorted = new SwaggerSort().sort(unsorted);
        Yaml.prettyPrint(sorted);
        return sorted;
    }
}
