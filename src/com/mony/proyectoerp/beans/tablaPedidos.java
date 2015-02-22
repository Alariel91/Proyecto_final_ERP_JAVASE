package com.mony.proyectoerp.beans;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mony.proyectoerp.base.Articulo;
import com.mony.proyectoerp.base.LineaDetalle;
import com.mony.proyectoerp.base.Pedido;
import com.mony.proyectoerp.util.HibernateUtil;

public class tablaPedidos extends JTable{
	/**
	 * Clase que genera una tabla con la lista de Pedidos
	 * @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	public Connection conexion;
	public DefaultTableModel tabla;
	
	public tablaPedidos(){
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
		tabla.addColumn("Numero");
		tabla.addColumn("Forma de pago");
		tabla.addColumn("Fecha del pedido");
		tabla.addColumn("Productos");
		tabla.addColumn("Cliente");
		
		this.setModel(tabla);
	}
	/**
	 * Añade filas a la tabla
	 * @param pedidos
	 */
	public void listar(List<Pedido> pedidos){
		tabla.setNumRows(0);
		for(Pedido pedido: pedidos){
			
			String detalles = "";
			for (LineaDetalle linea : pedido.getLineaDetalles()) {
				detalles+= linea.getArticulo().getNombreArt() + " ,";
				
			}
			
			Object[] fila = new Object[]{pedido.getId(),pedido.getNumero(),pedido.getFormaPago(),pedido.getFechaPedido(),detalles,
					pedido.getCliente().getNombreCli() + " " + pedido.getCliente().getApellidos()};
			tabla.addRow(fila);
		}
	}
	/**
	 * Obtiene el cliente de la tabla seleccionado
	 * @return pedido
	 */
	public Pedido getPedidoSeleccionado() {
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		int id = (Integer) getValueAt(filaSeleccionada, 0);
		
		Pedido pedido = (Pedido) HibernateUtil.getCurrentSession().get(Pedido.class, id);
		return pedido;
	}
	/**
	 * Elimina la tabla
	 */
	public void vaciar(){
		tabla.setNumRows(0);
	}
}
