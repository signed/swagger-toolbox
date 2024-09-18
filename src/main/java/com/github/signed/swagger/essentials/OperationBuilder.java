package com.github.signed.swagger.essentials;

import static com.google.common.collect.Lists.newArrayList;

import io.swagger.models.Operation;
import java.util.List;
import java.util.function.Consumer;

public class OperationBuilder {
    private final List<String> tags = newArrayList();
    private final List<ParameterBuilder> parameters = newArrayList();
    private final List<Consumer<Operation>> responses = newArrayList();

    public OperationBuilder withTag(String tag) {
        tags.add(tag);
        return this;
    }

    public ParameterBuilder withParameter(String name) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        withParameter(name, parameterBuilder);
        return parameterBuilder;
    }

    public OperationBuilder withParameter(String name, ParameterBuilder parameterBuilder) {
        parameterBuilder.withName(name);
        parameters.add(parameterBuilder);
        return this;
    }

    public OperationBuilder withResponse(String httpStatusCode, ResponseBuilder responseBuilder) {
        this.responses.add(operation -> operation.addResponse(httpStatusCode, responseBuilder.build()));
        return this;
    }

    public Operation build() {
        Operation operation = new Operation();
        if (!tags.isEmpty()) {
            operation.setTags(newArrayList(tags));
        }
        parameters.forEach(parameterBuilder -> operation.addParameter(parameterBuilder.build()));
        responses.forEach(response -> response.accept(operation));
        return operation;
    }
}
