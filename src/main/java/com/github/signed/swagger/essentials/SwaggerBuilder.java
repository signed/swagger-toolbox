package com.github.signed.swagger.essentials;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.models.Swagger;

public class SwaggerBuilder {

    private final InfoBuilder infoBuilder = new InfoBuilder();
    private final Map<String, PathBuilder> paths = Maps.newLinkedHashMap();
    private final List<TagDefinitionBuilder> tags = Lists.newArrayList();
    private final Map<String, ModelBuilder> definitions = Maps.newLinkedHashMap();
    private final Map<String, ParameterBuilder> parameters = Maps.newLinkedHashMap();
    private final Map<String, ResponseBuilder> responses = Maps.newLinkedHashMap();

    public InfoBuilder withInfo() {
        return infoBuilder;
    }

    public PathBuilder withPath(String path) {
        PathBuilder pathBuilder = new PathBuilder();
        paths.put(path, pathBuilder);
        return pathBuilder;
    }

    public TagDefinitionBuilder defineTag(String tagName) {
        TagDefinitionBuilder tagDefinitionBuilder = new TagDefinitionBuilder();
        tags.add(tagDefinitionBuilder);
        return tagDefinitionBuilder.withName(tagName);
    }

    public ModelBuilder withModelDefinition(String modelIdentifier) {
        ModelBuilder modelBuilder = new ModelBuilder();
        definitions.put(modelIdentifier, modelBuilder);
        return modelBuilder;
    }

    public ParameterBuilder withParameterDefinition(String parameterIdentifier) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        withParameterDefinition(parameterIdentifier, parameterBuilder);
        return parameterBuilder;
    }

    public SwaggerBuilder withParameterDefinition(String parameterIdentifier, ParameterBuilder parameterBuilder) {
        parameters.put(parameterIdentifier, parameterBuilder);
        return this;
    }

    public SwaggerBuilder withResponseDefinition(String responseIdentifier, ResponseBuilder responseBuilder) {
        responses.put(responseIdentifier, responseBuilder);
        return this;
    }

    public Swagger build() {
        Swagger swagger = new Swagger();
        swagger.setInfo(infoBuilder.build());
        tags.forEach(tagDefinitionBuilder -> swagger.addTag(tagDefinitionBuilder.build()));
        paths.forEach((path, pathBuilder) -> swagger.path(path, pathBuilder.build()));
        definitions.forEach((definitionIdentifier, modelBuilder) -> swagger.addDefinition(definitionIdentifier, modelBuilder.build()));
        parameters.forEach((parameterIdentifier, parameterBuilder) -> swagger.addParameter(parameterIdentifier, parameterBuilder.build()));
        responses.forEach((responseIdentifier, responseBuilder) -> swagger.response(responseIdentifier, responseBuilder.build()));
        return swagger;
    }
}
