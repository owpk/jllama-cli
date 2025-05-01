package org.owpk.service.role.def;

import org.owpk.service.role.Role;

class ShellRolePrompt extends Role {
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String OS_NAME = OS.contains("win") ? "windows" : "linux";

    ShellRolePrompt() {
        super("shell",
                String.format("""
                        Return only bash shell commands for the %s operating system.
                        Provide only plain text without Markdown formatting, and only the command without explanation.
                        If there is not enough detail, provide the most logical solution.
                        Make sure you return the correct shell command.
                        If multiple commands are required, try to combine them into one.
                        """, OS_NAME),
                String.format("Creates a shell command for the %s operating system", OS_NAME),
                String.format("A shell command for the %s operating system", OS_NAME));
    }

}