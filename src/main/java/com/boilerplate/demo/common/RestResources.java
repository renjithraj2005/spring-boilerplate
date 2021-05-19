package com.boilerplate.demo.common;

public interface RestResources {

	/*
	* API Endpoints for User related operations
	* */
	String USER_ROOT = "/user";
	String USER_POST_REGISTER = "/register";
	String USER_POST_LOGIN = "/oauth/token";
	String USER_GET_ALL = "/all";
	String USER_GET_ME = "/me";
	String USER_GET_DETAILS = "/profile";
	String USER_DELETE_LOGOUT = "/logout";
	String USER_DELETE_BY_ID = "/{id}/";

	/*
	 * API Endpoints for property related operations
	 * */

	String PROPERTY_ROOT = "/property";
	String PROPERTY_POST_SAVE = "/create";
	String PROPERTY_PUT_UPDATE = "/update";
	String PROPERTY_PUT_APPROVE = "/approve/{id}";
	String PROPERTY_GET_SEARCH = "/search";
}
