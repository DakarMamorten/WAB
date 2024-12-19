package com.project.wab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@author "Vladyslav Paun"
 */
@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

  @GetMapping
  public String index() {
    return "/index";
  }
}
