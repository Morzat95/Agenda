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
import presentacion.vista.Vista;
import dto.LocalidadDTO;
import dto.PersonaDTO;

public class Controlador implements ActionListener
{
		private Vista vista;
		private List<PersonaDTO> personasEnTabla;
		private List<LocalidadDTO> localidadesEnLista;
		private VentanaPersona ventanaPersona;
		private VentanaLocalidad ventanaLocalidad;
		private Agenda agenda;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.vista.getMenuLocalidad().addActionListener(a->ventanaAgregarLocalidad(a));
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.ventanaLocalidad = VentanaLocalidad.getInstance();
			this.ventanaLocalidad.getBtnAgregarLocalidad().addActionListener(l->guardarLocalidad(l));
			this.ventanaLocalidad.getBtnEliminarLocalidad().addActionListener(l->borrarLocalidad(l));
			this.ventanaLocalidad.getBtnEditarLocalidad().addActionListener(l->editarLocalidad(l));
			this.agenda = agenda;
		}
		
		private void ventanaAgregarPersona(ActionEvent a) {
			this.ventanaPersona.mostrarVentana();
		}
		
		private void ventanaAgregarLocalidad(ActionEvent a) {
			this.ventanaLocalidad.mostrarVentana();
		}

		private void guardarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel);
			this.agenda.agregarPersona(nuevaPersona);
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
		
		private void guardarLocalidad(ActionEvent l) {
			String nombre = this.ventanaLocalidad.getTxtNombre().getText();
			boolean exists = localidadesEnLista.stream().anyMatch(e -> e.getNombre().equals(nombre));
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "Ya existe una localidad con ese nombre.");
				return;
			}
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nombre);
			this.agenda.agregarLocalidad(nuevaLocalidad);
			this.refrescarLista();
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
		
		public void editarLocalidad(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaLocalidad.getListaLocalidades().getSelectedIndices();
			
			if (elementosSeleccionados.length == 0) {
				JOptionPane.showMessageDialog(ventanaLocalidad, "Debe seleccionar una localidad de la lista para poder editarla.");
				return;
			}
			
			for (int index : elementosSeleccionados)
			{
				String nuevoNombre = this.ventanaLocalidad.getTxtNombre().getText();
				LocalidadDTO localidad_a_modificar = this.localidadesEnLista.get(index);
				System.out.println(nuevoNombre);
				localidad_a_modificar.setNombre(nuevoNombre);
				this.agenda.modificarLocalidad(localidad_a_modificar);
			}
			
			this.refrescarLista();
		}
		
		public void inicializar()
		{
			this.refrescarTabla();
			this.refrescarLista();
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

		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
