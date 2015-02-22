package com.mony.proyectoerp.beans;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import com.mony.proyectoerp.base.Articulo;
import com.mony.proyectoerp.util.HibernateUtil;

public class tablaArticulos extends JTable{
	/**
	 * Clase que genera una tabla con la lista de Articulos
	 * @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	public Connection conexion;
	public DefaultTableModel tabla;
	
	public tablaArticulos(){
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
		tabla.addColumn("Nombre");
		tabla.addColumn("Precio");
		tabla.addColumn("Descripcion");
		tabla.addColumn("Stock");
		tabla.addColumn("Proveedor");
		tabla.addColumn("Categoria");
		tabla.addColumn("Almacen");
		tabla.addColumn("Impuesto");
		this.setModel(tabla);
	}
	/**
	 * Añade filas a la tabla
	 * @param articulos
	 */
	public void listar(List<Articulo> articulos){
		tabla.setNumRows(0);
		for(Articulo articulo: articulos){
			Object[] fila = new Object[]{articulo.getId(),articulo.getNombreArt(),articulo.getPrecio(),articulo.getDescripcion(),
					articulo.getStock(),articulo.getProveedor().getNombrePro(),articulo.getCategoria().getNombreCat(),articulo.getAlmacen().getNombreAlm(),articulo.getTipoImpuesto().getValor()};
			tabla.addRow(fila);
		}
	}
	/**
	 * Obtiene el cliente de la tabla seleccionado
	 * @return articulo
	 */
	public Articulo getArticuloSeleccionado() {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		int id = (Integer) getValueAt(filaSeleccionada, 0);
		
		Articulo articulo = (Articulo) HibernateUtil.getCurrentSession().get(Articulo.class, id);
		return articulo;
	}
	/**
	 * Elimina la tabla
	 */
	public void vaciar(){
		tabla.setNumRows(0);
	}
	
}
