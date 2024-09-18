package com.github.signed.swagger.essentials;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

import com.github.signed.swagger.trim.ToolboxPath;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import java.util.Map;
import java.util.stream.Stream;

public class SwaggerStreams {

    public Stream<Operation> operationsStream(Swagger swagger) {
        return pathDefinitionStream(swagger).flatMap(path -> path.getOperations().stream());
    }

    public Stream<Path> pathDefinitionStream(Swagger swagger) {
        return paths(swagger).values().stream();
    }

    public Stream<ToolboxPath> toolboxPathStream(Swagger swagger) {
        return pathStream(swagger).map(ToolboxPath::new);
    }

    public Stream<String> pathStream(Swagger one) {
        return paths(one).keySet().stream();
    }

    public Map<String, Path> paths(Swagger one) {
        return ofNullable(one.getPaths()).orElse(emptyMap());
    }

    public Stream<Tag> tagsStream(Swagger swagger) {
        return ofNullable(swagger.getTags()).orElse(emptyList()).stream();
    }

    public Map<String, Response> responses(Swagger swagger) {
        return ofNullable(swagger.getResponses()).orElse(emptyMap());
    }

    public Map<String, Model> definitions(Swagger swagger) {
        return ofNullable(swagger.getDefinitions()).orElse(emptyMap());
    }
}
