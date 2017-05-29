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

import java.util.Date;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PreferReturnEnum;
import nl.axians.camel.components.fhir.commands.CreateCommand;
import nl.axians.camel.components.fhir.commands.DeleteCommand;

public class DeleteTests extends FhirTestSupport {

	@EndpointInject(uri = "mock:create")
	protected MockEndpoint createEndpoint;

	@EndpointInject(uri = "mock:delete")
	protected MockEndpoint deleteEndpoint;
	
	@Produce(uri = "direct:delete")
    protected ProducerTemplate templateDelete;	
	
	@Produce(uri = "direct:create")
    protected ProducerTemplate templateCreate;	
	
	@Test
	public void testDeleteResource() throws InterruptedException {
		IBaseResource resource = createResource("Doe" + getDateTime(), "John").getResource();
		DeleteCommand command = DeleteCommand.createDeleteResourceCommand(resource);
		templateDelete.sendBodyAndHeaders(command, getFhirHeaders());
	}
	
	@Test
	public void testDeleteResourceById() throws InterruptedException {
		IIdType resourceId = createResource("Doe" + getDateTime(), "John").getId();
		DeleteCommand command = DeleteCommand.createDeleteResourceByIdCommand(resourceId);
		templateDelete.sendBodyAndHeaders(command, getFhirHeaders());		
	}

	@Test
	public void testDeleteResourceByUrl() throws InterruptedException {
		String dateTime = getDateTime();
		createResource("Doe" + dateTime, "John").getId();
		DeleteCommand command = DeleteCommand.createDeleteResourceByUrlCommand(
				Patient.class, String.format("family=Doe%s&given=John", dateTime));
		templateDelete.sendBodyAndHeaders(command, getFhirHeaders());		
	}
	
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:delete")
                	.to("fhir:delete?dataFormat=json&prettyPrint=true")
                	.to("mock:delete");
                
                from("direct:create")
                	.to("fhir:create?dataFormat=json&prettyPrint=true")
                	.to("mock:create");
            }
        };
    }	
    
    protected MethodOutcome createResource(String family, String given) {
		Patient p = new Patient();
		p.addName()
			.addFamily(family)
			.addGiven(given);
		
		// Create the command and start the route.
		CreateCommand command = new CreateCommand(PreferReturnEnum.REPRESENTATION, p);
		templateCreate.sendBodyAndHeaders(command, getFhirHeaders());
		
		// Verify test results.
		Exchange exchange = createEndpoint.getExchanges().get(0);
		MethodOutcome response = exchange.getIn().getBody(MethodOutcome.class);
		assertEquals(true, response.getCreated());
		assertNotNull(response.getId());

		return response;
    }
    
    protected String getDateTime() {
    	return String.valueOf(new Date().getTime());
    }

}
