/*******************************************************************************
 * Copyright 2017 AXIANS
 *  
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
 *******************************************************************************/
package nl.axians.camel.components.fhir;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import ca.uhn.fhir.rest.client.IGenericClient;
import nl.axians.camel.components.fhir.commands.FhirCommand;

/**
 * FHIR Camel Producer to send messages to FHIR compatible endpoints.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class FhirProducer extends DefaultProducer {

	private static final Logger logger = Logger.getLogger(FhirProducer.class.getName());
	
	//*************************************************************************
	// Constructors.
	//*************************************************************************
	
	/**
	 * Specialized constructor.
	 * 
	 * @param endpoint The {@link Endpoint} that created this producer.
	 */
	public FhirProducer(Endpoint endpoint) {
		super(endpoint);
	}
	
	//*************************************************************************
	// Functionality.
	//*************************************************************************	

	/**
	 * Called when a message should be sent to a FHIR compatible endpoint.
	 * 
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String serverBase = exchange.getIn().getHeader(Exchange.HTTP_BASE_URI, String.class);
		logger.log(Level.INFO, "FHIR server base = " + serverBase);
		
		IGenericClient client = getEndpoint().getFhirContext().newRestfulGenericClient(serverBase);
		FhirConfiguration configuration = getEndpoint().getConfiguration();
		
		FhirCommand command = exchange.getIn().getBody(FhirCommand.class);
		command.execute(configuration, client, exchange);
	}
	
	//*************************************************************************
	// Getters and Setters.
	//*************************************************************************
	
	/**
	 * Get the endpoint that created this producer as {@link FhirEndpoint} instance.
	 * 
	 * @return Instance of the {@link FhirEndpoint} that created this producer. 
	 */
	public FhirEndpoint getEndpoint() {
		return (FhirEndpoint)super.getEndpoint();
	}
}
