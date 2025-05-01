package org.owpk.service.role.def;

import org.owpk.service.role.Role;

class ShellRolePrompt extends Role {

    ShellRolePrompt() {
        super("shell", """
                Return only bash shell commands for the {os} operating system.
                Provide only plain text without Markdown formatting, and only the command without explanation.
                If there is not enough detail, provide the most logical solution.
                Make sure you return the correct shell command.
                If multiple commands are required, try to combine them into one.
                """,
                "Creates a shell command for the {os} operating system",
                "A shell command for the {os} operating system");
    }

}