package com.boilerplate.demo.controller;

import com.boilerplate.demo.domain.model.auth.User;
import com.boilerplate.demo.helper.utils.AuthUtils;
import com.boilerplate.demo.model.common.CustomResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ping")
@Api(tags = "ping")
public class PingController {

    @Autowired
    private AuthUtils authUtils;

    @ApiOperation(value = "Ping the backend api", response = CustomResponse.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> ping() {
        return ResponseEntity.ok(CustomResponse.builder().message("pong"));
    }

    @ApiOperation(value = "Ping the backend api with admin user", response = CustomResponse.class)
    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> pingAdmin() throws Exception {
        User loggedInUser = authUtils.getLoggedInUser();
        loggedInUser.getUsername();
        return ResponseEntity.ok(CustomResponse.builder().message("pong admin"));
    }

    @ApiOperation(value = "Ping the backend api with admin user", response = CustomResponse.class)
    @GetMapping(value = "/client")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> pingUser() throws Exception {
        User loggedInUser = authUtils.getLoggedInUser();
        loggedInUser.getUsername();
        return ResponseEntity.ok(CustomResponse.builder().message("pong client"));
    }
}