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
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
	
	public static void EnsureDatabaseTablesCreated() throws Exception {
		
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
		
		long seed = 1L;
		
		List<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
		
		if ( !localidadDAO.hasData() ) { // Si ya hay localidades no hacemos nada
			
			// Argentina
			
			// Retrieve Localidades fron JSON
			BufferedReader br;
			try {
				
				br = new BufferedReader(new FileReader("localidades.json"));
				JSONLocalidades json = new Gson().fromJson(br, JSONLocalidades.class);
				List<modelo.SeedData.JSONLocalidades.Localidad> localidadesJson = json.localidades;
				
				amount = amount > localidadesJson.size() ? localidadesJson.size() : amount;
				
				Collections.shuffle(localidadesJson, new Random(seed)); // Permutamos aleatoriamente las localidades

				while (amount-- > 0) {
					
					JSONLocalidades.Localidad localidad = localidadesJson.remove(0);
					String nombre = localidad.nombre;
					String nombreProvincia = localidad.provincia.nombre;
					
					ProvinciaDTO provincia = provincias.stream().filter(p -> p.getNombre().equals(nombreProvincia)).findFirst().get();
					
					LocalidadDTO nuevaLocalidad = new LocalidadDTO(0, nombre, provincia);
					
					localidades.add(nuevaLocalidad);
					localidadDAO.insert(nuevaLocalidad);
					
				}
				
				List<LocalidadDTO> tmp = new ArrayList<LocalidadDTO>();
				
			// Estados Unidos
			
				ProvinciaDTO alabama = provincias.stream().filter(p -> p.getNombre().equals("Alabama")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Abbeville", alabama));
				tmp.add(new LocalidadDTO(0, "Hamilton", alabama));
				
				ProvinciaDTO georgia = provincias.stream().filter(p -> p.getNombre().equals("Georgia")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Atlanta", georgia));
				tmp.add(new LocalidadDTO(0, "Savannah", georgia));
			
			// Rusia
			
				ProvinciaDTO astracán = provincias.stream().filter(p -> p.getNombre().equals("Astracán")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Jarabali", astracán));
				tmp.add(new LocalidadDTO(0, "Tulugánovka", astracán));
				
				ProvinciaDTO moscú = provincias.stream().filter(p -> p.getNombre().equals("Moscú")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Dmítrov", moscú));
				tmp.add(new LocalidadDTO(0, "Istra", moscú));
			
			// Alemania
				
				ProvinciaDTO berlín = provincias.stream().filter(p -> p.getNombre().equals("Berlín")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Dahlem", berlín));
				tmp.add(new LocalidadDTO(0, "Gatow", berlín));
				
				ProvinciaDTO brandeburgo = provincias.stream().filter(p -> p.getNombre().equals("Brandeburgo")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Friedrichswalde", brandeburgo));
				tmp.add(new LocalidadDTO(0, "Ziethen", brandeburgo));
				
			// Noruega
			
				ProvinciaDTO nordland = provincias.stream().filter(p -> p.getNombre().equals("Nordland")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Lurøy", nordland));
				tmp.add(new LocalidadDTO(0, "Værøy", nordland));
				
				ProvinciaDTO vestfoldOgTelemark = provincias.stream().filter(p -> p.getNombre().equals("Vestfold og Telemark")).findFirst().get();
				tmp.add(new LocalidadDTO(0, "Fyresdal", vestfoldOgTelemark));
				tmp.add(new LocalidadDTO(0, "Porsgrunn", vestfoldOgTelemark));
				
				for (LocalidadDTO localidad : tmp) {
					localidadDAO.insert(localidad);
					localidades.add(localidad);
				}
					
				System.out.println("Localidades cargadas con éxito.");
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				System.out.println("No se pudo obtener las localidades.");
				
			}
			
		}
		
		return localidades;
//		return localidadDAO.readAll();
		
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
		
		return tiposDecontactos;
//		return tipoContactoDAO.readAll();
		
	}
	
	private static List<PersonaDTO> populateContactos(PersonaDAO personaDAO, List<LocalidadDTO> localidades, List<TipoContactoDTO> tiposDeContacto) {
		
		DomicilioDAO domicilioDAO = new DAOSQLFactory().createDomicilioDAO();
		
		if (personaDAO.hasData())
			return null;
		
		long seed = 1L;
		Random random = new Random(seed);
		
		List<PersonaDTO> contactos = new ArrayList<PersonaDTO>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			PersonaDTO persona1 = new PersonaDTO(0, "Homero Simpson", "123456", "homer@donuts.com", dateFormat.parse("2020-03-01"), filterByName(tiposDeContacto, "Familia"), true);
			DomicilioDTO domicilio1 = new DomicilioDTO("Avenida Siempreviva", 742, "0", "", localidades.get(random.nextInt(localidades.size())));
			persona1.addDomicilio(domicilio1);
			contactos.add(persona1);
			
			PersonaDTO persona2 = new PersonaDTO(0, "Marge Simpson", "987654", "marge@hotmail.com", dateFormat.parse("2020-03-04"), filterByName(tiposDeContacto, "Amigos"), false);
			DomicilioDTO domicilio2 = new DomicilioDTO("Avenida Siempreviva", 742, "", "", localidades.get(random.nextInt(localidades.size())));
			persona2.addDomicilio(domicilio2);
			contactos.add(persona2);
			
			PersonaDTO persona3 = new PersonaDTO(0, "Bart Simpson", "666666", "elbarto@gmail.com", dateFormat.parse("2006-06-06"), filterByName(tiposDeContacto, "Familia"), true); 
			DomicilioDTO domicilio3 = new DomicilioDTO("Calle Falsa", 123, "", "", localidades.get(random.nextInt(localidades.size())));
			persona3.addDomicilio(domicilio3);
			contactos.add(persona3);
			
			PersonaDTO persona4 = new PersonaDTO(0, "Lisa Simpson", "3141592653", "jazzrules@realponies", dateFormat.parse("2020-03-27"), filterByName(tiposDeContacto, "Trabajo"), true); 
			DomicilioDTO domicilio4 = new DomicilioDTO("All Vegetables", 2718, "2", "e", localidades.get(random.nextInt(localidades.size())));
			persona4.addDomicilio(domicilio4);
			contactos.add(persona4);
			
			for (PersonaDTO persona : contactos) {
				personaDAO.insert(persona);
				domicilioDAO.insert(persona.getDomicilio());
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
			
			provincias.add(new ProvinciaDTO(0, "Ciudad Autónoma de Buenos Aires", argentina));
			provincias.add(new ProvinciaDTO(0, "Tierra del Fuego, Antártida e Islas del Atlántico Sur", argentina));
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
			provincias.add(new ProvinciaDTO(0, "Santa Cruz", argentina));
			provincias.add(new ProvinciaDTO(0, "Santa Fe", argentina));
			provincias.add(new ProvinciaDTO(0, "Santiago del Estero", argentina));
			provincias.add(new ProvinciaDTO(0, "Tierra del Fuego", argentina));
			provincias.add(new ProvinciaDTO(0, "Tucumán", argentina));
			
			PaísDTO estadosUnidos = países.stream().filter(p -> p.getNombre().equals("Estados Unidos")).findFirst().get();
			
			provincias.add(new ProvinciaDTO(0, "Alabama", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Alaska", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Arizona", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Arkansas", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "California", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Colorado", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Connecticut", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Delaware", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Florida", estadosUnidos));
			provincias.add(new ProvinciaDTO(0, "Georgia", estadosUnidos));
			
			PaísDTO rusia = países.stream().filter(p -> p.getNombre().equals("Rusia")).findFirst().get();
			
			provincias.add(new ProvinciaDTO(0, "Amur", rusia));
			provincias.add(new ProvinciaDTO(0, "Arjángelsk", rusia));
			provincias.add(new ProvinciaDTO(0, "Astracán", rusia));
			provincias.add(new ProvinciaDTO(0, "Bélgorod", rusia));
			provincias.add(new ProvinciaDTO(0, "Briansk", rusia));
			provincias.add(new ProvinciaDTO(0, "Cheliábinsk", rusia));
			provincias.add(new ProvinciaDTO(0, "Irkutsk", rusia));
			provincias.add(new ProvinciaDTO(0, "Ivánovo", rusia));
			provincias.add(new ProvinciaDTO(0, "Kaliningrado", rusia));
			provincias.add(new ProvinciaDTO(0, "Moscú", rusia));
			
			PaísDTO alemania = países.stream().filter(p -> p.getNombre().equals("Alemania")).findFirst().get();
			
			provincias.add(new ProvinciaDTO(0, "Baden-Wurtemberg", alemania));
			provincias.add(new ProvinciaDTO(0, "Baviera", alemania));
			provincias.add(new ProvinciaDTO(0, "Berlín", alemania));
			provincias.add(new ProvinciaDTO(0, "Brandeburgo", alemania));
			provincias.add(new ProvinciaDTO(0, "Bremen", alemania));
			provincias.add(new ProvinciaDTO(0, "Hamburgo", alemania));
			provincias.add(new ProvinciaDTO(0, "Hesse", alemania));
			provincias.add(new ProvinciaDTO(0, "Mecklemburgo-Pomerania Occidental", alemania));
			provincias.add(new ProvinciaDTO(0, "Sajonia", alemania));
			provincias.add(new ProvinciaDTO(0, "Renania del Norte-Westfalia", alemania));
			
			PaísDTO noruega = países.stream().filter(p -> p.getNombre().equals("Noruega")).findFirst().get();
			
			provincias.add(new ProvinciaDTO(0, "Agder", noruega));
			provincias.add(new ProvinciaDTO(0, "Innlandet", noruega));
			provincias.add(new ProvinciaDTO(0, "Møre og Romsdal", noruega));
			provincias.add(new ProvinciaDTO(0, "Nordland", noruega));
			provincias.add(new ProvinciaDTO(0, "Oslo", noruega));
			provincias.add(new ProvinciaDTO(0, "Rogaland", noruega));
			provincias.add(new ProvinciaDTO(0, "Svalbard", noruega));
			provincias.add(new ProvinciaDTO(0, "Trøndelag", noruega));
			provincias.add(new ProvinciaDTO(0, "Troms og Finnmark", noruega));
			provincias.add(new ProvinciaDTO(0, "Vestfold og Telemark", noruega));
			provincias.add(new ProvinciaDTO(0, "Vestland", noruega));
			provincias.add(new ProvinciaDTO(0, "Viken", noruega));
			
			for (ProvinciaDTO provincia : provincias)
				provinciaDAO.insert(provincia);
			
		}
		
		return provincias;
		
	}
	
	@SuppressWarnings("unused")
	private class JSONLocalidades {
//		public Localidad[] localidades;
		public List<Localidad> localidades;
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
