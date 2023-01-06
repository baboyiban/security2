package com.ezen.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sec")
public class SecEmpController {
	@GetMapping("/index")
	public String loginForm() {
		return "thymeleaf/index";
	}
}
