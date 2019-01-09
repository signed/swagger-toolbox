package com.github.signed.swagger.essentials;

import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

class ParameterBuilder_Test {

    @Test
    void produce_header_parameter() {
        ParameterBuilder builder = new ParameterBuilder()
                .withName("Authorization")
                .ofTypeString().inHeader().withDescription("Awsome").required();
        assertThat(builder.build(), Matchers.instanceOf(HeaderParameter.class));
    }

    @Test
    void refparameter_take_their_name_from_the_parameter_definition_that_is_referenced() {
        Parameter build = new ParameterBuilder().referencingParameterDefinition("some-definition").withName("should be ignored by the builder").build();

        assertThat(build.getName(), nullValue());
    }
}