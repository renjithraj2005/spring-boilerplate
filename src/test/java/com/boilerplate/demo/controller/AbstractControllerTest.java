package com.boilerplate.demo.controller;

import com.boilerplate.demo.ApplicationRunner;
import com.boilerplate.demo.helper.SeedDataService;
import com.boilerplate.demo.service.oauth.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * refer to https://docs.spring.io/spring-security/site/docs/4.0.x/reference/htmlsingle/#test-method-withmockuser
 *
 */
@SpringBootTest(classes = {ApplicationRunner.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureTestDatabase
public class AbstractControllerTest {

	protected static final ObjectMapper om = new ObjectMapper();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@LocalServerPort
	protected int randomServerPort;

	@LocalManagementPort
	protected int randomManagementPort;

	// mocked
	@MockBean // disable data initiation and fix in SeedDataService
	protected SeedDataService seedService;

	// wired
	@Autowired
	protected WebApplicationContext context;
	@Autowired
	protected CustomUserDetailsService userDetailsService;

    private String testTempDir;

	protected MockMvc mockMvc;


	public static final String P_listingType = "listingType";
	public static final String P_pageNr = "pageNr";
	public static final String P_pageSize = "pageSize";
	public static final String P_id = "id";
	public static final String P_email = "email";

	public static final String ADMIN = "admin";
	public static final String ADMIN_EMAIL = "admin@example.com";
	public static final String CLIENT_1 = "user";
	public static final String CLIENT_1_EMAIL = "user@example.com";

	@BeforeEach
	public void setup() throws IOException {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.context)
				.apply(springSecurity())
				.build();
        String tempDir = System.getProperty("java.io.tmpdir");
		testTempDir = FilenameUtils.concat(tempDir, UUID.randomUUID().toString());
        File file = new File(testTempDir);
        FileUtils.forceMkdir(file);
    }

    @AfterEach
    public void cleanup() throws IOException {
        if (StringUtils.isNotBlank(testTempDir)) {
            try {
                File file = new File(testTempDir);
                FileUtils.forceDelete(file);
            } catch (Exception e) {
                logger.warn("Cannot delete temp dir: " + testTempDir, e);
            }
        }
    }

	protected UserDetails admin() {
		return userDetailsService.loadUserByUsername(ADMIN_EMAIL);
	}

	protected UserDetails client1() {
		return userDetailsService.loadUserByUsername(CLIENT_1_EMAIL);
	}



    protected String responsePayload(MvcResult res) throws UnsupportedEncodingException {
        return res.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    protected byte[] responseFile(MvcResult res) {
        return res.getResponse().getContentAsByteArray();
    }

	protected Map<String, Object> payloadToMap(String payload) throws com.fasterxml.jackson.core.JsonProcessingException {
		return (Map<String, Object>) om.readValue(payload, LinkedHashMap.class);
	}

	protected List<Map<String, Object>> payloadToListMap(String payload) throws com.fasterxml.jackson.core.JsonProcessingException {
		return (List<Map<String, Object>>) om.readValue(payload, List.class);
	}

	protected Long toLong(Object id) {
		if (id == null) {
			return null;
		}
		return Long.valueOf(id.toString());
	}

	protected Double toDouble(Object id) {
		if (id == null) {
			return null;
		}
		return Double.valueOf(id.toString());
	}

	protected Integer toInt(Object obj) {
		if (obj == null) {
			return null;
		}
		return Integer.valueOf(obj.toString());
	}


} 