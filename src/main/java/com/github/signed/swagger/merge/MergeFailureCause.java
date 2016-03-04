package com.github.signed.swagger.merge;

public interface MergeFailureCause {
    void conflict(SwaggerMergeException exception);
}
