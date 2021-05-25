package com.demo.emailapp.service;

import java.util.ArrayList;
import java.util.List;

import com.demo.emailapp.model.MessagePojo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("inbox")
@EnableWebMvc
public class EmailController {

	@Autowired
	EmailService eService;

	@PostMapping(value = "/emails", produces = "application/json; charset=UTF-8")
	@CrossOrigin
	public List<MessagePojo> listEmails(@RequestParam("username") String uName, @RequestParam("password") String uPass){
		List<MessagePojo> mailLst = new ArrayList<MessagePojo>();

		try {
			mailLst = eService.listMail("outlook.office365.com", "imap", "993", uName, uPass);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mailLst;
	}

	@PostMapping(value = "/email/{enumber}", produces = "application/json; charset=UTF-8")
	@CrossOrigin
	public List<MessagePojo> listEmail(@RequestParam("username") String username, @RequestParam("password") String password, @PathVariable String enumber){
		List<MessagePojo> mailLst = new ArrayList<MessagePojo>();
		MessagePojo mailPojo = null;


		try {
			if(StringUtils.isNotBlank(enumber)) {
				 mailPojo = eService.fetchSingleMail("outlook.office365.com", "imap", "993", username, password, Integer.parseInt(enumber));
				mailLst.add(mailPojo);
			}
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return mailLst;
	}

}
