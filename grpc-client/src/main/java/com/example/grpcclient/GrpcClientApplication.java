package com.example.grpcclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SpringBootApplication
public class GrpcClientApplication {

	private final GrpcClient grpcClient; 

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

	@GetMapping("/")
	public String test() {
		return grpcClient.sampleCall();
	}

}
