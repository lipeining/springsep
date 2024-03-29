package com.example.springbootweb.controller;

import java.util.Locale;


import javax.servlet.http.HttpSession;

import com.example.springbootweb.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/hello")
	public String hello(Locale locale, Model model) {
		return "Hello World";
	}

}