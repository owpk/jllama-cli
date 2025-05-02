package org.owpk;

import org.owpk.cli.LlmCommand;

import io.micronaut.configuration.picocli.PicocliRunner;

/**
 * The application is built using Micronaut framework and uses Picocli for
 * command line parsing.
 * 
 * The application provides a command line interface for:
 * <ul>
 * <li>Generating text via various LLM providers (Ollama, etc.)</li>
 * <li>Conducting chats with context preservation</li>
 * <li>Managing system roles for specialized tasks</li>
 * </ul>
 *
 *
 * Configuration is stored in:
 * <ul>
 * <li>~/.ollama-cli/ollama-cli.yaml - main config</li>
 * <li>~/.ollama-cli/chats/ - history chats</li>
 * </ul>
 *
 * The main of this application is to provide a simple and extensible interface
 * for working with LLMs, allowing users to easily switch between different
 * providers and manage their configurations.
 * <p>
 * 
 * Main abstractions:
 * 
 * @see org.owpk.service.LlmService Service for working with LLM
 * @see org.owpk.llm.provider.LlmProvider Abstraction of LLM providers
 * 
 *      Middleware helpful services:
 * @see org.owpk.service.dialog.DialogService Service for managing chat sessions
 * @see org.owpk.service.role.RolesManager Service for managing system roles
 *
 *      Application configuration:
 * @see org.owpk.config.properties.ApplicationProperties Application properties
 * @see org.owpk.config.properties.PropertiesManager Properties manager
 * 
 * @version 0.1 - Ollama support, chat, roles, configuration properties
 * @author owpk
 */
public class Jllama {

	public static void main(String[] args) throws Exception {
		PicocliRunner.run(LlmCommand.class, args);
	}
}
