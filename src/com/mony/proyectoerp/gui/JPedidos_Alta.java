package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;

import com.mony.proyectoerp.base.Articulo;
import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.base.LineaDetalle;
import com.mony.proyectoerp.base.Pedido;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;

public class JPedidos_Alta extends JDialog {

	/**
	 * Formulario de Alta de pedidos
	 *  @author Monica Saenz
	 *  @version 1.0
	 */
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JLabel lbNumero;
	
	private JLabel lbFormaPago;
	private JLabel lbFechaPedido;
	private JLabel lbCantidad;
	private JLabel lbProveedor;
	private JLabel lbTituloAlta;
	
	private JTextField txtCantidad;
	private JTextField txtNumero;
	private JTextField txtFormaPago;
	private JDateChooser dateChooser;
	//------------------------
	private String numero;
	private Date fechaPedido;
	private String formaPago;
	private int cantidad;
	private float precio;
	
	private int id_producto;
	private int id_cliente;
	//JLIST Productos
	private JList<Articulo> listProductos;
	private DefaultListModel<Articulo> modeloListaProductos;
	private Articulo productoSeleccionado;
	//JLIST clientes
	private JList<Cliente> listCliente;
	private DefaultListModel<Cliente> modeloListaClientes;
	private Cliente clienteSeleccionado;
	private boolean nuevo;
	//JLIST lineadetalle
	private JList<LineaDetalle> listDetalle;
	private DefaultListModel<LineaDetalle> modeloListaLineaDetalles;
	
	private ArrayList<LineaDetalle>l=new ArrayList<LineaDetalle>();
	private ArrayList<LineaDetalle>lineasEliminadas=new ArrayList<LineaDetalle>();

	LineaDetalle ld=new LineaDetalle();
	private JLabel lblCliente;
	private JButton btAnadirCantidad;
	
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	
	/**
	 * Muestra la ventana para rellenar los datos
	 * @return accion aceptar/cancelar
	 */
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
	
	//getters y setters
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.txtNumero.setText(numero);
	}

	public Date getFechaPago() {
		return fechaPedido;
	}

	public void setFechaPago(Date fechaPago) {
		this.dateChooser.setDate(fechaPago);
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.txtFormaPago.setText(formaPago);
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Articulo getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(Articulo productoSeleccionado) {
		this.listProductos.setSelectedValue(productoSeleccionado, true);
	}

	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.listCliente.setSelectedValue(clienteSeleccionado, true);
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

public ArrayList<LineaDetalle> getL() {
		return l;
	}

	public void setL(ArrayList<LineaDetalle> l) {
		this.l = l;
		for (LineaDetalle lineaDetalle : l) {
			this.modeloListaLineaDetalles.addElement(lineaDetalle);
		}
		
		
	}

public ArrayList<LineaDetalle> getLineasEliminadas() {
		return lineasEliminadas;
	}

	public void setLineasEliminadas(ArrayList<LineaDetalle> lineasEliminadas) {
		this.lineasEliminadas = lineasEliminadas;
		for (LineaDetalle lineaDetalle : l) {
			this.modeloListaLineaDetalles.removeElement(lineaDetalle);
		}
	}
/**
 * Devuelve un pedido
 * @return pedido
 */
public Pedido getPedido() {
		
		Pedido pedido = new Pedido();
		pedido.setNumero(numero);
		pedido.setFormaPago(formaPago);
		pedido.setFechaPedido(fechaPedido);
		pedido.setCliente(clienteSeleccionado);
		pedido.setLineaDetalles(new HashSet<LineaDetalle>(l));
	
		return pedido;
	}
	

	
	/**
	 * Se invoca cuando el usuario ha pulsado en Aceptar. Recoge y valida la información de las cajas de texto
	 * y cierra la ventana
	 */
	private void aceptar() {
		
		if (txtNumero.getText().equals("") || txtFormaPago.getText().equals("") ||
				listCliente.getSelectedValue().equals("null") || listProductos.getSelectedValue().equals("null")
			 ){
			Util.mensajeError("Debes rellenar todos los campos.");
		}
		
		
		numero = txtNumero.getText();
		formaPago = txtFormaPago.getText();
		//cantidad = Integer.parseInt(txtCantidad.getText());
		fechaPedido = dateChooser.getDate();
		clienteSeleccionado = listCliente.getSelectedValue();
		id_cliente=clienteSeleccionado.getId();
		for( int i = 0 ; i < l.size() ; i++ ){
				precio=l.get(i).getPrecio()*cantidad;
				id_producto=l.get(i).getArticulo().getId();
			}

		accion = Accion.ACEPTAR;
		setVisible(false);
	}
	
	/**
	 * Invocado cuando el usuario cancela. Simplemente cierra la ventana
	 */
	private void cancelar() {
		if(!nuevo){
			numero = txtNumero.getText();
			formaPago = txtFormaPago.getText();
			fechaPedido = dateChooser.getDate();
			clienteSeleccionado = listCliente.getSelectedValue();
			id_cliente=clienteSeleccionado.getId();
			for (int i = 0; i < lineasEliminadas.size(); i++) {
				l.add(lineasEliminadas.get(i));
			}
					for( int i = 0 ; i < l.size() ; i++ ){
							precio=l.get(i).getPrecio()*cantidad;
							id_producto=l.get(i).getArticulo().getId();
						}
	}
		
		accion = Accion.CANCELAR;
		setVisible(false);
}
private void inicializar() {
		
		modeloListaProductos= new DefaultListModel<Articulo>();
		listProductos.setModel(modeloListaProductos);
		cargarProductos();
		
		modeloListaClientes= new DefaultListModel<Cliente>();
		listCliente.setModel(modeloListaClientes);
		cargarClientes();
		
		modeloListaLineaDetalles= new DefaultListModel<LineaDetalle>();
		listDetalle.setModel(modeloListaLineaDetalles);
		
			
	}
	private void cargarProductos() {
		
		Query query = HibernateUtil.
			getCurrentSession().createQuery("FROM Articulo");
		List<Articulo> articulos = query.list();
		
		for (Articulo articulo : articulos) {
			modeloListaProductos.addElement(articulo);
		}
	}
	private void cargarClientes() {
		
		Query query = HibernateUtil.
			getCurrentSession().createQuery("FROM Cliente");
		List<Cliente> clientes = query.list();
		
		for (Cliente cliente : clientes) {
			modeloListaClientes.addElement(cliente);
		}
	}
	
	private void cargarDetalles() {
		modeloListaLineaDetalles.clear();
		for( int i = 0 ; i < l.size() ; i++ ){
			   modeloListaLineaDetalles.addElement(l.get(i));
			}
	
	}
	
	/**
	 * Create the dialog.
	 */
	public JPedidos_Alta() {
		setTitle("Datos del Pedido");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancelar();
			}
		});
		this.setModal(true);
		setBounds(100, 100, 731, 494);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lbNumero = new JLabel("Numero Pedido");
		lbNumero.setBounds(78, 72, 112, 14);
		contentPanel.add(lbNumero);
		
		lbFormaPago = new JLabel("Forma Pago:");
		lbFormaPago.setBounds(78, 113, 92, 14);
		contentPanel.add(lbFormaPago);
		
		lbFechaPedido = new JLabel("Fecha Pedido:");
		lbFechaPedido.setBounds(78, 153, 92, 14);
		contentPanel.add(lbFechaPedido);
		
		lbCantidad = new JLabel("Cantidad:");
		lbCantidad.setBounds(412, 275, 64, 14);
		contentPanel.add(lbCantidad);
		
		lbProveedor = new JLabel("Productos:");
		lbProveedor.setBounds(264, 232, 92, 14);
		contentPanel.add(lbProveedor);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(173, 69, 169, 20);
		contentPanel.add(txtNumero);
		txtNumero.setColumns(10);
		
		txtFormaPago = new JTextField();
		txtFormaPago.setColumns(10);
		txtFormaPago.setBounds(173, 107, 169, 20);
		contentPanel.add(txtFormaPago);
		
		txtCantidad = new JTextField();
		txtCantidad.setColumns(10);
		txtCantidad.setBounds(412, 345, 58, 20);
		contentPanel.add(txtCantidad);
		
		lbTituloAlta = new JLabel("Datos del Pedido");
		lbTituloAlta.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbTituloAlta.setBounds(125, 11, 204, 36);
		contentPanel.add(lbTituloAlta);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(176, 153, 166, 20);
		contentPanel.add(dateChooser);
		
		lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(78, 232, 92, 14);
		contentPanel.add(lblCliente);
		
		btAnadirCantidad = new JButton("");
		btAnadirCantidad.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/flecha-verde.gif")));
		btAnadirCantidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearLineaDetalles();
			}
		});
		btAnadirCantidad.setBounds(412, 294, 58, 48);
		contentPanel.add(btAnadirCantidad);
		
		lblNewLabel = new JLabel("Detalle");
		lblNewLabel.setBounds(523, 232, 46, 14);
		contentPanel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 257, 186, 149);
		contentPanel.add(scrollPane);
		
		listCliente = new JList<Cliente>();
		scrollPane.setViewportView(listCliente);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(229, 257, 169, 149);
		contentPanel.add(scrollPane_1);
		
		listProductos = new JList<Articulo>();
		scrollPane_1.setViewportView(listProductos);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(484, 257, 221, 149);
		contentPanel.add(scrollPane_2);
		
		listDetalle = new JList<LineaDetalle>();
		scrollPane_2.setViewportView(listDetalle);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btAceptar = new JButton("Aceptar");
				btAceptar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						aceptar();
					}
				});
				btAceptar.setActionCommand("OK");
				buttonPane.add(btAceptar);
				getRootPane().setDefaultButton(btAceptar);
			}
			{
				JButton btCancelar = new JButton("Cancelar");
				btCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				btCancelar.setActionCommand("Cancel");
				buttonPane.add(btCancelar);
			}
		}
		inicializar();
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}
	public void crearLineaDetalles(){
		LineaDetalle ld=new LineaDetalle();
		cantidad= Integer.parseInt(txtCantidad.getText());
		productoSeleccionado = listProductos.getSelectedValue();
		precio=productoSeleccionado.getPrecio()*cantidad;
		Query query = HibernateUtil.getCurrentSession().createQuery("FROM Articulo");
		List<Articulo> articulos = query.list();
			for (Articulo articulo : articulos) {
				if(articulo.equals(productoSeleccionado)){
					if(articulo.getStock()<cantidad){
						Util.mensajeError("No hay stock suficiente");
						return;
					}
				}
				
			}
		ld.setArticulo(productoSeleccionado);
		ld.setCantidad(cantidad);
		ld.setPrecio(precio);
		
		l.add(ld);
	
		cargarDetalles();
	}
	/*
	private void guardarEliminados(){
		LineaDetalle ld=new LineaDetalle();
		Articulo art=new Articulo();
		
		String linea=listDetalle.getSelectedValue() + "";
		System.out.println(linea);
		String[] separador = linea.split(" ");
		System.out.println(separador[0]);
		System.out.println(separador[1]);
		System.out.println(separador[2]);
		
		art.setNombreArt(separador[0]);
		ld.setArticulo(art);
		ld.setCantidad(Integer.parseInt(separador[1]));
		ld.setPrecio(Float.parseFloat(separador[2]));
		
		lineasEliminadas.add(ld);
		for (int i = 0; i < lineasEliminadas.size(); i++) {
			for( int j = 0 ; j < l.size() ; j++ ){
				  if(lineasEliminadas.get(i).getArticulo().getNombreArt().equals(l.get(i).getArticulo().getNombreArt())){
					  	art.setNombreArt(separador[0]);
					  	art.setId(l.get(i).getArticulo().getId());
						ld.setArticulo(art);
						ld.setCantidad(Integer.parseInt(separador[1]));
						ld.setPrecio(Float.parseFloat(separador[2]));
						lineasEliminadas.set(i, ld);
						
						l.remove(i);
				  }
				}
		}
	
		cargarDetalles();
		for( int j = 0 ; j < l.size() ; j++ ){
			System.out.println("**actuales");
			System.out.println(l.get(j).getArticulo().getNombreArt());
		}
	}*/
}
