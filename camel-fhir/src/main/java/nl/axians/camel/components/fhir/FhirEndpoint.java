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

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.http.client.HttpClient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.PreferReturnEnum;

/**
 * FHIR Camel Endpoint.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class FhirEndpoint extends DefaultEndpoint {

	private PreferReturnEnum returnType;
	private String remaining;
	
	//*************************************************************************
	// Constructors.
	//*************************************************************************
	
	/**
	 * Specialized constructor.
	 * 
	 * @param fhirComponent The component that created the endpoint.
	 * @param remaining The remaining part of the protocol.
	 */
	public FhirEndpoint(FhirComponent fhirComponent, String remaining) {
		this.remaining = remaining;
	}
	
	//*************************************************************************
	// Functionality.
	//*************************************************************************
	
	/**
	 * Create a new consumer that listens for FHIR requests.
	 * 
	 * @param processor The processor to call after an Exchange has been created for 
	 *                  the FHIR request received.
	 * 
	 * @see org.apache.camel.Endpoint#createConsumer(org.apache.camel.Processor)
	 */
	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		return new FhirConsumer(this, processor);
	}

	/**
	 * Create a new producer that is capable of sending a request to a FHIR compatible
	 * endpoint.
	 * 
	 * @see org.apache.camel.Endpoint#createProducer()
	 */
	@Override
	public Producer createProducer() throws Exception {
		return new FhirProducer(this);
	}

	/**
	 * Get a configured {@link HttpClient} for sending messages to FHIR compatible endpoints.
	 *  
	 * @return A configured {@link HttpClient} for sending messages to FHIR 
	 *         compatible endpoints. 
	 *         <p/>
	 *         <i>Never</i> null.
	 */
	public FhirContext getFhirContext() {
		return FhirContext.forDstu2();
	}
	
	@Override
	protected String createEndpointUri() {
		return "fhir";
	}
	
	//*************************************************************************
	// Getters and Setters.
	//*************************************************************************
	
	/**
	 * New consumers and producers can use the same endpoint instance.
	 * 
	 * @see org.apache.camel.IsSingleton#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	public PreferReturnEnum getReturnType() {
		return returnType;
	}

	public void setReturnType(PreferReturnEnum returnType) {
		this.returnType = returnType;
	}
	
	public String getRemaining() {
		return this.remaining;
	}

}
