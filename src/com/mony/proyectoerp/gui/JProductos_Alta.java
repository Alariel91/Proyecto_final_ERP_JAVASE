package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;

import com.mony.proyectoerp.base.Almacen;
import com.mony.proyectoerp.base.Articulo;
import com.mony.proyectoerp.base.Categoria;
import com.mony.proyectoerp.base.Proveedor;
import com.mony.proyectoerp.base.TipoImpuesto;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;
import javax.swing.JScrollPane;

public class JProductos_Alta extends JDialog {

	/**
	 * Formulario de Alta de productos
	 *  @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JLabel lbNombre;
	
	private JLabel lbPrecio;
	private JLabel lblDescripcion;
	private JLabel lblStock;
	private JLabel lbProveedor;
	private JLabel lbTituloAlta;
	
	private JTextField txtStock;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JTextField txtDescripcion;
	//------------------------
	private String nombre;
	private float precio;
	private String descripcion;
	private int stock;
	//JLIST PROVEEDORES
	private JList<Proveedor> listProveedor;
	private DefaultListModel<Proveedor> modeloListaProveedores;
	private Proveedor proveedorSeleccionado;
	//JLIST CATEGORIAS
	private JList<Categoria> listCategoria;
	private DefaultListModel<Categoria> modeloListaCategorias;
	private Categoria categoriaSeleccionado;
	//JLIST IMPUESTOS
	private JList<TipoImpuesto> listImpuesto;
	private DefaultListModel<TipoImpuesto> modeloListaTipoImpuestos;
	private TipoImpuesto tipoImpuestoSeleccionado;
	//JLIST ALMACEN
	private JList<Almacen> listAlmacen;
	private DefaultListModel<Almacen> modeloListaAlmacenes;
	private Almacen almacenSeleccionado;
	//------------------------
	
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private boolean nuevo;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	/**
	 * Muestra la ventana para rellenar los datos
	 * @return accion aceptar/cancelar
	 */
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
	
	//getters y setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.txtNombre.setText(nombre);
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.txtPrecio.setText(String.valueOf(precio));
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.txtDescripcion.setText(descripcion);
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.txtStock.setText(String.valueOf(stock));
	}

	public Proveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
		this.listProveedor.setSelectedValue(proveedorSeleccionado, true);
	}

	public Categoria getCategoriaSeleccionado() {
		return categoriaSeleccionado;
	}

	public void setCategoriaSeleccionado(Categoria categoriaSeleccionado) {
		this.listCategoria.setSelectedValue(categoriaSeleccionado, true);
	}

	public TipoImpuesto getTipoImpuestoSeleccionado() {
		return tipoImpuestoSeleccionado;
	}

	public void setTipoImpuestoSeleccionado(TipoImpuesto tipoImpuestoSeleccionado) {
		this.listImpuesto.setSelectedValue(tipoImpuestoSeleccionado, true);
	}

	public Almacen getAlmacenSeleccionado() {
		return almacenSeleccionado;
	}

	public void setAlmacenSeleccionado(Almacen almacenSeleccionado) {
		this.listAlmacen.setSelectedValue(almacenSeleccionado, true);
	}
/**
 * Devuelve un articulo
 * @return articulo
 */
	public Articulo getArticulo() {
		
		Articulo articulo = new Articulo();
		articulo.setNombreArt(nombre);
		articulo.setPrecio(precio);
		articulo.setDescripcion(descripcion);
		articulo.setStock(stock);
		articulo.setProveedor(proveedorSeleccionado);
		articulo.setCategoria(categoriaSeleccionado);
		articulo.setAlmacen(almacenSeleccionado);
		articulo.setTipoImpuesto(tipoImpuestoSeleccionado);
		
		
		return articulo;
	}
	

	/**
	 * Se invoca cuando el usuario ha pulsado en Aceptar. Recoge y valida la información de las cajas de texto
	 * y cierra la ventana
	 */
	private void aceptar() {
		
		if (txtNombre.getText().equals("") || txtPrecio.getText().equals("") || 
				txtDescripcion.getText().equals("") || txtStock.getText().equals("")||listProveedor.getSelectedValue().equals("null") ||
				listCategoria.getSelectedValue().equals("null") || listAlmacen.getSelectedValue().equals("null") 
				|| listImpuesto.getSelectedValue().equals("null") ){
			Util.mensajeError("Debes rellenar todos los campos.");
		}
		
		if(!Util.isNumeric(txtPrecio.getText()))
			Util.mensajeError("El precio debe de ser un número.");
		
		if(!Util.isNumeric(txtStock.getText()))
			Util.mensajeError("El stock debe de ser un número.");
		
		nombre = txtNombre.getText();
		precio = Float.parseFloat(txtPrecio.getText());
		descripcion = txtDescripcion.getText();
		stock = Integer.parseInt(txtStock.getText());
		almacenSeleccionado = listAlmacen.getSelectedValue();
		categoriaSeleccionado = listCategoria.getSelectedValue();
		tipoImpuestoSeleccionado = listImpuesto.getSelectedValue();
		proveedorSeleccionado = listProveedor.getSelectedValue();

		
		accion = Accion.ACEPTAR;
		setVisible(false);
	}
	
	/**
	 * Invocado cuando el usuario cancela. Simplemente cierra la ventana
	 */
	private void cancelar() {
		if(!nuevo){
		nombre = txtNombre.getText();
		precio = Float.parseFloat(txtPrecio.getText());
		descripcion = txtDescripcion.getText();
		stock =Integer.parseInt(txtStock.getText());
		almacenSeleccionado = listAlmacen.getSelectedValue();
		categoriaSeleccionado = listCategoria.getSelectedValue();
		tipoImpuestoSeleccionado = listImpuesto.getSelectedValue();
		proveedorSeleccionado = listProveedor.getSelectedValue();
		}
		accion = Accion.CANCELAR;
		setVisible(false);
	}
	/**
	 * Inicializa todos los Jlist
	 */
private void inicializar() {
		
		modeloListaProveedores= new DefaultListModel<Proveedor>();
		listProveedor.setModel(modeloListaProveedores);
		cargarProveedores();
			
		modeloListaAlmacenes= new DefaultListModel<Almacen>();
		listAlmacen.setModel(modeloListaAlmacenes);
		cargarAlmacenes();
		
		modeloListaCategorias= new DefaultListModel<Categoria>();
		listCategoria.setModel(modeloListaCategorias);
		cargarCategorias();
		
		modeloListaTipoImpuestos= new DefaultListModel<TipoImpuesto>();
		listImpuesto.setModel(modeloListaTipoImpuestos);
		cargarTipoImpuestos();
	}
/**
 * Carga los proveedores en el Jlist
 */
	private void cargarProveedores() {
		
		Query query = HibernateUtil.
			getCurrentSession().createQuery("FROM Proveedor");
		List<Proveedor> proveedores = query.list();
		
		for (Proveedor proveedor : proveedores) {
			modeloListaProveedores.addElement(proveedor);
		}
	}
	/**
	 * Carga los almacenes en el Jlist
	 */
	private void cargarAlmacenes() {
			
			Query query = HibernateUtil.
				getCurrentSession().createQuery("FROM Almacen");
			List<Almacen> Almacenes = query.list();
			
			for (Almacen Almacen : Almacenes) {
				modeloListaAlmacenes.addElement(Almacen);
			}
		}
	/**
	 * Carga las categorias en el Jlist
	 */
	private void cargarCategorias() {
		
		Query query = HibernateUtil.
			getCurrentSession().createQuery("FROM Categoria");
		List<Categoria> categorias = query.list();
		
		for (Categoria categoria : categorias) {
			modeloListaCategorias.addElement(categoria);
		}
	}
	/**
	 * Carga los tipos de impuestos en el Jlist
	 */
	private void cargarTipoImpuestos() {
		
		Query query = HibernateUtil.
			getCurrentSession().createQuery("FROM TipoImpuesto");
		List<TipoImpuesto> tipoimpuestos = query.list();
		
		for (TipoImpuesto tipoimpuesto : tipoimpuestos) {
			modeloListaTipoImpuestos.addElement(tipoimpuesto);
		}
	}

	/**
	 * Create the dialog.
	 */
	public JProductos_Alta() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancelar();
			}
		});
		this.setModal(true);
		setBounds(100, 100, 565, 427);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lbNombre = new JLabel("Nombre:");
		lbNombre.setBounds(30, 77, 92, 14);
		contentPanel.add(lbNombre);
		
		lbPrecio = new JLabel("Precio:");
		lbPrecio.setBounds(30, 115, 92, 14);
		contentPanel.add(lbPrecio);
		
		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(30, 155, 92, 14);
		contentPanel.add(lblDescripcion);
		
		lblStock = new JLabel("Stock:");
		lblStock.setBounds(30, 198, 102, 14);
		contentPanel.add(lblStock);
		
		lbProveedor = new JLabel("Proveedor:");
		lbProveedor.setBounds(384, 40, 92, 14);
		contentPanel.add(lbProveedor);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(125, 71, 169, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(125, 109, 169, 20);
		contentPanel.add(txtPrecio);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(125, 149, 169, 20);
		contentPanel.add(txtDescripcion);
		
		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(125, 192, 169, 20);
		contentPanel.add(txtStock);
		
		lbTituloAlta = new JLabel("Datos del Producto");
		lbTituloAlta.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbTituloAlta.setBounds(78, 11, 204, 36);
		contentPanel.add(lbTituloAlta);
		
		lblNewLabel = new JLabel("Categor\u00EDa:");
		lblNewLabel.setBounds(392, 192, 67, 14);
		contentPanel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Tipo de Impuesto:");
		lblNewLabel_1.setBounds(30, 240, 102, 14);
		contentPanel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Almacen:");
		lblNewLabel_2.setBounds(160, 240, 79, 14);
		contentPanel.add(lblNewLabel_2);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(160, 255, 150, 90);
		contentPanel.add(scrollPane);
		
		listAlmacen = new JList<Almacen>();
		scrollPane.setViewportView(listAlmacen);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 255, 131, 71);
		contentPanel.add(scrollPane_1);
		
		listImpuesto = new JList<TipoImpuesto>();
		scrollPane_1.setViewportView(listImpuesto);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(338, 210, 174, 135);
		contentPanel.add(scrollPane_2);
		
		listCategoria = new JList<Categoria>();
		scrollPane_2.setViewportView(listCategoria);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(340, 61, 172, 126);
		contentPanel.add(scrollPane_3);
		
		listProveedor = new JList<Proveedor>();
		scrollPane_3.setViewportView(listProveedor);
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
}
