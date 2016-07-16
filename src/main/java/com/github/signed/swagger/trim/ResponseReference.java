package com.github.signed.swagger.trim;

import io.swagger.models.RefResponse;

public class ResponseReference {

    private final RefResponse refResponse;

    public ResponseReference(RefResponse refResponse) {
        this.refResponse = refResponse;
    }

    public String responseIdentifier() {
        return refResponse.getSimpleRef();
    }
}
