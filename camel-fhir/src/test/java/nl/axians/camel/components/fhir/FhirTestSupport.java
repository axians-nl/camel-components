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

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelTestSupport;

import nl.axians.camel.components.fhir.commands.FhirVersion;

public class FhirTestSupport extends CamelTestSupport {
	
	private static final String FHIRTEST_BASE_DSTU1 = "http://fhirtest.uhn.ca/baseDstu1";
	private static final String FHIRTEST_BASE_DSTU2 = "http://fhirtest.uhn.ca/baseDstu2";
	private static final String FHIRTEST_BASE_DSTU2_HL7ORG = "http://fhirtest.uhn.ca/baseDstu2";
	private static final String FHIRTEST_BASE_DSTU2_1 = "http://fhirtest.uhn.ca/baseDstu2";
	private static final String FHIRTEST_BASE_DSTU3 = "http://fhirtest.uhn.ca/baseDstu3";
	

	protected Map<String, Object> getFhirHeaders(FhirVersion version) {
		Map<String, Object> headers = new HashMap<>();
		
		switch(version) {
		case DSTU1:
			headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU1);
			break;
		case DSTU2:
			headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU2);
			break;
		case DSTU2_1:
			headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU2_1);
			break;
		case DSTU2_HL7ORG:
			headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU2_HL7ORG);
			break;
		case DSTU3:
			headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU3);
			break;
		default:
			headers.put(Exchange.HTTP_BASE_URI, FHIRTEST_BASE_DSTU2);
		}
		
		return headers;
	}
	
	protected Map<String, Object> getFhirHeaders() {
		return getFhirHeaders(FhirVersion.DSTU2_HL7ORG);
	}
	
}
