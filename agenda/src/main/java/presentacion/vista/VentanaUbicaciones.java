package presentacion.vista;

import java.awt.Component;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import dto.LocalidadDTO;
import dto.PaísDTO;
import dto.ProvinciaDTO;

public class VentanaUbicaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private static VentanaUbicaciones INSTANCE;

	private JPanel contentPane;
	
	private JTextField textNombrePais;
	private JButton btnAgregarPais;
	private JButton btnEditarPais;
	private JButton btnEliminarPais;
	private JList<PaísDTO> listaPaises;
	private DefaultListModel<PaísDTO> modelPaises;
	
	private JTextField textNombreProvincia;
	private JButton btnAgregarProvincia;
	private JButton btnEditarProvincia;	
	private JButton btnEliminarProvincia;
	private JComboBox<PaísDTO> comboBoxPaíses;
	private JList<ProvinciaDTO> listaProvincias;
	private DefaultListModel<ProvinciaDTO> modelProvincias;
	
	private JTextField textNombreLocalidad;
	private JButton btnAgregarLocalidad;
	private JButton btnEditarLocalidad;
	private JButton btnEliminarLocalidad;
	private JComboBox<ProvinciaDTO> comboBoxProvincias;
	private JList<LocalidadDTO> listaLocalidades;
	private DefaultListModel<LocalidadDTO> modelLocalidades;
	
	public static VentanaUbicaciones getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new VentanaUbicaciones();
		}
		return INSTANCE;
	}
	
	public VentanaUbicaciones() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(false);
		
		//Countries
		JLabel lblNombrePais = new JLabel("Nombre del Pais");
		lblNombrePais.setBounds(20, 30, 130, 20);
		contentPane.add(lblNombrePais);
		
		textNombrePais = new JTextField();
		textNombrePais.setBounds(160, 30, 150, 20);
		contentPane.add(textNombrePais);
		textNombrePais.setColumns(10);
		
		btnAgregarPais = new JButton("Agregar");
		btnAgregarPais.setBounds(20, 73, 80, 30);
		contentPane.add(btnAgregarPais);
		
		btnEditarPais = new JButton("Editar");
		btnEditarPais.setBounds(125, 73, 80, 30);
		contentPane.add(btnEditarPais);
		
		btnEliminarPais = new JButton("Eliminar");
		btnEliminarPais.setBounds(230, 73, 80, 30);
		contentPane.add(btnEliminarPais);
		
		modelPaises = new DefaultListModel<PaísDTO>();
		listaPaises = new JList<PaísDTO>(modelPaises);
		listaPaises.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaPaises.setBounds(360, 32, 250, 148);
		listaPaises.setCellRenderer(new DefaultListCellRenderer() {
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
		contentPane.add(listaPaises);
		
		JScrollPane scrollPanePais = new JScrollPane(listaPaises);
		scrollPanePais.setBounds(360, 30, 250, 150);
		contentPane.add(scrollPanePais);
		
		//Provinces
		JLabel lblNombreProvincia = new JLabel("Nombre de Provincia");
		lblNombreProvincia.setBounds(20, 191, 130, 20);
		contentPane.add(lblNombreProvincia);
		
		textNombreProvincia = new JTextField();
		textNombreProvincia.setColumns(10);
		textNombreProvincia.setBounds(160, 191, 150, 20);
		contentPane.add(textNombreProvincia);
		
		btnAgregarProvincia = new JButton("Agregar");
		btnAgregarProvincia.setBounds(20, 234, 80, 30);
		contentPane.add(btnAgregarProvincia);
		
		btnEditarProvincia = new JButton("Editar");
		btnEditarProvincia.setBounds(125, 234, 80, 30);
		contentPane.add(btnEditarProvincia);
		
		btnEliminarProvincia = new JButton("Eliminar");
		btnEliminarProvincia.setBounds(230, 234, 80, 30);
		contentPane.add(btnEliminarProvincia);
		

		comboBoxPaíses = new JComboBox<PaísDTO>();
		comboBoxPaíses.setBounds(360, 191, 250, 30);
		contentPane.add(comboBoxPaíses);
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
		
		modelProvincias = new DefaultListModel<ProvinciaDTO>();
		listaProvincias = new JList<ProvinciaDTO>(modelProvincias);
		listaProvincias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaProvincias.setBounds(360, 233, 250, 108);
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
		contentPane.add(listaProvincias);
		
		JScrollPane scrollPaneProvincia = new JScrollPane(listaProvincias);
		scrollPaneProvincia.setBounds(360, 233, 250, 110);
		contentPane.add(scrollPaneProvincia);
		
		//Localities
		JLabel lblNombreLocalidad = new JLabel("Nombre de Localidad");
		lblNombreLocalidad.setBounds(20, 360, 130, 20);
		contentPane.add(lblNombreLocalidad);
		
		textNombreLocalidad = new JTextField();
		textNombreLocalidad.setColumns(10);
		textNombreLocalidad.setBounds(160, 360, 150, 20);
		contentPane.add(textNombreLocalidad);
		
		btnAgregarLocalidad = new JButton("Agregar");
		btnAgregarLocalidad.setBounds(20, 403, 80, 30);
		contentPane.add(btnAgregarLocalidad);
		
		btnEditarLocalidad = new JButton("Editar");
		btnEditarLocalidad.setBounds(125, 403, 80, 30);
		contentPane.add(btnEditarLocalidad);
		
		btnEliminarLocalidad = new JButton("Eliminar");
		btnEliminarLocalidad.setBounds(230, 403, 80, 30);
		contentPane.add(btnEliminarLocalidad);
		
		comboBoxProvincias = new JComboBox<ProvinciaDTO>();
		comboBoxProvincias.setBounds(360, 360, 250, 30);
		//contentPane.add(comboBoxProvincias);
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
		
		modelLocalidades = new DefaultListModel<LocalidadDTO>();
		listaLocalidades = new JList<LocalidadDTO>(modelLocalidades);
		listaLocalidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//listaLocalidades.setBounds(360, 402, 250, 108); //En caso de tener combo box
		listaLocalidades.setBounds(360, 360, 250, 108);
		listaLocalidades.setCellRenderer(new DefaultListCellRenderer() {

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
		contentPane.add(listaLocalidades);
		
		JScrollPane scrollPaneLocalidades = new JScrollPane(listaLocalidades);
		//scrollPaneLocalidades.setBounds(360, 402, 250, 110);
		scrollPaneLocalidades.setBounds(360, 360, 250, 150);
		contentPane.add(scrollPaneLocalidades);
		
	}
	
	public void mostrarVentana() {
		setVisible(true);
	}
	
	public void llenarListaPais(List<PaísDTO> paisesEnLista) {
		this.modelPaises.clear();

		for (PaísDTO pais : paisesEnLista)
			this.modelPaises.addElement(pais);
	}	
	
	public void llenarComboPaíses(List<PaísDTO> paísesEnLista) {
		this.comboBoxPaíses.removeAllItems();

		for (PaísDTO p : paísesEnLista)
			this.comboBoxPaíses.addItem(p);
		
	}
	
	public void llenarListaProvincias(List<ProvinciaDTO> provinciasEnLista) {
		this.modelProvincias.clear();

		for (ProvinciaDTO provincia : provinciasEnLista)
			this.modelProvincias.addElement(provincia);
		
	}
	
	public void llenarComboProvincias(List<ProvinciaDTO> provinciasEnLista) {
		this.comboBoxProvincias.removeAllItems();
		
		for (ProvinciaDTO provincia : provinciasEnLista)
			this.comboBoxProvincias.addItem(provincia);
	}
	
	public void llenarListaLocalidades(List<LocalidadDTO> localidadesEnLista) {
		this.modelLocalidades.clear();
		
		for(LocalidadDTO localidad : localidadesEnLista)
			this.modelLocalidades.addElement(localidad);
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
	
	public void removeComboPaísesListeners() {
		for (ActionListener l : this.comboBoxPaíses.getActionListeners())
			this.comboBoxPaíses.removeActionListener(l);
	}
	
	public void removeComboProvinciasListeners() {
		for (ActionListener l : this.comboBoxProvincias.getActionListeners())
			this.comboBoxProvincias.removeActionListener(l);
	}
	
	public void limpiarFormulario() {
		this.textNombrePais.setText(null);
		this.textNombreProvincia.setText(null);
		this.textNombreLocalidad.setText(null);
	}

	public JTextField getTextNombrePais() {
		return textNombrePais;
	}

	public JTextField getTextNombreProvincia() {
		return textNombreProvincia;
	}

	public JTextField getTextNombreLocalidad() {
		return textNombreLocalidad;
	}

	public JButton getBtnAgregarPais() {
		return btnAgregarPais;
	}

	public JButton getBtnAgregarProvincia() {
		return btnAgregarProvincia;
	}

	public JButton getBtnAgregarLocalidad() {
		return btnAgregarLocalidad;
	}

	public JButton getBtnEditarPais() {
		return btnEditarPais;
	}

	public JButton getBtnEditarProvincia() {
		return btnEditarProvincia;
	}

	public JButton getBtnEditarLocalidad() {
		return btnEditarLocalidad;
	}

	public JButton getBtnEliminarPais() {
		return btnEliminarPais;
	}

	public JButton getBtnEliminarProvincia() {
		return btnEliminarProvincia;
	}

	public JButton getBtnEliminarLocalidad() {
		return btnEliminarLocalidad;
	}

	public JList<PaísDTO> getListaPaises() {
		return listaPaises;
	}

	public JList<ProvinciaDTO> getListaProvincias() {
		return listaProvincias;
	}

	public JList<LocalidadDTO> getListaLocalidades() {
		return listaLocalidades;
	}
	
	public JComboBox<PaísDTO> getComboBoxPaíses() {
		return comboBoxPaíses;
	}

	public JComboBox<ProvinciaDTO> getComboBoxProvincias() {
		return comboBoxProvincias;
	}
	
}
