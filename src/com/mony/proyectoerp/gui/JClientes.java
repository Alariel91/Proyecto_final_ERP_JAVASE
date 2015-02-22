package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JScrollPane;



import org.hibernate.Query;
import org.hibernate.Session;

import com.mony.proyectoerp.beans.tablaClientes;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class JClientes extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JTextField txtBuscar;
	private tablaClientes tablaClientes;
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
	public JClientes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JClientes.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				salir();
			}
		});
		this.setModal(true);
		setTitle("Clientes");
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
					public void actionPerformed(ActionEvent arg0) {
						nuevoCliente();
					}
				});
				btNuevo.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/nuevo.png")));
				toolBar.add(btNuevo);
			}
			{
				JButton btModificar = new JButton("Modificar");
				btModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modificarCliente();
					}
				});
				btModificar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/modificar.png")));
				toolBar.add(btModificar);
			}
			{
				JButton btEliminar = new JButton("Eliminar");
				btEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminarCliente();
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
				tablaClientes = new tablaClientes();
				scrollPane.setViewportView(tablaClientes);
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
					public void keyReleased(KeyEvent arg0) {
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
						buscar();
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
	 * Carga todos los clientes desde Hibernate
	 */
		private void cargarDatos() {	
			// Prepara y ejecuta la consulta
				Query query = HibernateUtil.getCurrentSession().createQuery("FROM Cliente");
				List<Cliente> clientes = query.list();
			// Muestra la lista en la JTable
				tablaClientes.listar(clientes);
	
	}
		/**
		 * Permite abrir el formulario para rellenar un nuevo cliente
		 * y lo guarda en el BBDD
		 */
		private void nuevoCliente(){
			JClientes_Alta jClientesAlta = new JClientes_Alta();
			if (jClientesAlta.mostrarDialogo() == Accion.CANCELAR)
				return;
			
			Cliente cliente = jClientesAlta.getCliente();
			Session sesion = HibernateUtil.getCurrentSession();
			sesion.beginTransaction();
			sesion.save(cliente);
			sesion.getTransaction().commit();
			sesion.close();
			
			cargarDatos();
		}
		/**
		 * Permite abrir el formulario para modificar un cliente
		 * y lo guarda en el BBDD
		 */
		private void modificarCliente(){
			Cliente cliente = tablaClientes.getClienteSeleccionado();
			
			  if(cliente == null){
			   return;
			  }
			  
			 
			  JClientes_Alta jClientesAlta = new JClientes_Alta();
			  
			  jClientesAlta.setNombre(cliente.getNombreCli());
			  jClientesAlta.setApellidos(cliente.getApellidos());
			  jClientesAlta.setDni(cliente.getDni());
			  jClientesAlta.setDireccion(cliente.getDireccion());
			  jClientesAlta.setTelefono(cliente.getTelefono());
			  if (jClientesAlta.mostrarDialogo() == Accion.ACEPTAR){
				  int respuesta=Util.mensajeConfirmacion("¿Está seguro de que desea guardar la modificación?");
					if(respuesta==JOptionPane.NO_OPTION)
						return;
			  }else{
					return;
			  }
			  
			  cliente.setNombreCli(jClientesAlta.getNombre());
			  cliente.setApellidos(jClientesAlta.getApellidos());
			  cliente.setDni(jClientesAlta.getDni());
			  cliente.setDireccion(jClientesAlta.getDireccion());
			  cliente.setTelefono(jClientesAlta.getTelefono());
		
				Session sesion = HibernateUtil.getCurrentSession();
				sesion.beginTransaction();
				sesion.update(cliente);
				sesion.getTransaction().commit();
				sesion.close();
			  
			  cargarDatos();
		}
		/**
		 * Permite  eliminar un cliente
		 * y lo guarda en el BBDD
		 */
		private void eliminarCliente(){
			int respuesta=Util.mensajeConfirmacion("Se borrarán los pedidos del socio. ¿Estás seguro?");
			if(respuesta==JOptionPane.NO_OPTION)
				return;
			Cliente cliente = tablaClientes.getClienteSeleccionado();
			if (cliente == null)
				return;
		
			Session session = HibernateUtil.getCurrentSession();
			session.beginTransaction();
			session.delete(cliente);
			session.getTransaction().commit();
			session.close();
			
			cargarDatos();
		}
		/**
		 * Permite buscar un registro por un campo determinado.
		 */
		private void buscar(){
			String filtro = txtBuscar.getText();
			if(filtro.equals("")){
				cargarDatos();
				return;
			}
			Query query = HibernateUtil.getCurrentSession().createQuery("SELECT c FROM Cliente c WHERE c.nombreCli = :nombre OR c.apellidos = :apellidos"
					+ " OR c.dni = :dni");
			query.setParameter("nombre", filtro);
			query.setParameter("apellidos", filtro);
			query.setParameter("dni", filtro);
			List<Cliente> clientes = query.list();
			tablaClientes.listar(clientes);
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
