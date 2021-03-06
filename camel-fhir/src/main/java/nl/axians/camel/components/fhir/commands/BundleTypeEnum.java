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

import org.hl7.fhir.instance.model.api.IBaseBundle;

/**
 * Enumeration used in some of the commands to identify which bundle class to use.
 *  
 * @author Jacob Hoeflaken
 * @since 1.0
 */
public enum BundleTypeEnum {
	/**
	 * Use the HL7 bundle class {@link org.hl7.fhir.instance.model.Bundle}.
	 */
	HL7,
	
	/**
	 * Use the DTU2 bundle class {@link ca.uhn.fhir.model.dstu2.resource.Bundle}
	 */
	DSTU2;
	
	/**
	 * Get the class for the specified bundle type.
	 * 
	 * @param bundleType The bundle type for which to get the class.
	 * 
	 * @return Class {@link org.hl7.fhir.instance.model.Bundle} if bundleType is {@link #HL7}, 
	 *         class {@link ca.uhn.fhir.model.dstu2.resource.Bundle} otherwise.
	 */
	public static final Class<? extends IBaseBundle> getBundleTypeClass(BundleTypeEnum bundleType) {
		return bundleType == BundleTypeEnum.HL7 ? org.hl7.fhir.instance.model.Bundle.class : 
			ca.uhn.fhir.model.dstu2.resource.Bundle.class;
	}

}
