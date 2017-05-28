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
package nl.axians.camel.components.fhir;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * FHIR Camel component.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class FhirComponent extends DefaultComponent  {

	//*************************************************************************
	// Constructors.
	//*************************************************************************

	/**
	 * Default constructor.
	 */
	public FhirComponent() {
	}

	/**
	 * Specialized constructor.
	 * 
	 * @param camelContext The {@link CamelContext} where to add the component to.
	 */
	public FhirComponent(CamelContext camelContext) {
		super(camelContext);
	}
	
	//*************************************************************************
	// Functionality.
	//*************************************************************************
	
	/**
	 * Create a new FHIR endpoint. Automatically called by Camel when it needs a new
	 * FHIR endpoint.
	 * 
	 * @param protocol The protocol ("fhir").
	 * @param remaining Any remaining part of the protocol.
	 * @param parameters Any parameters specified in the component URI.
	 * 
	 * @return New initialized FHIR endpoint.
	 * 
	 * @see org.apache.camel.impl.DefaultComponent#createEndpoint(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	protected Endpoint createEndpoint(String protocol, String remaining, 
			Map<String, Object> parameters) throws Exception {
		
		// Set the endpoint properties using the specified parameters.
		FhirConfiguration configuration = new FhirConfiguration();
		setProperties(configuration, parameters);
		
		// Create the endpoint.
		FhirEndpoint endpoint = new FhirEndpoint(this, remaining, configuration);
		
		return endpoint;
	}
	
}
