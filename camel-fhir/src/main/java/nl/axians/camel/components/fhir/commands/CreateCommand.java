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
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PreferReturnEnum;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.ICreateTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class CreateCommand implements FhirCommand {

	private PreferReturnEnum returnType;
	private IBaseResource resource;
	
	public CreateCommand() {
	}

	public CreateCommand(PreferReturnEnum returnType, IBaseResource resource) {
		super();
		this.returnType = returnType;
		this.resource = resource;
	}

	public PreferReturnEnum getReturnType() {
		return returnType;
	}

	public void setReturnType(PreferReturnEnum returnType) {
		this.returnType = returnType;
	}

	public IBaseResource getResource() {
		return resource;
	}

	public void setResource(IBaseResource resource) {
		this.resource = resource;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		// Initialize the create request by setting the resource and preferred return type (id or resource).
		ICreateTyped request = client.create()
				.resource(resource)
				.prefer(returnType);
		
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
		MethodOutcome response = request.execute();
		exchange.getIn().setBody(response);
	}
	
}
