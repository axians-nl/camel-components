package nl.axians.camel.components.fhir.commands;

import org.hl7.fhir.instance.model.api.IBaseBundle;

public enum BundleTypeEnum {
	HL7,
	DTU2;
	
	public static final Class<? extends IBaseBundle> getBundleTypeClass(BundleTypeEnum bundleType) {
		return bundleType == BundleTypeEnum.HL7 ? org.hl7.fhir.instance.model.Bundle.class : 
			ca.uhn.fhir.model.dstu2.resource.Bundle.class;
	}
}
