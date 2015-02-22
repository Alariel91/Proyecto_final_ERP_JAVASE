package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mony.proyectoerp.base.Articulo;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JScrollPane;




import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import com.mony.proyectoerp.beans.tablaArticulos;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Dimension;

public class JProductos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private tablaArticulos tablaArticulos;
	private JTextField txtBuscar;
	/**
	 * Muestra la ventana para rellenar los datos
	 * @return accion aceptar/cancelar
	 */
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
	private boolean refrescar;
	private void inicializar() {
		
		try {
			HibernateUtil.buildSessionFactory();
			HibernateUtil.openSession();
			cargarDatos();
		} catch (HibernateException he) {
			he.printStackTrace();
		}
	}
	private void hilo(){
		
		Thread hilo = new Thread() {

			@Override
			public void run() {
				while(refrescar){		
					try {
						inicializar();
						Thread.sleep(10000);
				
					} catch (InterruptedException e) {
						e.printStackTrace();
						
					}
				}
			}
		};
		hilo.start();
	}
	/**
	 * Create the dialog.
	 */
	public JProductos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JProductos.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		this.setModal(true);
		setTitle("Productos");
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
						nuevoArticulo();
					}
				});
				btNuevo.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/nuevo.png")));
				toolBar.add(btNuevo);
			}
			{
				JButton btModificar = new JButton("Modificar");
				btModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modificarArticulo();
					}
				});
				btModificar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/modificar.png")));
				toolBar.add(btModificar);
			}
			{
				JButton btEliminar = new JButton("Eliminar");
				btEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminarArticulo();
					}
				});
				btEliminar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/eliminar.png")));
				toolBar.add(btEliminar);
			}
			{
				JButton btRefrescar = new JButton("Refrescar");
				btRefrescar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/refresh.png")));
				btRefrescar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						refrescar=true;
						hilo();
					}
				});
				toolBar.add(btRefrescar);
			}
			{
				JButton btStop = new JButton("Stop");
				btStop.setPreferredSize(new Dimension(79, 23));
				btStop.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/stop.png")));
				btStop.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						refrescar=false;
					}
				});
				toolBar.add(btStop);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tablaArticulos = new tablaArticulos();
				scrollPane.setViewportView(tablaArticulos);
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
					public void actionPerformed(ActionEvent arg0) {
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
	 * Carga todos los productos desde Hibernate
	 */
			private void cargarDatos() {	
				// Prepara y ejecuta la consulta
					Query query = HibernateUtil.getCurrentSession().createQuery("FROM Articulo");
					List<Articulo> articulos = query.list();
				// Muestra la lista en la JTable
					tablaArticulos.listar(articulos);
		
		}
			/**
			 * Permite abrir el formulario para rellenar un nuevo articulo
			 * y lo guarda en el BBDD
			 */
			private void nuevoArticulo(){
				JProductos_Alta jProductosAlta = new JProductos_Alta();
				jProductosAlta.setNuevo(true);
				if (jProductosAlta.mostrarDialogo() == Accion.CANCELAR)
					return;
				
				Articulo articulo = jProductosAlta.getArticulo();
				Session sesion = HibernateUtil.getCurrentSession();
				sesion.beginTransaction();
				sesion.save(articulo);
				sesion.getTransaction().commit();
				sesion.close();
				
				cargarDatos();
			}
			/**
			 * Permite abrir el formulario para modificar un articulo
			 * y lo guarda en el BBDD
			 */
			private void modificarArticulo(){
				Articulo articulo = tablaArticulos.getArticuloSeleccionado();
				  if(articulo == null){
				   return;
				  }
				  JProductos_Alta jProductosAlta = new JProductos_Alta();
				  jProductosAlta.setNuevo(false);
				  
				  jProductosAlta.setNombre(articulo.getNombreArt());
				  jProductosAlta.setPrecio(articulo.getPrecio());
				  jProductosAlta.setDescripcion(articulo.getDescripcion());
				  jProductosAlta.setStock(articulo.getStock());
				  jProductosAlta.setAlmacenSeleccionado(articulo.getAlmacen());
				  jProductosAlta.setCategoriaSeleccionado(articulo.getCategoria());
				  jProductosAlta.setProveedorSeleccionado(articulo.getProveedor());
				  jProductosAlta.setTipoImpuestoSeleccionado(articulo.getTipoImpuesto());
				  
				  
				  if (jProductosAlta.mostrarDialogo() == Accion.ACEPTAR){
					  int respuesta=Util.mensajeConfirmacion("¿Está seguro de que desea guardar la modificación?");
						if(respuesta==JOptionPane.NO_OPTION)
							return;
				  }else{
						return;
				  }
				  
				  articulo.setNombreArt(jProductosAlta.getNombre());
				  articulo.setPrecio(jProductosAlta.getPrecio());
				  articulo.setDescripcion(jProductosAlta.getDescripcion());
				  articulo.setStock(jProductosAlta.getStock());
				  articulo.setAlmacen(jProductosAlta.getAlmacenSeleccionado());
				  articulo.setCategoria(jProductosAlta.getCategoriaSeleccionado());
				  articulo.setProveedor(jProductosAlta.getProveedorSeleccionado());
				  articulo.setTipoImpuesto(jProductosAlta.getTipoImpuestoSeleccionado());

					Session sesion = HibernateUtil.getCurrentSession();
					sesion.beginTransaction();
					sesion.update(articulo);
					sesion.getTransaction().commit();
					sesion.close();
				  
				  cargarDatos();
			}
			/**
			 * Permite  eliminar un producto
			 * y lo guarda en el BBDD
			 */
			private void eliminarArticulo(){
				int respuesta=Util.mensajeConfirmacion("Se eliminará el articulo. ¿Estás seguro?");
				if(respuesta==JOptionPane.NO_OPTION)
					return;
				Articulo articulo = tablaArticulos.getArticuloSeleccionado();
				if (articulo == null)
					return;
			
				Session session = HibernateUtil.getCurrentSession();
				session.beginTransaction();
				session.delete(articulo);
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
				Query query = HibernateUtil.getCurrentSession().createQuery("SELECT art FROM Articulo art WHERE art.nombreArt = :nombre"
						+  " OR art.descripcion = :descripcion OR art.almacen.nombreAlm = :nombrealm OR art.tipoImpuesto.nombreImp = :nombreimp OR art.categoria.nombreCat = :nombrecat OR art.proveedor.nombrePro = :nombrepro");
				query.setParameter("nombre", filtro);
				query.setParameter("descripcion", filtro);
				query.setParameter("nombrealm", filtro);
				query.setParameter("nombreimp", filtro);
				query.setParameter("nombrecat", filtro);
				query.setParameter("nombrepro", filtro);
				
				List<Articulo> articulos = query.list();
				tablaArticulos.listar(articulos);
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
