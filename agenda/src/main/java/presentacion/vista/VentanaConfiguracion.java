package presentacion.vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VentanaConfiguracion extends JFrame {

	private static final long serialVersionUID = 1L;
	private static VentanaConfiguracion INSTANCE;
	private JPanel contentPane;
	private JTextField textUsuario;
//	private JTextField textContraseña;
	private JPasswordField textContraseña;
	private JTextField textBaseDeDatos;
	private JButton btnConectar;

	public static VentanaConfiguracion getInstance() {
		if (INSTANCE == null)
			INSTANCE = new VentanaConfiguracion();

		return INSTANCE;
	}

	public VentanaConfiguracion() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);

				cerrar();
			}
		});
		setBounds(100, 100, 450, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(false);

		JLabel lblBaseDeDatos = new JLabel("Base de Datos");
		lblBaseDeDatos.setBounds(94, 35, 100, 20);
		contentPane.add(lblBaseDeDatos);

		textBaseDeDatos = new JTextField();
		textBaseDeDatos.setColumns(10);
		textBaseDeDatos.setBounds(201, 35, 120, 20);
		contentPane.add(textBaseDeDatos);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(111, 95, 80, 20);
		contentPane.add(lblUsuario);

		textUsuario = new JTextField();
		textUsuario.setBounds(201, 95, 120, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);

		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setBounds(111, 137, 80, 20);
		contentPane.add(lblContraseña);

		textContraseña = new JPasswordField();
		textContraseña.setColumns(10);
		textContraseña.setBounds(201, 137, 120, 20);
		contentPane.add(textContraseña);

		btnConectar = new JButton("Conectar");
		btnConectar.setBounds(170, 181, 102, 39);
		contentPane.add(btnConectar);
	}
	
	public void mostrarVentana() {
		this.setVisible(true);
	}

	public JTextField getTextUsuario() {
		return textUsuario;
	}

	public JTextField getTextContraseña() {
		return textContraseña;
	}

	public JTextField getTextBaseDeDatos() {
		return textBaseDeDatos;
	}

	public JButton getBtnConectar() {
		return btnConectar;
	}

	public void cerrar() {
		this.textUsuario.setText(null);
		this.textContraseña.setText(null);
		this.textBaseDeDatos.setText(null);
		this.dispose();
	}
}
