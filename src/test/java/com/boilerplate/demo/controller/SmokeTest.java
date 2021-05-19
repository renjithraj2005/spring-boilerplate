package com.boilerplate.demo.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * refer to https://docs.spring.io/spring-security/site/docs/4.0.x/reference/htmlsingle/#test-method-withmockuser
 *
 */
public class SmokeTest extends AbstractControllerTest {

	@Autowired
	private PingController pingController;

	@Test
	@Order(0)
	public void contextLoads() throws Exception {
		assertThat(pingController).isNotNull();
	}

	@Test
	@Order(1)
	public void test_ping() throws Exception {
		String uri = "/ping";
		mockMvc.perform(get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("pong")));

	}

	@Test
	@Order(2)
	@WithUserDetails(value = ADMIN_EMAIL)
	public void test_pingAdmin() throws Exception {
		String uri = "/ping/admin";
		mockMvc.perform(get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("pong admin")));

	}

	@Test
	@Order(2)
	@WithUserDetails(value = CLIENT_1_EMAIL)
	public void test_pingAdmin_403() throws Exception {
		String uri = "/ping/admin";
		mockMvc.perform(get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isForbidden());

	}



	@Test
	@Order(4)
	@WithUserDetails(value = CLIENT_1_EMAIL)
	public void test_pingClient() throws Exception {
		String uri = "/ping/client";
		mockMvc.perform(get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("pong client")));

	}

	@Test
	@Order(5)
	public void test_404() throws Exception {
		mockMvc.perform(get("/some-random-uri")).andExpect(status().isNotFound());
	}
} 