package com.github.signed.swagger.essentials;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import java.util.Optional;

public class Paths {

    public Optional<Operation> getOperation(Path path, HttpMethod verb) {
        switch (verb) {
            case DELETE:
                return Optional.ofNullable(path.getDelete());
            case GET:
                return Optional.ofNullable(path.getGet());
            case HEAD:
                return Optional.ofNullable(path.getHead());
            case OPTIONS:
                return Optional.ofNullable(path.getOptions());
            case PATCH:
                return Optional.ofNullable(path.getPatch());
            case POST:
                return Optional.ofNullable(path.getPost());
            case PUT:
                return Optional.ofNullable(path.getPut());
            default:
                return Optional.empty();
        }
    }
}
