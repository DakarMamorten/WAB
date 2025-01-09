package com.project.wab.controller;

import com.braintreegateway.BraintreeGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BraintreeController {

    private final BraintreeGateway gateway;

    public BraintreeController(BraintreeGateway gateway) {
        this.gateway = gateway;
    }

    @GetMapping("/client-token")
    public String getClientToken() {
        return gateway.clientToken().generate();
    }
}
