package org.owpk.config.properties.core;

import java.util.List;

import org.owpk.config.properties.AbsPropertiesProvider;
import org.owpk.config.properties.PropertyDef;
import org.owpk.storage.RootedStorage;
import org.owpk.storage.Storage;
import org.owpk.storage.impl.LocalFileStorage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AppBaseProps extends AbsPropertiesProvider {
    public static final PropertyDef DEF_GIGACHAT_CLI_HOME = new PropertyDef("ollama_cli_home",
            System.getProperty("user.home") + "/.ollama-cli/");

    public static final PropertyDef DEF_CHATS_HISTORY_MODE = new PropertyDef("chat_history_mode", "local");
    public static final PropertyDef DEF_CHATS_HISTORY_HOME = new PropertyDef("chat_history_home", "/chats/");
    public static final PropertyDef DEF_CURRENT_CHAT_NAME = new PropertyDef("chat_current_chat", "");

    public static final PropertyDef DEF_ROLES_YML = new PropertyDef("roles_yml", "roles.yml");
    public static final PropertyDef DEF_CURRENT_MODEL = new PropertyDef("current_model", "llama3.2");

    public AppBaseProps(String propName, Storage storage) {
        super(propName, storage);
    }

    public AppBaseProps() {
        super("main.properties",
                new RootedStorage(DEF_GIGACHAT_CLI_HOME.value(), new LocalFileStorage()));
    }

    @Override
    public void bootstrapValidation() {
        getDefaultProperties()
                .forEach(it -> this.acceptEmpty(it, empty -> {
                    log.warn(propertiesName + ": " + empty.key() + " is empty.");
                    setProperty(empty, findDefaultValueByKey(it));
                }));
    }

    @Override
    protected List<PropertyDef> getDefaultProperties() {
        return List.of(
                DEF_CHATS_HISTORY_MODE,
                DEF_CHATS_HISTORY_HOME,
                DEF_CURRENT_CHAT_NAME,

                DEF_ROLES_YML,

                DEF_CURRENT_MODEL,
                DEF_GIGACHAT_CLI_HOME);
    }
}