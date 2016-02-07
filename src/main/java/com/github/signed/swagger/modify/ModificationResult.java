package com.github.signed.swagger.modify;

import java.util.function.Consumer;

import io.swagger.models.Swagger;

public class ModificationResult {

    public interface Cause {
        void pathNotDefined(OperationIdentifier identifier);

        void operationNotDefined(OperationIdentifier identifier);
    }

    public static ModificationResult noSuchPath(Swagger swagger, OperationIdentifier identifier) {
        return new ModificationResult(swagger, cause -> cause.pathNotDefined(identifier));
    }

    public static ModificationResult noSuchOperation(Swagger swagger, OperationIdentifier identifier) {
        return new ModificationResult(swagger, cause -> cause.operationNotDefined(identifier));
    }

    public static ModificationResult updatedSuccessfully(Swagger swagger) {
        return new ModificationResult(swagger, null);
    }

    private final Consumer<Cause> causeConsumer;
    private final Swagger swagger;

    public ModificationResult(Swagger swagger, Consumer<Cause> causeConsumer) {
        this.swagger = swagger;
        this.causeConsumer = causeConsumer;
    }

    public Swagger swagger() {
        return swagger;
    }

    public boolean SuccessOr(Cause cause) {
        if (null != causeConsumer) {
            causeConsumer.accept(cause);
        }
        return success();
    }

    public boolean success() {
        return null == causeConsumer;
    }
}
