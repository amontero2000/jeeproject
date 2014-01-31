package com.microforum.prototipo.constantes;

public class Constantes {

	/**
	 * Modo de acceso a la aplicacion.
	 */
	public final static String ACCESS_MODE = Properties.getString("mode");
	public final static String DUMMY_ACCESS_MODE = "DUMMY";
	public final static String SSA_ACCESS_MODE = "SSA";

	/**
	 * URL de la aplicacion de seguridad.
	 */
	public final static String URL_APLICACION_SEGURIDAD = Properties
			.getString("url.aplicacion.seguridad");
}