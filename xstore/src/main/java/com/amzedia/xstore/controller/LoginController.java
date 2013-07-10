/**
 * 
 */
package com.amzedia.xstore.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sushant
 *
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		
		return "login";
	}
}
