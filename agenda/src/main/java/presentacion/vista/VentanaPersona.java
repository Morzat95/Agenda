package presentacion.vista;

import java.awt.Choice;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.log.SysoCounter;
import com.toedter.calendar.JDateChooser;

import dto.PersonaDTO;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JDateChooser fechaCumpleanios;
	private JComboBox listTipoDeContacto;
	private JButton btnAgregarPersona;
	private JButton btnEditarPersona;
	private static VentanaPersona INSTANCE;
	
	public static VentanaPersona getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaPersona();
		
		return INSTANCE;
	}

	private VentanaPersona() 
	{
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				
				restoreDefaultForm();
			}
		});
		
		setBounds(100, 100, 384, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 380, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombreYApellido = new JLabel("Nombre y apellido");
		lblNombreYApellido.setBounds(10, 11, 113, 14);
		panel.add(lblNombreYApellido);
		
		JLabel lblTelfono = new JLabel("Telefono");
		lblTelfono.setBounds(10, 52, 113, 14);
		panel.add(lblTelfono);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 93, 113, 14);
		panel.add(lblEmail);
		
		JLabel lblFechaCumpleanios = new JLabel("Fecha de Nacimiento");
		lblFechaCumpleanios.setBounds(10, 134, 125, 14);
		panel.add(lblFechaCumpleanios);
		
		JLabel lblTipoDeContacto = new JLabel("Tipo de Contacto");
		lblTipoDeContacto.setBounds(10, 175, 125, 14);
		panel.add(lblTipoDeContacto);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(140, 8, 164, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(140, 49, 164, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(140, 90, 164, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		fechaCumpleanios = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		fechaCumpleanios.setBounds(204, 131, 100, 20);
		panel.add(fechaCumpleanios);
		
		listTipoDeContacto = new JComboBox();
		listTipoDeContacto.setBounds(140, 171, 164, 20);
		panel.add(listTipoDeContacto);
		
		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setBounds(208, 212, 89, 23);
		panel.add(btnAgregarPersona);
		
		btnEditarPersona = new JButton("Editar");
		btnEditarPersona.setBounds(208, 212, 89, 23);
		panel.add(btnEditarPersona);
		
		this.setVisible(false);
	}
	
	public void mostrarVentana()
	{
		this.setVisible(true);
	}
	
	public JTextField getTxtNombre() 
	{
		return txtNombre;
	}

	public JTextField getTxtTelefono() 
	{
		return txtTelefono;
	}
	
	public JTextField getTxtEmail()
	{
		return txtEmail;
	}

	public Date getFechaCumpleanio()
	{
		return this.fechaCumpleanios.getDate(); //Sun Mar 01 01:34:24 ART 2020
	}
	
	public JComboBox getListTipoDeTrabajo()
	{
		return this.listTipoDeContacto;
	}
	
	public JButton getBtnAgregarPersona() 
	{
		return btnAgregarPersona;
	}
	
	public JButton getBtnEditarPersona() 
	{
		return btnEditarPersona;
	}
	
	public void llenarFormulario(PersonaDTO persona_a_editar) {
		this.txtNombre.setText(persona_a_editar.getNombre());
		this.txtTelefono.setText(persona_a_editar.getTelefono());
		this.txtEmail.setText(persona_a_editar.getEmail());
		this.fechaCumpleanios.setDate(new Date(persona_a_editar.getFechaCumpleanio().getTime()));	
		this.btnAgregarPersona.setVisible(false);
		this.btnEditarPersona.setVisible(true);
	}
	
	private void restoreDefaultForm() {
		this.btnAgregarPersona.setVisible(true);
		this.btnEditarPersona.setVisible(false);
	}

	public void cerrar()
	{
		this.txtNombre.setText(null);
		this.txtTelefono.setText(null);
		this.txtEmail.setText(null);
		this.fechaCumpleanios.setDate(null);
		restoreDefaultForm();
		this.dispose();
	}
	
}

