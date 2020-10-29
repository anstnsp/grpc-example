package com.example.grpcserver;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component
public class GrpcRunner implements ApplicationRunner {

  private static final int PORT = 1234;
  private static final Server SERVER = ServerBuilder.forPort(PORT).addService(new SampleServiceImpl()).build();

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("##grpc 서버 시작 전##");
    SERVER.start(); 
    System.out.println("##grpc 서버 시작 후##");
  }
  
                                      
}