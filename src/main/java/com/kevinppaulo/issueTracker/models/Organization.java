package com.kevinppaulo.issueTracker.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@GeneratedValue
	private Long organizationId;
	private String organizationName;
	private String description;
	@OneToMany
	private List<Issue> issues;
	
}
