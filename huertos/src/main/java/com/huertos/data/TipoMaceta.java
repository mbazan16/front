package com.huertos.data;

public enum TipoMaceta {
	
	SIMPLE(1,"Tipo Simple",1), DOBLE(2,"Tipo Doble",2),TRIPLE(3,"Tipo Triple",3);
	
	private int id;
	private String descripcion;
	private int num_plantas;
	
	private TipoMaceta(int id, String descripcion, int num_plantas) {
		this.id = id;
		this.descripcion = descripcion;
		this.num_plantas = num_plantas;
	}
	public int getId() {
		return id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public int getNum_plantas() {
		return num_plantas;
	}	
	
	

}
