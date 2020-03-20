package com.kevinppaulo.issueTracker.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
	@Id
	@GeneratedValue
	private Long commentId;
	@Column(length = 10000)
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date addedAt;
	@ManyToOne(cascade = CascadeType.ALL)
	private Issue issue;
	@ManyToOne
	private ApplicationUser author;
}
