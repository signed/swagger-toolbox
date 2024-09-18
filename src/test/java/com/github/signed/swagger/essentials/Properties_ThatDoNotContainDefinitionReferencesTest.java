package com.github.signed.swagger.essentials;

import static org.hamcrest.MatcherAssert.assertThat;

import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.EmailProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.PasswordProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;
import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class Properties_ThatDoNotContainDefinitionReferencesTest {

    static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {new PasswordProperty()}
            , {new StringProperty()}
            , {new EmailProperty()}
            , {new DateTimeProperty()}
            , {new IntegerProperty()}
            , {new DateProperty()}
            , {new DateProperty()}

        });
    }

    private final Properties properties = new Properties();

    @ParameterizedTest
    @MethodSource("data")
    void test(Property property) {
        assertThat(properties.definitionReferencesIn(property), Matchers.empty());
    }

}