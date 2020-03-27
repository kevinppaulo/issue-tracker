package com.kevinppaulo.issueTracker.models;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long organizationId;
	private String name;
	private String description;
	@ManyToMany
	@JoinTable(name = "organization_user")
	List<ApplicationUser> users;
	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	List<Issue> issues;
	
}
