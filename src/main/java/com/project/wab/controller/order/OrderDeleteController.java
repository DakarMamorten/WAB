//package com.project.wab.controller.order;
//
//import com.project.wab.service.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * @author "Vladyslav Paun"
// */
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/order")
//public class OrderDeleteController {
//    private final OrderService orderService;
//
//    @PostMapping("/delete")
//    public String delete(final Long id) {
//        orderService.deleteOrder(id);
//        return "redirect:/order/list";
//    }
//}
//
//
