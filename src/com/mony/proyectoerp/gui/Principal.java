package com.mony.proyectoerp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.hibernate.HibernateException;



import com.mony.proyectoerp.util.HibernateUtil;
import com.mony.proyectoerp.util.Util;
import com.mony.proyectoerp.util.Util.Accion;
/**
 * Clase principal del programa.
 * @author MONY
 * @version 1.0
 */
public class Principal {

	private JFrame frmNikagesGestion;
	private JLabel lbfondo;
	private JLabel lblNewLabel;
	private JButton btAlta;
	private JButton btnPedidos;
	private JButton btnProductos;
	private JButton btnFacturas;
	private JButton btAlmacenes;
	private JButton btEstadisticas;
	private JButton btSalir;
	private JButton btnProveedores;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frmNikagesGestion.setLocationRelativeTo(null);
					window.frmNikagesGestion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		Thread hilo=new Thread (new Runnable(){
			public void run(){
				SplashScreen splash=new SplashScreen();
				splash.setVisible(true);
				try {
					Thread.sleep(3000);
					inicializar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				splash.setVisible(false);
			}
		});
		hilo.start();
		initialize();
	}
	/**
	 * Inicializa la sesion de Hibernate
	 */
	private void inicializar() {
		try {
			HibernateUtil.buildSessionFactory();
			HibernateUtil.openSession();
		} catch (HibernateException he) {
			he.printStackTrace();
			
		}
		
	}
	/**
	 * Abre la ventana Clientes
	 */
	private void clientes() {
		JClientes jCliente = new JClientes();
		if (jCliente.mostrarDialogo() == Accion.CANCELAR)
			return;
	}
	/**
	 * Abre la ventana pedidos
	 */
	private void pedidos() {
		JPedidos jPedido = new JPedidos();
		if (jPedido.mostrarDialogo() == Accion.CANCELAR)
			return;
	}
	/**
	 * Abre la ventana productos
	 */
	private void productos() {
		JProductos jProducto = new JProductos();
		if (jProducto.mostrarDialogo() == Accion.CANCELAR)
			return;
	}
	/**
	 * Abre la ventana facturas
	 */
	private void facturas() {
		JFacturas jFactura = new JFacturas();
		if (jFactura.mostrarDialogo() == Accion.CANCELAR)
			return;
	}
	/**
	 * Abre la ventana Almacenes
	 */
	private void almacenes() {
		JAlmacenes jAlmacen = new JAlmacenes();
		if (jAlmacen.mostrarDialogo() == Accion.CANCELAR)
			return;
	}
	/**
	 * Abre la ventana Proveedores
	 */
	private void proveedores() {
		JProveedores jProveedor = new JProveedores();
		if (jProveedor.mostrarDialogo() == Accion.CANCELAR)
			return;
	}
	/**
	 * Abre la ventana Estadisticas
	 */
	private void estadisticas(){
	
		JEstadisticas jGenerarEstadisticas= new JEstadisticas();
		if(jGenerarEstadisticas.mostrarDialogo() == Accion.CANCELAR)
			return;	
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNikagesGestion = new JFrame();
		frmNikagesGestion.setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/com/mony/proyectoerp/icons/nika.png")));
		frmNikagesGestion.setTitle("NikaGes 1.0");
		frmNikagesGestion.setBounds(0, 0, 900,700);
		frmNikagesGestion.setUndecorated(true);
		frmNikagesGestion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNikagesGestion.getContentPane().setLayout(new BorderLayout(0, 0));
		//poner imagen de fondo
		JPanel objPanel = new JPanelConFondo();
		objPanel.setSize(frmNikagesGestion.getContentPane().getWidth(), frmNikagesGestion.getContentPane().getHeight());
		frmNikagesGestion.getContentPane().add(objPanel);
		objPanel.setLayout(null);
		
		btAlta = new JButton("CLIENTES");
		btAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clientes();
			}
		});
		btAlta.setBackground(Color.WHITE);
		btAlta.setBounds(136, 149, 304, 161);
		btAlta.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/clientes.png")));
		objPanel.add(btAlta);
		
		btnPedidos = new JButton("PEDIDOS");
		btnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pedidos();
			}
		});
		btnPedidos.setBackground(Color.WHITE);
		btnPedidos.setBounds(445, 149, 304, 161);
		btnPedidos.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/pedidos.jpg")));
		objPanel.add(btnPedidos);
		
		btnProductos = new JButton("PRODUCTOS");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productos();
			}
		});
		btnProductos.setBackground(Color.WHITE);
		btnProductos.setForeground(Color.BLACK);
		btnProductos.setBounds(136, 347, 304, 161);
		btnProductos.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/productos.png")));
		objPanel.add(btnProductos);
		
		btnFacturas = new JButton("FACTURAS");
		btnFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facturas();
			}
		});
		btnFacturas.setBackground(Color.WHITE);
		btnFacturas.setBounds(445, 347, 304, 161);
		btnFacturas.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/factura.png")));
		objPanel.add(btnFacturas);
		
		btAlmacenes = new JButton("Almacenes");
		btAlmacenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				almacenes();
			}
		});
		btAlmacenes.setBackground(Color.WHITE);
		btAlmacenes.setBounds(289, 552, 217, 73);
		btAlmacenes.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/almacen.png")));
		objPanel.add(btAlmacenes);
		
		btEstadisticas = new JButton("Estadisticas");
		btEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				estadisticas();
			}
		});
		btEstadisticas.setBackground(Color.WHITE);
		btEstadisticas.setBounds(532, 552, 217, 73);
		btEstadisticas.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/estadistica.png")));
		objPanel.add(btEstadisticas);
		
		btSalir = new JButton("");
		btSalir.setBackground(Color.WHITE);
		btSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btSalir.setBounds(795, 604, 79, 47);
		btSalir.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/salir.png")));
		objPanel.add(btSalir);
		
		btnProveedores = new JButton("Proveedores");
		btnProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 proveedores();
			}
		});
		btnProveedores.setBackground(Color.WHITE);
		btnProveedores.setBounds(40, 552, 217, 73);
		btnProveedores.setIcon(new ImageIcon(Principal.class.getResource("/com/mony/proyectoerp/icons/proveedor.png")));
		objPanel.add(btnProveedores);
		frmNikagesGestion.getContentPane().validate();

		
		
}
}
