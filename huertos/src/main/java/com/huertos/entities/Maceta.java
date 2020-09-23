package com.huertos.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.huertos.data.TipoMaceta;


/**
 * The persistent class for the MACETAS database table.
 * 
 */
@Entity
@Table(name="MACETAS")
@NamedQuery(name="Maceta.findAll", query="SELECT m FROM Maceta m")
public class Maceta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Enumerated
	@Column(name="IDTIPOMACETA")
	private TipoMaceta tipoMaceta;

	@Column(name="MCACOMPLETA")
	private boolean completa;

	//bi-directional many-to-one association to Huerto
	@ManyToOne
	@JoinColumn(name="IDHUERTO")
	private Huerto huerto;

	//bi-directional many-to-one association to Planta
	@OneToMany(mappedBy="maceta")
	private List<Planta> plantas;

	public Maceta() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}	

	public TipoMaceta getTipoMaceta() {
		return tipoMaceta;
	}

	public void setTipoMaceta(TipoMaceta tipoMaceta) {
		this.tipoMaceta = tipoMaceta;
	}

	public boolean isCompleta() {
		return this.completa;
	}

	public void setCompleta(boolean completa) {
		this.completa = completa;
	}

	public Huerto getHuerto() {
		return this.huerto;
	}

	public void setHuerto(Huerto huerto) {
		this.huerto = huerto;
	}

	public List<Planta> getPlantas() {
		return this.plantas;
	}

	public void setPlantas(List<Planta> plantas) {
		this.plantas = plantas;
	}

	public Planta addPlanta(Planta planta) {
		getPlantas().add(planta);
		planta.setMaceta(this);

		return planta;
	}

	public Planta removePlanta(Planta planta) {
		getPlantas().remove(planta);
		planta.setMaceta(null);

		return planta;
	}

}