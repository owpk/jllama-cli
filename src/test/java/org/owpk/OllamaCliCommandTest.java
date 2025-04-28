package org.owpk;

import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class OllamaCliCommandTest {

    @Test
    public void testWithCommandLineOption() throws Exception {
        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            // var manager = (PropertiesManager) ctx.getBean(PropertiesManager.class);
            // var requestApi = "TEST_ME_PLEASE";
            // String[] args = new String[] { "-v", "--api-url", requestApi };
            // PicocliRunner.run(JllamaCliCommand.class, ctx, args);
            // var props = manager.getProvider(OllamaProps.class);
            // var apiUrl = props.getProperty(OllamaProps.OLLAMA_API_URL);
            // assertEquals(requestApi, apiUrl);
        }
    }
}
