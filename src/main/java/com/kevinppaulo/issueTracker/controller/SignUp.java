package com.kevinppaulo.issueTracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.models.Organization;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.repository.OrganizationJpaRepository;
import com.kevinppaulo.issueTracker.security.ApplicationUserRole;

@Controller
@RequestMapping("/signup")
public class SignUp {
	
	private ApplicationUserJpaRepository appUserRepo;
	private PasswordEncoder passwordEncoder;
	private OrganizationJpaRepository organizationRepo;

	@Autowired
	public SignUp(ApplicationUserJpaRepository appUserRepo, PasswordEncoder passwordEncoder,
			OrganizationJpaRepository organizationRepo) {
		this.appUserRepo = appUserRepo;
		this.passwordEncoder = passwordEncoder;
		this.organizationRepo = organizationRepo;
	}

	@GetMapping()
	public String signUp() {
		return "signup";
	}
	
	@GetMapping("/{organizationId}")
	public ModelAndView signUp(@PathVariable Long organizationId) {
		ModelAndView mv = new ModelAndView("signup");
		mv.addObject("organizationId", organizationId);
		return mv;
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
		appUserRepo.save(user);
		return "redirect: /login";
	}
	
	
	@PostMapping("/{organizationId}")
	public String signUpReferral(@PathVariable("organizationId") Long organizationId, @Valid ApplicationUser user, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("Result has errors " + bindingResult.getAllErrors());
			return "redirect:/signup";
		}
		
		Organization organization = organizationRepo.findById(organizationId).orElseThrow(RuntimeException::new);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setRole(ApplicationUserRole.ADMIN);
		user.setOrganization(organization);
		appUserRepo.save(user);
		return "redirect:/login";
	}
	
}
