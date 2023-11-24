package com.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Licence {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String licenceNumber;

	private String licenceState;

	private String licenceExpirationDate;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Driver driver;
	
	public Licence() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getLicenceState() {
		return licenceState;
	}

	public void setLicenceState(String licenceState) {
		this.licenceState = licenceState;
	}

	public String getLicenceExpirationDate() {
		return licenceExpirationDate;
	}

	public void setLicenceExpirationDate(String licenceExpirationDate) {
		this.licenceExpirationDate = licenceExpirationDate;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	

}
