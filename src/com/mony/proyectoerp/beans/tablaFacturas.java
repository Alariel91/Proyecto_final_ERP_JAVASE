package com.mony.proyectoerp.beans;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.base.Factura;
import com.mony.proyectoerp.util.HibernateUtil;

public class tablaFacturas extends JTable{
	/**
	 * Clase que genera una tabla con la lista de Facturas
	 * @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	public Connection conexion;
	public DefaultTableModel tabla;
	
	public tablaFacturas(){
		inicializar();
	}
	/**
	 * Método que inicializa la tabla con los nombres de las columnas
	 */
	public void inicializar(){
		tabla = new DefaultTableModel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * Metodo que evita que las celdas sean editables
			 * @return false
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
			
				return false;
			}
			
		};
		tabla.addColumn("#");
		tabla.addColumn("Nombre");
		tabla.addColumn("Numero");
		tabla.addColumn("Serie");
		tabla.addColumn("Lugar");
		tabla.addColumn("Descripcion");
		tabla.addColumn("Fecha Emision");
		this.setModel(tabla);
	}
	/**
	 * Añade filas a la tabla
	 * @param facturas
	 */
	public void listar(List<Factura> facturas){
		tabla.setNumRows(0);
		for(Factura factura: facturas){
			Object[] fila = new Object[]{factura.getId(),factura.getNombreFac(),factura.getNumero(),
					factura.getSerie(),factura.getLugar(),factura.getDescripcion(),factura.getFechaEmision()};
			tabla.addRow(fila);
		}
	}
	/**
	 * Obtiene la factura de la tabla seleccionado
	 * @return factura
	 */
	public Factura getFacturaSeleccionado() {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		int id = (Integer) getValueAt(filaSeleccionada, 0);
		
		Factura factura = (Factura) HibernateUtil.getCurrentSession().get(Factura.class, id);
		return factura;
	}
	/**
	 * Elimina la tabla
	 */
	public void vaciar(){
		tabla.setNumRows(0);
	}
	
}
