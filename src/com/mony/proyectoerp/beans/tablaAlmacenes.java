package com.mony.proyectoerp.beans;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mony.proyectoerp.base.Almacen;
import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.util.HibernateUtil;

public class tablaAlmacenes extends JTable{
	/**
	 * Clase que genera una tabla con la lista de Almacenes
	 * @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	public Connection conexion;
	public DefaultTableModel tabla;
	
	public tablaAlmacenes(){
		inicializar();
	}
	/**
	 * Método que inicializa la tabla con los nombres de las columnas
	 */
	public void inicializar(){
		tabla = new DefaultTableModel(){

			
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
		tabla.addColumn("nombre");
		this.setModel(tabla);
	}
	/**
	 * Añade filas a la tabla
	 * @param almacenes
	 */
	public void listar(List<Almacen> almacenes){
		tabla.setNumRows(0);
		for(Almacen almacen: almacenes){
			Object[] fila = new Object[]{almacen.getId(),almacen.getNombreAlm()};
			tabla.addRow(fila);
		}
	}
	/**
	 * Obtiene el cliente de la tabla seleccionado
	 * @return almacen
	 */
	public Almacen getAlmacenSeleccionado() {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		int id = (Integer) getValueAt(filaSeleccionada, 0);
		
		Almacen almacen = (Almacen) HibernateUtil.getCurrentSession().get(Almacen.class, id);
		return almacen;
	}
	/**
	 * Elimina la tabla
	 */
	public void vaciar(){
		tabla.setNumRows(0);
	}
	
}
