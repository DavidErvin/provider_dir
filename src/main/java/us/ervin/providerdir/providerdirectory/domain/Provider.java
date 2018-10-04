package us.ervin.providerdir.providerdirectory.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Provider {

	@Id
	@Basic
	@Column(name = "email_address")
	private String email;
	
	@Basic
	@Column(name = "last_name")
	private String lastName;
	
	@Basic
	@Column(name = "first_name")
	private String firstName;
	
	@Basic
	@Column(name = "specialty")
	private String specialty;
	
	@Basic
	@Column(name = "practice_name")
	private String practiceName;
}
