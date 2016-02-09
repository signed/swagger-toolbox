package com.github.signed.swagger.trim;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.swagger.models.Swagger;

public class PathContainedInBooth {

    public Predicate<String> pathContainedInBooth(Swagger two){
        return exposedPath -> two.getPaths().keySet().stream()
                .map(this::unifyUrlTemplateVariableNames)
                .collect(Collectors.toList())
                .contains(unifyUrlTemplateVariableNames(exposedPath));
    }

    private String unifyUrlTemplateVariableNames(String url) {
        return url.replaceAll("\\{[^\\}]+\\}", "{variable}");
    }
}
