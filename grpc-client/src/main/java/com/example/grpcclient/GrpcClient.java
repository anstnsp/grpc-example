package com.example.grpcclient;

import com.levi.yoon.proto.SampleRequest;
import com.levi.yoon.proto.SampleResponse;
import com.levi.yoon.proto.SampleServiceGrpc;

import org.springframework.stereotype.Service;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrpcClient {


// Stub : Remote procedure call을 하기 위한 가짜 객체라고 생각하면 된다. 마치 자신의 메서드를 호출하는 듯한 느낌이지만, 
//       실제 원격(grpc-server) 메서드를 호출한다. Stub에는 async, blocking, future등 여러 스텁 종류가 존재하지만, 우리는 asyncStub을 이용한다.

// Channel : 결국 Stub도 원격 서버의 메서드를 호출한다. 그렇기 때문에 grpc-server와 통신할 channel을 정의해준다.

  private static final int PORT = 1234; 
  public static final String HOST = "localhost"; 

  //우리가 정의한 service의 스텁 객체를 생성해주고, 매개변수로 Channel을 생성해서 넣어준다. 
  //우리가 띄울 grpc-server의 host와 port를 넣는다.
  private final SampleServiceGrpc.SampleServiceStub asyncStub = SampleServiceGrpc.newStub(
    ManagedChannelBuilder.forAddress(HOST, PORT)
    .usePlaintext()
    .build()
  );

  /**
   *   .proto로 message를 정의하면 java에서 사용할 수 있는 빌더패턴의 메서드, setter&getter등을 만들어준다. 
   *   요청을 위한 SampleRequest 객체를 만들었다. 이제 stub의 sampleCall(우리가 정의한 서비스)를 호출한다. 
   *   그런데 매개변수로 StreamObserver가 들어가는데, 해당 Stub은 asyncStub이기 때문에 callback 객체를 넣어주는 것이다. 
   *   간단히 로그를 찍는 callback 로직을 넣어주었다.
   */


  public String sampleCall() {
    final SampleRequest sampleRequest = SampleRequest.newBuilder()
                                            .setUserId("anstnsp")
                                            .setMessage("grpc request")
                                            .build();

    asyncStub.sampleCall(sampleRequest, new StreamObserver<SampleResponse>(){
    
      @Override
      public void onNext(SampleResponse value) {
        log.info("GrpcClient#sampleCall - {}", value+ "를 grpc 서버가 응답함");
      }
    
      @Override
      public void onError(Throwable t) {
        log.error("GrpcClient#sampleCall - onError", t);
      }
    
      @Override
      public void onCompleted() {
        log.info("GrpcClient#sampleCall - onCompleted");
      }
    });
    return "String"; 
  }
}
