package nl.axians.camel.components.fhir;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.resource.Patient;

public class FhirComponentTests extends CamelTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;
	
	@Produce(uri = "direct:start")
    protected ProducerTemplate template;	
	
	@Before
	public void initialize() {
		
	}
	
	@Test
	public void testCreate() throws InterruptedException {
		Map<String, Object> headers = new HashMap<>();
		headers.put(Exchange.HTTP_BASE_URI, "http://fhirtest.uhn.ca/baseDstu2");
		
		Patient p = new Patient();
		p.addName()
			.addFamily("Doe")
			.addGiven("John");
		
		template.sendBodyAndHeaders(p, headers);
	}
	
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start")
                	.to("fhir:create?returnType=representation")
                	.to("mock:result");
            }
        };
    }	

}
