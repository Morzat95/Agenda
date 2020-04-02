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

public class VentanaPaís extends JFrame {

	private static final long serialVersionUID = 1L;
	private static VentanaPaís INSTANCE;
	private JTextField textFieldNombre;
	private JButton btnAgregarPaís;
	private JButton btnEliminarPaís;
	private JButton btnEditarPaís;
	private JList<PaísDTO> listaPaíses;
	private DefaultListModel<PaísDTO> modelPaíses;
	
	private JSplitPane splitPane;

	public static VentanaPaís getInstance() {
		if(INSTANCE == null)
			INSTANCE = new VentanaPaís();
		
		return INSTANCE;
	}
	
	public VentanaPaís() {
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 350);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPaneTipoContactos = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneTipoContactos);
		
		modelPaíses = new DefaultListModel<PaísDTO>();
		listaPaíses = new JList<PaísDTO>(modelPaíses);
		listaPaíses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaPaíses.setCellRenderer(new DefaultListCellRenderer() {

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
		scrollPaneTipoContactos.setViewportView(listaPaíses);
		
		JLabel lblPaíses = new JLabel("Países");
		lblPaíses.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblPaíses.setOpaque(true);
		lblPaíses.setBackground(Color.LIGHT_GRAY);
		scrollPaneTipoContactos.setColumnHeaderView(lblPaíses);
		
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
		panelData.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNombre = new JLabel("Nombre");
		panelData.add(lblNombre);
		
		textFieldNombre = new JTextField();
		panelData.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JPanel panelOperaciones = new JPanel();
		GridBagConstraints gbc_panelOperaciones = new GridBagConstraints();
		gbc_panelOperaciones.gridx = 0;
		gbc_panelOperaciones.gridy = 1;
		panelForm.add(panelOperaciones, gbc_panelOperaciones);
		panelOperaciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAgregarPaís = new JButton("Agregar");
		panelOperaciones.add(btnAgregarPaís);
		
		btnEliminarPaís = new JButton("Eliminar");
		panelOperaciones.add(btnEliminarPaís);
		
		btnEditarPaís = new JButton("Editar");
		panelOperaciones.add(btnEditarPaís);
				
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

	public JButton getBtnAgregarPaís() {
		return btnAgregarPaís;
	}

	public JButton getBtnEliminarPaís() {
		return btnEliminarPaís;
	}

	public JButton getBtnEditarPaís() {
		return btnEditarPaís;
	}

	public JList<PaísDTO> getListaPaíses() {
		return listaPaíses;
	}
	
	public void llenarLista(List<PaísDTO> paísesEnLista) {
		this.modelPaíses.clear();

		for (PaísDTO p : paísesEnLista)
			this.modelPaíses.addElement(p);
		
	}
	
	public void limpiarFormulario() {
		this.textFieldNombre.setText(null);
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
