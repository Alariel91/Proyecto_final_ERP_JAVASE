package com.mony.proyectoerp.util;

import javax.swing.JOptionPane;
/**
 * Clase donde se encuentran los métodos de utilidad
 * @author MONY
 *
 */
public class Util {
	
	public static enum Accion {
		ACEPTAR, CANCELAR;
	}
	/**
	 * Envia un mensaje de confirmacion
	 * @param mensaje
	 * @return respuesta del mensaje
	 */
	public static int mensajeConfirmacion(String mensaje) {
		
		return JOptionPane.showConfirmDialog(null, 
				mensaje,
				"Confirmación", JOptionPane.YES_NO_OPTION);
	}
	/**
	 * Envia un Mensaje de Error
	 * @param mensaje
	 */
public static void mensajeError(String mensaje) {
		
		 JOptionPane.showMessageDialog(null,mensaje);
	}
/**
 * Comprueba si la cadena insertada es un numero
 * @param cadena
 * @return true si es un numero , en caso contrario false.
 */
public static boolean isNumeric(String cadena){
	 try {
	  Integer.parseInt(cadena);
	  return true;
	 } catch (NumberFormatException nfe){
	  return false;
	 }
}
}
