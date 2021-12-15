package com.hrms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hrms.ImageUtil;
import com.hrms.dao.CaptchaUtil;

import com.hrms.model.Employee;

import com.hrms.model.Login;
import com.hrms.model.MenuModule;

import com.hrms.model.UserEntity;
import com.hrms.service.ModuleService;
import com.hrms.service.UserService;
import com.hrms.service.EmployeeService;

import cn.apiclub.captcha.Captcha;
@Controller
public class UserController {
	@Autowired
	UserService userService;
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	EmployeeService employeeService;

	@Autowired
	PasswordEncoder passwordEn;
	@PostMapping("/loginUser")
	public String loginUser(@ModelAttribute("user") Login login, Model model, RedirectAttributes redirectAttributes,
			HttpSession session) {


		boolean isUserExist = userService.checkUserExists(login);

		if (isUserExist && login.getCaptcha().equals(login.getHiddenCaptcha())) {

			String id = login.getUserCode();
			UserEntity userRecord = userService.findDataById(id);
			session.setAttribute("uuuuu", userRecord.getUserName());
			session.setAttribute("USER_NAME", userRecord.getUserName());

			String profilePic = userRecord.getEmpCode().getProfilePic();

			session.setAttribute("profilePic", profilePic);

			System.out.println("==user image====>" + userRecord.getEmpCode().getProfilePic());

	
			session.setAttribute("username", login.getUserCode());
			String userCode = (String) session.getAttribute("username");

			List<MenuModule> modules = moduleService.getAllModulesList(userCode);
			model.addAttribute("modules", modules);

			logger.debug("loginUser()");
			logger.info("loginUser()");
			return "dashboard";
		}

		if (login.getCaptcha() != null && login.getCaptcha().isEmpty()) {
			redirectAttributes.addFlashAttribute("wrongcode", "Fill Captcha First !  ");
			return "redirect:./";
		}

		else if (!login.getCaptcha().equals(login.getHiddenCaptcha())) {
			redirectAttributes.addFlashAttribute("wrongcode", "Captcha not valid !  ");
			return "redirect:./";
		} else {
			redirectAttributes.addFlashAttribute("message", "Incorrect Username/ Password Please Try Again.!  ");
			return "redirect:./";
		}
	}
	
	
	@GetMapping("/dashboard")
	public String dashBoardMethod(Model model, HttpSession session) {

		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}
		String userCode = (String) session.getAttribute("username");
		List<MenuModule> modules = moduleService.getAllModulesList(userCode);
		if (modules != null) {
			model.addAttribute("modules", modules);
		}

		return "dashboard";

	}
	
	
	
	
	@GetMapping("/userMaster")
	public String userMaster(Model model, HttpSession session) {
		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}
		session.setAttribute("imgUtil", new ImageUtil());
		List<Employee> lrt = employeeService.getAllEmployees();
		model.addAttribute("listEmployee", lrt);
		List<UserEntity> listUsers = userService.getAllUsers();
		model.addAttribute("users", listUsers);
		String userCode = (String) session.getAttribute("username");
		List<MenuModule> modules = moduleService.getAllModulesList(userCode);
		if (modules != null) {
			model.addAttribute("modules", modules);
		}
		session.setAttribute("username", session.getAttribute("username"));
		return "list-users";
	}
	


	/**
	 * 
	 * @param Method             to be Save userEntity
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") UserEntity userEntity, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		String pass = userEntity.getUserPass();
		
       

		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}

		boolean isUserExist = userService.checkUserExistsOrNot(userEntity);
		
		if (isUserExist) {
			redirectAttributes.addFlashAttribute("message", "User Code Already exists !  ");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/userMaster";
		} else {
			
			userEntity.setUserPass(passwordEn.encode(pass));
			userEntity.setInsBy((String) session.getAttribute("USER_NAME"));
			userService.addUser(userEntity);
			redirectAttributes.addFlashAttribute("message", "Record  has been saved successfully!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			session.setAttribute("username", session.getAttribute("username"));
			return "redirect:/userMaster";

		}

	}

	@GetMapping(value = { "/editUser/{id}" })
	public String editUser(@PathVariable("id") String id, Model model, HttpSession session) {
		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}
		List<Employee> lrt = employeeService.getAllEmployees();
		model.addAttribute("listEmployee", lrt);
		UserEntity userEdit = userService.findUserById(id);
		String pass = passwordEn.encode(userEdit.getUserPass());
		userEdit.setUserPass(pass);
		model.addAttribute("userEdit", userEdit);
		session.setAttribute("username", session.getAttribute("username"));
		return "/editUser";
	}
	
	
	@GetMapping(value = { "/editUser/{id}/password" })
	public String editUserPasword(@PathVariable("id") String id, Model model, HttpSession session) {
		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}
		List<Employee> lrt = employeeService.getAllEmployees();
		model.addAttribute("listEmployee", lrt);
		UserEntity userEdit = userService.findUserById(id);
		String pass = passwordEn.encode(userEdit.getUserPass());
		userEdit.setUserPass(pass);
		model.addAttribute("userEdit", userEdit);
		session.setAttribute("username", session.getAttribute("username"));
		return "/editUserPassword";
	}

	@PostMapping("/upadteUser")
	public String updateUser(@ModelAttribute("userUpdate") UserEntity u, Model model, HttpSession session,RedirectAttributes redirectAttributes) {
		if (session.getAttribute("username") == null) {
			return "redirect:" + "./";
		}
		String pass = passwordEn.encode(u.getUserPass());
		
		String userCode = (String) session.getAttribute("username");
		System.out.println(":::::::::::::::::::::::userCode::::::::::::::::::::::::::::" + userCode);
		u.setUserPass(pass);
		u.setUpdBy((String) session.getAttribute("USER_NAME"));

		if (userCode.equals(u.getUserCode())) {
			// userService.updateUser(u);
			System.out.println("if block");
			return "redirect:/logout";
		} else {
			
			userService.updateUser(u);
			   redirectAttributes.addFlashAttribute("message", "Record  has been updated successfully! ");
			   redirectAttributes.addFlashAttribute("alertClass", "alert-success");

			// to end a session of a user:

			expireUserSessions(u, u.getUserCode());

			return "redirect:/userMaster";
		}
	}
	/**
	 * 
	 * @param Method  to Delete by id
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping(value = { "/deleteUser/{id}" })
	public String deleteUser(@PathVariable("id") String id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			this.userService.removeUser(id);
			redirectAttributes.addFlashAttribute("message", "Record  has been deleted duccessfully!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "User can't delete  record  Mapping child table !");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			return "redirect:/userMaster";
		}
		session.setAttribute("username", session.getAttribute("username"));
		return "redirect:/userMaster";
	}

	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		session.invalidate();
		return "redirect:" + "./";

	}
	

	private void getCaptcha(Login user) {
		Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
		user.setHiddenCaptcha(captcha.getAnswer());
		user.setCaptcha(""); // value entered by the User
		user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));

	}
	
	public void expireUserSessions(UserEntity u, String username) {
		System.out.println("expired method");

		List<Object> principals = sessionRegistry.getAllPrincipals();

		for (Object principal : principals) {
			// Check for the principal you want to expire here
			if (principal.equals(username)) {
				List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
				for (SessionInformation sessionInformation : sessionInformations) {
					sessionInformation.expireNow();
				
				}
			}

		}
	}


}
