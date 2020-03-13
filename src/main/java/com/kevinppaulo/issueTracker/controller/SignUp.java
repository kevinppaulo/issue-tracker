package com.kevinppaulo.issueTracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.security.ApplicationUserRole;

@Controller
@RequestMapping("/signup")
public class SignUp {
	
	private ApplicationUserJpaRepository appUserRepo;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public SignUp(ApplicationUserJpaRepository appUserRepo, PasswordEncoder passwordEncoder) {
		this.appUserRepo = appUserRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public String signUp() {
		return "signup";
	}
	
	@PostMapping
	public String signUpPost(@Valid ApplicationUser user, BindingResult bindingResult, RedirectAttributes attributes ) {
		if(bindingResult.hasErrors()) {
			System.out.println("Result has errors " + bindingResult.getAllErrors());
			return "redirect:/signup";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setRole(ApplicationUserRole.ADMIN);
		System.out.println("Saving user " + user);
		appUserRepo.save(user);
		return "redirect: /login";
	}
	
}
