package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import com.mony.proyectoerp.base.Cliente;
import com.mony.proyectoerp.util.Util.Accion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JClientes_Alta extends JDialog {

	/**
	 * Formulario de Alta de Clientes
	 *  @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JLabel lbNombre;
	
	private JLabel lbApellidos;
	private JLabel lblDni;
	private JLabel lblDireccion;
	private JLabel lblTelefono;
	private JLabel lbTituloAlta;
	
	private JTextField txtDireccion;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDni;
	private JTextField txtTelefono;
	
	
	private String nombre;
	private String apellidos;
	private String dni;
	private String direccion;
	private String telefono;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.txtApellidos.setText(apellidos);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.txtDni.setText(dni);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.txtDireccion.setText(direccion);
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.txtTelefono.setText(telefono);
	}
	
	/**
	 * Devuelve un cliente.
	 * @return cliente.
	 */
	public Cliente getCliente() {
		
		Cliente cliente = new Cliente();
		cliente.setNombreCli(nombre);
		cliente.setApellidos(apellidos);
		cliente.setDni(dni);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		
		return cliente;
	}
	/**
	 * Se invoca cuando el usuario ha pulsado en Aceptar. Recoge y valida la información de las cajas de texto
	 * y cierra la ventana
	 */
	private void aceptar() {
		
		if (txtNombre.getText().equals("") || txtApellidos.getText().equals("") || 
				txtDni.getText().equals("") || txtDireccion.getText().equals("") || txtTelefono.getText().equals(""))
			return;
		
		
		nombre = txtNombre.getText();
		apellidos = txtApellidos.getText();
		dni =  txtDni.getText();
		direccion = txtDireccion.getText();
		telefono=txtTelefono.getText();
		
		accion = Accion.ACEPTAR;
		setVisible(false);
	}
	
	/**
	 * Invocado cuando el usuario cancela. Simplemente cierra la ventana
	 */
	private void cancelar() {
		
		nombre = txtNombre.getText();
		apellidos = txtApellidos.getText();
		dni =  txtDni.getText();
		direccion = txtDireccion.getText();
		telefono=txtTelefono.getText();
		
		accion = Accion.CANCELAR;
		setVisible(false);
	}
	

	/**
	 * Create the dialog.
	 */
	public JClientes_Alta() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancelar();
			}
		});
		this.setModal(true);
		setBounds(100, 100, 346, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lbNombre = new JLabel("Nombre:");
		lbNombre.setBounds(30, 77, 92, 14);
		contentPanel.add(lbNombre);
		
		lbApellidos = new JLabel("Apellidos:");
		lbApellidos.setBounds(30, 115, 92, 14);
		contentPanel.add(lbApellidos);
		
		lblDni = new JLabel("Dni:");
		lblDni.setBounds(30, 155, 92, 14);
		contentPanel.add(lblDni);
		
		lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(30, 198, 102, 14);
		contentPanel.add(lblDireccion);
		
		lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(30, 242, 92, 14);
		contentPanel.add(lblTelefono);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(125, 71, 169, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(125, 109, 169, 20);
		contentPanel.add(txtApellidos);
		
		txtDni = new JTextField();
		txtDni.setColumns(10);
		txtDni.setBounds(125, 149, 169, 20);
		contentPanel.add(txtDni);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(125, 192, 169, 20);
		contentPanel.add(txtDireccion);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(125, 236, 169, 20);
		contentPanel.add(txtTelefono);
		
		lbTituloAlta = new JLabel("Datos del Cliente");
		lbTituloAlta.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbTituloAlta.setBounds(78, 11, 204, 36);
		contentPanel.add(lbTituloAlta);
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
	}

	
}
