package com.mony.proyectoerp.gui;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
	 
	public class JPanelConFondo extends JPanel {
	 

	    /**
		 * Permite poner un fondo a un panel
		 * @author MONY
		 * @version 1.0
		 */
		private static final long serialVersionUID = 1L;

		public JPanelConFondo() {
	    	super();
	    }
	 
	    @Override
	    public void paintComponent(Graphics g) {
	    Dimension tam = getSize();
	    ImageIcon imagen = new ImageIcon("fondo.png");
	    g.drawImage(imagen.getImage(), 0, 0, tam.width, tam.height, null);
	    setOpaque(false);
	    super.paintComponent(g);
	    }
	     
	    }