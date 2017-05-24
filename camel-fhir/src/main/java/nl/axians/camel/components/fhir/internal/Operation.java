/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.axians.camel.components.fhir.internal;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PreferReturnEnum;
import ca.uhn.fhir.rest.client.IGenericClient;
import nl.axians.camel.components.fhir.FhirConfiguration;

/**
 * Enumeration of supported FHIR operations. Each enumeration also implements an 
 * {@link #execute(FhirConfiguration, Exchange)} method with the actual logic to execute.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public enum Operation {
	CREATE("create") {
		
		@Override
		public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
			IBaseResource resource = exchange.getIn().getBody(IBaseResource.class);
			MethodOutcome response = client.create()
					.resource(resource)
					.prettyPrint()
					.encodedJson()
					.prefer(configuration.getReturnType())
					.execute();
			
			if (PreferReturnEnum.REPRESENTATION == configuration.getReturnType())
				exchange.getIn().setBody(response.getResource());
			else
				exchange.getIn().setBody(response.getId());
		}
	};

	private final String value;

	/**
	 * Specialized constructor.
	 * 
	 * @param value The name of the FHIR operation this enumeration represents.
	 */
	Operation(String value) {
		this.value = value;
	}

	/**
	 * @return The name of the FHIR operation this enumeration represents.
	 */
	public String value() {
		return value;
	}

	/**
	 * Create the enumeration for the specified FHIR operation name.
	 * 
	 * @param value The FHIR operation name where to create the enumeration for.
	 * 
	 * @return The {@link Operation} instance for the specified FHIR operation name.
	 * 
	 * @throws IllegalArgumentException When specified operation name is unknown.
	 */
	public static Operation fromValue(String value) {
		for (Operation operation : Operation.values()) {
			if (operation.value.equals(value)) {
				return operation;
			}
		}
		
		throw new IllegalArgumentException(value);
	}

	/**
	 * Implements for a specific {@link Operation} the actual logic to execute.
	 * 
	 * @param configuration The {@link FhirConfiguration} to be used.
	 * @param client The FHIR client to use for sending FHIR messages.
	 * @param exchange The {@link Exchange} for which the operation should be executed.
	 */
	public abstract void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange);

}
