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

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PreferReturnEnum;
import nl.axians.camel.components.fhir.commands.CreateCommand;

public class CreateTests extends FhirTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;
	
	@Produce(uri = "direct:start")
    protected ProducerTemplate template;	

	@Test
	public void testCreateWithReturnTypeMinimal() throws InterruptedException {
		// Create the resource to create.
		Patient p = new Patient();
		p.addName()
			.addFamily("Doe")
			.addGiven("John");
		
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(MethodOutcome.class);

		// Create the command and start the route.
		CreateCommand command = new CreateCommand(PreferReturnEnum.MINIMAL, p);
		template.sendBodyAndHeaders(command, getFhirHeaders());
		
		// Verify test results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		MethodOutcome response = exchange.getIn().getBody(MethodOutcome.class);
		assertEquals(true, response.getCreated());
		assertNotNull(response.getId());
		assertNull(response.getResource());
	}

	@Test
	public void testCreateWithReturnTypeRepresentation() throws InterruptedException {
		// Create the resource to create.
		Patient p = new Patient();
		p.addName()
			.addFamily("Doe")
			.addGiven("John");
		
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(MethodOutcome.class);

		// Create the command and start the route.
		CreateCommand command = new CreateCommand(PreferReturnEnum.REPRESENTATION, p);
		template.sendBodyAndHeaders(command, getFhirHeaders());
		
		// Verify test results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		MethodOutcome response = exchange.getIn().getBody(MethodOutcome.class);
		assertEquals(true, response.getCreated());
		assertNotNull(response.getId());
		assertNotNull(response.getResource());
		
		// Verify returned patient resource.
		assertEquals(true, response.getResource() instanceof Patient);		
		Patient patient = (Patient) response.getResource();
		assertEquals(new StringDt("Doe"), patient.getNameFirstRep().getFamily().get(0));
		assertEquals(new StringDt("John"), patient.getNameFirstRep().getGiven().get(0));
	}
	
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                	.to("fhir:create?dataFormat=json&prettyPrint=true")
                	.to("mock:result");
            }
        };
    }	

}
