package nl.axians.camel.components.fhir;

import ca.uhn.fhir.rest.api.PreferReturnEnum;

public class FhirConfiguration {

	private PreferReturnEnum returnType;

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
	
}
