package com.hrms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hrms.ImageUtil;


import com.hrms.model.MenuModule;

import com.hrms.model.UserEntity;

import com.hrms.service.ModuleService;

import com.hrms.service.UserService;


@Controller
public class DashbordController {

	@Autowired
	UserService userService;
	@Autowired
	private ModuleService moduleService;
	

	

	@GetMapping("/getDashBoardData")
	public String getDashBoardData(Model model,HttpSession session) {
		String userCode= (String)session.getAttribute("username");
		session.setAttribute("imgUtil", new ImageUtil());
		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}
		List<MenuModule> modules = moduleService.getAllModulesList(userCode);
		if (modules != null) {
			model.addAttribute("modules", modules);
		}
		
		return "redirect:/";

	}
	
	@GetMapping("/home")
	public String homePage(Model model , HttpSession session,Principal principal) {

	
	
		
	
	
	
		String id = principal.getName();
		System.out.println("principal is : "+ id);
		
		
		UserEntity userRecord = userService.findDataById(id);
		session.setAttribute("uuuuu", userRecord.getUserName());
		session.setAttribute("USER_NAME", userRecord.getUserName());
		
		String profilePic = userRecord.getEmpCode().getProfilePic();
		
		session.setAttribute("profilePic", profilePic);
		
		System.out.println("==user image====>"+userRecord.getEmpCode().getProfilePic());

		//session.setAttribute("User_Profile_Pic", userRecord.getEmpCode().getImageProfile());
		//session.setAttribute("imgUtil", new ImageUtil());
		session.setAttribute("username", principal.getName());
		String userCode = (String) session.getAttribute("username");
		
		
		List<MenuModule> modules = moduleService.getAllModulesList(userCode);
		model.addAttribute("modules", modules);
		
		
		
		

		System.out.println("home dashbaord controller");
		
		return "dashboard";
	}
	
	
	



}
