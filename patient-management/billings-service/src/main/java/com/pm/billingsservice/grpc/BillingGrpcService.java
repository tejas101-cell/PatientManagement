package com.pm.billingsservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                    StreamObserver<BillingResponse> responseObserver){
        log.info("CreateBillingAccount request received {}",billingRequest.toString());

        // internally the business logic is happening eg: save, calculation etc
        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();
        responseObserver.onNext(billingResponse);
        responseObserver.onCompleted();
    }
}
