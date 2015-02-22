package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
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

import com.mony.proyectoerp.util.Util.Accion;

import javax.swing.JLabel;

import java.awt.Font;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JReport extends JDialog {

	/**
	 * Clase que se utiliza para generar informes.
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Accion accion;
	JDateChooser dateChooser;
	/**
	 * Muestra la ventana para rellenar los datos
	 * @return accion aceptar/cancelar
	 */
	public Accion mostrarDialogo() {
		setVisible(true);
		return accion;
	}
	/**
	 * Permite generar un informe enviando la fecha que seleccionemos 
	 * de un dateChooser.
	 */
	private void generarInforme(){
		
		
		try {
JasperReport report;	
			report = (JasperReport) JRLoader.loadObject("INFORMEPEDIDOFECHA.jasper");
			JasperPrint jasperPrint;
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("fecha_pedido", dateChooser.getDate());
			jasperPrint = JasperFillManager.fillReport(report,parametros,JEstadisticas.conexion);
		
			JasperViewer.viewReport(jasperPrint,false);
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	
	}
	
	private void cancelar(){
		accion = Accion.CANCELAR;
		setVisible(false);
	}
	/**
	 * Create the dialog.
	 */
	public JReport() {
		setBounds(100, 100, 376, 115);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Selecciona la fecha del pedido:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			contentPanel.add(lblNewLabel);
		}
		{
			dateChooser = new JDateChooser();
			contentPanel.add(dateChooser);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						generarInforme();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		JEstadisticas.conectar();
	}

}
