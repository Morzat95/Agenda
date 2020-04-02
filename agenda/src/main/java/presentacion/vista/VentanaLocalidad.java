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
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.Color;

import javax.swing.border.LineBorder;
import dto.LocalidadDTO;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

public class VentanaLocalidad extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JButton btnAgregarLocalidad;
	private JButton btnEliminarLocalidad;
	private JButton btnEditarLocalidad;
	private JList listaLocalidades;
	private DefaultListModel modelLocalidades;
	private static VentanaLocalidad INSTANCE;
	
	private JSplitPane splitPane;
	
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
		
		modelLocalidades = new DefaultListModel();
		listaLocalidades = new JList(modelLocalidades);
		listaLocalidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneLocalidades.setViewportView(listaLocalidades);
		
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
	
	public JList getListaLocalidades() {
		return listaLocalidades;
	}
	
	public void llenarLista(List<LocalidadDTO> localidadesEnLista) {
		this.modelLocalidades.clear();

		for (LocalidadDTO l : localidadesEnLista)
		{
			String nombre = l.getNombre();
			this.modelLocalidades.addElement(nombre);
		}
		
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
