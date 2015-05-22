package com.p.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ValidateConditions implements Serializable {

	private static final long serialVersionUID = 6289771252513353533L;

	public ValidateConditions() {
		super();
	}

	private LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>>> shoot_conditions;

	public LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>>> getShoot_conditions() {
		return shoot_conditions;
	}

	public void setShoot_conditions(
			LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>>> shoot_conditions) {
		this.shoot_conditions = shoot_conditions;
	}

	public void setShoot_conditions(String list_shoot_conditions) {
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>>> or = new LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>>>();
		
		ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>> listadoAnds = new ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>>();
		
		String[] lista = list_shoot_conditions.split(";");
		for(String c : lista){
			try {
				LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>> and = new LinkedHashMap<String, ArrayList<LinkedHashMap<String, Integer>>>();
	
				and.put("and", stringToArrayList(c));
				listadoAnds.add(and);
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		or.put("or", listadoAnds);
		
		this.shoot_conditions = or;
	}

	private ArrayList<LinkedHashMap<String, Integer>> stringToArrayList(
			String shoot_c) {

		ArrayList<LinkedHashMap<String, Integer>> listado = new ArrayList<LinkedHashMap<String, Integer>>();

		LinkedHashMap<String, Integer> params = new LinkedHashMap<String, Integer>();

		String[] arrayPares = shoot_c.split(",");

		for (String string : arrayPares) {
			params.put(string.split(":")[0],
					Integer.valueOf(string.split(":")[1]));
		}

		listado.add(params);

		return listado;

	}

}
