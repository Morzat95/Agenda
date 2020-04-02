package modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.google.gson.Gson;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaísDTO;
import dto.PersonaDTO;
import dto.ProvinciaDTO;
import dto.TipoContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DomicilioDAO;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.PaísDAO;
import persistencia.dao.interfaz.PersonaDAO;
import persistencia.dao.interfaz.ProvinciaDAO;
import persistencia.dao.interfaz.TipoContactoDAO;
import persistencia.dao.mysql.DAOSQLFactory;

public class SeedData {
	
	public static void Initialize(DAOAbstractFactory DAOFactory) {
		
		try {
			EnsureDatabaseTablesCreated(); // Initialize database
			System.out.println("Base de datos actualizada con éxito.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al intentar crear la base de datos.");
		}
		
		// Países
		PaísDAO paísDAO = DAOFactory.createPaísDAO();
		List<PaísDTO> países = populatePaíses(paísDAO);
		
		// Provincias
		ProvinciaDAO provinciaDAO = DAOFactory.createProvinciaDAO();
		List<ProvinciaDTO> provincias = populateProvincias(provinciaDAO, países);
		
		// Localidades
		LocalidadDAO localidadDAO = DAOFactory.createLocalidadDAO();
		List<LocalidadDTO> localidades = populateLocalidades(localidadDAO, provincias, 50); // Para testear.
		
		// Tipos de Contacto
		TipoContactoDAO tipoContactoDAO = DAOFactory.createTipoContactoDAO();
		List<TipoContactoDTO> tiposDeContacto = populateTiposDeContacto(tipoContactoDAO);
		
		// Contactos
		PersonaDAO personaDAO = DAOFactory.createPersonaDAO();
		populateContactos(personaDAO, localidades, tiposDeContacto);
		
	}
	
	private static void EnsureDatabaseTablesCreated() throws Exception {
		
		Connection conn = Conexion.getConexion().getSQLConexion();
		ScriptRunner runner = new ScriptRunner(conn);
		InputStreamReader reader = null;
		
		try {
//			reader = new InputStreamReader(new FileInputStream("sql/scriptAgenda.sql"), "ISO-8859-1");
			reader = new InputStreamReader(new FileInputStream("sql/scriptAgenda.sql"), "UTF-8");
			runner.runScript(reader);
			reader.close();
			Conexion.getConexion().cerrarConexion();
		} finally {
			
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader = null;
			}
			
		}
		
	}
	
	private static List<LocalidadDTO> populateLocalidades(LocalidadDAO localidadDAO, List<ProvinciaDTO> provincias, int amount) {
		
		List<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
		
		if ( !localidadDAO.hasData() ) { // Si ya hay localidades no hacemos nada
			
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
					String nombreProvincia = l.provincia.nombre;
					ProvinciaDTO provincia = provincias.stream().filter(p -> p.getNombre().equals(nombreProvincia)).findFirst().get();
					LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nombre, provincia);
					localidades.add(nuevaLocalidad);
					localidadDAO.insert(nuevaLocalidad);
				}
				
				System.out.println("Localidades cargadas con éxito.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("No se pudo obtener las localidades.");
			}
			
		}
		
//		return localidades;
		return localidadDAO.readAll();
		
	}
	
	private static List<TipoContactoDTO> populateTiposDeContacto(TipoContactoDAO tipoContactoDAO) {
		
		List<TipoContactoDTO> tiposDecontactos = new ArrayList<TipoContactoDTO>();
		
		if ( !tipoContactoDAO.hasData() ) { // Si ya hay localidades no hacemos nada
			
			tiposDecontactos.add(new TipoContactoDTO(1, "Familia"));
			tiposDecontactos.add(new TipoContactoDTO(2, "Amigos"));
			tiposDecontactos.add(new TipoContactoDTO(3, "Trabajo"));
			
			for (TipoContactoDTO contacto : tiposDecontactos)
				tipoContactoDAO.insert(contacto);
			
			System.out.println("Tipos de Contacto cargados con éxito.");
			
		}
		
//		return tiposDecontactos;
		return tipoContactoDAO.readAll();
		
	}
	
	private static List<PersonaDTO> populateContactos(PersonaDAO personaDAO, List<LocalidadDTO> localidades, List<TipoContactoDTO> tiposDeContacto) {
		
		DomicilioDAO domicilioDAO = new DAOSQLFactory().createDomicilioDAO();
		
		if (personaDAO.hasData())
			return null;
		
		List<PersonaDTO> contactos = new ArrayList<PersonaDTO>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			DomicilioDTO domicilio1 = new DomicilioDTO("Avenida Siempreviva", 742, "0", "", localidades.get(0));
			contactos.add(new PersonaDTO(0, "Homero Simpson", "123456", "homer@donuts.com", dateFormat.parse("2020-03-01"), filterByName(tiposDeContacto, "Familia"), domicilio1, true));
			
			DomicilioDTO domicilio2 = new DomicilioDTO("Avenida Siempreviva", 742, "", "", localidades.get(0));
			contactos.add(new PersonaDTO(0, "Marge Simpson", "987654", "marge@hotmail.com", dateFormat.parse("2020-03-04"), filterByName(tiposDeContacto, "Amigos"), domicilio2, false));
			
			DomicilioDTO domicilio3 = new DomicilioDTO("Calle Falsa", 123, "", "", localidades.get(1));
			contactos.add(new PersonaDTO(0, "Bart Simpson", "666666", "elbarto@gmail.com", dateFormat.parse("2006-06-06"), filterByName(tiposDeContacto, "Familia"), domicilio3, true));
			
			DomicilioDTO domicilio4 = new DomicilioDTO("All Vegetables", 2718, "2", "e", localidades.get(8));
			contactos.add(new PersonaDTO(0, "Lisa Simpson", "3141592653", "jazzrules@realponies", dateFormat.parse("2020-03-27"), filterByName(tiposDeContacto, "Trabajo"), domicilio4, true));
			
			for (PersonaDTO persona : contactos) {
				domicilioDAO.insert(persona.getDomicilio());
				personaDAO.insert(persona);
			}
			
			System.out.println("Personas cargadas con éxito.");
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		return contactos;
		return personaDAO.readAll();
		
	}
	
	private static TipoContactoDTO filterByName(List<TipoContactoDTO> tiposDeContacto, String name) {
		return tiposDeContacto.stream().filter(t -> t.getNombre().equals(name)).findAny().orElse(null);
	}
	
	private static List<PaísDTO> populatePaíses(PaísDAO paísDAO) {
		
		List<PaísDTO> países = new ArrayList<PaísDTO>();
		
		if (!paísDAO.hasData()) {
			
			países.add(new PaísDTO(0, "Argentina"));
			países.add(new PaísDTO(0, "Chile"));
			países.add(new PaísDTO(0, "Brasil"));
			países.add(new PaísDTO(0, "Paraguay"));
			países.add(new PaísDTO(0, "Bolivia"));
			países.add(new PaísDTO(0, "Estados Unidos"));
			países.add(new PaísDTO(0, "China"));
			países.add(new PaísDTO(0, "Italia"));
			países.add(new PaísDTO(0, "Francia"));
			países.add(new PaísDTO(0, "España"));
			países.add(new PaísDTO(0, "Hungría"));
			países.add(new PaísDTO(0, "Alemania"));
			países.add(new PaísDTO(0, "Polonia"));
			países.add(new PaísDTO(0, "Noruega"));
			países.add(new PaísDTO(0, "Rusia"));
			países.add(new PaísDTO(0, "Inglaterra"));
			
			for (PaísDTO país : países)
				paísDAO.insert(país);
			
		}
		
		return países;
		
	}
	
	private static List<ProvinciaDTO> populateProvincias(ProvinciaDAO provinciaDAO, List<PaísDTO> países) {
			
		List<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
		
		if (!provinciaDAO.hasData()) {
			
			PaísDTO argentina = países.stream().filter(p -> p.getNombre().equals("Argentina")).findFirst().get();
			
			provincias.add(new ProvinciaDTO(0, "Buenos Aires", argentina));
			provincias.add(new ProvinciaDTO(0, "Catamarca", argentina));
			provincias.add(new ProvinciaDTO(0, "Chaco", argentina));
			provincias.add(new ProvinciaDTO(0, "Chubut", argentina));
			provincias.add(new ProvinciaDTO(0, "Córdoba", argentina));
			provincias.add(new ProvinciaDTO(0, "Corrientes", argentina));
			provincias.add(new ProvinciaDTO(0, "Entre Ríos", argentina));
			provincias.add(new ProvinciaDTO(0, "Formosa", argentina));
			provincias.add(new ProvinciaDTO(0, "Jujuy", argentina));
			provincias.add(new ProvinciaDTO(0, "La Pampa", argentina));
			provincias.add(new ProvinciaDTO(0, "La Rioja", argentina));
			provincias.add(new ProvinciaDTO(0, "Mendoza", argentina));
			provincias.add(new ProvinciaDTO(0, "Misiones", argentina));
			provincias.add(new ProvinciaDTO(0, "Neuquén", argentina));
			provincias.add(new ProvinciaDTO(0, "Río Negro", argentina));
			provincias.add(new ProvinciaDTO(0, "Salta", argentina));
			provincias.add(new ProvinciaDTO(0, "San Juan", argentina));
			provincias.add(new ProvinciaDTO(0, "San Luis", argentina));
			provincias.add(new ProvinciaDTO(0, "Santa Fe", argentina));
			provincias.add(new ProvinciaDTO(0, "Santiago del Estero", argentina));
			provincias.add(new ProvinciaDTO(0, "Tierra del Fuego", argentina));
			provincias.add(new ProvinciaDTO(0, "Tucumán", argentina));
			
			for (ProvinciaDTO provincia : provincias)
				provinciaDAO.insert(provincia);
			
		}
		
		return provincias;
		
	}
	
	@SuppressWarnings("unused")
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
			private String categoria;
			private String fuente;
			public Provincia provincia;
			
			class Centroide {
				private double lat;
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
