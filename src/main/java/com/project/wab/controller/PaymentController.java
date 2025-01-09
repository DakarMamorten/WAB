package com.project.wab.controller;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.project.wab.domain.order.PaymentState;
import com.project.wab.service.user.OrderService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final BraintreeGateway gateway;
    private final OrderService orderService;


    @PostMapping
    public String processPayment(String nonce,
                                 Long addressId,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 RedirectAttributes redirectAttributes) {
        var cartToken = WebUtil.checkToken(request);
        var order = orderService.placeOrder(cartToken, addressId);
        redirectAttributes.addAttribute("orderId", order.getId().toString());
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(order.getTotalPrice())
                .currencyIsoCode("PLN")
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(transactionRequest);

        if (result.isSuccess()) {
            orderService.updatePaymentStatus(order.getId(), PaymentState.PAID);
            orderService.updateTransactionId(order.getId(), result.getTarget().getId());
            return "redirect:/checkout/success";
        } else {
            response.addCookie(WebUtil.removeCookie());
            return "redirect:/";
        }
    }
}