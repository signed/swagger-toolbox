package com.github.signed.swagger.modify;

import java.util.Optional;
import java.util.function.Consumer;

import io.swagger.models.Swagger;

public class ModificationResult {

    public static ModificationResult failed(Swagger swagger, Consumer<ModificationFailureCause> causeConsumer) {
        return new ModificationResult(swagger, Optional.of(causeConsumer));
    }

    public static ModificationResult success(Swagger swagger) {
        return new ModificationResult(swagger, Optional.empty());
    }

    private final Optional<Consumer<ModificationFailureCause>> causeConsumer;
    private final Swagger swagger;

    public ModificationResult(Swagger swagger, Optional<Consumer<ModificationFailureCause>> causeConsumer) {
        this.swagger = swagger;
        this.causeConsumer = causeConsumer;
    }

    public Swagger swagger() {
        return swagger;
    }

    public boolean success() {
        return successOr(new ModificationFailureCause() {
            @Override
            public void pathNotDefined(OperationIdentifier identifier) {
                //do nothing
            }

            @Override
            public void operationNotDefined(OperationIdentifier identifier) {
                //do nothing
            }
        });
    }

    public boolean successOr(ModificationFailureCause cause) {
        if (causeConsumer.isPresent()) {
            causeConsumer.get().accept(cause);
            return false;
        }
        return true;
    }


}
