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

package io.spring.initializr.generator.project.code.groovy;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyType;
import io.spring.initializr.generator.project.build.BuildCustomizer;

/**
 * {@link BuildCustomizer} that adds the dependencies required by projects written in
 * Groovy.
 *
 * @author Stephane Nicoll
 */
class GroovyDependenciesConfigurer implements BuildCustomizer<Build> {

	@Override
	public void customize(Build build) {
		build.dependencies().add("groovy", "org.codehaus.groovy", "groovy",
				DependencyType.COMPILE);
	}

}
