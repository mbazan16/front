package com.example;

import java.util.ArrayList;
import java.util.List;

public class MiServicioImpl {
	
	public List<Departamento> listado(){
		List<Departamento> departamentos = new ArrayList<Departamento>();
		
		Departamento departamento1 = new Departamento(1,"I+D");
		Departamento departamento2 = new Departamento(1,"Comercial");
		Departamento departamento3 = new Departamento(1,"Direccion");
		
		departamentos.add(departamento1);
		departamentos.add(departamento2);
		departamentos.add(departamento3);
		return departamentos;
		
	}

}
