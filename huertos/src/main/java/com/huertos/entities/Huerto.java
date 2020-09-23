package com.huertos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the HUERTOS database table.
 * 
 */
@Entity
@Table(name="HUERTOS")
@NamedQuery(name="Huerto.findAll", query="SELECT h FROM Huerto h")
public class Huerto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String nombre;
	

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="IDUSUARIO")
	private Usuario usuario;

	//bi-directional many-to-one association to Maceta
	@OneToMany(mappedBy="huerto")
	private List<Maceta> macetas;

	public Huerto() {
	}	
	

	public Huerto(long id, String nombre, Usuario usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.usuario = usuario;
	}



	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Maceta> getMacetas() {
		return this.macetas;
	}

	public void setMacetas(List<Maceta> macetas) {
		this.macetas = macetas;
	}

	public Maceta addMaceta(Maceta maceta) {
		getMacetas().add(maceta);
		maceta.setHuerto(this);

		return maceta;
	}

	public Maceta removeMaceta(Maceta maceta) {
		getMacetas().remove(maceta);
		maceta.setHuerto(null);

		return maceta;
	}

}