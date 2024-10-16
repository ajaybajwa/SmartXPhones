package com.smartxphones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.common.UserDefault;
import com.smartxphones.email.EmailService;
import com.smartxphones.model.User;
import com.smartxphones.service.RoleService;
import com.smartxphones.service.UserService;

@CrossOrigin(origins = {"http://localhost:4201","smartxstore.azurewebsites.net" })
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/customer/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.getUserById(id);
	}
	
	@GetMapping("/customer/okta/{id}")
	public User getUser(@PathVariable String id) {
		return userService.getUserOktaId(id);
	}
	
	@GetMapping("/admin")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping("/customer/register")
	public void addUser(@RequestBody User user) throws Exception {
		String encryptPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptPassword);
		userService.addUser(user);
		emailService.sendEmail(user);
		userService.addRoleToUser(user.getUserId(), roleService.getRoleByName(UserDefault.DEFAULT_ROLE).getRoleId());
	}
	
	@PostMapping("/admin/{userId}/role/{roleId}")
	public void addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) throws Exception {
		userService.addRoleToUser(userId, roleId);
	}
	
	@PutMapping("/customer/update")
	public void updateUser(@RequestBody User user) {
		String encryptPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptPassword);
		userService.updateUser(user);
	}
	
	@DeleteMapping("/customer/delete/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
	}

}
