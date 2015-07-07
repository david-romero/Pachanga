package com.p.infrastructure.exceptions;

public class HorarioNoCompatibleException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1941951302847864094L;

	public HorarioNoCompatibleException() {
		super("El Horario del partido no es compatible con otros en los que ya estás apuntando");
	}

	public HorarioNoCompatibleException(String message) {
		super(message);
	}

}
