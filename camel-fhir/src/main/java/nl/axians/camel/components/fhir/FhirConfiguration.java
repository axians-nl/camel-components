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

import ca.uhn.fhir.rest.api.PreferReturnEnum;
import nl.axians.camel.components.fhir.commands.FhirVersion;

public class FhirConfiguration {

	private PreferReturnEnum returnType = PreferReturnEnum.MINIMAL;
	private Boolean prettyPrint = true;
	private DataFormatEnum dataFormat = DataFormatEnum.JSON;
	private String type;
	private FhirVersion version;

	//*************************************************************************
	// Constructors.
	//*************************************************************************
	
	/**
	 * Default constructor.
	 */
	public FhirConfiguration() {
	}

	//*************************************************************************
	// Getters and Setters.
	//*************************************************************************
	
	/**
	 * @return The preferred return type. {@link PreferReturnEnum#MINIMAL} for just
	 *         the id of the resource or {@link PreferReturnEnum#REPRESENTATION} 
	 *         for the complete resource.
	 */
	public PreferReturnEnum getReturnType() {
		return returnType;
	}

	/**
	 * Set the preferred return type.
	 * 
	 * @param returnType {@link PreferReturnEnum#MINIMAL} for just the id of the resource 
	 *                   or {@link PreferReturnEnum#REPRESENTATION} for the complete resource.
	 */
	public void setReturnType(PreferReturnEnum returnType) {
		this.returnType = returnType;
	}

	public Boolean getPrettyPrint() {
		return prettyPrint;
	}

	public void setPrettyPrint(Boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}

	public DataFormatEnum getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(DataFormatEnum dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FhirVersion getVersion() {
		return version;
	}

	public void setVersion(FhirVersion version) {
		this.version = version;
	}	

}
