package com.mony.proyectoerp.pruebas;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mony.proyectoerp.base.Almacen;



import com.mony.proyectoerp.gui.JAlmacenes_Alta;
import com.mony.proyectoerp.util.Util;

import junit.framework.TestCase;
/**
 * Clase de Prueba de caja negra.
 */
public class Testeando extends TestCase{
	Almacen almacen;
	String cadena;
	Almacen almacenAlta;
	JAlmacenes_Alta JAlmacen;
	@Before
	public void setUp(){
		almacen=new Almacen();
		almacen.setNombreAlm("almacenprueba");
		
		cadena="3";
		
		almacenAlta=new Almacen();
		almacenAlta.setId(111);
		almacenAlta.setNombreAlm("Nombre Almacen");
		
		JAlmacen=new JAlmacenes_Alta();
	}
	@Ignore
	public void tearDown(){
		
	}
	/**
	 * Las anotaciones @Test indican que el siguiente metodo es una prueba.
	 */
	@Test
	public void testToString(){
		String nombre=almacen.toString();
		assertTrue(nombre.equals("almacenprueba"));
	}
	@Test
	public void testisNumeric(){
		boolean funciona=Util.isNumeric(cadena);
		assertTrue(funciona);
	}
	@Test
	public void testgetAlmacen(){
		Almacen real=JAlmacen.getAlmacen(almacenAlta.getNombreAlm());
		assertSame(almacenAlta.getNombreAlm(),real.getNombreAlm());
	}
}
