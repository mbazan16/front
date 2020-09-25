package com.huertos.exhuertos.common.exceptions;

@SuppressWarnings("serial")
public class DAOException extends Exception {

	private Exception e;
	private String mensaje;
	private boolean isGenerated;

	public DAOException(Exception e) {
		super();
		this.e = e;
		this.isGenerated = Boolean.FALSE;
	}

	public DAOException(MensajesExceptions mensaje) {
		super();
		this.mensaje = mensaje.getDescripcion();
		this.isGenerated = Boolean.TRUE;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isGenerated() {
		return isGenerated;
	}

	
	
	

}
