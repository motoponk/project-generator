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

package io.spring.start.extension.actuator;

import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;

import org.springframework.context.annotation.Bean;

/**
 * Configuration for generation of projects that depend on actuator.
 *
 * @author Madhura Bhave
 */
@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency(id = "actuator")
public class ActuatorProjectGenerationConfiguration {

	@Bean
	public MicrometerHelpDocumentCustomizer micrometerHelpDocumentCustomizer() {
		return new MicrometerHelpDocumentCustomizer();
	}

}
