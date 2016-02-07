package com.github.signed.swagger.modify;

public interface ModificationFailureCause {
    void pathNotDefined(OperationIdentifier identifier);

    void operationNotDefined(OperationIdentifier identifier);
}
