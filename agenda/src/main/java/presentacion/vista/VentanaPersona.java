package presentacion.vista;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaísDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoContactoDTO;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JDateChooser fechaCumpleanios;
	private JComboBox<TipoContactoDTO> comboTipoDeContacto;
	private JTextField txtCalle;
	private JTextField txtAltura;
	private JTextField txtPiso;
	private JTextField txtDepartamento;
	private JComboBox<PaísDTO> comboPaíses;
	private JComboBox<ProvinciaDTO> comboProvincias;
	private JComboBox<LocalidadDTO> comboLocalidades;
	private JCheckBox checkFavorito;
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
				
				cerrar();
			}
		});
		
		setBounds(100, 100, 475, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 470, 650);
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
		lblCalle.setBounds(10, 216, 113, 14);
		panel.add(lblCalle);
		
		JLabel lblAltura = new JLabel("Altura");
		lblAltura.setBounds(10, 257, 113, 14);
		panel.add(lblAltura);
		
		JLabel lblPiso = new JLabel("Piso");
		lblPiso.setBounds(10, 298, 113, 14);
		panel.add(lblPiso);
		
		JLabel lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setBounds(10, 339, 113, 14);
		panel.add(lblDepartamento);
		
		JLabel lblPaís= new JLabel("País");
		lblPaís.setBounds(10, 380, 113, 14);
		panel.add(lblPaís);
		
		JLabel lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(10, 421, 113, 14);
		panel.add(lblProvincia);
		
		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 462, 113, 14);
		panel.add(lblLocalidad);
		
		JLabel lblFavorito = new JLabel("Es Favorito");
		lblFavorito.setBounds(10, 503, 113, 14);
		panel.add(lblFavorito);
		
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
		
		comboTipoDeContacto = new JComboBox<TipoContactoDTO>();
		comboTipoDeContacto.setBounds(140, 172, 164, 20);
		panel.add(comboTipoDeContacto);
		
		txtCalle = new JTextField();
		txtCalle.setBounds(140, 213, 164, 20);
		panel.add(txtCalle);
		txtCalle.setColumns(10);
		
		txtAltura = new JTextField();
		txtAltura.setBounds(140, 254, 164, 20);
		panel.add(txtAltura);
		txtAltura.setColumns(10);
		
		txtPiso = new JTextField();
		txtPiso.setBounds(140, 295, 164, 20);
		panel.add(txtPiso);
		
		txtDepartamento = new JTextField();
		txtDepartamento.setBounds(140, 336, 164, 20);
		panel.add(txtDepartamento);
		
		comboPaíses = new JComboBox<PaísDTO>();
		comboPaíses.setBounds(140, 377, 164, 20);
		panel.add(comboPaíses);
		comboPaíses.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
				if (value instanceof PaísDTO) {
					PaísDTO país = (PaísDTO) value;
					setText(país.getNombre());
					setToolTipText(país.getNombre());
				}
				
				return this;
			}
			
		});
		
		comboProvincias = new JComboBox<ProvinciaDTO>();
		comboProvincias.setBounds(140, 418, 164, 20);
		panel.add(comboProvincias);
		comboProvincias.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
				if (value instanceof ProvinciaDTO) {
					ProvinciaDTO provincia = (ProvinciaDTO) value;
					setText(provincia.getNombre());
					setToolTipText(provincia.getNombre());
				}
				
				return this;
			}
			
		});
		
		comboLocalidades = new JComboBox<LocalidadDTO>();
		comboLocalidades.setBounds(140, 459, 164, 20);
		panel.add(comboLocalidades);
		comboLocalidades.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				
				if (value instanceof LocalidadDTO) {
					LocalidadDTO localidad = (LocalidadDTO) value;
					setText(localidad.getNombre());
					setToolTipText(localidad.getNombre());
				}
				
				return this;
			}
			
		});
		
		checkFavorito = new JCheckBox();
		checkFavorito.setBounds(140, 500, 164, 20);
		panel.add(checkFavorito);
		
		btnAgregarPersona = new JButton("Agregar");
		btnAgregarPersona.setBounds(208, 549, 89, 23);
		panel.add(btnAgregarPersona);
		
		btnEditarPersona = new JButton("Editar");
		btnEditarPersona.setBounds(208, 549, 89, 23);
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
	
	public JComboBox<TipoContactoDTO> getListTipoDeContacto()
	{
		return this.comboTipoDeContacto;
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
	
	public JComboBox<PaísDTO> getComboPaíses() {
		return comboPaíses;
	}
	
	public JComboBox<ProvinciaDTO> getComboProvincias() {
		return comboProvincias;
	}

	public JComboBox<LocalidadDTO> getComboLocalidades() {
		return comboLocalidades;
	}
	
	public JCheckBox getCheckFavorito() {
		return checkFavorito;
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
		this.checkFavorito.setSelected(persona_a_editar.getFavorito());
		this.comboTipoDeContacto.setSelectedItem(persona_a_editar.getTipoDeContacto());
		
		DomicilioDTO domicilio_persona = persona_a_editar.getDomicilio();
		this.txtCalle.setText(domicilio_persona.getCalle());
		this.txtAltura.setText(String.valueOf(domicilio_persona.getAltura()));
		this.txtPiso.setText(domicilio_persona.getPiso());
		this.txtDepartamento.setText(domicilio_persona.getDepartamento());
		this.comboPaíses.setSelectedItem(domicilio_persona.getLocalidad().getProvincia().getPaís());
		this.comboProvincias.setSelectedItem(domicilio_persona.getLocalidad().getProvincia());
		this.comboLocalidades.setSelectedItem(domicilio_persona.getLocalidad());
		
		this.btnAgregarPersona.setVisible(false);
		this.btnEditarPersona.setVisible(true);
	}
	
	public void llenarComboTipoContacto(List<TipoContactoDTO> tipoContactosEnLista) {
		this.comboTipoDeContacto.removeAllItems();
		for (TipoContactoDTO tipoContacto : tipoContactosEnLista)
		{
			this.comboTipoDeContacto.addItem(tipoContacto);
		}
	}
	
	public void llenarComboPaíses(List<PaísDTO> paísesEnLista) {
		this.comboPaíses.removeAllItems();
		
		for (PaísDTO país : paísesEnLista)
			this.comboPaíses.addItem(país);
		
	}
	
	public void llenarComboProvincias(List<ProvinciaDTO> provinciasEnLista) {
		this.comboProvincias.removeAllItems();
		
		for (ProvinciaDTO provincia : provinciasEnLista)
			this.comboProvincias.addItem(provincia);
		
	}
	
	public void llenarComboLocalidades(List<LocalidadDTO> localidadesEnLista) {
		
		this.comboLocalidades.removeAllItems();
		
		for (LocalidadDTO localidad : localidadesEnLista)
			this.comboLocalidades.addItem(localidad);
		
	}
	
	private void restoreDefaultForm() {
		this.btnAgregarPersona.setVisible(true);
		this.btnEditarPersona.setVisible(false);
	}
	
	// Para agregar/eliminar los listener dinámicamente y mejorar la performance del sistema disparándolos solo la cantidad de veces necesaria
	public void removeComboProvinciasListeners() {
		for (ActionListener l : this.comboProvincias.getActionListeners())
			this.comboProvincias.removeActionListener(l);
	}
	
	public void removeComboPaísesListeners() {
		for (ActionListener l : this.comboPaíses.getActionListeners())
			this.comboPaíses.removeActionListener(l);
	}

	public void cerrar()
	{
		this.txtNombre.setText(null);
		this.txtTelefono.setText(null);
		this.txtEmail.setText(null);
		this.fechaCumpleanios.setDate(null);
		this.txtCalle.setText(null);
		this.txtAltura.setText(null);
		this.txtPiso.setText(null);
		this.txtDepartamento.setText(null);
		this.checkFavorito.setSelected(false);
		
		removeComboPaísesListeners();
		removeComboProvinciasListeners();
		
		this.comboProvincias.removeAllItems();
		this.comboLocalidades.removeAllItems();
		
		restoreDefaultForm();
		this.dispose();
	}
	
}

