package com.huertos.exhuertos.dto;

import com.huertos.exhuertos.entities.Huerto;
import com.huertos.exhuertos.entities.Usuario;

@SuppressWarnings("serial")
public class DatosHuertos extends Huerto {
	
	private Long numMacetas;
	private Long numPlantas;
	
	public DatosHuertos() {
		super();
	}
	
	public DatosHuertos(Long id,String nombre, Usuario usuario, Long numMacetas, Long numPlantas) {
		super(id,nombre,usuario);
		this.numMacetas=numMacetas;
		this.numPlantas=numPlantas;
	}
	


	public Long getNumMacetas() {
		return numMacetas;
	}
	public void setNumMacetas(Long numMacetas) {
		this.numMacetas = numMacetas;
	}
	public Long getNumPlantas() {
		return numPlantas;
	}
	public void setNumPlantas(Long numPlantas) {
		this.numPlantas = numPlantas;
	}
	
	

}
