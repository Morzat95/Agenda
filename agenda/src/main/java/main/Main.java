package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dto.LocalidadDTO;
import dto.TipoContactoDTO;
import modelo.Agenda;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.TipoContactoDAO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.Controlador;
import presentacion.vista.Vista;


public class Main 
{

	public static void main(String[] args) 
	{
		SeedData(); // Initialize test data
		Vista vista = new Vista();
		Agenda modelo = new Agenda(new DAOSQLFactory());
		Controlador controlador = new Controlador(vista, modelo);
		controlador.inicializar();	
	}
	
	// ====================================================================================================
	// = 											 SEED DATA								 			  =
	// ====================================================================================================
	
	private static void SeedData() {
		
		// Localidades
		populateLocalidades(50); // Para testear.
		
		// Tipos de Contacto
		populateTiposDeContacto();
		
	}
	
	private static void populateLocalidades(int amount) {
		LocalidadDAO localidadDAO = new DAOSQLFactory().createLocalidadDAO();
		
		if (localidadDAO.hasData()) // Si ya hay localidades no hacemos nada
			return;
		
		// Retrieve Localidades		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("localidades.json"));
			JSONLocalidades json = new Gson().fromJson(br, JSONLocalidades.class);
			JSONLocalidades.Localidad[] localidadesJson = json.localidades;
			for (int idx = 0; idx < amount; idx++) {
				JSONLocalidades.Localidad l = localidadesJson[idx];
//				LocalidadDTO nuevaLocalidad = new LocalidadDTO(Integer.parseInt(l.id.substring(1), 10), l.nombre);
				String nombre = l.nombre.length() > 45 ? l.nombre.substring(0, 45) : l.nombre; // por limitaciones de la esctructura de la tabla
				LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nombre);
				localidadDAO.insert(nuevaLocalidad);
			}
			
			System.out.println("Localidades cargadas con éxito.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("No se pudo obtener las localidades.");
		}	
	}
	
	private static void populateTiposDeContacto() {
		TipoContactoDAO tipoContactoDAO = new DAOSQLFactory().createTipoContactoDAO();
		
		if (tipoContactoDAO.hasData()) // Si ya hay localidades no hacemos nada
			return;
		
		List<TipoContactoDTO> contactos = new ArrayList<TipoContactoDTO>(); 
		
		contactos.add(new TipoContactoDTO(0, "Familia"));
		contactos.add(new TipoContactoDTO(0, "Amigos"));
		contactos.add(new TipoContactoDTO(0, "Trabajo"));
		
		for (TipoContactoDTO contacto : contactos)
			tipoContactoDAO.insert(contacto);
		
		System.out.println("Tipos de Contacto cargados con éxito.");
		
	}
	
	private class JSONLocalidades {
		public Localidad[] localidades;
		public int total;
		public int cantidad;
		public Object parametros;
		public int inicio;
		
		abstract class BasicEntity {
			public String id;
			public String nombre;
		}
		
		class Localidad extends BasicEntity {
			@SuppressWarnings("unused")
			private String categoria;
			@SuppressWarnings("unused")
			private String fuente;
			
			class Centroide {
				@SuppressWarnings("unused")
				private double lat;
				@SuppressWarnings("unused")
				private double lon;
			}
			
			class Municipio extends BasicEntity {
			}
			class Departamento extends BasicEntity {
			}
			class Provincia extends BasicEntity {
			}
			class LocalidadCensal extends BasicEntity {
			}
		}
	}
}
