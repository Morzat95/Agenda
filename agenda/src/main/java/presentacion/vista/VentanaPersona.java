package presentacion.vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;

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
import dto.TipoContactoDTO;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JDateChooser fechaCumpleanios;
	private JComboBox<Object> listTipoDeContacto;
	private JTextField txtCalle;
	private JTextField txtAltura;
	private JTextField txtPiso;
	private JTextField txtDepartamento;
	private JComboBox<Object> listLocalidad;	
	
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
		
		setBounds(100, 100, 410, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 390, 350);
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
		
		JLabel lblCalle = new JLabel("Calle");
		lblCalle.setBounds(10, 216, 125, 14);
		panel.add(lblCalle);
		
		JLabel lblAltura = new JLabel("Altura");
		lblAltura.setBounds(10, 257, 125, 14);
		panel.add(lblAltura);
		
		JLabel lblPiso = new JLabel("Piso");
		lblPiso.setBounds(10, 298, 125, 14);
		panel.add(lblPiso);
		
		JLabel lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setBounds(10, 339, 125, 14);
		panel.add(lblDepartamento);
		
		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 380, 125, 14);
		panel.add(lblLocalidad);
			
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
		
		listTipoDeContacto = new JComboBox<Object>();
		listTipoDeContacto.setBounds(140, 171, 164, 20);
		panel.add(listTipoDeContacto);
		
		txtCalle = new JTextField();
		txtCalle.setBounds(140, 90, 164, 20);
		panel.add(txtEmail);
		txtCalle.setColumns(10);
		
		txtAltura = new JTextField();
		txtAltura.setBounds(140, 90, 164, 20);
		panel.add(txtEmail);
		txtAltura.setColumns(10);
		
		txtPiso = new JTextField();
		txtPiso.setBounds(140, 90, 164, 20);
		panel.add(txtPiso);
		txtPiso.setColumns(10);
		
		txtDepartamento = new JTextField();
		txtDepartamento.setBounds(140, 90, 164, 20);
		panel.add(txtDepartamento);
		txtDepartamento.setColumns(10);
		
		listLocalidad = new JComboBox<Object>();
		listLocalidad.setBounds(140, 380, 164, 20);
		panel.add(listLocalidad);
		
		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setBounds(208, 780, 89, 23);
		panel.add(btnAgregarPersona);
		
		btnEditarPersona = new JButton("Editar");
		btnEditarPersona.setBounds(208, 780, 89, 23);
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
	
	public JComboBox<Object> getListTipoDeContacto()
	{
		return this.listTipoDeContacto;
	}
	
	public JTextField getTxtCalle() {
		return txtCalle;
	}

	public JTextField getTxtAltura() {
		return txtAltura;
	}

	public JTextField getTxtPiso() {
		return txtPiso;
	}

	public JTextField getTxtDepartamento() {
		return txtDepartamento;
	}

	public JComboBox<Object> getListLocalidad() {
		return listLocalidad;
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
	
	public void llenarListaTipoContacto(List<TipoContactoDTO> tipoContactosEnLista) {
		this.listTipoDeContacto.removeAllItems();
		for (TipoContactoDTO tipoContacto : tipoContactosEnLista)
		{
			this.listTipoDeContacto.addItem(tipoContacto);
		}
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
		restoreDefaultForm();
		this.dispose();
	}
	
}

