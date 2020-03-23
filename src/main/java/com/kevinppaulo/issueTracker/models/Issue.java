package com.kevinppaulo.issueTracker.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@GeneratedValue
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
			return "secondary";
		default:
			return "primary";
		}
	}
}
