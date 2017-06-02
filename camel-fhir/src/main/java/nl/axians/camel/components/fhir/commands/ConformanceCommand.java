/**
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
 */
package nl.axians.camel.components.fhir.commands;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseConformance;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IFetchConformanceTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class ConformanceCommand implements FhirCommand {

	private Class<? extends IBaseConformance> type;

	public static final ConformanceCommand createHL7ConformanceCommand() {
		return new ConformanceCommand(org.hl7.fhir.instance.model.Conformance.class);
	}

	public static final ConformanceCommand createDTU2ConformanceCommand() {
		return new ConformanceCommand(ca.uhn.fhir.model.dstu2.resource.Conformance.class);
	}
	
	public ConformanceCommand() {
	}
	
	public ConformanceCommand(Class<? extends IBaseConformance> type) {
		super();
		this.type = type;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IFetchConformanceTyped<?> request = client.fetchConformance().ofType(type);

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

	public Class<? extends IBaseConformance> getType() {
		return type;
	}

	public void setType(Class<? extends IBaseConformance> type) {
		this.type = type;
	}
	
}
