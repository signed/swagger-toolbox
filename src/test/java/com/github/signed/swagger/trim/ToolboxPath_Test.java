package com.github.signed.swagger.trim;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class ToolboxPath_Test {

    @Test
    public void provide_the_source_format() throws Exception {
        assertThat(new ToolboxPath("/").asString(), is("/"));
    }

    @Test
    public void reference_same_resource() throws Exception {
        assertThat("they are referencing the same resource", new ToolboxPath("/constant").referenceSameResource(new ToolboxPath("/constant")));
    }

}