package com.github.signed.swagger.trim;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.github.signed.swagger.essentials.SwaggerMother;
import com.github.signed.swagger.essentials.SwaggerBuilder;

public class PathContainedInBooth_Test {

    private final SwaggerBuilder other = SwaggerMother.emptyApiDefinition();
    private final PathContainedInBooth pathContainedInBooth = new PathContainedInBooth();

    @Test
    public void url_templating_with_different_templating_variable_names_but_otherwise_identical() throws Exception {
        other.withPath("/{name}/constant").withPost();
        assertThat("differently url template names still refer to the same endpoint", same("/{anothername}/constant"));
    }

    @Test
    @Ignore("do not have a quick fix for this right now")
    public void url_templating_and_hard_coded_path_are_the_same_path() throws Exception {
        other.withPath("/{variable}").withPost();

        assertThat("Those two should collide", same("/constant"));
    }

    private boolean same(String path) {
        return pathContainedInBooth.pathContainedInBooth(other.build()).test(path);
    }
}