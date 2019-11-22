package com.everis.springboot.app.models.documents;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Documento persona", description = "Persona involucrada en el club de estudios")
@Document(collection="persona")
public class Person {

	@ApiModelProperty(value = "Identificador de la persona")
	@Id
	private String id;
	
	@ApiModelProperty(value = "Representa si es estudiante", required = false)
	private boolean student;
	
	@ApiModelProperty(value = "Id del padre #1", required = false)
	private String parentOne="No Registrado";
	
	@ApiModelProperty(value = "Id del padre #2", required = false)
	private String parentTwo="No Registrado";	
	
	@ApiModelProperty(value = "Id del esposo o esposa", required = false)
	private String spouse = "No Registrado";
	
	@ApiModelProperty(value = "Nombre Completo de la persona", required = true)
	@NotEmpty
	private String fullName;	
	
	@ApiModelProperty(value = "Género de la persona", required = true)
	@NotEmpty
	private String gender;
	
	@ApiModelProperty(value = "Fecha de nacimiento de la persona")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@ApiModelProperty(value = "Tipo de documento de la persona", required = true)
	@NotEmpty
	private String typeDocument;
	
	@ApiModelProperty(value = "Numero de documento de la persona", required = true)
	@NotEmpty
	private String numberDocument;
	
	@ApiModelProperty(value = "Id del familiar relacionado", required = false)
	private String idRelative = "No Registrado";
	
	@ApiModelProperty(value = "Nombre del parentesco con el familiar relacionado", required = false)
	private String relation = "No Registrado";
	
	public Person(boolean student, @NotEmpty String fullName, @NotEmpty String gender, Date dateOfBirth,
			@NotEmpty String typeDocument, @NotEmpty String numberDocument) {		
		this.student = student;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.typeDocument = typeDocument;
		this.numberDocument = numberDocument;
	}

	//getters and setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public boolean isStudent() {
		return student;
	}
	public void setStudent(boolean student) {
		this.student = student;
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
	public String getIdRelative() {
		return idRelative;
	}
	public void setIdRelative(String idRelative) {
		this.idRelative = idRelative;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	
	
	
}
