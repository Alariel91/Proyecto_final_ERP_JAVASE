package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mony.proyectoerp.base.Proveedor;
import com.mony.proyectoerp.beans.tablaProveedores;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;

public class JProveedores extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private tablaProveedores tablaProveedores;
	private JTextField txtBuscar;
	/**
	 * Muestra la ventana para rellenar los datos
	 * @return accion aceptar/cancelar
	 */
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
	/**
	 * Create the dialog.
	 */
	public JProveedores() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JProveedores.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		this.setModal(true);
		setTitle("Proveedores");
		setBounds(0, 0, 800,600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JToolBar toolBar = new JToolBar();
			toolBar.setBackground(Color.GRAY);
			contentPanel.add(toolBar, BorderLayout.NORTH);
			{
				JButton btNuevo = new JButton("Nuevo");
				btNuevo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						nuevoProveedor();
					}
				});
				btNuevo.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/nuevo.png")));
				toolBar.add(btNuevo);
			}
			{
				JButton btModificar = new JButton("Modificar");
				btModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modificarProveedor();
					}
				});
				btModificar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/modificar.png")));
				toolBar.add(btModificar);
			}
			{
				JButton btEliminar = new JButton("Eliminar");
				btEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminarProveedor();
					}
				});
				btEliminar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/eliminar.png")));
				toolBar.add(btEliminar);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tablaProveedores = new tablaProveedores();
				scrollPane.setViewportView(tablaProveedores);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				txtBuscar = new JTextField();
				txtBuscar.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						limpiarBusqueda();
					}
				});
				buttonPane.add(txtBuscar);
				txtBuscar.setColumns(10);
			}
			{
				JButton btBuscar = new JButton("Buscar");
				btBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buscarProveedor();
					}
				});
				btBuscar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/buscar.png")));
				buttonPane.add(btBuscar);
				getRootPane().setDefaultButton(btBuscar);
			}
			{
				JButton btSalir = new JButton("Salir");
				btSalir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						salir();
					}
				});
				btSalir.setActionCommand("Cancel");
				btSalir.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/salir.png")));
				buttonPane.add(btSalir);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
		}
		cargarDatos();
	}
	/**
	 * Carga todos los proveedores desde Hibernate
	 */
		private void cargarDatos() {	
			// Prepara y ejecuta la consulta
				Query query = HibernateUtil.getCurrentSession().createQuery("FROM Proveedor");
				List<Proveedor> proveedores = query.list();
			// Muestra la lista en la JTable
				tablaProveedores.listar(proveedores);
	
	}
		/**
		 * Permite abrir el formulario para rellenar un nuevo proveedor
		 * y lo guarda en el BBDD
		 */
		private void nuevoProveedor(){
			JProveedores_Alta jProveedoresAlta = new JProveedores_Alta();
			if (jProveedoresAlta.mostrarDialogo() == Accion.CANCELAR)
				return;
			
			Proveedor proveedor = jProveedoresAlta.getProveedor();
			Session sesion = HibernateUtil.getCurrentSession();
			sesion.beginTransaction();
			sesion.save(proveedor);
			sesion.getTransaction().commit();
			sesion.close();
			
			cargarDatos();
		}
		/**
		 * Permite abrir el formulario para modificar un proveedor
		 * y lo guarda en el BBDD
		 */
		private void modificarProveedor(){
			Proveedor proveedor = tablaProveedores.getProveedorSeleccionado();
			  if(proveedor == null){
			   return;
			  }
			  JProveedores_Alta jProveedoresAlta = new JProveedores_Alta();
			  
			  jProveedoresAlta.setNombre(proveedor.getNombrePro());
			  jProveedoresAlta.setCif(proveedor.getCif());
			  jProveedoresAlta.setDireccion(proveedor.getDireccion());
			  jProveedoresAlta.setFormapago(proveedor.getFormaPago());
			 
			  
			  if (jProveedoresAlta.mostrarDialogo() == Accion.ACEPTAR){
				  int respuesta=Util.mensajeConfirmacion("¿Está seguro de que desea guardar la modificación?");
					if(respuesta==JOptionPane.NO_OPTION)
						return;
			  }else{
					return;
			  }
			  
			  proveedor.setNombrePro(jProveedoresAlta.getNombre());
			  proveedor.setCif(jProveedoresAlta.getCif());
			  proveedor.setDireccion(jProveedoresAlta.getDireccion());
			  proveedor.setFormaPago(jProveedoresAlta.getFormapago());

				Session sesion = HibernateUtil.getCurrentSession();
				sesion.beginTransaction();
				sesion.update(proveedor);
				sesion.getTransaction().commit();
				sesion.close();
			  
			  cargarDatos();
		}
		/**
		 * Permite  eliminar un proveedor
		 * y lo guarda en el BBDD
		 */
		private void eliminarProveedor(){
			int respuesta=Util.mensajeConfirmacion("Se eliminará el proveedor. ¿Estás seguro?");
			if(respuesta==JOptionPane.NO_OPTION)
				return;
			Proveedor proveedor = tablaProveedores.getProveedorSeleccionado();
			if (proveedor == null)
				return;
		
			Session session = HibernateUtil.getCurrentSession();
			session.beginTransaction();
			session.delete(proveedor);
			session.getTransaction().commit();
			session.close();
			
			cargarDatos();
		}
		/**
		 * Permite buscar un registro por un campo determinado.
		 */
		private void buscarProveedor(){
			String filtro = txtBuscar.getText();
			if(filtro.equals("")){
				cargarDatos();
				return;
			}
			Query query = HibernateUtil.getCurrentSession().createQuery("SELECT pro FROM Proveedor pro WHERE pro.nombrePro = :nombre OR pro.direccion = :direccion"
					+ " OR pro.cif = :cif"
					+ " OR pro.formaPago = :formaPago");
			query.setParameter("nombre", filtro);
			query.setParameter("direccion", filtro);
			query.setParameter("cif", filtro);
			query.setParameter("formaPago", filtro);
			List<Proveedor> proveedores = query.list();
			tablaProveedores.listar(proveedores);
		}
		/**
		 * Vuelve a cargar todos los datos, quitando la busqueda realizada 
		 * anteriormente.
		 */
		private void limpiarBusqueda(){
			String filtro = txtBuscar.getText();
			if(filtro.equals("")){
				cargarDatos();
				return;
			}
		}
		/**
		 * Pone la ventana invisible.
		 */
		private void salir(){
			this.setVisible(false);
			accion= Accion.CANCELAR;
		}


}
