package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import modelo.Agenda;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaLocalidad;
import presentacion.vista.VentanaPersona;
import presentacion.vista.VentanaTipoContacto;
import presentacion.vista.Vista;
import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;

public class Controlador implements ActionListener
{
		private Vista vista;
		private List<PersonaDTO> personasEnTabla;
		private List<LocalidadDTO> localidadesEnLista;
		private List<TipoContactoDTO> tiposContactoEnLista;
		private VentanaPersona ventanaPersona;
		private VentanaLocalidad ventanaLocalidad;
		private VentanaTipoContacto ventanaTipoContacto;
		private Agenda agenda;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnEditar().addActionListener(a->ventanaEditarPersona(a));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.vista.getMenuLocalidad().addActionListener(a->ventanaAgregarLocalidad(a));
			this.vista.getMenuTipoContacto().addActionListener(a->ventanaAgregarTipoContacto(a));
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.ventanaPersona.getBtnEditarPersona().addActionListener(p->editarPersona(p));
			this.ventanaLocalidad = VentanaLocalidad.getInstance();
			this.ventanaLocalidad.getBtnAgregarLocalidad().addActionListener(l->guardarLocalidad(l));
			this.ventanaLocalidad.getBtnEliminarLocalidad().addActionListener(l->borrarLocalidad(l));
			this.ventanaLocalidad.getBtnEditarLocalidad().addActionListener(l->editarLocalidad(l));
			this.ventanaTipoContacto = VentanaTipoContacto.getInstance();
			this.ventanaTipoContacto.getBtnAgregarTipoContacto().addActionListener(l->guardarTipoContacto(l));
			this.ventanaTipoContacto.getBtnEliminarTipoContacto().addActionListener(l->borrarTipoContacto(l));
			this.ventanaTipoContacto.getBtnEditarTipoContacto().addActionListener(l->editarTipoContacto(l));
			this.agenda = agenda;
		}
		
		private void ventanaAgregarPersona(ActionEvent a) {
			this.ventanaPersona.mostrarVentana();
		}
		
		public void ventanaEditarPersona(ActionEvent a)
		{
			int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			
			if (filasSeleccionadas.length == 0) {
				JOptionPane.showMessageDialog(null, "Debe serleccionar un contacto de la tabla para poder editarlo.");
				return;
			}
			
			if (filasSeleccionadas.length > 1) {
				JOptionPane.showMessageDialog(null, "Solo puede editar un contacto a la vez.");
				return;
			}
			
			int index = filasSeleccionadas[0];
			
			PersonaDTO persona_a_editar = this.personasEnTabla.get(index);
			this.ventanaPersona.llenarFormulario(persona_a_editar);
			this.ventanaPersona.mostrarVentana();
			
			this.refrescarListaTipoContacto();
			this.ventanaTipoContacto.limpiarFormulario();
		}
		
		private void ventanaAgregarLocalidad(ActionEvent a) {
			this.ventanaLocalidad.mostrarVentana();
		}
		
		private void ventanaAgregarTipoContacto(ActionEvent a) {
			this.ventanaTipoContacto.mostrarVentana();
		}

		private void guardarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel);
			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
		
		private void editarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			int index = this.vista.getTablaPersonas().getSelectedRow();
			PersonaDTO persona_a_editar = this.personasEnTabla.get(index);
			persona_a_editar.setNombre(nombre);
			persona_a_editar.setTelefono(tel);
			this.agenda.editarPersona(persona_a_editar);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
		
		private void guardarLocalidad(ActionEvent l) {
			String nombre = this.ventanaLocalidad.getTxtNombre().getText();
			
			if (nombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			boolean exists = localidadesEnLista.stream().anyMatch(e -> e.getNombre().equals(nombre));
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "Ya existe una localidad con ese nombre.");
				return;
			}
			
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nombre);
			this.agenda.agregarLocalidad(nuevaLocalidad);
			this.refrescarLista();
			this.ventanaLocalidad.limpiarFormulario();
		}
		
		private void guardarTipoContacto(ActionEvent l) {
			String nombre = this.ventanaTipoContacto.getTxtNombre().getText();
			
			if (nombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaTipoContacto, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			boolean exists = tiposContactoEnLista.stream().anyMatch(e -> e.getNombre().equals(nombre));
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaTipoContacto, "Ya existe un tipo de contacto con ese nombre.");
				return;
			}
			TipoContactoDTO nuevoTipoContacto = new TipoContactoDTO(0, nombre);
			this.agenda.agregarTipoContacto(nuevoTipoContacto);
			this.refrescarListaTipoContacto();
			this.ventanaTipoContacto.limpiarFormulario();
		}

		private void mostrarReporte(ActionEvent r) {
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();	
		}

		public void borrarPersona(ActionEvent s)
		{
			int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			for (int fila : filasSeleccionadas)
			{
				this.agenda.borrarPersona(this.personasEnTabla.get(fila));
			}
			
			this.refrescarTabla();
		}
		
		public void borrarLocalidad(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaLocalidad.getListaLocalidades().getSelectedIndices();
			for (int index : elementosSeleccionados)
			{
				this.agenda.borrarLocalidad(this.localidadesEnLista.get(index));
			}
			
			this.refrescarLista();
		}
		
		public void borrarTipoContacto(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaTipoContacto.getListaTiposContacto().getSelectedIndices();
			for (int index : elementosSeleccionados)
			{
				this.agenda.borrarTipoContacto(this.tiposContactoEnLista.get(index));
			}
			
			this.refrescarListaTipoContacto();
		}
		
		public void editarLocalidad(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaLocalidad.getListaLocalidades().getSelectedIndices();
			
			if (elementosSeleccionados.length == 0) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "Debe seleccionar una localidad de la lista para poder editarla.");
				return;
			}
			
			for (int index : elementosSeleccionados)
			{
				String nuevoNombre = this.ventanaLocalidad.getTxtNombre().getText();
				
				if (nuevoNombre.equals("")) {
					JOptionPane.showMessageDialog(this.ventanaLocalidad, "No puede ingresar un nombre en blanco.");	
					return;
				}
				
				LocalidadDTO localidad_a_modificar = this.localidadesEnLista.get(index);
				localidad_a_modificar.setNombre(nuevoNombre);
				this.agenda.modificarLocalidad(localidad_a_modificar);
			}
			
			this.refrescarLista();
			this.ventanaLocalidad.limpiarFormulario();
		}
		
		public void editarTipoContacto(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaTipoContacto.getListaTiposContacto().getSelectedIndices();
			
			if (elementosSeleccionados.length == 0) {
				JOptionPane.showMessageDialog(this.ventanaTipoContacto, "Debe seleccionar un tipo de contacto de la lista para poder editarlo.");
				return;
			}
			
			for (int index : elementosSeleccionados)
			{
				String nuevoNombre = this.ventanaTipoContacto.getTxtNombre().getText();
				
				if (nuevoNombre.equals("")) {
					JOptionPane.showMessageDialog(this.ventanaTipoContacto, "No puede ingresar un nombre en blanco.");	
					return;
				}
				
				TipoContactoDTO tipo_de_contacto_a_modificar = this.tiposContactoEnLista.get(index);
				tipo_de_contacto_a_modificar.setNombre(nuevoNombre);
				this.agenda.modificarTipoContacto(tipo_de_contacto_a_modificar);
			}
			
			this.refrescarListaTipoContacto();
			this.ventanaTipoContacto.limpiarFormulario();
		}

		
		public void inicializar()
		{
			this.refrescarTabla();
			this.refrescarLista();
			this.refrescarListaTipoContacto();
			this.vista.show();
		}
		
		private void refrescarTabla()
		{
			this.personasEnTabla = agenda.obtenerPersonas();
			this.vista.llenarTabla(this.personasEnTabla);
		}
		
		private void refrescarLista()
		{
			this.localidadesEnLista = agenda.obtenerLocalidades();
			this.ventanaLocalidad.llenarLista(this.localidadesEnLista);
		}
		
		private void refrescarListaTipoContacto()
		{
			this.tiposContactoEnLista = agenda.obtenerTiposContacto();
			this.ventanaTipoContacto.llenarLista(this.tiposContactoEnLista);
		}

		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
