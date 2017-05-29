package nl.axians.camel.components.fhir.utils;

import ca.uhn.fhir.rest.gclient.ICriterion;
import ca.uhn.fhir.rest.gclient.IParam;

public interface IQueryPart {

	IQueryPart and(ICriterion<? extends IParam> criterion);
	
	String build();
	
}
