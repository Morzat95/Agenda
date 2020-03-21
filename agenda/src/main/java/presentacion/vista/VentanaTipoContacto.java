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
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

public class VentanaTipoContacto extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JButton btnAgregarTipoContacto;
	private JButton btnEliminarTipoContacto;
	private JButton btnEditarTipoContacto;
	private JList listaTipoContactos;
	private DefaultListModel modelTipoContactos;
	private static VentanaTipoContacto INSTANCE;
	
	private JSplitPane splitPane;
	
	public static VentanaTipoContacto getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new VentanaTipoContacto();
		
		return INSTANCE;
	}
	
	private VentanaTipoContacto() 
	{
		super();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 350);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPaneTipoContactos = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneTipoContactos);
		
		modelTipoContactos = new DefaultListModel();
		listaTipoContactos = new JList(modelTipoContactos);
		listaTipoContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneTipoContactos.setViewportView(listaTipoContactos);
		
		JLabel lblTipoContactos = new JLabel("Tipos de Contactos");
		lblTipoContactos.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTipoContactos.setOpaque(true);
		lblTipoContactos.setBackground(Color.LIGHT_GRAY);
		scrollPaneTipoContactos.setColumnHeaderView(lblTipoContactos);
		
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
		
		btnAgregarTipoContacto = new JButton("Agregar");
		panelOperaciones.add(btnAgregarTipoContacto);
		
		btnEliminarTipoContacto = new JButton("Eliminar");
		panelOperaciones.add(btnEliminarTipoContacto);
		
		btnEditarTipoContacto = new JButton("Editar");
		panelOperaciones.add(btnEditarTipoContacto);
				
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

	public JButton getBtnAgregarTipoContacto() 
	{
		return btnAgregarTipoContacto;
	}
	
	public JButton getBtnEliminarTipoContacto() 
	{
		return btnEliminarTipoContacto;
	}
	
	public JButton getBtnEditarTipoContacto() 
	{
		return btnEditarTipoContacto;
	}
	
	public JList getListaTiposContacto() {
		return listaTipoContactos;
	}
	
	public void llenarLista(List<TipoContactoDTO> tipoContactosEnLista) {
		this.modelTipoContactos.clear();

		for (TipoContactoDTO l : tipoContactosEnLista)
		{
			String nombre = l.getNombre();
			this.modelTipoContactos.addElement(nombre);
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
