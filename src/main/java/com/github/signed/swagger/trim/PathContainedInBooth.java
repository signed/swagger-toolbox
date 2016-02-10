package com.github.signed.swagger.trim;

import java.util.function.Predicate;

import com.github.signed.swagger.essentials.SwaggerStreams;

import io.swagger.models.Swagger;

public class PathContainedInBooth {
    private final SwaggerStreams swaggerStreams = new SwaggerStreams();

    public Predicate<String> pathContainedInBooth(Swagger two){
        return exposedPath -> {
            ToolboxPath toolboxPath = new ToolboxPath(exposedPath);
            return swaggerStreams.pathStream(two).map(ToolboxPath::new).anyMatch(toolboxPath::referenceSameResource);
        };
    }

}
