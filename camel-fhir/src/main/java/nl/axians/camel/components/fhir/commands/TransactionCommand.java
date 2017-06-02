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

import java.util.List;

import org.apache.camel.Exchange;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.ITransactionTyped;
import nl.axians.camel.components.fhir.DataFormatEnum;
import nl.axians.camel.components.fhir.FhirConfiguration;

public class TransactionCommand implements FhirCommand {

	private List<? extends IBaseResource> resources;
	private IBaseBundle bundle;
	
	public TransactionCommand() {
	}
	
	public TransactionCommand(List<? extends IBaseResource> resources, IBaseBundle bundle) {
		super();
		this.resources = resources;
		this.bundle = bundle;
	}

	public List<? extends IBaseResource> getResources() {
		return resources;
	}

	public void setResources(List<? extends IBaseResource> resources) {
		this.resources = resources;
	}

	public IBaseBundle getBundle() {
		return bundle;
	}

	public void setBundle(IBaseBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public void execute(FhirConfiguration configuration, IGenericClient client, Exchange exchange) {
		if (bundle != null) {
			ITransactionTyped<IBaseBundle> request = client.transaction().withBundle(bundle);
			applyConfiguration(request, configuration);
			IBaseBundle response = request.execute();
			exchange.getIn().setBody(response);
		} else {
			ITransactionTyped<List<IBaseResource>> request = client.transaction().withResources(resources);
			applyConfiguration(request, configuration);
			List<IBaseResource> response = request.execute();
			exchange.getIn().setBody(response);
		}
	}
	
	protected void applyConfiguration(ITransactionTyped<?> request, FhirConfiguration configuration) {
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
	}

}
