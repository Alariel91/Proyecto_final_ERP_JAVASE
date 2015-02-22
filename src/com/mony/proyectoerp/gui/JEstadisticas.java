package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.mony.proyectoerp.util.Util.Accion;
import com.mysql.jdbc.Connection;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.UIManager;

public class JEstadisticas extends JDialog {

	/**
	 * Genera estadisticas con graficos de jasperreport.
	 * @author MONY
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	public static Connection conexion;
	/**
	 * Muestra la ventana para rellenar los datos
	 * @return accion aceptar/cancelar
	 */
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
/**
 * Genera un grafico cargando el jasper que le asignemos
 */
	private void generarGrafico1(){
		JasperReport report;
		try {
			report = (JasperReport) JRLoader.loadObject("NIKA.jasper");
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(report,null,conexion);
		
			JasperViewer.viewReport(jasperPrint,false);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Genera un grafico cargando el jasper que le asignemos
	 */
	private void generarGrafico2(){
		JasperReport report;
		try {
			report = (JasperReport) JRLoader.loadObject("nikacronologico.jasper");
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(report,null,conexion);
		
			JasperViewer.viewReport(jasperPrint,false);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Conecta a la base de datos para poder generar los graficos.
	 * @return conexion
	 */
	public static Connection conectar() {
		
		Properties configuracion = new Properties();
		
		try {
			configuracion.load(new FileInputStream("configuracion.props"));
			
			String driver = configuracion.getProperty("driver");
			String protocolo = configuracion.getProperty("protocolo");
			String servidor = configuracion.getProperty("servidor");
			String puerto = configuracion.getProperty("puerto");
			String baseDatos = configuracion.getProperty("basedatos");
			String usuario = configuracion.getProperty("usuario");
			String contrasena = configuracion.getProperty("contrasena");
		
			Class.forName(driver).newInstance();
			conexion = (Connection) DriverManager.getConnection(
					protocolo + 
					servidor + ":" + puerto +
					"/" + baseDatos, 
					usuario, contrasena);

			return conexion;
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "Error al leer el fichero de configuración");
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Error al leer el fichero de configuración");
		} catch (ClassNotFoundException cnfe) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar el driver de la Base de Datos");
		} catch (SQLException sqle) {
			JOptionPane.showMessageDialog(null, "No se ha podido conectar con la Base de Datos");
			sqle.printStackTrace();
		} catch (InstantiationException ie) {
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return conexion;
	}

	/**
	 * Create the dialog.
	 */
	public JEstadisticas() {
		setBackground(UIManager.getColor("Button.darkShadow"));
		setBounds(100, 100, 786, 451);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JButton btGrafico1 = new JButton("Ventas/Cliente");
			btGrafico1.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/ventas.png")));
			btGrafico1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarGrafico1();
				}
			});
			btGrafico1.setPreferredSize(new Dimension(350, 23));
			contentPanel.add(btGrafico1, BorderLayout.WEST);
		}
		{
			JButton btGrafico2 = new JButton("Ventas/Cliente cronol\u00F3gicas.");
			btGrafico2.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/stockmarket.png")));
			btGrafico2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					generarGrafico2();
				}
			});
			btGrafico2.setPreferredSize(new Dimension(50, 23));
			contentPanel.add(btGrafico2, BorderLayout.CENTER);
		}
		conectar();
	}

}
