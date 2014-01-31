package com.microforum.prototipo.web.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class DefaultController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/*")
	public String loginFail(Model uiModel, Locale locale) {
		logger.info("Default page required");
		return "default";
	}

}
