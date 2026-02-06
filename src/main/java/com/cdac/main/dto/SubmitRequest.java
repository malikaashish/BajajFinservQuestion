package com.cdac.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitRequest {
	@JsonProperty("finalQuery")
    private String finalQuery;

    public String getFinalQuery() {
		return finalQuery;
	}
	public void setFinalQuery(String finalQuery) {
		this.finalQuery = finalQuery;
	}
	public SubmitRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }
}
