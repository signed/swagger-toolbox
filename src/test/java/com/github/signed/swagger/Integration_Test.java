package com.github.signed.swagger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.github.signed.swagger.merge.SwaggerMerge;
import com.github.signed.swagger.reduce.SwaggerReduce;
import com.github.signed.swagger.trim.SwaggerTrim;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import io.swagger.util.Yaml;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Integration_Test {

    private static final String MarkerTag = "public";
    private final String first = TestFiles.Json.petstoreExample();
    private final String second = TestFiles.Json.petstoreExample();
    private final SwaggerParser parser = new SwaggerParser();
    private final SwaggerReduce reduce = new SwaggerReduce(MarkerTag);
    private final SwaggerTrim trim = new SwaggerTrim();
    private final SwaggerMerge merge = new SwaggerMerge();

    @Test
    void just_reduce() {
        Swagger petShop = parser.read(first);
        petShop.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(operation -> operation.tag(MarkerTag));
        reduce.reduce(petShop);

        Yaml.prettyPrint(petShop);
        assertThat(petShop, notNullValue());
    }

    @Test
    void reduce_trim() {
        Swagger petShop = parser.read(first);
        petShop.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(operation -> operation.tag(MarkerTag));
        reduce.reduce(petShop);

        Yaml.prettyPrint(petShop);

        trim.trim(petShop);

        Yaml.prettyPrint(petShop);
        assertThat(petShop, notNullValue());
    }

    @Test
    void reduce_trim_merge() {
        Swagger _1st = parser.read(first);
        _1st.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(operation -> operation.tag(MarkerTag));
        Swagger _2nd = parser.read(second);
        List<Swagger> collect = Stream.of(_1st, _2nd).map(reduce::reduce).map(trim::trim).collect(Collectors.toList());
        Swagger result = this.merge.merge(collect.get(0), collect.get(1)).swagger();
        Yaml.prettyPrint(_1st);
        Json.prettyPrint(result);
        assertThat(result, notNullValue());
    }

    @Test
    void model_with_composition() {
        Swagger swagger = parser.read(TestFiles.Yaml.modelWithComposition());
        Swagger trim = this.trim.trim(swagger);
        Yaml.prettyPrint(trim);
        assertThat(trim.getDefinitions().size(), is(2));
    }
}
