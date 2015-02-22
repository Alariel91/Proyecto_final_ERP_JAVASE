package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mony.proyectoerp.base.Proveedor;
import com.mony.proyectoerp.util.Util.Accion;

public class JProveedores_Alta extends JDialog {
	/**
	 * Formulario de Alta de proveedores
	 *  @author Monica Saenz
	 *  @version 1.0
	 */
	private final JPanel contentPanel = new JPanel();
	private Accion accion;


	private JTextField txtNombre;
	private JTextField txtCif;
	private JTextField txtDireccion;
	private JTextField txtFormaPago;
	private String nombre;
	private String cif;
	private String direccion;
	private String formapago;
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

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.txtCif.setText(cif);
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.txtDireccion.setText(direccion);
	}

	public String getFormapago() {
		return formapago;
	}

	public void setFormapago(String formapago) {
		this.txtFormaPago.setText(formapago);
	}

	/**
	 * Devuelve un proveedor
	 * @return proveedor.
	 */
	public Proveedor getProveedor() {
		
		Proveedor proveedor = new Proveedor();
		proveedor.setNombrePro(nombre);
		proveedor.setCif(cif);
		proveedor.setDireccion(direccion);
		proveedor.setFormaPago(formapago);
		return proveedor;
	}
	

	/**
	 * Se invoca cuando el usuario ha pulsado en Aceptar. Recoge y valida la información de las cajas de texto
	 * y cierra la ventana
	 */
	private void aceptar() {
		
		if (txtNombre.getText().equals("") ||  txtCif.getText().equals("")||  txtDireccion.getText().equals("")||txtFormaPago.getText().equals("") )
			return;

		nombre = txtNombre.getText();
		cif=txtCif.getText();
		direccion= txtDireccion.getText();
		formapago=txtFormaPago.getText();
		accion= Accion.ACEPTAR;
		setVisible(false);
	}
	
	/**
	 * Invocado cuando el usuario cancela. Simplemente cierra la ventana
	 */
	private void cancelar() {
		
		nombre = txtNombre.getText();
		cif=txtCif.getText();
		direccion= txtDireccion.getText();
		formapago=txtFormaPago.getText();
		
		accion = Accion.CANCELAR;
		setVisible(false);
	}
	

	/**
	 * Create the dialog.
	 */
	public JProveedores_Alta() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancelar();
			}
		});
		this.setModal(true);
		setBounds(100, 100, 339, 295);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblDatosDelProveedor = new JLabel("Datos del Proveedor");
			lblDatosDelProveedor.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblDatosDelProveedor.setBounds(74, 11, 204, 36);
			contentPanel.add(lblDatosDelProveedor);
		}
		{
			JLabel label = new JLabel("Nombre:");
			label.setBounds(26, 77, 92, 14);
			contentPanel.add(label);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(121, 71, 169, 20);
			contentPanel.add(txtNombre);
		}
		{
			txtCif = new JTextField();
			txtCif.setColumns(10);
			txtCif.setBounds(121, 102, 169, 20);
			contentPanel.add(txtCif);
		}
		{
			JLabel lblCif = new JLabel("CIF:");
			lblCif.setBounds(26, 108, 92, 14);
			contentPanel.add(lblCif);
		}
		{
			JLabel label = new JLabel("Direccion:");
			label.setBounds(26, 151, 102, 14);
			contentPanel.add(label);
		}
		{
			txtDireccion = new JTextField();
			txtDireccion.setColumns(10);
			txtDireccion.setBounds(121, 145, 169, 20);
			contentPanel.add(txtDireccion);
		}
		{
			txtFormaPago = new JTextField();
			txtFormaPago.setColumns(10);
			txtFormaPago.setBounds(121, 189, 169, 20);
			contentPanel.add(txtFormaPago);
		}
		{
			JLabel lblFormaDePago = new JLabel("Forma de pago:");
			lblFormaDePago.setBounds(26, 195, 92, 14);
			contentPanel.add(lblFormaDePago);
		}
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
