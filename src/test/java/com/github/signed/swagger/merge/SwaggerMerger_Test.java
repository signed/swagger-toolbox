package com.github.signed.swagger.merge;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerBuilder;
import com.github.signed.swagger.essentials.SwaggerMother;

public class SwaggerMerger_Test {

    private final SwaggerBuilder first = SwaggerMother.emptyApiDefinition();
    private final SwaggerBuilder second = SwaggerMother.emptyApiDefinition();
    private final SwaggerMerger swaggerMerger = new SwaggerMerger();

    @Before
    public void setUp() throws Exception {
        first.withPath("/{variable}/constant").withPost();
        second.withPath("/{argument}/constant").withOption();
    }

    @Test
    public void same_path_but_different_operations_results_in_merge_conflict() throws Exception {
        assertThat("merging the same path with different operations should not be successful", !mergeResult().success());
    }

//    @Test
//    public void report_conflicting_path() throws Exception {
//        assertThat(mergeResult().successOr(), is("/{variable|argument}/constant"));
//    }

    private MergeResult mergeResult() {
        return swaggerMerger.merge(first.build(), second.build());
    }

}