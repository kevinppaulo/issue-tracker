package com.kevinppaulo.issueTracker.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Issue {
	@Id
	@GeneratedValue
	private Long issueId;
	private String title;
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	private IssueStatus issueStatus;
	@OneToMany
	private List<Comment> comments;
}
