package com.github.signed.swagger.trim;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.signed.swagger.essentials.SwaggerStreams;

import io.swagger.models.Swagger;

public class PathContainedInBooth {
    private final SwaggerStreams swaggerStreams = new SwaggerStreams();

    public Predicate<String> pathContainedInBooth(Swagger two){
        return exposedPath -> swaggerStreams.pathStream(two)
                .map(this::unifyUrlTemplateVariableNames)
                .collect(Collectors.toList())
                .contains(unifyUrlTemplateVariableNames(exposedPath));
    }

    private String unifyUrlTemplateVariableNames(String url) {
        return url.replaceAll("\\{[^\\}]+\\}", "{variable}");
    }
}
