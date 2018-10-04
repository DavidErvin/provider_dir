package us.ervin.providerdir.providerdirectory.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "provider")
public class Provider {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Basic
	@Column(name = "email_address")
	@JsonProperty("email_address")
	private String email;
	
	@Basic
	@Column(name = "last_name")
	@JsonProperty("last_name")
	private String lastName;
	
	@Basic
	@Column(name = "first_name")
	@JsonProperty("first_name")
	private String firstName;
	
	@Basic
	@Column(name = "specialty")
	private String specialty;
	
	@Basic
	@Column(name = "practice_name")
	@JsonProperty("practice_name")
	private String practiceName;
}
