package com.kevinppaulo.issueTracker.models;

import java.awt.List;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kevinppaulo.issueTracker.security.ApplicationUserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationUser {
	@Id
	@GeneratedValue
	private Long userId;
	@Column(unique = true)
	private String username;
	@Column(unique = true)
	private String email;
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private String bio;
	@ManyToOne
	@JoinColumn(name = "user_organization")
	private Organization organization;
	
	
	private ApplicationUserRole role;
	@Transient
	private Set<? extends GrantedAuthority> grantedAuthorities;
	private boolean isAccountNonLocked;
	private boolean isAccountNonExpired;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	
	public Set<? extends GrantedAuthority> getGrantedAuthorities(){
		HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getRole()));
		return authorities;
	}

}
