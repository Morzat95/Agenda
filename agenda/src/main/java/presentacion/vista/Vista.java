package presentacion.vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.PersonaDTO;

import javax.swing.JButton;

import persistencia.conexion.Conexion;

public class Vista
{
	private JFrame frame;
	private JTable tablaPersonas;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnEditar;
	private JButton btnReporte;
//	private JMenu menuLocalidad;
	private JMenuItem menuLocalidad;
		private JMenuItem mnItemAgregarLocalidad;
		private JMenuItem mnItemEliminarLocalidad;
		private JMenuItem mnItemEditarLocalidad;
	private JMenuItem menuTipoContacto;
	private DefaultTableModel modelPersonas;
	private  String[] nombreColumnas = {"Nombre y apellido","Telefono", "Email", "Fecha de Cumplea�os", "Tipo de Contacto", "Domicilio"};

	public Vista() 
	{
		super();
		initialize();
	}


	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 800, 295);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane spPersonas = new JScrollPane();
		spPersonas.setBounds(10, 51, 770, 182);
		panel.add(spPersonas);
		
		modelPersonas = new DefaultTableModel(null,nombreColumnas);
		tablaPersonas = new JTable(modelPersonas);
		
		tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(103);
		tablaPersonas.getColumnModel().getColumn(0).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaPersonas.getColumnModel().getColumn(1).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(2).setPreferredWidth(103);
		tablaPersonas.getColumnModel().getColumn(2).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(3).setPreferredWidth(100);
		tablaPersonas.getColumnModel().getColumn(3).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(4).setPreferredWidth(103);
		tablaPersonas.getColumnModel().getColumn(4).setResizable(false);
		tablaPersonas.getColumnModel().getColumn(5).setPreferredWidth(100);
		tablaPersonas.getColumnModel().getColumn(5).setResizable(false);
		
		spPersonas.setViewportView(tablaPersonas);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(120, 268, 89, 23);
		panel.add(btnAgregar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(229, 268, 89, 23);
		panel.add(btnEditar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(338, 268, 89, 23);
		panel.add(btnBorrar);
		
		btnReporte = new JButton("Reporte");
		btnReporte.setBounds(447, 268, 89, 23);
		panel.add(btnReporte);
		
		// Add Menu	
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 800, 25);
		panel.add(menuBar);
		
		JMenu menu = new JMenu("ABM");
		menuBar.add(menu);
		
			// Add ABM Localidad
//			menuLocalidad = new JMenu("Localidad");
//			menu.add(menuLocalidad);
			menuLocalidad = new JMenuItem("Localidad");
			menu.add(menuLocalidad);
			
//				mnItemAgregarLocalidad = new JMenuItem("Agregar");
//				menuLocalidad.add(mnItemAgregarLocalidad);
//				
//				mnItemEliminarLocalidad = new JMenuItem("Eliminar");
//				menuLocalidad.add(mnItemEliminarLocalidad);
//				
//				mnItemEditarLocalidad = new JMenuItem("Editar");
//				menuLocalidad.add(mnItemEditarLocalidad);
				
			// Add ABM Tipo de Contacto
//			JMenu menuContactos = new JMenu("Contactos");
//			menu.add(menuContactos);
			menuTipoContacto = new JMenuItem("Tipos de Contactos");
			menu.add(menuTipoContacto);
			
//				JMenuItem mnItemContactosAgregar = new JMenuItem("Agregar");
//				menuContactos.add(mnItemContactosAgregar);
//				
//				JMenuItem mnItemContactosEliminar = new JMenuItem("Eliminar");
//				menuContactos.add(mnItemContactosEliminar);
//				
//				JMenuItem mnItemContactosEditar = new JMenuItem("Editar");
//				menuContactos.add(mnItemContactosEditar);

	}
	
	public void show()
	{
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() 
		{
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "¿Estás seguro que quieres salir de la Agenda?", 
		             "Confirmación", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	Conexion.getConexion().cerrarConexion();
		           System.exit(0);
		        }
		    }
		});
		this.frame.setVisible(true);
	}
	
	public JButton getBtnAgregar() 
	{
		return btnAgregar;
	}

	public JButton getBtnBorrar() 
	{
		return btnBorrar;
	}
	
	public JButton getBtnEditar() 
	{
		return btnEditar;
	}
	
//	public JMenu getMenuLocalidad() {
	public JMenuItem getMenuLocalidad() {
		return menuLocalidad;
	}
	
	public JMenuItem getMnItemAgregarLocalidad() // TODO: Delete
	{
		return mnItemAgregarLocalidad;
	}
	
	public JMenuItem getMnItemEliminarLocalidad() // TODO: Delete
	{
		return mnItemEliminarLocalidad;
	}
	
	public JMenuItem getMnItemEditarLocalidad() // TODO: Delete
	{
		return mnItemEditarLocalidad;
	}
	
	public JMenuItem getMenuTipoContacto() {
		return menuTipoContacto;
	}
	
	public JButton getBtnReporte() 
	{
		return btnReporte;
	}
	
	public DefaultTableModel getModelPersonas() 
	{
		return modelPersonas;
	}
	
	public JTable getTablaPersonas()
	{
		return tablaPersonas;
	}

	public String[] getNombreColumnas() 
	{
		return nombreColumnas;
	}


	public void llenarTabla(List<PersonaDTO> personasEnTabla) {
		this.getModelPersonas().setRowCount(0); //Para vaciar la tabla
		this.getModelPersonas().setColumnCount(0);
		this.getModelPersonas().setColumnIdentifiers(this.getNombreColumnas());

		for (PersonaDTO p : personasEnTabla)
		{
			String nombre = p.getNombre();
			String tel = p.getTelefono();
			String email = p.getEmail();
			String fechaCumpleanio = new SimpleDateFormat("dd/MM").format(p.getFechaCumpleanio());
			String tipoContacto = p.getTipoDeContacto().getNombre();
			String domicilio = p.getDomicilio().getCalle() + " - Nro " + p.getDomicilio().getAltura();
			Object[] fila = {nombre, tel, email, fechaCumpleanio, tipoContacto, domicilio};
			this.getModelPersonas().addRow(fila);
		}
		
	}
}
