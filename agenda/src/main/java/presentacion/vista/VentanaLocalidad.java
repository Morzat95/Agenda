package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import dto.LocalidadDTO;
import dto.ProvinciaDTO;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import javax.swing.JComboBox;

public class VentanaLocalidad extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JButton btnAgregarLocalidad;
	private JButton btnEliminarLocalidad;
	private JButton btnEditarLocalidad;
	private JList<LocalidadDTO> listaLocalidades;
	private DefaultListModel<LocalidadDTO> modelLocalidades;
	private static VentanaLocalidad INSTANCE;
	
	private JSplitPane splitPane;
	private JComboBox<ProvinciaDTO> comboBoxProvincias;
	
	public static VentanaLocalidad getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaLocalidad();
		
		return INSTANCE;
	}
	
	private VentanaLocalidad() 
	{
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 500, 350);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
//		splitPane.setDividerLocation(0.4); No anda bien
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPaneLocalidades = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneLocalidades);
		
		modelLocalidades = new DefaultListModel<LocalidadDTO>();
		listaLocalidades = new JList<LocalidadDTO>(modelLocalidades);
		listaLocalidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneLocalidades.setViewportView(listaLocalidades);
		listaLocalidades.setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof ProvinciaDTO) {
					LocalidadDTO localidad = (LocalidadDTO) value;
					setText(localidad.getNombre());
					setToolTipText(localidad.getNombre());
				}
				return this;
			}
			
		});
		
		JLabel lblLocalidades = new JLabel("Localidades");
		lblLocalidades.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblLocalidades.setOpaque(true);
		lblLocalidades.setBackground(Color.LIGHT_GRAY);
		scrollPaneLocalidades.setColumnHeaderView(lblLocalidades);
		
		JPanel panelForm = new JPanel();
		splitPane.setRightComponent(panelForm);
		GridBagLayout gbl_panelForm = new GridBagLayout();
		gbl_panelForm.columnWidths = new int[]{365, 0};
		gbl_panelForm.rowHeights = new int[]{202, 56, 0};
		gbl_panelForm.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelForm.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelForm.setLayout(gbl_panelForm);
		
		JPanel panelData = new JPanel();
		GridBagConstraints gbc_panelData = new GridBagConstraints();
		gbc_panelData.insets = new Insets(0, 0, 5, 0);
		gbc_panelData.gridx = 0;
		gbc_panelData.gridy = 0;
		panelForm.add(panelData, gbc_panelData);
		GridBagLayout gbl_panelData = new GridBagLayout();
		gbl_panelData.columnWidths = new int[]{37, 86, 0};
		gbl_panelData.rowHeights = new int[]{20, 20, 0};
		gbl_panelData.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelData.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelData.setLayout(gbl_panelData);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panelData.add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNombre.anchor = GridBagConstraints.NORTHWEST;
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 0;
		panelData.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblProvincia = new JLabel("Pertenece a:");
		GridBagConstraints gbc_lblProvincia = new GridBagConstraints();
		gbc_lblProvincia.anchor = GridBagConstraints.EAST;
		gbc_lblProvincia.insets = new Insets(0, 0, 0, 5);
		gbc_lblProvincia.gridx = 0;
		gbc_lblProvincia.gridy = 1;
		panelData.add(lblProvincia, gbc_lblProvincia);
		
		comboBoxProvincias = new JComboBox<ProvinciaDTO>();
		GridBagConstraints gbc_comboBoxProvincias = new GridBagConstraints();
		gbc_comboBoxProvincias.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxProvincias.gridx = 1;
		gbc_comboBoxProvincias.gridy = 1;
		panelData.add(comboBoxProvincias, gbc_comboBoxProvincias);
		comboBoxProvincias.setRenderer(new DefaultListCellRenderer() {

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
		
		JPanel panelOperaciones = new JPanel();
		GridBagConstraints gbc_panelOperaciones = new GridBagConstraints();
		gbc_panelOperaciones.gridx = 0;
		gbc_panelOperaciones.gridy = 1;
		panelForm.add(panelOperaciones, gbc_panelOperaciones);
		panelOperaciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAgregarLocalidad = new JButton("Agregar");
		panelOperaciones.add(btnAgregarLocalidad);
		
		btnEliminarLocalidad = new JButton("Eliminar");
		panelOperaciones.add(btnEliminarLocalidad);
		
		btnEditarLocalidad = new JButton("Editar");
		panelOperaciones.add(btnEditarLocalidad);
				
		this.setVisible(false);
		
		pack();
		restoreDefaults();
	}
	
	private void restoreDefaults() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				splitPane.setDividerLocation(0.4);
			}
		});
	}
	
	public void mostrarVentana()
	{
		this.setVisible(true);
	}
	
	public JTextField getTxtNombre() 
	{
		return textFieldNombre;
	}

	public JButton getBtnAgregarLocalidad() 
	{
		return btnAgregarLocalidad;
	}
	
	public JButton getBtnEliminarLocalidad() 
	{
		return btnEliminarLocalidad;
	}
	
	public JButton getBtnEditarLocalidad() 
	{
		return btnEditarLocalidad;
	}
	
	public JList<LocalidadDTO> getListaLocalidades() {
		return listaLocalidades;
	}
	
	public JComboBox<ProvinciaDTO> getComboProvincias() {
		return comboBoxProvincias;
	}
	
	public void llenarLista(List<LocalidadDTO> localidadesEnLista) {
		this.modelLocalidades.clear();

		for (LocalidadDTO l : localidadesEnLista)
			this.modelLocalidades.addElement(l);
		
	}
	
	public void llenarComboProvincias(List<ProvinciaDTO> provinciasEnLista) {
		this.comboBoxProvincias.removeAllItems();
		
		for (ProvinciaDTO provincia : provinciasEnLista)
			this.comboBoxProvincias.addItem(provincia);
	}
	
	public void limpiarFormulario() {		
		this.textFieldNombre.setText(null);
		this.comboBoxProvincias.setSelectedIndex(-1);
	}
	
	public void cerrar()
	{
		limpiarFormulario();
		this.dispose();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		limpiarFormulario();
	}
}
