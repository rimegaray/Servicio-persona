package com.everis.springboot.app.models.documents;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="persona")
public class Person {

	@Id
	private String id;
	
	@NotNull
	private boolean isStudent;
	
	private String parentOne;
	
	private String parentTwo;
	
	private List<String> sibling;
	
	private String spouse;
	
	@NotEmpty
	private String fullName;	
	
	@NotEmpty
	private String gender;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@NotEmpty
	private String typeDocument;
	
	@NotEmpty
	private String numberDocument;
	
	public Person(@NotNull boolean isStudent, @NotEmpty String fullName, @NotEmpty String gender, Date dateOfBirth,
			@NotEmpty String typeDocument, @NotEmpty String numberDocument) {		
		this.isStudent = isStudent;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.typeDocument = typeDocument;
		this.numberDocument = numberDocument;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isStudent() {
		return isStudent;
	}
	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	public String getParentOne() {
		return parentOne;
	}
	public void setParentOne(String parentOne) {
		this.parentOne = parentOne;
	}
	public String getParentTwo() {
		return parentTwo;
	}
	public void setParentTwo(String parentTwo) {
		this.parentTwo = parentTwo;
	}
	public List<String> getSibling() {
		return sibling;
	}
	public void setSibling(List<String> sibling) {
		this.sibling = sibling;
	}
	
	public String getSpouse() {
		return spouse;
	}
	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}
	public String getNumberDocument() {
		return numberDocument;
	}
	public void setNumberDocument(String numberDocument) {
		this.numberDocument = numberDocument;
	}
	
	
	
	
}
