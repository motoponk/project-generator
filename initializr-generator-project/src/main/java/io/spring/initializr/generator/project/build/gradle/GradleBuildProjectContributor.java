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
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import io.spring.initializr.generator.ProjectContributor;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.IndentingWriterFactory;

/**
 * {@link ProjectContributor} for the project's {@code build.gradle} file.
 *
 * @author Andy Wilkinson
 */
class GradleBuildProjectContributor implements ProjectContributor {

	private final GradleBuild build;

	private final IndentingWriterFactory indentingWriterFactory;

	private final GradleBuildWriter buildWriter;

	GradleBuildProjectContributor(GradleBuild build,
			IndentingWriterFactory indentingWriterFactory) {
		this.build = build;
		this.indentingWriterFactory = indentingWriterFactory;
		this.buildWriter = new GradleBuildWriter();
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		Path buildGradle = Files.createFile(projectRoot.resolve("build.gradle"));
		writeBuild(Files.newBufferedWriter(buildGradle));
	}

	public void writeBuild(Writer out) throws IOException {
		try (IndentingWriter writer = this.indentingWriterFactory
				.createIndentingWriter("gradle", out)) {
			this.buildWriter.writeTo(writer, this.build);
		}
	}

}
