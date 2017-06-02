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
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.model.api.TagList;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.IGetTags;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class TagsCommand implements FhirCommand {

	private TagTypeEnum type;
	private Class<? extends IBaseResource> resourceClass;
	private String resourceId;
	private String version;
	
	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		IGetTags request = null;
		switch(type) {
		case ALL:
			request = client.getTags();
			break;
		case RESOURCE_TYPE:
			request = client.getTags().forResource(resourceClass);
			break;
		case RESOURCE:
			request = client.getTags().forResource(resourceClass, resourceId);
			break;
		case RESOURCE_VERSION:
			request = client.getTags().forResource(resourceClass, resourceId, version);
		}
		
		if (request != null) {
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
			
			TagList response = request.execute();
			exchange.getIn().setBody(response);
		}
		
	}

}
