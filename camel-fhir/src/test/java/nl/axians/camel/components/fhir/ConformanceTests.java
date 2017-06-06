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
import org.hl7.fhir.dstu3.model.CapabilityStatement;
import org.hl7.fhir.instance.model.api.IBaseConformance;
import org.junit.Test;

import ca.uhn.fhir.context.FhirVersionEnum;
import nl.axians.camel.components.fhir.commands.ConformanceCommand;
import nl.axians.camel.components.fhir.commands.FhirVersion;

/**
 * Implements the tests for the {@link ConformanceCommand}.
 * 
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public class ConformanceTests extends FhirTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "direct:fetchConformanceDSTU1")
    protected ProducerTemplate templateDSTU1;
	
	@Produce(uri = "direct:fetchConformanceDSTU2")
    protected ProducerTemplate templateDSTU2;	

	@Produce(uri = "direct:fetchConformanceDSTU2_1")
    protected ProducerTemplate templateDSTU2_1;	

	@Produce(uri = "direct:fetchConformanceDSTU3")
    protected ProducerTemplate templateDSTU3;	

	@Produce(uri = "direct:fetchConformanceDSTU2_HL7ORG")
    protected ProducerTemplate templateDSTU2_HL7ORG;	
	
	@Produce(uri = "direct:fetchConformanceDefault")
    protected ProducerTemplate templateDefault;	
	
	/**
	 * Test the {@link ConformanceCommand} using the DSTU2 
	 * {@link ca.uhn.fhir.model.dstu2.resource.ConformanceConformance Conformance} class.
	 *  
	 * @throws InterruptedException
	 */
	@Test
	public void testFetchConformanceDSTU1() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(IBaseConformance.class);
		
		// Execute test.
		ConformanceCommand command = new ConformanceCommand();
		templateDSTU1.sendBodyAndHeaders(command, getFhirHeaders(FhirVersion.DSTU1));
		
		// Verify results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		ca.uhn.fhir.model.dstu.resource.Conformance response = exchange.getIn().getBody(
				ca.uhn.fhir.model.dstu.resource.Conformance.class);
		assertEquals("0.0.82-3059", response.getFhirVersion().toString());
		assertEquals(FhirVersionEnum.DSTU1, response.getStructureFhirVersionEnum());
	}
	
	/**
	 * Test the {@link ConformanceCommand} using the DSTU2 
	 * {@link ca.uhn.fhir.model.dstu2.resource.ConformanceConformance Conformance} class.
	 *  
	 * @throws InterruptedException
	 */
	@Test
	public void testFetchConformanceDSTU2() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(IBaseConformance.class);
		
		// Execute test.
		ConformanceCommand command = new ConformanceCommand();
		templateDSTU2.sendBodyAndHeaders(command, getFhirHeaders(FhirVersion.DSTU2));
		
		// Verify results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		ca.uhn.fhir.model.dstu2.resource.Conformance response = exchange.getIn().getBody(
				ca.uhn.fhir.model.dstu2.resource.Conformance.class);
		assertEquals("1.0.2", response.getFhirVersion());
		assertEquals(FhirVersionEnum.DSTU2, response.getStructureFhirVersionEnum());
	}	
	
	/**
	 * Test the {@link ConformanceCommand} using the DSTU2 
	 * {@link ca.uhn.fhir.model.dstu2.resource.ConformanceConformance Conformance} class.
	 *  
	 * @throws InterruptedException
	 */
	@Test
	public void testFetchConformanceDSTU2_HL7ORG() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(IBaseConformance.class);
		
		// Execute test.
		ConformanceCommand command = new ConformanceCommand();
		templateDSTU2_HL7ORG.sendBodyAndHeaders(command, getFhirHeaders(FhirVersion.DSTU2_HL7ORG));
		
		// Verify results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		org.hl7.fhir.instance.model.Conformance response = exchange.getIn().getBody(
				org.hl7.fhir.instance.model.Conformance.class);
		assertEquals("1.0.2", response.getFhirVersion());
		assertEquals(FhirVersionEnum.DSTU2_HL7ORG, response.getStructureFhirVersionEnum());
	}	

	/**
	 * Test the {@link ConformanceCommand} using the DSTU2 
	 * {@link ca.uhn.fhir.model.dstu2.resource.ConformanceConformance Conformance} class.
	 *  
	 * @throws InterruptedException
	 */
	@Test
	public void testFetchConformanceDSTU2_1() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(IBaseConformance.class);
		
		// Execute test.
		ConformanceCommand command = new ConformanceCommand();
		templateDSTU2_1.sendBodyAndHeaders(command, getFhirHeaders(FhirVersion.DSTU2_1));
		
		// Verify results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		org.hl7.fhir.dstu2016may.model.Conformance response = exchange.getIn().getBody(
				org.hl7.fhir.dstu2016may.model.Conformance.class);
		assertEquals("1.0.2", response.getFhirVersion());
		assertEquals(FhirVersionEnum.DSTU2_1, response.getStructureFhirVersionEnum());
	}
	
	/**
	 * Test the {@link ConformanceCommand} using the DSTU2 
	 * {@link ca.uhn.fhir.model.dstu2.resource.ConformanceConformance Conformance} class.
	 *  
	 * @throws InterruptedException
	 */
	@Test
	public void testFetchConformanceDSTU3() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(IBaseConformance.class);
		
		// Execute test.
		ConformanceCommand command = new ConformanceCommand();
		templateDSTU3.sendBodyAndHeaders(command, getFhirHeaders(FhirVersion.DSTU3));
		
		// Verify results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		CapabilityStatement response = exchange.getIn().getBody(
				CapabilityStatement.class);
		assertEquals("3.0.1", response.getFhirVersion());
		assertEquals(FhirVersionEnum.DSTU3, response.getStructureFhirVersionEnum());
	}	

	/**
	 * Test the {@link ConformanceCommand} using the DSTU2 
	 * {@link ca.uhn.fhir.model.dstu2.resource.ConformanceConformance Conformance} class.
	 *  
	 * @throws InterruptedException
	 */
	@Test
	public void testFetchConformanceDefault() throws InterruptedException {
		// Define test results.
		resultEndpoint.reset();
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodyReceived().body(IBaseConformance.class);
		
		// Execute test.
		ConformanceCommand command = new ConformanceCommand();
		templateDefault.sendBodyAndHeaders(command, getFhirHeaders());
		
		// Verify results.
		resultEndpoint.assertIsSatisfied();
		Exchange exchange = resultEndpoint.getExchanges().get(0);
		org.hl7.fhir.instance.model.Conformance response = exchange.getIn().getBody(
				org.hl7.fhir.instance.model.Conformance.class);
		assertEquals("1.0.2", response.getFhirVersion());
		assertEquals(FhirVersionEnum.DSTU2_HL7ORG, response.getStructureFhirVersionEnum());
	}	
	
	/**
	 * Create the test routes.
	 */
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:fetchConformanceDSTU1")
                	.to("fhir:DSTU1?dataFormat=json&prettyPrint=true")
                	.to("mock:result");
                
                from("direct:fetchConformanceDSTU2")
            		.to("fhir:DSTU2?dataFormat=json&prettyPrint=true")
            		.to("mock:result");
                
                from("direct:fetchConformanceDSTU2_1")
            		.to("fhir:DSTU2_1?dataFormat=json&prettyPrint=true")
            		.to("mock:result");

                from("direct:fetchConformanceDSTU3")
            		.to("fhir:DSTU3?dataFormat=json&prettyPrint=true")
            		.to("mock:result");
                
                from("direct:fetchConformanceDSTU2_HL7ORG")
            		.to("fhir:DSTU2_HL7ORG?dataFormat=json&prettyPrint=true")
            		.to("mock:result");

                from("direct:fetchConformanceDefault")
            		.to("fhir?dataFormat=json&prettyPrint=true")
            		.to("mock:result");
            }
        };
    }	
    
}
