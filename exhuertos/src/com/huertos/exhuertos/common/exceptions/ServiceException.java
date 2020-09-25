package com.huertos.exhuertos.common.exceptions;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	private int code;
	private String mensaje;

	public ServiceException(Exception e) {
		super();
		this.code = CodeException.EXCEPCION_GENERAL;
	}
	
	public ServiceException(DAOException daoe) {
		super();
		this.code = CodeException.EXCEPCION_DAO;
		this.mensaje= daoe.getMensaje();
	}	

	public ServiceException(int code, String mensaje) {
		super();
		this.code = code;
		this.mensaje = mensaje;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	

}
