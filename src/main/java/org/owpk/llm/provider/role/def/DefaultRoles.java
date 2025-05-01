package org.owpk.llm.provider.role.def;

import java.util.List;

import org.owpk.llm.provider.role.Role;

import lombok.Getter;

public final class DefaultRoles {
	@Getter(lazy = true)
	private static final Role DEFAULT = Role.builder().id("default").prompt("You are helpfull assistant.").build();

	@Getter(lazy = true)
	private static final Role SHELL = new ShellRolePrompt();

	@Getter(lazy = true)
	private static final Role DESCRIBE = new DescribeRolePrompt();

	@Getter(lazy = true)
	private static final Role GIT_COMMIT = new GitCommitRolePrompt();

	public static List<Role> getDefaultRoles() {
		return List.of(getSHELL(), getDESCRIBE(), getGIT_COMMIT());
	}
}
