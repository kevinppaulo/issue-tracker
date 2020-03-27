package com.kevinppaulo.issueTracker.models.dto;

import java.util.Date;
import java.util.List;

import com.kevinppaulo.issueTracker.models.Comment;
import com.kevinppaulo.issueTracker.models.Issue;
import com.kevinppaulo.issueTracker.models.IssueStatus;
import com.kevinppaulo.issueTracker.models.Organization;

import lombok.NonNull;

public class IssueAndOrganizationIdDto extends Issue {
	@NonNull
	private Long organizationId;

	public IssueAndOrganizationIdDto(Long issueId, String title, String description, Date createdAt, Date closedAt,
			Date lastUpdated, IssueStatus issueStatus, Organization organization, List<Comment> comments,
			String badgeColor, Long organizationId) {
		super(issueId, title, description, createdAt, closedAt, lastUpdated, issueStatus, organization, comments,
				badgeColor);
		this.organizationId = organizationId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "IssueAndOrganizationIdDto [organizationId=" + organizationId + "]";
	}
	
	
}
