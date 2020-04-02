package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import dto.PaísDTO;
import dto.ProvinciaDTO;

public class VentanaProvincia extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static VentanaProvincia INSTANCE;
	private JTextField textFieldNombre;
	private JButton btnAgregarProvincia;
	private JButton btnEliminarProvincia;
	private JButton btnEditarProvincia;
	private JList<ProvinciaDTO> listaProvincias;
	private DefaultListModel<ProvinciaDTO> modelProvincias;
	private JComboBox<PaísDTO> comboBoxPaíses;
	
	private JSplitPane splitPane;

	public static VentanaProvincia getInstance() {
		if(INSTANCE == null)
			INSTANCE = new VentanaProvincia();
		
		return INSTANCE;
	}
	
	public VentanaProvincia() {
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 350);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPaneTipoContactos = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneTipoContactos);
		
		modelProvincias = new DefaultListModel<ProvinciaDTO>();
		listaProvincias = new JList<ProvinciaDTO>(modelProvincias);
		listaProvincias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaProvincias.setCellRenderer(new DefaultListCellRenderer() {

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
		scrollPaneTipoContactos.setViewportView(listaProvincias);
		
		JLabel lblProvincias = new JLabel("Provincias");
		lblProvincias.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblProvincias.setOpaque(true);
		lblProvincias.setBackground(Color.LIGHT_GRAY);
		scrollPaneTipoContactos.setColumnHeaderView(lblProvincias);
		
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
		gbl_panelData.rowHeights = new int[]{20, 22, 0};
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
		
		JLabel lblPaís = new JLabel("Pertenece A: ");
		GridBagConstraints gbc_lblPaís = new GridBagConstraints();
		gbc_lblPaís.anchor = GridBagConstraints.EAST;
		gbc_lblPaís.insets = new Insets(0, 0, 0, 5);
		gbc_lblPaís.gridx = 0;
		gbc_lblPaís.gridy = 1;
		panelData.add(lblPaís, gbc_lblPaís);
		
		comboBoxPaíses = new JComboBox<PaísDTO>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panelData.add(comboBoxPaíses, gbc_comboBox);
		comboBoxPaíses.setRenderer(new DefaultListCellRenderer() {

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
		
		JPanel panelOperaciones = new JPanel();
		GridBagConstraints gbc_panelOperaciones = new GridBagConstraints();
		gbc_panelOperaciones.gridx = 0;
		gbc_panelOperaciones.gridy = 1;
		panelForm.add(panelOperaciones, gbc_panelOperaciones);
		panelOperaciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAgregarProvincia = new JButton("Agregar");
		panelOperaciones.add(btnAgregarProvincia);
		
		btnEliminarProvincia = new JButton("Eliminar");
		panelOperaciones.add(btnEliminarProvincia);
		
		btnEditarProvincia = new JButton("Editar");
		panelOperaciones.add(btnEditarProvincia);
				
		this.setVisible(false);
		
		pack();
		restoreDefaults();
	}
	
	private void restoreDefaults() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				splitPane.setDividerLocation(0.41);
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

	public JButton getBtnAgregarProvincia() {
		return btnAgregarProvincia;
	}

	public JButton getBtnEliminarProvincia() {
		return btnEliminarProvincia;
	}

	public JButton getBtnEditarProvincia() {
		return btnEditarProvincia;
	}

	public JList<ProvinciaDTO> getListaProvincias() {
		return listaProvincias;
	}
	
	public JComboBox<PaísDTO> getComboPaíses() {
		return this.comboBoxPaíses;
	}
	
	public void llenarListaProvincias(List<ProvinciaDTO> provinciasEnLista) {
		this.modelProvincias.clear();

		for (ProvinciaDTO p : provinciasEnLista)
			this.modelProvincias.addElement(p);
		
	}
	
	public void llenarComboPaíses(List<PaísDTO> paísesEnLista) {
		this.comboBoxPaíses.removeAllItems();

		for (PaísDTO p : paísesEnLista)
			this.comboBoxPaíses.addItem(p);
		
	}
	
	public void limpiarFormulario() {
		this.textFieldNombre.setText(null);
		this.comboBoxPaíses.setSelectedIndex(-1);
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
