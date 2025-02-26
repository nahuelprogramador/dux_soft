package com.dux.software.app_dux.duxsoftware.controller.security_contoller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dux.software.app_dux.duxsoftware.security.dto.AuthRequest;
import com.dux.software.app_dux.duxsoftware.service.security_service.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authenticacíon", description = "Controller de Authenticacíon")
public class AuthController {
	 private AuthenticationManager authenticationManager;
	 
	 private JwtUtil jwtUtil;
	 
	  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
	        this.authenticationManager = authenticationManager;
	        this.jwtUtil = jwtUtil;
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	            );
	            String token = jwtUtil.generateToken(request.getUsername());
	            Map<String, String> response = new HashMap<>();
	            response.put("token", token);
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	        	 return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
	        }
	    }
	    
	    @GetMapping("/users")
	    public ResponseEntity<?> getUsers() {
	        List<String> users = List.of("test", "admin", "user123");

	        return ResponseEntity.ok(users);
	    }
}
