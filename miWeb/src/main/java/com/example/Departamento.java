package com.example;

public class Departamento {
	
	private Integer id;
	private String nombre;
	
	public Departamento() {
		super();
	}
	
	
	public Departamento(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	

}
