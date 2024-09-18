package com.github.signed.swagger.modify;

import static com.github.signed.swagger.modify.ModificationResult.failed;

import com.github.signed.swagger.essentials.Paths;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import java.util.Optional;
import java.util.function.Consumer;

public class SwaggerModify {

    private final Paths paths = new Paths();

    public ModificationResult modify(Swagger swagger, OperationIdentifier identifier, Consumer<Operation> modification) {
        Path path = swagger.getPath(identifier.path);
        if (null == path) {
            return failed(swagger, cause -> cause.pathNotDefined(identifier));
        }
        Optional<Operation> maybeOperation = paths.getOperation(path, identifier.verb);
        if (!maybeOperation.isPresent()) {
            return failed(swagger, cause -> cause.operationNotDefined(identifier));
        }
        modification.accept(maybeOperation.get());
        return ModificationResult.success(swagger);
    }
}
