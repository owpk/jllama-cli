package org.owpk.config;

import java.io.IOException;
import java.util.Properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class VersionManager {
	private final String version;
	private final String buildTime;
	private final String gitCommit;

	public VersionManager() {
		Properties props = new Properties();
		try {
			props.load(getClass().getClassLoader().getResourceAsStream("version.properties"));
			this.version = props.getProperty("version", "unknown");
			this.buildTime = props.getProperty("build.time", "unknown");
			this.gitCommit = props.getProperty("git.commit", "unknown");
		} catch (IOException e) {
			log.error("Failed to load version properties", e);
			throw new RuntimeException("Failed to load version properties", e);
		}
	}

	public VersionInfo getVersionInfo() {
		return new VersionInfo(version, buildTime, gitCommit);
	}

	public static record VersionInfo(String version, String buildTime, String gitCommit) {

		@Override
		public String toString() {
			return String.format("Version: %s (Build: %s, Commit: %s)",
					version, buildTime, gitCommit);
		}
	}
}
