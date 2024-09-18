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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Integration_Test {

    private static final String MarkerTag = "public";
    private final SwaggerParser parser = new SwaggerParser();
    private final SwaggerReduce reduce = new SwaggerReduce(MarkerTag);
    private final SwaggerTrim trim = new SwaggerTrim();
    private final SwaggerMerge merge = new SwaggerMerge();

    @Test
    void just_reduce() {
        Swagger petShop = parse(TestFiles.Json.petstoreExample());
        petShop.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(operation -> operation.tag(MarkerTag));
        reduce.reduce(petShop);

        Yaml.prettyPrint(petShop);
        assertThat(petShop, notNullValue());
    }

    @Test
    void reduce_trim() {
        Swagger petShop = parse(TestFiles.Json.petstoreExample());
        petShop.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(operation -> operation.tag(MarkerTag));
        reduce.reduce(petShop);

        Yaml.prettyPrint(petShop);

        trim.trim(petShop);

        Yaml.prettyPrint(petShop);
        assertThat(petShop, notNullValue());
    }

    @Test
    void reduce_trim_merge() {
        Swagger _1st = parse(TestFiles.Json.petstoreExample());
        _1st.getPaths().values().stream().map(Path::getOperations).flatMap(Collection::stream).forEach(operation -> operation.tag(MarkerTag));
        Swagger _2nd = parse(TestFiles.Json.petstoreExample());
        List<Swagger> collect = Stream.of(_1st, _2nd).map(reduce::reduce).map(trim::trim).collect(Collectors.toList());
        Swagger result = this.merge.merge(collect.get(0), collect.get(1)).swagger();
        Yaml.prettyPrint(_1st);
        Json.prettyPrint(result);
        assertThat(result, notNullValue());
    }

    @Test
    void model_with_composition() {
        Swagger swagger = parse(TestFiles.Yaml.modelWithComposition());
        Swagger trim = this.trim.trim(swagger);
        Yaml.prettyPrint(trim);
        assertThat(trim.getDefinitions().size(), is(2));
    }

    private Swagger parse(String file) {

        try {
            final var classpathLocation = file.replace("src/test/resources/", "/");
            final var specificationAsString = Files.readString(
                Paths.get(this.getClass().getResource(classpathLocation).toURI()), Charset.defaultCharset());
            return parser.parse(specificationAsString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
