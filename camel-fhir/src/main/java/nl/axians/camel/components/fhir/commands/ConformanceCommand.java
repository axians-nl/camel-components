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
package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.hl7.fhir.dstu3.model.CapabilityStatement;
import org.hl7.fhir.instance.model.api.IBaseConformance;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IFetchConformanceTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

/**
 * Implements the command to execute the conformance operation.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class ConformanceCommand implements FhirCommand {

	/**
	 * Default constructor.
	 */
	public ConformanceCommand() {
	}
	
	/**
	 * Execute the command.
	 * 
	 * @param configuration The FHIR configuration to use.
	 * @param client The FHIR client to use for actually executing the command.
	 * @param exchange The Camel exchange for which the command is executed.
	 */
	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IFetchConformanceTyped<?> request;
		switch(configuration.getVersion()) {
		case DSTU1:
			request = client.fetchConformance().ofType(ca.uhn.fhir.model.dstu.resource.Conformance.class);
			break;
		case DSTU2:
			request = client.fetchConformance().ofType(ca.uhn.fhir.model.dstu2.resource.Conformance.class);
			break;
		case DSTU2_1:
			request = client.fetchConformance().ofType(org.hl7.fhir.dstu2016may.model.Conformance.class);
			break;		
		case DSTU3:
			request = client.fetchConformance().ofType(CapabilityStatement.class);
			break;
		case DSTU2_HL7ORG:
		default:
			request = client.fetchConformance().ofType(org.hl7.fhir.instance.model.Conformance.class);
		}

		// Check if we need to apply pretty printing of the request.
		if (configuration.getPrettyPrint()) {
			request.prettyPrint();
		}
		
		// Check which output format to use: XML or JSON.
		if (configuration.getDataFormat() == DataFormatEnum.XML) {
			request.encodedXml();
		} else {
			request.encodedJson();
		}

		// Execute the request and put response on the exchange body.
		IBaseConformance response = request.execute();
		exchange.getIn().setBody(response);
	}
	
}
