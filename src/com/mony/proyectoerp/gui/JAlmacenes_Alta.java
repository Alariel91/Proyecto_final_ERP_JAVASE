package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;




import com.mony.proyectoerp.base.Almacen;
import com.mony.proyectoerp.util.Util.Accion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class JAlmacenes_Alta extends JDialog {

	/**
	 * Formulario de Alta de almacenes
	 *  @author Monica Saenz
	 *  @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private JLabel lbNombre;
	private JTextField txtNombre;

	private String nombre;

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
	/**
	 * Devuelve un almacen
	 * @return almacen
	 */
	
	public Almacen getAlmacen() {
		
		Almacen almacen = new Almacen();
		almacen.setNombreAlm(nombre);
		
		return almacen;
	}
	//metodo para prueba junit
	public Almacen getAlmacen(String nombre) {
			
			Almacen almacen = new Almacen();
			almacen.setNombreAlm(nombre);
			
			return almacen;
		}
	/**
	 * Se invoca cuando el usuario ha pulsado en Aceptar. Recoge y valida la información de las cajas de texto
	 * y cierra la ventana
	 */
	private void aceptar() {
		
		if (txtNombre.getText().equals("") )
			return;

		nombre = txtNombre.getText();
	
		
		accion = Accion.ACEPTAR;
		setVisible(false);
	}
	
	/**
	 * Invocado cuando el usuario cancela. Simplemente cierra la ventana
	 */
	private void cancelar() {
		
		nombre = txtNombre.getText();

		accion = Accion.CANCELAR;
		setVisible(false);
	}
	

	/**
	 * Create the dialog.
	 */
	public JAlmacenes_Alta() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JAlmacenes_Alta.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cancelar();
			}
		});
		this.setModal(true);
		setBounds(100, 100, 336, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lbNombre = new JLabel("Nombre:");
		lbNombre.setBounds(30, 77, 92, 14);
		contentPanel.add(lbNombre);
		
		
		txtNombre = new JTextField();
		txtNombre.setBounds(125, 71, 169, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		{
			JLabel lblDatosDeAlmacen = new JLabel("Datos de Almac\u00E9n");
			lblDatosDeAlmacen.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblDatosDeAlmacen.setBounds(82, 25, 177, 14);
			contentPanel.add(lblDatosDeAlmacen);
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
