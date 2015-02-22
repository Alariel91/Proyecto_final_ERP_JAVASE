package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mony.proyectoerp.base.Articulo;
import com.mony.proyectoerp.base.Factura;
import com.mony.proyectoerp.base.LineaDetalle;
import com.mony.proyectoerp.base.Pedido;
import com.mony.proyectoerp.beans.tablaPedidos;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;
import com.mysql.jdbc.Connection;

public class JPedidos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JTextField txtBuscar;
	private tablaPedidos tablaPedidos;
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
	public JPedidos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JPedidos.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
	
		setTitle("Pedidos");
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
				JButton btNuevo = new JButton("Realizar un pedido");
				btNuevo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						nuevoPedido();
					}
				});
				btNuevo.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/nuevo.png")));
				toolBar.add(btNuevo);
			}
			{
				JButton btModificar = new JButton("Modificar");
				btModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modificarPedido();
					}
				});
				btModificar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/modificar.png")));
				toolBar.add(btModificar);
			}
			{
				JButton btEliminar = new JButton("Eliminar");
				btEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminarPedido();
					}
				});
				btEliminar.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/eliminar.png")));
				toolBar.add(btEliminar);
			}
			{
				JButton btFactura = new JButton("Generar Factura");
				btFactura.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						generarFactura();
					}
				});
				btFactura.setFont(new Font("Tahoma", Font.BOLD, 15));
				btFactura.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/factura.gif")));
				toolBar.add(btFactura);
			}
			{
				JButton btInforme = new JButton("Listado de Pedidos");
				btInforme.setFont(new Font("Tahoma", Font.BOLD, 15));
				btInforme.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/informe.png")));
				btInforme.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						report();
					}
				});
				toolBar.add(btInforme);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tablaPedidos = new tablaPedidos();
				scrollPane.setViewportView(tablaPedidos);
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
	 * Carga todos los pedidos desde Hibernate
	 */
			private void cargarDatos() {	
				// Prepara y ejecuta la consulta
					Query query = HibernateUtil.getCurrentSession().createQuery("FROM Pedido p where p.factura=null");
					List<Pedido> pedidos = query.list();
				// Muestra la lista en la JTable
					tablaPedidos.listar(pedidos);
		
		}
			private void generarFactura(){
				 int respuesta=Util.mensajeConfirmacion("¿Está seguro de que quiere generar la factura?");
					if(respuesta==JOptionPane.NO_OPTION)
						return;
				Pedido pedido=tablaPedidos.getPedidoSeleccionado();
				int idpedido = tablaPedidos.getPedidoSeleccionado().getId();
				String numpedido = tablaPedidos.getPedidoSeleccionado().getNumero();
				
				Calendar fecha = new GregorianCalendar();
				String dia=String.valueOf(fecha.get(Calendar.DATE));
				String mes=String.valueOf(fecha.get(Calendar.MONTH)+1);
				String ano=String.valueOf(fecha.get(Calendar.YEAR));
				String fechadehoy=dia + "-" + mes +"-"+ano;
				java.util.Date f = new Date();
	
				String nombrefact="factura-" + idpedido + "-" + numpedido + "-" + fechadehoy;
				
				Factura factura = new Factura();
				factura.setNombreFac("fact" + pedido.getCliente().getId());
				factura.setNumero(numpedido);
				factura.setSerie(nombrefact);
				factura.setDescripcion("Cliente:" + pedido.getCliente().getDni());
				factura.setLugar(pedido.getCliente().getDireccion());
				factura.setFechaEmision(f);
				
				pedido.setFactura(factura);
				
				try {
				
					
					JasperReport report;
					report = (JasperReport) JRLoader.loadObject("facturaniages.jasper");
					
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("IDPEDIDO", idpedido);
				
					Connection conexion=JEstadisticas.conectar();
						JasperPrint jasperPrint;
					jasperPrint = JasperFillManager.fillReport(report,parametros,conexion);
					JRExporter exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("facturas//"+nombrefact + ".pdf"));
					exporter.exportReport();
					jasperPrint = JasperFillManager.fillReport(report,parametros,conexion);
					
					JasperViewer.viewReport(jasperPrint, false);
					
					
				} catch (JRException e) {
					e.printStackTrace();
				}
				
				Session sesion = HibernateUtil.getCurrentSession();
				sesion.beginTransaction();
				sesion.save(pedido);
				sesion.save(factura);
				sesion.getTransaction().commit();
				sesion.close();
			
				
				cargarDatos();
			}
			/**
			 * Permite abrir el formulario para rellenar un nuevo pedido
			 * y lo guarda en el BBDD
			 */
			private void nuevoPedido(){
				JPedidos_Alta jPedidosAlta = new JPedidos_Alta();
				jPedidosAlta.setNuevo(true);
				if (jPedidosAlta.mostrarDialogo() == Accion.CANCELAR)
					return;
				
				Pedido pedido = jPedidosAlta.getPedido();
				ArrayList<LineaDetalle> lineas=new ArrayList<LineaDetalle>();
			
				for (LineaDetalle linea : pedido.getLineaDetalles()) {
					LineaDetalle jLineaAlta=new LineaDetalle();
					jLineaAlta.setPedido(pedido);
					jLineaAlta.setArticulo(linea.getArticulo());
					jLineaAlta.setCantidad(linea.getCantidad());
					jLineaAlta.setPrecio(linea.getPrecio());
					lineas.add(jLineaAlta);
					}
				
				Session sesion = HibernateUtil.getCurrentSession();
				sesion.beginTransaction();
				sesion.save(pedido);
				for (LineaDetalle lineaDetalle : lineas) {
					sesion.save(lineaDetalle);
				}
				Query query = HibernateUtil.getCurrentSession().createQuery("FROM Articulo");
				List<Articulo> articulos = query.list();
				for (LineaDetalle lineaDetalle : lineas) {
					for (Articulo articulo : articulos) {
						if(articulo.getId().equals(lineaDetalle.getArticulo().getId())){
							articulo.setStock(articulo.getStock()-lineaDetalle.getCantidad());
							sesion.saveOrUpdate(articulo);
						}
					}
				}
				sesion.getTransaction().commit();
				sesion.close();
			
				
				cargarDatos();
			}
			/**
			 * Permite abrir el formulario para modificar un pedido
			 * y lo guarda en el BBDD
			 */
			private void modificarPedido(){
				Pedido pedido = tablaPedidos.getPedidoSeleccionado();
				  if(pedido == null){
				   return;
				  }
				  JPedidos_Alta jPedidosAlta = new JPedidos_Alta();
				  jPedidosAlta.setNuevo(false);
				  
				  jPedidosAlta.setNumero(pedido.getNumero());
				  jPedidosAlta.setFormaPago(pedido.getFormaPago());
				  jPedidosAlta.setFechaPago(pedido.getFechaPedido());
				  jPedidosAlta.setClienteSeleccionado(pedido.getCliente());
				  jPedidosAlta.setL(new ArrayList<LineaDetalle>(pedido.getLineaDetalles()));
				  
		
				  if (jPedidosAlta.mostrarDialogo() == Accion.ACEPTAR){
					  int respuesta=Util.mensajeConfirmacion("¿Está seguro de que desea guardar la modificación?");
						if(respuesta==JOptionPane.NO_OPTION)
							return;
				  }else{
						return;
				  }
				  
				  pedido.setNumero(jPedidosAlta.getNumero());
				  pedido.setFormaPago(jPedidosAlta.getFormaPago());
				  pedido.setFechaPedido(jPedidosAlta.getFechaPago());
				  pedido.setCliente(jPedidosAlta.getClienteSeleccionado());
				  pedido.setLineaDetalles(new HashSet<LineaDetalle>(jPedidosAlta.getL()));
				  
				  
					Session sesion = HibernateUtil.getCurrentSession();
					sesion.beginTransaction();
					sesion.update(pedido);
					for (LineaDetalle lineaDetalle : pedido.getLineaDetalles()) {
						lineaDetalle.setPedido(pedido);
						sesion.saveOrUpdate(lineaDetalle);
					}
					for (LineaDetalle lineaDetalle : pedido.getLineaDetalles()) {
						for (LineaDetalle lineasEliminadas: jPedidosAlta.getLineasEliminadas()) {
							if(!lineaDetalle.equals(lineasEliminadas))
								sesion.delete(lineasEliminadas);
						}
					}

					sesion.getTransaction().commit();
					sesion.close();
				  
					  cargarDatos();
			}
			/**
			 * Permite  eliminar un pedido
			 * y lo guarda en el BBDD
			 */
			private void eliminarPedido(){
				int respuesta=Util.mensajeConfirmacion("Se eliminará el pedido. ¿Estás seguro?");
				if(respuesta==JOptionPane.NO_OPTION)
					return;
				Pedido pedido = tablaPedidos.getPedidoSeleccionado();
				if (pedido == null)
					return;
			
				Session session = HibernateUtil.getCurrentSession();
				session.beginTransaction();
				Query query = HibernateUtil.getCurrentSession().createQuery("FROM Articulo");
				List<Articulo> articulos = query.list();
				for (LineaDetalle lineaDetalle : pedido.getLineaDetalles()) {
					for (Articulo articulo : articulos) {
						if(articulo.getId().equals(lineaDetalle.getArticulo().getId())){
							articulo.setStock(articulo.getStock()+lineaDetalle.getCantidad());
							session.saveOrUpdate(articulo);
						}
					}
				}
				session.delete(pedido);
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
				Query query = HibernateUtil.getCurrentSession().createQuery("SELECT p FROM Pedido p WHERE p.numero = :numero"
						+ " OR p.formaPago = :formaPago");
				query.setParameter("numero", filtro);
				query.setParameter("formaPago", filtro);
				
				List<Pedido> articulos = query.list();
				tablaPedidos.listar(articulos);
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
			 * Abre una nueva ventana
			 */
			private void report(){
				JReport jReport= new JReport();
				if(jReport.mostrarDialogo() == Accion.CANCELAR)
					return;	
			}
			/**
			 * Pone la ventana invisible.
			 */
			private void salir(){
				this.setVisible(false);
				accion= Accion.CANCELAR;
			}

}
