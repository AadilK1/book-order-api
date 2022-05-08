package com.bookapi.demo.exception;

public class ApiError {

    private int statusCode;
    
    private String error;


    public ApiError(int statusCode, String error) {
        this.statusCode = statusCode;
        this.error= error;
    }


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}
    
    
}
