package com.github.signed.swagger.merge;

import java.util.Optional;
import java.util.function.Consumer;

import io.swagger.models.Swagger;

public class MergeResult {

    public static MergeResult failed(Swagger swagger, Consumer<MergeFailureCause> causeConsumer) {
        return new MergeResult(swagger, Optional.of(causeConsumer));
    }

    public static MergeResult success(Swagger swagger) {
        return new MergeResult(swagger, Optional.empty());
    }

    private final Optional<Consumer<MergeFailureCause>> causeConsumer;
    private final Swagger swagger;

    public MergeResult(Swagger swagger, Optional<Consumer<MergeFailureCause>> causeConsumer) {
        this.swagger = swagger;
        this.causeConsumer = causeConsumer;
    }

    public Swagger swagger() {
        return swagger;
    }

    public boolean success() {
        return successOr(new MergeFailureCause(){

            @Override
            public void conflict(SwaggerMergeException exception) {

            }
        });
    }

    public boolean successOr(MergeFailureCause cause) {
        if (causeConsumer.isPresent()) {
            causeConsumer.get().accept(cause);
            return false;
        }
        return true;
    }
}
