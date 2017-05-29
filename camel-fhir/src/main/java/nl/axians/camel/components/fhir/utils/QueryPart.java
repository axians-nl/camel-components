package nl.axians.camel.components.fhir.utils;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.rest.gclient.ICriterion;
import ca.uhn.fhir.rest.gclient.IParam;

public class QueryPart implements IQueryPart {

	private List<ICriterion<? extends IParam>> criterions = new ArrayList<>();
	
	public QueryPart() {
	}
	
	public QueryPart(ICriterion<? extends IParam> criterion) {
		criterions.add(criterion);
	}
	
	@Override
	public String build() {
		return "";
	}

	@Override
	public IQueryPart and(ICriterion<? extends IParam> criterion) {
		criterions.add(criterion);		
		return this;
	}

}
