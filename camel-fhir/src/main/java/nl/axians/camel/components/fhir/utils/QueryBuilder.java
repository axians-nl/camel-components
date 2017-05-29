package nl.axians.camel.components.fhir.utils;

import ca.uhn.fhir.rest.gclient.ICriterion;
import ca.uhn.fhir.rest.gclient.IParam;

public class QueryBuilder {

	public static final IQueryPart where(ICriterion<? extends IParam> criterion) {
		return new QueryPart(criterion);
	}

}
