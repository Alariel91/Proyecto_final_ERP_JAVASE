package com.mony.proyectoerp.beans;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.util.HibernateUtil;

public class tablaClientes extends JTable{
	/**
	 * Clase que genera una tabla con la lista de Clientes
	 * @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	public Connection conexion;
	public DefaultTableModel tabla;
	
	public tablaClientes(){
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
		tabla.addColumn("nombre");
		tabla.addColumn("apellidos");
		tabla.addColumn("dni");
		tabla.addColumn("direccion");
		tabla.addColumn("telefono");
		this.setModel(tabla);
	}
	/**
	 * Añade filas a la tabla
	 * @param clientes
	 */
	public void listar(List<Cliente> clientes){
		tabla.setNumRows(0);
		for(Cliente cliente: clientes){
			Object[] fila = new Object[]{cliente.getId(),cliente.getNombreCli(),cliente.getApellidos(),
					cliente.getDni(),cliente.getDireccion(),cliente.getTelefono()};
			tabla.addRow(fila);
		}
	}
	/**
	 * Obtiene el cliente de la tabla seleccionado
	 * @return cliente
	 */
	public Cliente getClienteSeleccionado() {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		int id = (Integer) getValueAt(filaSeleccionada, 0);
		
		Cliente cliente = (Cliente) HibernateUtil.getCurrentSession().get(Cliente.class, id);
		return cliente;
	}
	/**
	 * Elimina la tabla
	 */
	public void vaciar(){
		tabla.setNumRows(0);
	}
	
}
