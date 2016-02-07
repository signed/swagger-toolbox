package com.github.signed.swagger.modify;

import java.util.Optional;
import java.util.function.Consumer;

import com.github.signed.swagger.essentials.Paths;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerModify {

    private final Paths paths = new Paths();

    public ModificationResult modify(Swagger swagger, OperationIdentifier identifier, Consumer<Operation> modification) {
        Path path = swagger.getPath(identifier.path);
        if (null == path) {
            return ModificationResult.noSuchPath(swagger, identifier);
        }
        Optional<Operation> maybeOperation = paths.getOperation(path, identifier.verb);
        if (!maybeOperation.isPresent()) {
            return ModificationResult.noSuchOperation(swagger, identifier);
        }
        modification.accept(maybeOperation.get());
        return ModificationResult.updatedSuccessfully(swagger);
    }
}
