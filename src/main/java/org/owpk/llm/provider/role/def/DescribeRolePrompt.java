package org.owpk.llm.provider.role.def;

import org.owpk.llm.provider.role.Role;

class DescribeRolePrompt extends Role {
    DescribeRolePrompt() {
        super("describe-shell", """
                Provide a short, one-sentence description of the command.
                Provide only plain text without Markdown formatting.
                Do not provide any warnings or information about your capabilities.
                If you need to store any data, assume it will be stored in the chat.
                """);
    }
}
