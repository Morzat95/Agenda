package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaísDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoContactoDTO;
import modelo.Agenda;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaLocalidad;
import presentacion.vista.VentanaPaís;
import presentacion.vista.VentanaPersona;
import presentacion.vista.VentanaProvincia;
import presentacion.vista.VentanaTipoContacto;
import presentacion.vista.Vista;

public class Controlador implements ActionListener
{
		private Vista vista;
		private List<PersonaDTO> personasEnTabla;
		private List<LocalidadDTO> localidadesEnLista;
		private List<TipoContactoDTO> tiposContactoEnLista;
		private List<PaísDTO> paísesEnLista;
		private List<ProvinciaDTO> provinciasEnLista;
		private VentanaPersona ventanaPersona;
		private VentanaLocalidad ventanaLocalidad;
		private VentanaTipoContacto ventanaTipoContacto;
		private VentanaPaís ventanaPaís;
		private VentanaProvincia ventanaProvincia;
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
			this.vista.getMenuPaís().addActionListener(a->ventanaPaís(a));
			this.vista.getMenuProvincia().addActionListener(a->ventanaProvincia(a));
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.ventanaPersona.getBtnEditarPersona().addActionListener(p->editarPersona(p));
//			this.ventanaPersona.getComboPaíses().addActionListener(l->actualizarProvinciasFormularioPersona(l));
//			this.ventanaPersona.getComboProvincias().addActionListener(l->actualizarLocalidadesFormularioPersona(l));
			this.ventanaLocalidad = VentanaLocalidad.getInstance();
			this.ventanaLocalidad.getBtnAgregarLocalidad().addActionListener(l->guardarLocalidad(l));
			this.ventanaLocalidad.getBtnEliminarLocalidad().addActionListener(l->borrarLocalidad(l));
			this.ventanaLocalidad.getBtnEditarLocalidad().addActionListener(l->editarLocalidad(l));
			this.ventanaLocalidad.getListaLocalidades().addListSelectionListener(l->actualizarFormularioLocalidad(l));
			this.ventanaTipoContacto = VentanaTipoContacto.getInstance();
			this.ventanaTipoContacto.getBtnAgregarTipoContacto().addActionListener(l->guardarTipoContacto(l));
			this.ventanaTipoContacto.getBtnEliminarTipoContacto().addActionListener(l->borrarTipoContacto(l));
			this.ventanaTipoContacto.getBtnEditarTipoContacto().addActionListener(l->editarTipoContacto(l));
			this.ventanaTipoContacto.getListaTiposContacto().addListSelectionListener(l->actualizarFormularioTipoDeContacto(l));
			this.ventanaPaís = VentanaPaís.getInstance();
			this.ventanaPaís.getBtnAgregarPaís().addActionListener(l->guardarPaís(l));
			this.ventanaPaís.getBtnEliminarPaís().addActionListener(l->borrarPaís(l));
			this.ventanaPaís.getBtnEditarPaís().addActionListener(l->editarPaís(l));
			this.ventanaProvincia = VentanaProvincia.getInstance();
			this.ventanaProvincia.getBtnAgregarProvincia().addActionListener(l->guardarProvincia(l));
			this.ventanaProvincia.getBtnEliminarProvincia().addActionListener(l->borrarProvincia(l));
			this.ventanaProvincia.getBtnEditarProvincia().addActionListener(l->editarProvincia(l));
			this.ventanaProvincia.getListaProvincias().addListSelectionListener(l->actualizarFormularioProvincia(l));
			this.agenda = agenda;
		}

		private void ventanaAgregarPersona(ActionEvent a) {
			this.refrescarListaTipoContactoEnVentanaPersona();
			this.refrescarListaPaísesEnVentanaPersona(null);
//			this.refrescarListaProvinciasEnVentanaPersona(null);
//			this.refrescarListaLocalidadesEnVentanaPersona(null);
			this.ventanaPersona.getListTipoDeContacto().setSelectedIndex(-1);
			this.ventanaPersona.getComboPaíses().setSelectedIndex(-1);
			
			this.ventanaPersona.getComboPaíses().addActionListener(l->actualizarProvinciasFormularioPersona(l));
//			this.ventanaPersona.getComboProvincias().addActionListener(l->actualizarLocalidadesFormularioPersona(l));
			
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
			
			this.refrescarListaTipoContactoEnVentanaPersona();
			this.refrescarListaPaísesEnVentanaPersona(null);
			this.refrescarListaProvinciasEnVentanaPersona(null);
			this.refrescarListaLocalidadesEnVentanaPersona(null);
			
			PersonaDTO persona_a_editar = this.personasEnTabla.get(index);
			this.ventanaPersona.llenarFormulario(persona_a_editar);
			
			this.ventanaPersona.getComboPaíses().addActionListener(l->actualizarProvinciasFormularioPersona(l));
//			this.ventanaPersona.getComboProvincias().addActionListener(l->actualizarLocalidadesFormularioPersona(l));
			
			this.ventanaPersona.mostrarVentana();
		}
		
		private void ventanaAgregarLocalidad(ActionEvent a) {
			refrescarComboProvinciasEnVentanaLocalidad();
			this.ventanaLocalidad.mostrarVentana();
		}
		
		private void ventanaAgregarTipoContacto(ActionEvent a) {
			this.ventanaTipoContacto.mostrarVentana();
		}

		private void ventanaPaís(ActionEvent a) {
			this.ventanaPaís.mostrarVentana();
		}
		
		private void ventanaProvincia(ActionEvent a) {
			refrescarComboPaísesEnVentanaProvincia();
			this.ventanaProvincia.mostrarVentana();
		}
		
		private void guardarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String email = ventanaPersona.getTxtEmail().getText();
			Date fechaCumpleanio = ventanaPersona.getFechaCumpleanio();
			TipoContactoDTO tipoContacto = (TipoContactoDTO) ventanaPersona.getListTipoDeContacto().getSelectedItem();
			
			if(nombre.isEmpty() || nombre.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Nombre y Apellido es obligatorio.\nNo debe exceder los 45 caracteres"); return;
			}
			
			if (!verifyTelefono(tel)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar un Teléfono valido.\nNo debe exceder los 20 caracteres"); return;
			}
			
			if (email.length() > 45 || !verifyEmail(email)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar un Email valido.\nNo debe exceder los 45 caracteres"); return;
			}
			
			if (!verifyFecha(fechaCumpleanio)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar una fecha valida."); return;
			}
				
			if (tipoContacto == null) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe seleccionar un Tipo de Contacto."); return;
			}
			
			boolean favorito = ventanaPersona.getCheckFavorito().isSelected();
			
			String calle = ventanaPersona.getTxtCalle().getText();
			String altura_string = ventanaPersona.getTxtAltura().getText();
			int altura;
			String piso = ventanaPersona.getTxtPiso().getText();
			String departamento = ventanaPersona.getTxtDepartamento().getText();
			LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboLocalidades().getSelectedItem();
			
			if (calle.isEmpty() || calle.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Calle es obligatorio.\nNo debe exceder los 45 caracteres"); return;
			}
			
			if(!verifyAltura(altura_string)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar una altura valida.\nNo debe exceder los 5 caracteres"); return;
			} else {
				altura = Integer.parseInt(altura_string);
			}
			
			if(piso.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "El campo Piso no debe exceder los 45 caracteres."); return;
			}
			
			if(departamento.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "El campo Departamento no debe exceder los 45 caracteres."); return;
			}
			
			if (localidad == null) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe seleccionar una localidad."); return;
			}
			
			DomicilioDTO domicilioDTO = new DomicilioDTO(calle, altura, piso, departamento, localidad);
			PersonaDTO nuevaPersona = new PersonaDTO(0, nombre, tel, email, fechaCumpleanio, tipoContacto, domicilioDTO, favorito);
				
			this.agenda.agregarDomicilio(domicilioDTO);
			this.agenda.agregarPersona(nuevaPersona);

			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
		
		private void editarPersona(ActionEvent p) {
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String tel = ventanaPersona.getTxtTelefono().getText();
			String email = ventanaPersona.getTxtEmail().getText();
			Date fechaCumpleanio = ventanaPersona.getFechaCumpleanio();
			TipoContactoDTO tipoContacto = (TipoContactoDTO) ventanaPersona.getListTipoDeContacto().getSelectedItem();
			boolean favorito = ventanaPersona.getCheckFavorito().isSelected();
			
			if(nombre.isEmpty() || nombre.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Nombre y Apellido es obligatorio.\nNo debe exceder los 45 caracteres"); return;
			}
			
			if (!verifyTelefono(tel)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar un Teléfono valido.\nNo debe exceder los 20 caracteres"); return;
			}
			
			if (email.length() > 45 || !verifyEmail(email)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar un Email valido.\nNo debe exceder los 45 caracteres"); return;
			}
			
			if (!verifyFecha(fechaCumpleanio)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar una fecha valida."); return;
			}
				
			if (tipoContacto == null) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe seleccionar un Tipo de Contacto."); return;
			}
			
			String calle = ventanaPersona.getTxtCalle().getText();
			String altura_string = this.ventanaPersona.getTxtAltura().getText();
			int altura;
			String piso = ventanaPersona.getTxtPiso().getText();
			String departamento = ventanaPersona.getTxtDepartamento().getText();
			LocalidadDTO localidad = (LocalidadDTO) ventanaPersona.getComboLocalidades().getSelectedItem();
					
			if (calle.isEmpty() || calle.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Calle es obligatorio.\nNo debe exceder los 45 caracteres"); return;
			}
			
			if(!verifyAltura(altura_string)) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe ingresar una altura valida.\nNo debe exceder los 5 caracteres"); return;
			} else {
				altura = Integer.parseInt(altura_string);
			}
			
			if(piso.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "El campo Piso no debe exceder los 45 caracteres."); return;
			}
			
			if(departamento.length() > 45) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "El campo Departamento no debe exceder los 45 caracteres."); return;
			}
			
			if (localidad == null) {
				JOptionPane.showMessageDialog(this.ventanaPersona, "Debe seleccionar una localidad."); return;
			}
			
			int index = this.vista.getTablaPersonas().getSelectedRow();
			PersonaDTO persona_a_editar = this.personasEnTabla.get(index);
			persona_a_editar.setNombre(nombre);
			persona_a_editar.setTelefono(tel);
			persona_a_editar.setEmail(email);
			persona_a_editar.setFechaCumpleanio(fechaCumpleanio);
			persona_a_editar.setTipoDeContacto(tipoContacto);
			persona_a_editar.setFavorito(favorito);
			persona_a_editar.getDomicilio().setCalle(calle);
			persona_a_editar.getDomicilio().setAltura(altura);
			persona_a_editar.getDomicilio().setPiso(piso);
			persona_a_editar.getDomicilio().setDepartamento(departamento);
			persona_a_editar.getDomicilio().setLocalidad(localidad);
			
			this.agenda.modificarDomicilio(persona_a_editar.getDomicilio());
			this.agenda.editarPersona(persona_a_editar);
			
			this.refrescarTabla();
			this.ventanaPersona.cerrar();
		}
		
		private boolean verifyEmail(String email) {
//			return email.matches("^(.+)@(.+)$"); // Simplest regex to validate email
			return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"); // Java email validation permitted by RFC 5322. TODO: escape sensitive characters to avoid SQL injection attacks.
		}
		
		private boolean verifyAltura(String altura) {
			return altura.matches("(0|[1-9]\\d*){1,5}");
		}
		
		private boolean verifyTelefono(String telefono) {
//			return telefono.matches("/^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$/");
			return telefono.matches("\\d{1,20}"); // TODO: validar correctamente
		}
		
		private boolean verifyFecha(Date fecha) {
			return (fecha == null || fecha.compareTo(new Date()) > 0);
		}
		
		private void guardarLocalidad(ActionEvent l) {
			String nuevoNombre = this.ventanaLocalidad.getTxtNombre().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaLocalidad.getComboProvincias().getSelectedItem();
			
			if (provinciaSeleccionada == null ) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, "Debe seleccionar la provincia a la que pertenece la localidad.");
				return;
			}
			
			boolean exists = localidadesEnLista.stream().anyMatch(e -> e.getNombre().equals(nuevoNombre) && e.getProvincia().equals(provinciaSeleccionada));
			
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "Ya existe una localidad con ese nombre.");
				return;
			}
			
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nuevoNombre, provinciaSeleccionada);
			this.agenda.agregarLocalidad(nuevaLocalidad);
			this.refrescarListaLocalidades();
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

		private void guardarProvincia(ActionEvent l) {
			String nuevoNombre = this.ventanaProvincia.getTxtNombre().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			PaísDTO paísAsignado = (PaísDTO) this.ventanaProvincia.getComboPaíses().getSelectedItem();
			
			if (paísAsignado == null) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, "Debe seleccionar el país al que pertenece la provincia.");
				return;
			}
			
			boolean exists = provinciasEnLista.stream().anyMatch(p -> p.getNombre().equals(nuevoNombre) && p.getPaís().equals(paísAsignado));
			
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, String.format("Ya existe una provincia con el nombre %s en %s.", nuevoNombre, paísAsignado.getNombre()));
				return;
			}
			
			ProvinciaDTO nuevaProvincia = new ProvinciaDTO(0, nuevoNombre, paísAsignado);
			this.agenda.agregarProvincia(nuevaProvincia);
			this.refrescarListaProvincias();
			this.ventanaProvincia.limpiarFormulario();
		}
		
		private void guardarPaís(ActionEvent l) {
			String nombre = this.ventanaPaís.getTxtNombre().getText();
			
			if (nombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaPaís, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			boolean exists = paísesEnLista.stream().anyMatch(e -> e.getNombre().equals(nombre));
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaPaís, "Ya existe un país con ese nombre.");
				return;
			}
			
			PaísDTO nuevoPaís = new PaísDTO(0, nombre);
			this.agenda.agregarPaís(nuevoPaís);
			this.refrescarListaPaíses();
			this.ventanaPaís.limpiarFormulario();
		}
		
		private void mostrarReporte(ActionEvent r) {
//			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
//			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas("Favorito")); // Otra opción
			
			List<PersonaDTO> personas = agenda.obtenerPersonas();
			
//			Collections.sort(personas, (p1, p2) -> {return Boolean.compare(p1.getFavorito(), p2.getFavorito());});
			
			Collections.sort(personas, Comparator.comparing(PersonaDTO::getFavorito).thenComparing(PersonaDTO::getNombre));
			
			ReporteAgenda reporte = new ReporteAgenda(personas);
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
			
			this.refrescarListaLocalidades();
			this.ventanaLocalidad.limpiarFormulario();
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
		
		public void borrarPaís(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaPaís.getListaPaíses().getSelectedIndices();
			for (int index : elementosSeleccionados)
			{
				this.agenda.borrarPaís(this.paísesEnLista.get(index));
			}
			
			this.refrescarListaPaíses();
		}
		
		public void borrarProvincia(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaProvincia.getListaProvincias().getSelectedIndices();
			for (int index : elementosSeleccionados)
			{
				this.agenda.borrarProvincia(this.provinciasEnLista.get(index));
			}
			
			this.refrescarListaProvincias();
			this.ventanaProvincia.limpiarFormulario();
		}
		
		public void editarLocalidad(ActionEvent s)
		{
			LocalidadDTO localidadSeleccionada = this.ventanaLocalidad.getListaLocalidades().getSelectedValue();
			
			if (localidadSeleccionada == null) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "Debe seleccionar una localidad de la lista para poder editarla.");
				return;
			}
			
			String nuevoNombre = this.ventanaLocalidad.getTxtNombre().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaLocalidad, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaLocalidad.getComboProvincias().getSelectedItem();
			
			if (this.localidadesEnLista.stream().anyMatch(p -> p.getNombre().equals(nuevoNombre) && p.getProvincia().equals(provinciaSeleccionada))) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, String.format("Ya existe una localidad con el nombre %s en %s.", nuevoNombre, provinciaSeleccionada.getNombre()));
				return;
			}
			
			localidadSeleccionada.setNombre(nuevoNombre);
			localidadSeleccionada.setProvincia(provinciaSeleccionada);
			this.agenda.modificarLocalidad(localidadSeleccionada);
			
			this.refrescarListaLocalidades();
			this.ventanaLocalidad.limpiarFormulario();
		}
		
		public void editarTipoContacto(ActionEvent s)
		{
			TipoContactoDTO elementosSeleccionados = this.ventanaTipoContacto.getListaTiposContacto().getSelectedValue();
			
			if (elementosSeleccionados == null) {
				JOptionPane.showMessageDialog(this.ventanaTipoContacto, "Debe seleccionar un tipo de contacto de la lista para poder editarlo.");
				return;
			}
			
			String nuevoNombre = this.ventanaTipoContacto.getTxtNombre().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaTipoContacto, "No puede ingresar un nombre en blanco.");	
				return;
			}
			
			if (this.tiposContactoEnLista.stream().anyMatch(t -> t.getNombre().equals(nuevoNombre))) {
				JOptionPane.showMessageDialog(this.ventanaTipoContacto, String.format("Ya existe un tipo de contacto con el nombre %s.", nuevoNombre));
				return;
			}
			
			elementosSeleccionados.setNombre(nuevoNombre);
			this.agenda.modificarTipoContacto(elementosSeleccionados);
			
			this.refrescarListaTipoContacto();
			this.ventanaTipoContacto.limpiarFormulario();
		}

		public void editarPaís(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaPaís.getListaPaíses().getSelectedIndices();
			
			if (elementosSeleccionados.length == 0) {
				JOptionPane.showMessageDialog(this.ventanaPaís, "Debe seleccionar un país de la lista para poder editarlo.");
				return;
			}
			
			for (int index : elementosSeleccionados)
			{
				String nuevoNombre = this.ventanaPaís.getTxtNombre().getText();
				
				if (nuevoNombre.equals("")) {
					JOptionPane.showMessageDialog(this.ventanaPaís, "No puede ingresar un nombre en blanco.");	
					return;
				}
				
				PaísDTO país_a_modificar = this.paísesEnLista.get(index);
				país_a_modificar.setNombre(nuevoNombre);
				this.agenda.modificarPaís(país_a_modificar);
			}
			
			this.refrescarListaPaíses();
			this.ventanaPaís.limpiarFormulario();
		}

		public void editarProvincia(ActionEvent s)
		{
			ProvinciaDTO provinciaSeleccionada = this.ventanaProvincia.getListaProvincias().getSelectedValue();
			
			if (provinciaSeleccionada == null) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, "Debe seleccionar una provincia de la lista para poder editarla.");
				return;
			}
			
			String nuevoNombre = this.ventanaProvincia.getTxtNombre().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, "No puede ingresar un nombre en blanco.");	
				return;
			}			
			
			PaísDTO paísSeleccionado = (PaísDTO) this.ventanaProvincia.getComboPaíses().getSelectedItem();
			
			if (this.provinciasEnLista.stream().anyMatch(p->p.getNombre().equals(nuevoNombre) && p.getPaís().equals(paísSeleccionado))) {
				JOptionPane.showMessageDialog(this.ventanaProvincia, String.format("Ya existe una provincia con el nombre %s en %s.", nuevoNombre, paísSeleccionado.getNombre()));
				return;	
			}
			
			provinciaSeleccionada.setNombre(nuevoNombre);
			provinciaSeleccionada.setPaís(paísSeleccionado);
			
			this.agenda.modificarProvincia(provinciaSeleccionada);
			this.refrescarListaProvincias();
			this.ventanaProvincia.limpiarFormulario();
		}
		
		private void actualizarProvinciasFormularioPersona(ActionEvent l) {
			
			System.out.println("ActionEvent combo Países");
			
			this.ventanaPersona.removeComboProvinciasListeners();
			
			PaísDTO paísSeleccionado = (PaísDTO) this.ventanaPersona.getComboPaíses().getSelectedItem();
			
			refrescarListaProvinciasEnVentanaPersona(p -> p.getPaís().equals(paísSeleccionado));
			
			this.ventanaPersona.getComboProvincias().addActionListener(a -> actualizarLocalidadesFormularioPersona(a));
		}
		
		private void actualizarLocalidadesFormularioPersona(ActionEvent l) {
			
			System.out.println("ActionEvent combo Provincias");
			
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaPersona.getComboProvincias().getSelectedItem();
			
			refrescarListaLocalidadesEnVentanaPersona(e -> e.getProvincia().equals(provinciaSeleccionada));
		}
		
		public void actualizarFormularioTipoDeContacto(ListSelectionEvent l) {
			
			if (this.ventanaTipoContacto.getListaTiposContacto().getValueIsAdjusting())
				return;
					
			TipoContactoDTO tipoDeContactoSeleccionado = this.ventanaTipoContacto.getListaTiposContacto().getSelectedValue();
			
			if (tipoDeContactoSeleccionado != null)
				this.ventanaTipoContacto.getTxtNombre().setText(tipoDeContactoSeleccionado.getNombre());				
			
		}
		
		public void actualizarFormularioLocalidad(ListSelectionEvent l) {
			
			if (this.ventanaLocalidad.getListaLocalidades().getValueIsAdjusting())
				return;
					
			LocalidadDTO localidadSeleccionada = this.ventanaLocalidad.getListaLocalidades().getSelectedValue();
			
			if (localidadSeleccionada != null) {
				this.ventanaLocalidad.getTxtNombre().setText(localidadSeleccionada.getNombre());
				this.ventanaLocalidad.getComboProvincias().setSelectedItem(localidadSeleccionada.getProvincia());				
			}
			
		}
		
		public void actualizarFormularioProvincia(ListSelectionEvent l) {
			
			if (this.ventanaProvincia.getListaProvincias().getValueIsAdjusting())
				return;
					
			ProvinciaDTO provinciaSeleccionada = this.ventanaProvincia.getListaProvincias().getSelectedValue();
			
			if (provinciaSeleccionada != null) {
				this.ventanaProvincia.getTxtNombre().setText(provinciaSeleccionada.getNombre());
				this.ventanaProvincia.getComboPaíses().setSelectedItem(provinciaSeleccionada.getPaís());				
			}
			
		}
		
		public void inicializar()
		{
			this.refrescarTabla();
			this.refrescarListaLocalidades();
			this.refrescarListaTipoContacto();
			this.refrescarListaPaíses();
			this.refrescarListaProvincias();
			this.vista.show();
		}
		
		private void refrescarTabla()
		{
			this.personasEnTabla = agenda.obtenerPersonas();
			this.vista.llenarTabla(this.personasEnTabla);
		}
		
		private void refrescarListaLocalidades() {
			this.localidadesEnLista = agenda.obtenerLocalidades();
			this.ventanaLocalidad.llenarLista(this.localidadesEnLista);
		}
		
		private void refrescarListaTipoContacto() {
			this.tiposContactoEnLista = agenda.obtenerTiposContacto();
			this.ventanaTipoContacto.llenarLista(this.tiposContactoEnLista);
		}
		
		private void refrescarListaPaíses() {
			this.paísesEnLista = agenda.obtenerPaíses();
			this.ventanaPaís.llenarLista(this.paísesEnLista);
		}
		
		private void refrescarListaProvincias() {
			this.provinciasEnLista = agenda.obtenerProvincias();
			this.ventanaProvincia.llenarListaProvincias(this.provinciasEnLista);
		}
		
		private void refrescarComboProvinciasEnVentanaLocalidad() {
			this.provinciasEnLista = agenda.obtenerProvincias();
			this.ventanaLocalidad.llenarComboProvincias(this.provinciasEnLista);
		}
		
		private void refrescarComboPaísesEnVentanaProvincia() {
			this.paísesEnLista = agenda.obtenerPaíses();
			this.ventanaProvincia.llenarComboPaíses(this.paísesEnLista);
		}
		
		private void refrescarListaTipoContactoEnVentanaPersona() {
			this.tiposContactoEnLista = agenda.obtenerTiposContacto();
			this.ventanaPersona.llenarComboTipoContacto(this.tiposContactoEnLista);
		}
		
		private void refrescarListaPaísesEnVentanaPersona(Predicate<PaísDTO> predicado) {
			this.paísesEnLista = agenda.obtenerPaíses();
			
			List<PaísDTO> países = paísesEnLista;
			
			if (predicado != null)
				países = países.stream().filter(predicado).collect(Collectors.toList());
			
			this.ventanaPersona.llenarComboPaíses(países);
		}
		
		private void refrescarListaProvinciasEnVentanaPersona(Predicate<ProvinciaDTO> predicado) {
			this.provinciasEnLista = agenda.obtenerProvincias();
			
			List<ProvinciaDTO> provincias = provinciasEnLista;
			
			if (predicado != null)
				provincias = provincias.stream().filter(predicado).collect(Collectors.toList());
			
			this.ventanaPersona.llenarComboProvincias(provincias);
		}
		
		private void refrescarListaLocalidadesEnVentanaPersona(Predicate<LocalidadDTO> predicado) {
			this.localidadesEnLista = agenda.obtenerLocalidades();
			
			List<LocalidadDTO> localidades = localidadesEnLista;
			
			if (predicado != null)
				localidades = localidades.stream().filter(predicado).collect(Collectors.toList());
			
			this.ventanaPersona.llenarComboLocalidades(localidades);
		}

		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
