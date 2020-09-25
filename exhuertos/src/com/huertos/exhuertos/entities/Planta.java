package com.huertos.exhuertos.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PLANTAS database table.
 * 
 */
@Entity
@Table(name="PLANTASEX")
@NamedQuery(name="Planta.findAll", query="SELECT p FROM Planta p")
public class Planta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String nombre;

	//bi-directional many-to-one association to Maceta
	@ManyToOne
	@JoinColumn(name="IDMACETA")
	private Maceta maceta;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="IDUSUARIO")
	private Usuario usuario;

	public Planta() {
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

	public Maceta getMaceta() {
		return this.maceta;
	}

	public void setMaceta(Maceta maceta) {
		this.maceta = maceta;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}