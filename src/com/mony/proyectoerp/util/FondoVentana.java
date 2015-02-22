package com.mony.proyectoerp.util;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;

	public class FondoVentana extends JDesktopPane {

		/**
		 * Clase que nos permite poner un fondo a una ventana de java
		 */
		private static final long serialVersionUID = 1L;
		private Image imagen;
		 
		     public FondoVentana() {
		  
		       this.setLayout(null); 
		       this.setToolTipText("NikaGES");
		       this.setBounds(0, 0, 900, 700);
		  
		  try {
		       imagen=ImageIO.read(getClass().getResource("/splashscreen.png"));
		  }
		 catch (IOException e) {
		   e.printStackTrace();
		  }

		  }
		 
		 public void paintComponent(Graphics g){
		  
		     super.paintComponent(g);
		     g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
		     setOpaque(false);
		 }
}
