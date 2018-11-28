/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.project.build.gradle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.spring.initializr.generator.ProjectContributor;
import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.io.IndentingWriter;

/**
 * {@link ProjectContributor} for the project's {@code settings.gradle} file.
 *
 * @author Andy Wilkinson
 */
class SettingsGradleProjectContributor implements ProjectContributor {

	private final GradleBuild build;

	SettingsGradleProjectContributor(GradleBuild build) {
		this.build = build;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		Path settingsGradle = Files.createFile(projectRoot.resolve("settings.gradle"));
		try (IndentingWriter writer = new IndentingWriter(
				Files.newBufferedWriter(settingsGradle))) {
			writePluginManagement(writer);
			writer.println("rootProject.name = '" + this.build.getName() + "'");
		}
	}

	private void writePluginManagement(IndentingWriter writer) {
		writer.println("pluginManagement {");
		writer.indented(() -> {
			writeRepositories(writer);
			writeResolutionStrategyIfNecessary(writer);
		});
		writer.println("}");
	}

	private void writeRepositories(IndentingWriter writer) {
		writer.println("repositories {");
		writer.indented(() -> {
			this.build.getMavenRepositories().stream().map(this::repositoryAsString)
					.forEach(writer::println);
			writer.println("gradlePluginPortal()");
		});
		writer.println("}");
	}

	private void writeResolutionStrategyIfNecessary(IndentingWriter writer) {
		if (!this.build.getMavenRepositories().stream()
				.filter((repository) -> !MavenRepository.MAVEN_CENTRAL.equals(repository))
				.findFirst().isPresent()) {
			return;
		}
		writer.println("resolutionStrategy {");
		writer.indented(() -> {
			writer.println("eachPlugin {");
			writer.indented(() -> {
				writer.println("if(requested.id.id == 'org.springframework.boot') {");
				writer.indented(() -> {
					writer.println(
							"useModule(\"org.springframework.boot:spring-boot-gradle-plugin:${requested.version}\")");
				});
				writer.println("}");
			});
			writer.println("}");
		});
		writer.println("}");
	}

	private String repositoryAsString(MavenRepository repository) {
		if (MavenRepository.MAVEN_CENTRAL.equals(repository)) {
			return "mavenCentral()";
		}
		return "maven { url '" + repository.getUrl() + "' }";
	}

}