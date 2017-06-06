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

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import nl.axians.camel.components.fhir.commands.BundleTypeEnum;
import nl.axians.camel.components.fhir.commands.SearchCommand;

public class SearchTests extends FhirTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;
	
	@Produce(uri = "direct:start")
    protected ProducerTemplate template;	

	@Test
	public void testSearchByUrl() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(Bundle.class);

		// Create the command and start the route.
		SearchCommand command = new SearchCommand(Patient.class, BundleTypeEnum.DSTU2, "family=Doe&given=John");
		template.sendBodyAndHeaders(command, getFhirHeaders());
		
		// Verify test results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		Bundle response = exchange.getIn().getBody(Bundle.class);
		assertNotNull(response);
		assertNotNull(response.getEntry());
		assertTrue(response.getEntry().size() > 0);
	}

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                	.to("fhir:search?dataFormat=json&prettyPrint=true")
                	.to("mock:result");
            }
        };
    }	

}
