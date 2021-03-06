package com.kevinppaulo.issueTracker.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long issueId;
	private String title;
	@Column(length = 10000)
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	private IssueStatus issueStatus;
	
	@ManyToOne
	Organization organization;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "issue")
	List<Comment> comments;

	@Transient
	private String badgeColor;
	
	public String getBadgeColor() {
		switch(this.issueStatus) {
		case OPEN:
			return "primary";
		case IN_ANALISYS:
			return "warning";
		case IN_PROGRESS:
			return "info";
		case CLOSED:
			return "success";
		case RESOLVED:
			return "success";
		default:
			return "primary";
		}
	}
	
	public int getNumberOfComments() {
		return this.comments.size();
	}
}
