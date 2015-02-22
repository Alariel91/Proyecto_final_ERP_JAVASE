package com.mony.proyectoerp.beans;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.base.Proveedor;
import com.mony.proyectoerp.util.HibernateUtil;

public class tablaProveedores extends JTable{
	/**
	 * Clase que genera una tabla con la lista de Proveedores
	 * @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	public Connection conexion;
	public DefaultTableModel tabla;
	
	public tablaProveedores(){
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
		tabla.addColumn("Cif");
		tabla.addColumn("Direccion");
		tabla.addColumn("Forma de pago");
		this.setModel(tabla);
	}
	/**
	 * Añade filas a la tabla
	 * @param proveedores
	 */
	public void listar(List<Proveedor> proveedores){
		tabla.setNumRows(0);
		for(Proveedor proveedor: proveedores){
			Object[] fila = new Object[]{proveedor.getId(),proveedor.getNombrePro(),
					proveedor.getCif(),proveedor.getDireccion(),proveedor.getFormaPago()};
			tabla.addRow(fila);
		}
	}
	/**
	 * Obtiene el cliente de la tabla seleccionado
	 * @return proveedor
	 */
	public Proveedor getProveedorSeleccionado() {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		int id = (Integer) getValueAt(filaSeleccionada, 0);
		
		Proveedor proveedor = (Proveedor) HibernateUtil.getCurrentSession().get(Proveedor.class, id);
		return proveedor;
	}
	/**
	 * Elimina la tabla
	 */
	public void vaciar(){
		tabla.setNumRows(0);
	}
	
}
