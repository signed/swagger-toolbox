package features;


import static com.github.signed.swagger.essentials.SwaggerMatcher.hasPathDefinitionsFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.github.signed.swagger.essentials.SwaggerBuilder;
import com.github.signed.swagger.essentials.SwaggerMatcher;
import com.github.signed.swagger.essentials.SwaggerMother;
import com.github.signed.swagger.merge.MergeResult;
import com.github.signed.swagger.merge.SwaggerMerge;
import com.github.signed.swagger.merge.SwaggerMergeException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.models.Swagger;

public class SwaggerMergeSteps {

    private final SwaggerMerge merger = new SwaggerMerge();
    private final SwaggerBuilder first = SwaggerMother.emptyApiDefinition();
    private final SwaggerBuilder second = SwaggerMother.emptyApiDefinition();
    private Swagger mergedApiDefinition;
    private SwaggerMergeException mergeException;

    @Given("^two distinct swagger api descriptions$")
    public void two_distinct_swagger_api_descriptions() {
        first.withPath("/first").withOption();
        second.withPath("/second").withOption();

        first.defineTag("first tag").withDescription("bsbbd");
        second.defineTag("second tag");
    }

    @Given("^two swagger api definitions with two identical path definitions$")
    public void two_swagger_api_definitions_with_two_identical_path_definitions() {
        first.withPath("/identical").withPost();
        second.withPath("/identical").withPost();
    }

    @Given("^two swagger api definitions with conflicting path definitions$")
    public void two_swagger_api_definitions_with_conflicting_path_definitions() {
        first.withPath("/identical").withPost();
        second.withPath("/identical").withOption();
    }

    @Given("^two swagger api description with distinct model definitions$")
    public void two_swagger_api_description_with_distinct_model_definitions() {
        first.withModelDefinition("something").withTypeObject();
        second.withModelDefinition("anotherthing").withTypeObject();
    }

    @Given("^two swagger api descriptions that contain two identical definitions$")
    public void two_swagger_api_descriptions_that_contain_two_identical_definitions() {
        first.withModelDefinition("identical identifier");
        second.withModelDefinition("identical identifier");
    }

    @Given("^two swagger api descriptions that have conflicting definitions$")
    public void two_swagger_api_descriptions_that_have_conflicting_definitions() {
        first.withModelDefinition("identical identifier").withTypeObject();
        second.withModelDefinition("identical identifier").withTypeString();
    }

    @Given("^two swagger api descriptions that contain two identical tag definitions$")
    public void two_swagger_api_descriptions_that_contain_two_identical_tag_definitions() {
        first.defineTag("identical tag");
        second.defineTag("identical tag");
    }

    @Given("^two swagger api descriptions with conflicting tag definitions$")
    public void two_swagger_api_descriptions_with_conflicting_tag_definitions() {
        first.defineTag("same tag name").withDescription("but different description");
        second.defineTag("same tag name").withDescription("another descriptions");
    }

    @When("^merged$")
    public void merged() {
        Swagger first = this.first.build();
        Swagger second = this.second.build();
        MergeResult mergeResult = merger.merge(first, second);
        if (mergeResult.successOr(exception -> mergeException = exception)) {
            mergedApiDefinition = mergeResult.swagger();
        }
    }

    @Then("^the path elements of booth are in the resulting swagger api description$")
    public void the_path_elements_of_booth_are_in_the_resulting_swagger_api_description() {
        assertThat(mergedApiDefinition, hasPathDefinitionsFor("/first", "/second"));
    }

    @Then("^the tag definitions of booth are in the resulting swagger api description$")
    public void the_tag_definitions_of_booth_are_in_the_resulting_swagger_api_description() {
        assertThat(mergedApiDefinition, SwaggerMatcher.hasTagDefinitionsFor("first tag", "second tag"));
    }

    @Then("^the path definition is contained only once$")
    public void the_path_definition_is_contained_only_once() {
        assertThat(mergedApiDefinition.getPaths().keySet(), hasSize(1));
    }

    @Then("^the model definitions of booth are in the resulting swagger api description$")
    public void the_model_definitions_of_booth_are_in_the_resulting_swagger_api_description() {
        assertThat(mergedApiDefinition, SwaggerMatcher.hasModelDefinitionsFor("something", "anotherthing"));
    }

    @Then("^the definition is contained only once$")
    public void the_definition_is_contained_only_once() {
        assertThat(mergedApiDefinition.getDefinitions().values(), hasSize(1));
    }

    @Then("^the caller is informed about the conflict$")
    public void the_caller_is_informed_about_the_conflict() {
        assertThat("The caller should have been notified", mergeException, not(nullValue()));
    }

    @Then("^there is only a single tag definition$")
    public void there_is_only_a_single_tag_definition() {
        assertThat(mergedApiDefinition.getTags(), hasSize(1));
    }
}
