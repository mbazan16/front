package com.huertos.exhuertos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USUARIOS database table.
 * 
 */
@Entity
@Table(name="USUARIOSEX")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String apellidos;

	private String nombre;

	private String password;

	private String username;

	//bi-directional many-to-one association to Huerto
	@OneToMany(mappedBy="usuario")
	private List<Huerto> huertos;

	//bi-directional many-to-one association to Planta
	@OneToMany(mappedBy="usuario")
	private List<Planta> plantas;

	public Usuario() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Huerto> getHuertos() {
		return this.huertos;
	}

	public void setHuertos(List<Huerto> huertos) {
		this.huertos = huertos;
	}

	public Huerto addHuerto(Huerto huerto) {
		getHuertos().add(huerto);
		huerto.setUsuario(this);

		return huerto;
	}

	public Huerto removeHuerto(Huerto huerto) {
		getHuertos().remove(huerto);
		huerto.setUsuario(null);

		return huerto;
	}

	public List<Planta> getPlantas() {
		return this.plantas;
	}

	public void setPlantas(List<Planta> plantas) {
		this.plantas = plantas;
	}

	public Planta addPlanta(Planta planta) {
		getPlantas().add(planta);
		planta.setUsuario(this);

		return planta;
	}

	public Planta removePlanta(Planta planta) {
		getPlantas().remove(planta);
		planta.setUsuario(null);

		return planta;
	}

}