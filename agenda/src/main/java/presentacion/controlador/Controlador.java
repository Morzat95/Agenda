package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import presentacion.vista.VentanaPersona;
import presentacion.vista.VentanaTipoContacto;
import presentacion.vista.VentanaUbicaciones;
import presentacion.vista.Vista;

public class Controlador implements ActionListener
{
		private Vista vista;
		private List<PersonaDTO> personasEnTabla = new ArrayList<PersonaDTO>();
		private List<LocalidadDTO> localidadesEnLista = new ArrayList<LocalidadDTO>();
		private List<TipoContactoDTO> tiposContactoEnLista = new ArrayList<TipoContactoDTO>();
		private List<PaísDTO> paísesEnLista = new ArrayList<PaísDTO>();
		private List<ProvinciaDTO> provinciasEnLista = new ArrayList<ProvinciaDTO>();
		private VentanaPersona ventanaPersona;
		private VentanaTipoContacto ventanaTipoContacto;
		private VentanaUbicaciones ventanaUbicaciones;
		private Agenda agenda;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(a->ventanaAgregarPersona(a));
			this.vista.getBtnBorrar().addActionListener(s->borrarPersona(s));
			this.vista.getBtnEditar().addActionListener(a->ventanaEditarPersona(a));
			this.vista.getBtnReporte().addActionListener(r->mostrarReporte(r));
			this.vista.getMenuUbicaciones().addActionListener(a->ventanaUbicaciones(a));
			this.vista.getMenuTipoContacto().addActionListener(a->ventanaAgregarTipoContacto(a));
			this.ventanaPersona = VentanaPersona.getInstance();
			this.ventanaPersona.getBtnAgregarPersona().addActionListener(p->guardarPersona(p));
			this.ventanaPersona.getBtnEditarPersona().addActionListener(p->editarPersona(p));
			this.ventanaUbicaciones = VentanaUbicaciones.getInstance();
			this.ventanaUbicaciones.getBtnAgregarPais().addActionListener(l->guardarPaís(l));
			this.ventanaUbicaciones.getBtnEditarPais().addActionListener(l->editarPaís(l));
			this.ventanaUbicaciones.getBtnEliminarPais().addActionListener(l->borrarPaís(l));
			this.ventanaUbicaciones.getBtnAgregarProvincia().addActionListener(l->guardarProvincia(l));
			this.ventanaUbicaciones.getBtnEditarProvincia().addActionListener(l->editarProvincia(l));
			this.ventanaUbicaciones.getBtnEliminarProvincia().addActionListener(l->borrarProvincia(l));
			this.ventanaUbicaciones.getBtnAgregarLocalidad().addActionListener(l->guardarLocalidad(l));
			this.ventanaUbicaciones.getBtnEditarLocalidad().addActionListener(l->editarLocalidad(l));
			this.ventanaUbicaciones.getBtnEliminarLocalidad().addActionListener(l->borrarLocalidad(l));
			this.ventanaTipoContacto = VentanaTipoContacto.getInstance();
			this.ventanaTipoContacto.getBtnAgregarTipoContacto().addActionListener(l->guardarTipoContacto(l));
			this.ventanaTipoContacto.getBtnEliminarTipoContacto().addActionListener(l->borrarTipoContacto(l));
			this.ventanaTipoContacto.getBtnEditarTipoContacto().addActionListener(l->editarTipoContacto(l));
			this.ventanaTipoContacto.getListaTiposContacto().addListSelectionListener(l->actualizarFormularioTipoDeContacto(l));
			this.agenda = agenda;
		}

		public void inicializar()
		{
			this.refrescarTabla();
			this.refrescarListaTipoContacto();
			this.vista.show();
		}

		// ====================================================================================================
		// =											Países												  =
		// ====================================================================================================
		
		private void ventanaUbicaciones(ActionEvent a) {
			refrescarListaPaíses();
			this.ventanaUbicaciones.getListaPaises().addListSelectionListener(l -> this.ventanaUbicaciones.llenarFormularioPaís());
			this.ventanaUbicaciones.getListaPaises().addListSelectionListener(l -> actualizarListaProvinciasEnVentanaUbicacionesSQL());
			this.ventanaUbicaciones.getListaProvincias().addListSelectionListener(l -> this.ventanaUbicaciones.llenarFormularioProvincia());
			this.ventanaUbicaciones.getListaProvincias().addListSelectionListener(l -> actualizarListaLocalidadesEnVentanaUbicacionesSQL());
			this.ventanaUbicaciones.getListaLocalidades().addListSelectionListener(l -> this.ventanaUbicaciones.llenarFormularioLocalidad());
			this.ventanaUbicaciones.mostrarVentana();
		}
		
		private void guardarPaís(ActionEvent l) {
			String nombre = this.ventanaUbicaciones.getTextNombrePais().getText();
			
			if (nombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "No puede ingresar un nombre en blanco.");
				return;
			}
			
			boolean exists = paísesEnLista.stream().anyMatch(e -> e.getNombre().equals(nombre));
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Ya existe un país con ese nombre.");
				return;
			}
			
			PaísDTO nuevoPaís = new PaísDTO(0, nombre);
			this.agenda.agregarPaís(nuevoPaís);
			
			this.refrescarListaPaíses();
			this.ventanaUbicaciones.limpiarFormulario();
		}
		
		private void borrarPaís(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaUbicaciones.getListaPaises().getSelectedIndices();
			if (elementosSeleccionados.length == 0) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar un país de la lista para poder eliminar.");
				return;
			}
			
			for (int index : elementosSeleccionados)
			{
				this.agenda.borrarPaís(this.paísesEnLista.get(index));
			}
			
			this.refrescarListaPaíses();
			this.ventanaUbicaciones.limpiarFormulario();
		}
		
		private void editarPaís(ActionEvent s)
		{
			PaísDTO paísSeleccionado = this.ventanaUbicaciones.getListaPaises().getSelectedValue();
			
			if (paísSeleccionado == null) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar un país de la lista para poder editarlo.");
				return;
			}
			
			String nuevoNombre = this.ventanaUbicaciones.getTextNombrePais().getText();
				
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "No puede ingresar un nombre en blanco.");
				return;
			}
				
			paísSeleccionado.setNombre(nuevoNombre);
			this.agenda.modificarPaís(paísSeleccionado);
			
			this.refrescarListaPaíses();
			this.ventanaUbicaciones.limpiarFormulario();
		}

		private void refrescarListaPaíses() {
			this.paísesEnLista = agenda.obtenerPaíses();
			this.ventanaUbicaciones.llenarListaPais(this.paísesEnLista);
		}

		// ====================================================================================================
		// =											Provincias										      =
		// ====================================================================================================
		
		private void guardarProvincia(ActionEvent l) {
			String nuevoNombre = this.ventanaUbicaciones.getTextNombreProvincia().getText();
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "No puede ingresar un nombre en blanco.");
				return;
			}
			
			PaísDTO paísSeleccionado = (PaísDTO) this.ventanaUbicaciones.getListaPaises().getSelectedValue();
			
			if (paísSeleccionado == null) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar el país al que pertenece la provincia.");
				return;
			}
			
			System.out.println(provinciasEnLista.isEmpty());
			boolean exists = provinciasEnLista.stream().anyMatch(p -> p.getNombre().equals(nuevoNombre) && p.getPaís().equals(paísSeleccionado));
			
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, String.format("Ya existe una provincia con el nombre %s en %s.", nuevoNombre, paísSeleccionado.getNombre()));
				return;
			}
			
			ProvinciaDTO nuevaProvincia = new ProvinciaDTO(0, nuevoNombre, paísSeleccionado);
			this.agenda.agregarProvincia(nuevaProvincia);
			
			actualizarListaProvinciasEnVentanaUbicacionesSQL();
			this.ventanaUbicaciones.limpiarFormularioProvincia();
		}

		private void borrarProvincia(ActionEvent s)
		{
			ProvinciaDTO provinciaSeleccionada = this.ventanaUbicaciones.getListaProvincias().getSelectedValue();
			
			if(provinciaSeleccionada == null) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar una Provincia de la lista para poder eliminar.");
				return;
			}
			
			this.agenda.borrarProvincia(provinciaSeleccionada);

			actualizarListaProvinciasEnVentanaUbicacionesSQL();
			this.ventanaUbicaciones.limpiarFormularioProvincia();	
		}
		
		private void editarProvincia(ActionEvent s)
		{
			ProvinciaDTO provinciaSeleccionada = this.ventanaUbicaciones.getListaProvincias().getSelectedValue();
			
			if (provinciaSeleccionada == null) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar una provincia de la lista para poder editarla.");
				return;
			}
			
			String nuevoNombre = this.ventanaUbicaciones.getTextNombreProvincia().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "No puede ingresar un nombre en blanco.");
				return;
			}			
			
			PaísDTO paísSeleccionado = (PaísDTO) this.ventanaUbicaciones.getListaPaises().getSelectedValue();
			
			if (this.provinciasEnLista.stream().anyMatch(p->p.getNombre().equals(nuevoNombre) && p.getPaís().equals(paísSeleccionado))) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, String.format("Ya existe una provincia con el nombre %s en %s.", nuevoNombre, paísSeleccionado.getNombre()));
				return;	
			}
			
			provinciaSeleccionada.setNombre(nuevoNombre);
			provinciaSeleccionada.setPaís(paísSeleccionado);
			
			this.agenda.modificarProvincia(provinciaSeleccionada);
			
			actualizarListaProvinciasEnVentanaUbicacionesSQL();
			this.ventanaUbicaciones.limpiarFormularioProvincia();
		}
		
//		// Ya no se usa por tener baja performance en comparación con el nuevo método
//		private void actualizarListaProvinciasEnVentanaUbicaciones() {
//			PaísDTO paísSeleccionado = this.ventanaUbicaciones.getListaPaises().getSelectedValue();
//			refrescarListaProvinciasEnVentanaUbicaciones(e -> e.getPaís().equals(paísSeleccionado));
//		}
//
//		private void refrescarListaProvinciasEnVentanaUbicaciones(Predicate<ProvinciaDTO> predicado) {
//			this.provinciasEnLista = agenda.obtenerProvincias();
//			
//			List<ProvinciaDTO> provincias = provinciasEnLista;
//			
//			if (predicado != null)
//				provincias = provincias.stream().filter(predicado).collect(Collectors.toList());
//			
//			this.ventanaUbicaciones.llenarListaProvincias(provincias);
//		}
// 		// --
		private void actualizarListaProvinciasEnVentanaUbicacionesSQL() {
			PaísDTO paísSeleccionado = this.ventanaUbicaciones.getListaPaises().getSelectedValue();
			
			List<ProvinciaDTO> localidades = this.agenda.obtenerProvinciasPor(paísSeleccionado);
			this.ventanaUbicaciones.llenarListaProvincias(localidades);
		}

		// ====================================================================================================
		// =											Localidades											  =
		// ====================================================================================================
		
		private void guardarLocalidad(ActionEvent l) {
			String nuevoNombre = this.ventanaUbicaciones.getTextNombreLocalidad().getText();
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "No puede ingresar un nombre en blanco.");
				return;
			}
			
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaUbicaciones.getListaProvincias().getSelectedValue();
			
			if (provinciaSeleccionada == null ) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar la provincia a la que pertenece la localidad.");
				return;
			}
			
			boolean exists = localidadesEnLista.stream().anyMatch(e -> e.getNombre().equals(nuevoNombre) && e.getProvincia().equals(provinciaSeleccionada));
			
			if (exists) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Ya existe una localidad con ese nombre.");
				return;
			}
			
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nuevoNombre, provinciaSeleccionada);
			this.agenda.agregarLocalidad(nuevaLocalidad);
			
			actualizarListaLocalidadesEnVentanaUbicacionesSQL();
			this.ventanaUbicaciones.limpiarFormularioLocalidad();
		}

		private void borrarLocalidad(ActionEvent s)
		{			
			LocalidadDTO localidadSeleccionada = this.ventanaUbicaciones.getListaLocalidades().getSelectedValue();
			
			if(localidadSeleccionada == null) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar una Localidad de la lista para poder eliminar.");
				return;
			}
			
			this.agenda.borrarLocalidad(localidadSeleccionada);
			
			actualizarListaLocalidadesEnVentanaUbicacionesSQL();
			this.ventanaUbicaciones.limpiarFormularioLocalidad();
		}
		
		private void editarLocalidad(ActionEvent s)
		{
			LocalidadDTO localidadSeleccionada = this.ventanaUbicaciones.getListaLocalidades().getSelectedValue();
			
			if (localidadSeleccionada == null) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "Debe seleccionar una localidad de la lista para poder editarla.");
				return;
			}
			
			String nuevoNombre = this.ventanaUbicaciones.getTextNombreLocalidad().getText();
			
			if (nuevoNombre.equals("")) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, "No puede ingresar un nombre en blanco.");
				return;
			}
			
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaUbicaciones.getListaProvincias().getSelectedValue();
			
			if (this.localidadesEnLista.stream().anyMatch(p -> p.getNombre().equals(nuevoNombre) && p.getProvincia().equals(provinciaSeleccionada))) {
				JOptionPane.showMessageDialog(this.ventanaUbicaciones, String.format("Ya existe una localidad con el nombre %s en %s.", nuevoNombre, provinciaSeleccionada.getNombre()));
				return;
			}
			
			localidadSeleccionada.setNombre(nuevoNombre);
			localidadSeleccionada.setProvincia(provinciaSeleccionada);
			this.agenda.modificarLocalidad(localidadSeleccionada);
			
			actualizarListaLocalidadesEnVentanaUbicacionesSQL();
			this.ventanaUbicaciones.limpiarFormularioLocalidad();
		}

//		// Ya no se usa por tener baja performance en comparación con el nuevo método
//			private void actualizarListaLocalidadesEnVentanaUbicaciones() {
//				ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaUbicaciones.getListaProvincias().getSelectedValue();
//				refrescarListaLocalidadesEnVentanaUbicaciones(e -> e.getProvincia().equals(provinciaSeleccionada));
//			}
//	
//			private void refrescarListaLocalidadesEnVentanaUbicaciones(Predicate<LocalidadDTO> predicado) {
//				this.localidadesEnLista = agenda.obtenerLocalidades();
//				
//				List<LocalidadDTO> localidades = localidadesEnLista;
//				
//				if (predicado != null)
//					localidades = localidades.stream().filter(predicado).collect(Collectors.toList());
//				
//				this.ventanaUbicaciones.llenarListaLocalidades(localidades);
//			}
//		// --
		
		private void actualizarListaLocalidadesEnVentanaUbicacionesSQL() {
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaUbicaciones.getListaProvincias().getSelectedValue();
			
			List<LocalidadDTO> localidades = this.agenda.obtenerLocalidadesPor(provinciaSeleccionada);
			this.ventanaUbicaciones.llenarListaLocalidades(localidades);
		}

		// ====================================================================================================
		// =											Contactos											  =
		// ====================================================================================================
		
		private void ventanaAgregarPersona(ActionEvent a) {
			this.refrescarListaTipoContactoEnVentanaPersona();
			this.refrescarListaPaísesEnVentanaPersona(null);
			this.ventanaPersona.getListTipoDeContacto().setSelectedIndex(-1);
			this.ventanaPersona.getComboPaíses().setSelectedIndex(-1);
			
			this.ventanaPersona.getComboPaíses().addActionListener(l->actualizarProvinciasFormularioPersona(l));
			
			this.ventanaPersona.mostrarVentana();
		}

		private void ventanaEditarPersona(ActionEvent a)
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
			
			this.refrescarListaTipoContactoEnVentanaPersona();
			this.refrescarListaPaísesEnVentanaPersona(null);
			this.refrescarListaProvinciasEnVentanaPersona(p -> p.getPaís().equals(persona_a_editar.getDomicilio().getLocalidad().getProvincia().getPaís()));
			this.refrescarListaLocalidadesEnVentanaPersona(l -> l.getProvincia().equals(persona_a_editar.getDomicilio().getLocalidad().getProvincia()));
			
			this.ventanaPersona.llenarFormulario(persona_a_editar);
			
			this.ventanaPersona.getComboPaíses().addActionListener(l->actualizarProvinciasFormularioPersona(l));
			this.ventanaPersona.getComboProvincias().addActionListener(l->actualizarLocalidadesFormularioPersona(l));
			
			this.ventanaPersona.mostrarVentana();
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

		private void borrarPersona(ActionEvent s)
		{
			int[] filasSeleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			for (int fila : filasSeleccionadas)
			{
				this.agenda.borrarPersona(this.personasEnTabla.get(fila));
			}
			
			this.refrescarTabla();
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
			return (fecha == null || fecha.compareTo(new Date()) < 0);
		}

		private void actualizarProvinciasFormularioPersona(ActionEvent l) {
			
			System.out.println("ActionEvent combo Países");
			
			this.ventanaPersona.removeComboProvinciasListeners();
			
			PaísDTO paísSeleccionado = (PaísDTO) this.ventanaPersona.getComboPaíses().getSelectedItem();
			
			refrescarListaProvinciasEnVentanaPersona(p -> p.getPaís().equals(paísSeleccionado));
			
			this.ventanaPersona.getComboProvincias().addActionListener(a -> actualizarLocalidadesFormularioPersona(a));
			
			this.ventanaPersona.getComboProvincias().setSelectedIndex(-1);
			this.ventanaPersona.getComboLocalidades().setSelectedIndex(-1);
		}
		
		private void actualizarLocalidadesFormularioPersona(ActionEvent l) {
			
			System.out.println("ActionEvent combo Provincias");
			
			ProvinciaDTO provinciaSeleccionada = (ProvinciaDTO) this.ventanaPersona.getComboProvincias().getSelectedItem();
			
			this.ventanaPersona.getComboLocalidades().setSelectedIndex(-1);
			
			refrescarListaLocalidadesEnVentanaPersona(e -> e.getProvincia().equals(provinciaSeleccionada));
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

		private void refrescarTabla()
		{
			this.personasEnTabla = agenda.obtenerPersonas();
			this.vista.llenarTabla(this.personasEnTabla);
		}

		// ====================================================================================================
		// =										Tipos de Contactos									  	  =
		// ====================================================================================================
		
		private void ventanaAgregarTipoContacto(ActionEvent a) {
			this.ventanaTipoContacto.mostrarVentana();
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

		private void borrarTipoContacto(ActionEvent s)
		{
			int[] elementosSeleccionados = this.ventanaTipoContacto.getListaTiposContacto().getSelectedIndices();
			for (int index : elementosSeleccionados)
			{
				this.agenda.borrarTipoContacto(this.tiposContactoEnLista.get(index));
			}
			
			this.refrescarListaTipoContacto();
		}
		
		private void editarTipoContacto(ActionEvent s)
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

		private void refrescarListaTipoContacto() {
			this.tiposContactoEnLista = agenda.obtenerTiposContacto();
			this.ventanaTipoContacto.llenarLista(this.tiposContactoEnLista);
		}

		private void actualizarFormularioTipoDeContacto(ListSelectionEvent l) {
			
			if (this.ventanaTipoContacto.getListaTiposContacto().getValueIsAdjusting())
				return;
			
			TipoContactoDTO tipoDeContactoSeleccionado = this.ventanaTipoContacto.getListaTiposContacto().getSelectedValue();
			
			if (tipoDeContactoSeleccionado != null)
				this.ventanaTipoContacto.getTxtNombre().setText(tipoDeContactoSeleccionado.getNombre());				
			
		}
		
		// ====================================================================================================
		// =											Reporte												  =
		// ====================================================================================================
		
		private void mostrarReporte(ActionEvent r) {
//			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
//			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas("Favorito")); // Otra opción
			
			List<PersonaDTO> personas = agenda.obtenerPersonas();
			
//			Collections.sort(personas, (p1, p2) -> {return Boolean.compare(p1.getFavorito(), p2.getFavorito());});
			
			Collections.sort(personas, Comparator.comparing(PersonaDTO::getFavorito).thenComparing(PersonaDTO::getNombre));
			
			ReporteAgenda reporte = new ReporteAgenda(personas);
			reporte.mostrar();	
		}

		
		@Override
		public void actionPerformed(ActionEvent e) { }
		
}
