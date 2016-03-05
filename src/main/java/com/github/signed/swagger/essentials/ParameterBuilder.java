package com.github.signed.swagger.essentials;

import io.swagger.models.RefModel;
import io.swagger.models.parameters.*;

import java.util.Optional;

import static java.lang.Boolean.TRUE;

public class ParameterBuilder {
    private Optional<String> maybeReferenceToADefinition = Optional.empty();
    private Optional<String> maybeReferenceToAParameterDefinition = Optional.empty();
    private Optional<String> maybeAName = Optional.empty();
    private Optional<String> maybeType = Optional.empty();
    private Optional<String> maybeDescription = Optional.empty();
    private Optional<Boolean> maybeRequired = Optional.empty();
    private boolean headerParameter;
    private boolean pathParameter;
    private boolean bodyParameter;

    public ParameterBuilder inHeader() {
        this.headerParameter = true;
        return this;
    }

    public ParameterBuilder inPath() {
        this.pathParameter = true;
        return this;
    }

    public ParameterBuilder referencingModelDefinition(String definitionId) {
        bodyParameter = true;
        maybeReferenceToADefinition = Optional.of(definitionId);
        return this;
    }

    public ParameterBuilder referencingParameterDefinition(String parameterKey) {
        maybeReferenceToAParameterDefinition = Optional.ofNullable(parameterKey);
        return this;
    }

    public ParameterBuilder withName(String name) {
        maybeAName = Optional.ofNullable(name);
        return this;
    }

    public ParameterBuilder ofTypeString() {
        maybeType = Optional.of("string");
        return this;
    }

    public ParameterBuilder withDescription(String description) {
        maybeDescription = Optional.of(description);
        return this;
    }

    public ParameterBuilder required() {
        maybeRequired = Optional.of(TRUE);
        return this;
    }

    public Parameter build() {
        if (bodyParameter) {
            BodyParameter bodyParameter = new BodyParameter();
            maybeReferenceToADefinition.ifPresent(referenceIdentifier -> bodyParameter.setSchema(new RefModel(referenceIdentifier)));
            maybeAName.ifPresent(bodyParameter::setName);
            return bodyParameter;
        }

        if (maybeReferenceToAParameterDefinition.isPresent()) {
            return new RefParameter(maybeReferenceToAParameterDefinition.get());
        }

        if (headerParameter) {
            HeaderParameter headerParameter = new HeaderParameter();
            maybeAName.ifPresent(headerParameter::setName);
            maybeType.ifPresent(headerParameter::setType);
            maybeDescription.ifPresent(headerParameter::setDescription);
            maybeRequired.ifPresent(headerParameter::setRequired);
            return headerParameter;
        }

        if (pathParameter) {
            PathParameter pathParameter = new PathParameter();
            pathParameter.setName(maybeAName.get());
            pathParameter.setType(maybeType.get());
            return pathParameter;
        }
        throw new UnsupportedOperationException("Where do you want this parameter to be?");
    }
}
