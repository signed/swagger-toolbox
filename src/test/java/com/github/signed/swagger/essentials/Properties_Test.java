package com.github.signed.swagger.essentials;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.RefProperty;
import org.junit.jupiter.api.Test;

class Properties_Test {

    private final Properties properties = new Properties();

    @Test
    void for_ref_property_just_return_the_DefinitionReference() {
        RefProperty refProperty = new RefProperty("some-model");

        assertThat(properties.definitionReferencesIn(refProperty).get(0).getSimpleRef(), is("some-model"));
    }

    @Test
    void for_array_property_the_element_type_needs_to_be_checked() {
        ArrayProperty arrayProperty = new ArrayProperty();
        arrayProperty.setItems(new RefProperty("element-type"));

        assertThat(properties.definitionReferencesIn(arrayProperty).get(0).getSimpleRef(), is("element-type")) ;
    }

    @Test
    void for_map_property_the_value_elements_need_to_be_checked() {
        assertThrows(UnsupportedOperationException.class, () -> {
            MapProperty arrayProperty = new MapProperty();
            assertThat(properties.definitionReferencesIn(arrayProperty), not(empty()));
        });
    }

}