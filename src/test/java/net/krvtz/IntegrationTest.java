package net.krvtz;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("config.yml");

    @ClassRule
    public static final DropwizardAppRule<DropGoatConfiguration> RULE = new DropwizardAppRule<>(DropGoatApplication.class, CONFIG_PATH);

    @Test
    public void testTagXssResource() {
        Response response = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/app/tag-xss")
                .queryParam("input", "XSS1")
                .request().get();
        String output = response.readEntity(String.class);
        System.out.println(output);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    }

}
