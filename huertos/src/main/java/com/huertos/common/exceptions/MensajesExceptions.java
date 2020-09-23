package com.huertos.common.exceptions;
public enum MensajesExceptions {
	NO_EXISTE_HUERTO("No existe huerto"),
	EXISTE_HUERTO("Existe huerto"),
	NO_EXISTE_MACETA("No existe maceta"),
	EXISTE_MACETA("Existe maceta"),
	NO_EXISTE_PLANTA("No existe planta"),
	EXISTE_PLANTA("Existe planta"),
	NO_EXISTE_USUARIO("No existe usuario"),
	EXISTE_USUARIO("Existe usuario");
	
	private String descripcion;
	
	private MensajesExceptions(String descripcion) {
		this.descripcion=descripcion;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}

}
