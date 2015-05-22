package com.p.model;

public class ChangePass {

	private String passActual;
	private String passNuevo;
	private String passValidar;
	
	public ChangePass() {
		super();
	}
	public ChangePass(String passActual, String passNuevo, String passValidar) {
		super();
		this.passActual = passActual;
		this.passNuevo = passNuevo;
		this.passValidar = passValidar;
	}
	public String getPassActual() {
		return passActual;
	}
	public void setPassActual(String passActual) {
		this.passActual = passActual;
	}
	public String getPassNuevo() {
		return passNuevo;
	}
	public void setPassNuevo(String passNuevo) {
		this.passNuevo = passNuevo;
	}
	public String getPassValidar() {
		return passValidar;
	}
	public void setPassValidar(String passValidar) {
		this.passValidar = passValidar;
	}
	
}
