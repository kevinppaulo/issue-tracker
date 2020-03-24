package com.kevinppaulo.issueTracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.models.Comment;
import com.kevinppaulo.issueTracker.models.Issue;
import com.kevinppaulo.issueTracker.models.IssueStatus;
import com.kevinppaulo.issueTracker.models.Organization;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.repository.CommentJpaRepository;
import com.kevinppaulo.issueTracker.repository.IssueJpaRepository;
import com.kevinppaulo.issueTracker.repository.OrganizationJpaRepository;
import com.kevinppaulo.issueTracker.security.ApplicationUserRole;

@SpringBootApplication
public class IssueTrackerApplication implements CommandLineRunner {

	private final ApplicationUserJpaRepository userRepo;
	private final OrganizationJpaRepository organizationRepo;
	private final IssueJpaRepository issueRepo;
	private final CommentJpaRepository commentRepo;
	private final PasswordEncoder encoder;

	@Autowired
	public IssueTrackerApplication(ApplicationUserJpaRepository userRepo, OrganizationJpaRepository organizationRepo,
			IssueJpaRepository issueRepo, CommentJpaRepository commentRepo, PasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.organizationRepo = organizationRepo;
		this.issueRepo = issueRepo;
		this.commentRepo = commentRepo;
		this.encoder = encoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(IssueTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Optional<ApplicationUser> coachellaUser = userRepo.findByUsername("coachella");
		
		if(!coachellaUser.isPresent()) {
			ApplicationUser demoUser = new ApplicationUser(null, "coachella", "coachella@demo.com",
					encoder.encode("password"), "Coachella", "Demo", "", ApplicationUserRole.ADMIN, null, true, true, true,
					true);
			
			ApplicationUser scubadiver = new ApplicationUser(null, "scubadiver", "scubadiver@demo.com",
					encoder.encode("password"), "Scuba", "Diver", "", ApplicationUserRole.ADMIN, null, true, true, true,
					true);
			
			userRepo.save(scubadiver);
			userRepo.save(demoUser);
		}
		
		
	
	}

}
