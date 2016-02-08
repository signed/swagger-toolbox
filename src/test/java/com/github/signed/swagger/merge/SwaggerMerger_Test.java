package com.github.signed.swagger.merge;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerMother;
import com.github.signed.swagger.essentials.SwaggerBuilder;

public class SwaggerMerger_Test {

    private final SwaggerBuilder first = SwaggerMother.emptyApiDefinition();
    private final SwaggerBuilder second = SwaggerMother.emptyApiDefinition();
    private final SwaggerMerger swaggerMerger = new SwaggerMerger();

    @Test
    public void same_path_but_different_operations() throws Exception {
        first.withPath("/{variable}/constant").withPost();
        second.withPath("/{argument}/constant").withOption();
        MergeResult result = swaggerMerger.merge(first.build(), second.build());
        assertThat("merging the same path with different operations should not be successful", !result.success());
    }

}