package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;

import com.mony.proyectoerp.base.Factura;
import com.mony.proyectoerp.beans.tablaFacturas;
import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util.Accion;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class JFacturas extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	private tablaFacturas tablaFacturas;
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
	public JFacturas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFacturas.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		this.setModal(true);
		setTitle("Facturas");
		setBounds(0, 0, 800,600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tablaFacturas = new tablaFacturas();
				tablaFacturas.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						  if (e.getClickCount() == 2) {
							  String nombrearchivo=tablaFacturas.getFacturaSeleccionado().getSerie();
							  String path = nombrearchivo+".pdf";
					          try {
								Desktop.getDesktop().open(new File ("facturas/"+path));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						  }
					}
				});
				scrollPane.setViewportView(tablaFacturas);
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
	 * Carga todos las facturas desde Hibernate
	 */
		private void cargarDatos() {	
			// Prepara y ejecuta la consulta
				Query query = HibernateUtil.getCurrentSession().createQuery("FROM Factura");
				List<Factura> facturas = query.list();
			// Muestra la lista en la JTable
				tablaFacturas.listar(facturas);
	
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
			Query query = HibernateUtil.getCurrentSession().createQuery("SELECT f FROM Factura f WHERE f.nombreFac = :nombre OR f.numero = :numero"
					+ " OR f.serie = :serie" + " OR f.lugar = :lugar");
			query.setParameter("nombre", filtro);
			query.setParameter("numero", filtro);
			query.setParameter("serie", filtro);
			query.setParameter("lugar", filtro);
			
			

			List<Factura> facturas = query.list();
			tablaFacturas.listar(facturas);
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
