package com.hooc.test.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hooc.test.service.TestService;

@Controller
public class TestController {
	@Autowired 
	private TestService testService;
	
	@RequestMapping("index")
	public String index(Model model,HttpServletRequest request,Integer userId){
		String userName = testService.getUserName(userId);
		model.addAttribute("userName", userName);
		return "index";
	}
}
