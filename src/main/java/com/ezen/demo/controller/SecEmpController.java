package com.ezen.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sec")
public class SecEmpController {
	@GetMapping({"","/","/loginForm"})
	public String loginForm(String logout) {
		System.out.println("logout:"+logout);
		
		return "thymeleaf/loginForm";
	}
	@GetMapping("/menu")
	public String menuForm() {
		return "thymeleaf/menu";
	}
	
	@GetMapping("/login-error")
	public String fail(Model m) {
		m.addAttribute("loginError", true);
		return "thymeleaf/loginForm";
	}
}
