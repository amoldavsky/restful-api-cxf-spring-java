package com.projectx.web.api.data;

import java.util.Collection;

/**
 * POJO to hold various search results
 *
 * @author Assaf Moldavsky
 */
public class SearchDTO<T> {

	int count;
	Collection<T> results;

	public SearchDTO() {}

	public SearchDTO( Collection<T> results ) {
		this.count = results.size();
		this.setResults( results );
	}

	public int getCount() {
		return count;
	}

	public void setCount( int count ) {
		this.count = count;
	}

	public Collection<T> getResults() {
		return results;
	}

	public void setResults( Collection<T> results ) {
		this.results = results;
	}
}
