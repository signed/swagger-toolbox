package com.github.signed.swagger.trim;

import static com.github.signed.swagger.essentials.SwaggerMatcher.hasModelDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.github.signed.swagger.essentials.*;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import io.swagger.util.Yaml;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class SwaggerTrim_Test {
    private final SwaggerBuilder swaggerBuilder = SwaggerMother.emptyApiDefinition();

    @Test
    void trim_of_empty_swagger_definition_should_work() {
        assertThat(trimmed().getTags(), nullValue());
    }

    @Test
    void trim_a_swagger_with_untagged_path_definition() {
        swaggerBuilder.withPath("/").withPost();

        assertThat(trimmed(), not(nullValue()));
    }

    @Test
    void do_not_remove_a_model_that_is_referenced_in_another_model_that_is_actually_referenced(){
        PathBuilder path = swaggerBuilder.withPath("/");
        path.withPost();
        path.withParameterForAllOperations(ParameterMother.anyParameterReferencingModelDefinition("referenced-in-path"));
        ModelBuilder referencedInPath = swaggerBuilder.withModelDefinition("referenced-in-path").withTypeObject();
        referencedInPath.withReferencePropertyNamed("some-property").withReferenceTo("not-referenced-in-a-path");
        swaggerBuilder.withModelDefinition("not-referenced-in-a-path").withTypeObject();

        assertThat(trimmed(), hasModelDefinitionsFor("not-referenced-in-a-path"));
    }

    @Test
    void remove_empty_tag_lists_in_path_operations() {
        swaggerBuilder.withPath("/").withPost();
        Swagger swagger = swaggerBuilder.build();
        swagger.getPath("/").getPost().setTags(Collections.emptyList());

        assertThat(trimmed(swagger).getPath("/").getPost().getTags(), nullValue());
    }

    private Swagger trimmed() {
        Swagger build = swaggerBuilder.build();
        Json.prettyPrint(build);
        Swagger trim = trimmed(build);
        Yaml.prettyPrint(trim);
        return trim;
    }

    private Swagger trimmed(Swagger build) {
        return new SwaggerTrim().trim(build);
    }
}