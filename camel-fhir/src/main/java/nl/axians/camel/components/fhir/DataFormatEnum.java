package nl.axians.camel.components.fhir;

import nl.axians.camel.components.fhir.internal.Operation;

public enum DataFormatEnum {
	JSON("json"),
	XML("xml");
	
	private final String value;
	
	/**
	 * Specialized constructor.
	 * 
	 * @param value The name of the FHIR operation this enumeration represents.
	 */
	DataFormatEnum(String value) {
		this.value = value;
	}

	/**
	 * @return The name of the FHIR operation this enumeration represents.
	 */
	public String value() {
		return value;
	}

	/**
	 * Create the enumeration for the specified FHIR operation name.
	 * 
	 * @param value The FHIR operation name where to create the enumeration for.
	 * 
	 * @return The {@link Operation} instance for the specified FHIR operation name.
	 * 
	 * @throws IllegalArgumentException When specified operation name is unknown.
	 */
	public static DataFormatEnum fromValue(String value) {
		for (DataFormatEnum dataFormat : DataFormatEnum.values()) {
			if (dataFormat.value.equals(value)) {
				return dataFormat;
			}
		}
		
		throw new IllegalArgumentException(value);
	}	
	
}
