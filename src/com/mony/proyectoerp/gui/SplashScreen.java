package com.mony.proyectoerp.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import com.mony.proyectoerp.util.FondoVentana;

/**
 * Clase que muestra una imagen antes de inicializar el programa.
 * @author MONY
 *	@version 1.0
 */

public class SplashScreen extends JFrame {

	private JPanel contentPane;
	private FondoVentana fondo;

	/**
	 * Create the frame.
	 */
	public SplashScreen() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		fondo = new FondoVentana();
        this.add(fondo);
	}
}
