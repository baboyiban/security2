package com.ezen.demo.csrf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("csrf")
public class CsrfTestController 
{
	
	@GetMapping("/")
	@ResponseBody
	public String index()
	{
		return "CSRF TEST";
	}

	@GetMapping("/score")
	public String score(Model model)
	{
		model.addAttribute("score", 75); //DB에서 가져온 점수를 보여준다
		return "thymeleaf/csrf/show_score";
	}

	@PostMapping("/save_score")
	@ResponseBody
	public String saveScore(@RequestParam("score") int score)
	{
		// DB에 점수를 저장한다
		return "점수(" + score + ")를 저장했습니다";
	}
}