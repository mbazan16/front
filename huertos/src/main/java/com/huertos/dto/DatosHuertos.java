package com.huertos.dto;

import com.huertos.entities.Huerto;
import com.huertos.entities.Usuario;

@SuppressWarnings("serial")
public class DatosHuertos extends Huerto {
	
	private int numMacetas;
	private int numPlantas;
	
	public DatosHuertos() {
		super();
	}
	
	public DatosHuertos(Long id,String nombre, Usuario usuario, int numMacetas, int numPlantas) {
		super(id,nombre,usuario);
		this.numMacetas=numMacetas;
		this.numPlantas=numPlantas;
	}
	


	public int getNumMacetas() {
		return numMacetas;
	}
	public void setNumMacetas(int numMacetas) {
		this.numMacetas = numMacetas;
	}
	public int getNumPlantas() {
		return numPlantas;
	}
	public void setNumPlantas(int numPlantas) {
		this.numPlantas = numPlantas;
	}
	
	

}
