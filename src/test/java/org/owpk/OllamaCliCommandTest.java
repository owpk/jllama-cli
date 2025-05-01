package org.owpk;

import org.junit.jupiter.api.Test;
import org.owpk.config.properties.PropertiesManager;
import org.owpk.service.dialog.DialogRepository;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class OllamaCliCommandTest {

    @Test
    public void testWithCommandLineOption() throws Exception {
        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            var dialogRepository = ctx.getBean(DialogRepository.class);
            var manager = ctx.getBean(PropertiesManager.class);

            var chatId = manager.getApplicationProperties().getCurrentChatId();

            var messages = dialogRepository.getMessages(chatId)
                    .collectList()
                    .block();
            System.out.println("Messages: " + messages);
        }
    }
}
