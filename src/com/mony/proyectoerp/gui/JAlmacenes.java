package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mony.proyectoerp.base.Almacen;
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

import com.mony.proyectoerp.beans.tablaAlmacenes;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class JAlmacenes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private tablaAlmacenes tablaAlmacenes;
	private JTextField txtBuscar;
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
	/**
	 * Create the dialog.
	 */
	public JAlmacenes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JAlmacenes.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		this.setModal(true);
		setTitle("Almacenes");
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
						nuevoAlmacen();
					}
				});
				btNuevo.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/nuevo.png")));
				toolBar.add(btNuevo);
			}
			{
				JButton btModificar = new JButton("Modificar");
				btModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modificarAlmacen();
					}
				});
				btModificar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/modificar.png")));
				toolBar.add(btModificar);
			}
			{
				JButton btEliminar = new JButton("Eliminar");
				btEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminarAlmacen();
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
				tablaAlmacenes = new tablaAlmacenes();
				scrollPane.setViewportView(tablaAlmacenes);
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
						buscarAlmacen();
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
			private void cargarDatos() {	
				// Prepara y ejecuta la consulta
					Query query = HibernateUtil.getCurrentSession().createQuery("FROM Almacen");
					List<Almacen> almacenes = query.list();
				// Muestra la lista en la JTable
					tablaAlmacenes.listar(almacenes);
		
		}
			/**
			 * Permite abrir el formulario para rellenar un nuevo almacen
			 * y lo guarda en el BBDD
			 */
			private void nuevoAlmacen(){
				JAlmacenes_Alta jAlmacenesAlta = new JAlmacenes_Alta();
				if (jAlmacenesAlta.mostrarDialogo() == Accion.CANCELAR)
					return;
				
				Almacen almacen = jAlmacenesAlta.getAlmacen();
				Session sesion = HibernateUtil.getCurrentSession();
				sesion.beginTransaction();
				sesion.save(almacen);
				sesion.getTransaction().commit();
				sesion.close();
				
				cargarDatos();
			}
			/**
			 * Permite abrir el formulario para modificar un almacen
			 * y lo guarda en el BBDD
			 */
			private void modificarAlmacen(){
				Almacen almacen = tablaAlmacenes.getAlmacenSeleccionado();
				  if(almacen == null){
				   return;
				  }
				  JAlmacenes_Alta jAlmacenesAlta = new JAlmacenes_Alta();
				  
				  jAlmacenesAlta.setNombre(almacen.getNombreAlm());
				
				 
				  if (jAlmacenesAlta.mostrarDialogo() == Accion.ACEPTAR){
					  int respuesta=Util.mensajeConfirmacion("¿Está seguro de que desea guardar la modificación?");
						if(respuesta==JOptionPane.NO_OPTION)
							return;
				  }else{
						return;
				  }
				  
				 
				  almacen.setNombreAlm(jAlmacenesAlta.getNombre());
				 
					Session sesion = HibernateUtil.getCurrentSession();
					sesion.beginTransaction();
					sesion.update(almacen);
					sesion.getTransaction().commit();
					sesion.close();
				  
				  cargarDatos();
			}
			/**
			 * Permite  eliminar un almacen
			 * y lo guarda en el BBDD
			 */
			private void eliminarAlmacen(){
				int respuesta=Util.mensajeConfirmacion("Se borrarán los productos del almacen. ¿Estás seguro?");
				if(respuesta==JOptionPane.NO_OPTION)
					return;
				Almacen almacen = tablaAlmacenes.getAlmacenSeleccionado();
				if (almacen == null)
					return;
			
				Session session = HibernateUtil.getCurrentSession();
				session.beginTransaction();
				session.delete(almacen);
				session.getTransaction().commit();
				session.close();
				
				cargarDatos();
			}
			/**
			 * Permite buscar un registro por un campo determinado.
			 */
			private void buscarAlmacen(){
				String filtro = txtBuscar.getText();
				if(filtro.equals("")){
					cargarDatos();
					return;
				}
				Query query = HibernateUtil.getCurrentSession().createQuery("SELECT al FROM Almacen al WHERE al.nombreAlm = :nombre");
				query.setParameter("nombre", filtro);
				
				List<Almacen> almacenes = query.list();
				tablaAlmacenes.listar(almacenes);
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
