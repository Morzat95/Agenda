package modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;

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
//			EnsureDatabaseTablesCreated(); // Initialize database
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
	
	private static void EnsureDatabaseTablesCreated() {
		
		Connection conn = Conexion.getConexion().getSQLConexion();
		ScriptRunner runner = new ScriptRunner(conn);
		InputStreamReader reader = null;
		
		try {
//			reader = new InputStreamReader(new FileInputStream("sql/scriptAgenda.sql"), "ISO-8859-1");
			reader = new InputStreamReader(new FileInputStream("sql/scriptAgenda.sql"), "UTF-8");

//			reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("sql/scriptAgenda.sql"), "UTF-8");
			
//			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//			reader = new InputStreamReader(classloader.getResourceAsStream("/sql/scriptAgenda.sql"), "UTF-8");
			
//			Class clazz = Matcher.class;
//		    reader = new InputStreamReader(clazz.getResourceAsStream("/scriptAgenda.sql"), "UTF-8");
			
			runner.runScript(reader);
			reader.close();
			Conexion.getConexion().cerrarConexion();
		} 
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		finally {
			
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
	
	private static List<PaísDTO> populatePaíses(PaísDAO paísDAO) {
		
		List<PaísDTO> países = new ArrayList<PaísDTO>();
		
		if (!paísDAO.hasData()) {
			
			países.add(new PaísDTO("Argentina"));
			países.add(new PaísDTO("Chile"));
			países.add(new PaísDTO("Brasil"));
			países.add(new PaísDTO("Paraguay"));
			países.add(new PaísDTO("Bolivia"));
			países.add(new PaísDTO("Estados Unidos"));
			países.add(new PaísDTO("China"));
			países.add(new PaísDTO("Italia"));
			países.add(new PaísDTO("Francia"));
			países.add(new PaísDTO("España"));
			países.add(new PaísDTO("Hungría"));
			países.add(new PaísDTO("Alemania"));
			países.add(new PaísDTO("Polonia"));
			países.add(new PaísDTO("Noruega"));
			países.add(new PaísDTO("Rusia"));
			países.add(new PaísDTO("Inglaterra"));
			
			for (PaísDTO país : países)
				paísDAO.insert(país);
			
		}
		
		System.out.println("Países cargados con éxito.");
		
		System.out.println(paísDAO.cantidadElementos());
//		System.out.println(paísDAO.readAll().size());
		
		return países;
		
	}
	
	private static List<ProvinciaDTO> populateProvincias(ProvinciaDAO provinciaDAO, List<PaísDTO> países) {
		
		List<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
		
		if (!provinciaDAO.hasData()) {
			
			PaísDTO argentina = países.stream().filter(p -> p.getNombre().equals("Argentina")).findFirst().get();
			
			provincias.add(new ProvinciaDTO("Ciudad Autónoma de Buenos Aires", argentina));
			provincias.add(new ProvinciaDTO("Tierra del Fuego, Antártida e Islas del Atlántico Sur", argentina));
			provincias.add(new ProvinciaDTO("Buenos Aires", argentina));
			provincias.add(new ProvinciaDTO("Catamarca", argentina));
			provincias.add(new ProvinciaDTO("Chaco", argentina));
			provincias.add(new ProvinciaDTO("Chubut", argentina));
			provincias.add(new ProvinciaDTO("Córdoba", argentina));
			provincias.add(new ProvinciaDTO("Corrientes", argentina));
			provincias.add(new ProvinciaDTO("Entre Ríos", argentina));
			provincias.add(new ProvinciaDTO("Formosa", argentina));
			provincias.add(new ProvinciaDTO("Jujuy", argentina));
			provincias.add(new ProvinciaDTO("La Pampa", argentina));
			provincias.add(new ProvinciaDTO("La Rioja", argentina));
			provincias.add(new ProvinciaDTO("Mendoza", argentina));
			provincias.add(new ProvinciaDTO("Misiones", argentina));
			provincias.add(new ProvinciaDTO("Neuquén", argentina));
			provincias.add(new ProvinciaDTO("Río Negro", argentina));
			provincias.add(new ProvinciaDTO("Salta", argentina));
			provincias.add(new ProvinciaDTO("San Juan", argentina));
			provincias.add(new ProvinciaDTO("San Luis", argentina));
			provincias.add(new ProvinciaDTO("Santa Cruz", argentina));
			provincias.add(new ProvinciaDTO("Santa Fe", argentina));
			provincias.add(new ProvinciaDTO("Santiago del Estero", argentina));
			provincias.add(new ProvinciaDTO("Tierra del Fuego", argentina));
			provincias.add(new ProvinciaDTO("Tucumán", argentina));
			
			PaísDTO estadosUnidos = países.stream().filter(p -> p.getNombre().equals("Estados Unidos")).findFirst().get();
			
			provincias.add(new ProvinciaDTO("Alabama", estadosUnidos));
			provincias.add(new ProvinciaDTO("Alaska", estadosUnidos));
			provincias.add(new ProvinciaDTO("Arizona", estadosUnidos));
			provincias.add(new ProvinciaDTO("Arkansas", estadosUnidos));
			provincias.add(new ProvinciaDTO("California", estadosUnidos));
			provincias.add(new ProvinciaDTO("Colorado", estadosUnidos));
			provincias.add(new ProvinciaDTO("Connecticut", estadosUnidos));
			provincias.add(new ProvinciaDTO("Delaware", estadosUnidos));
			provincias.add(new ProvinciaDTO("Florida", estadosUnidos));
			provincias.add(new ProvinciaDTO("Georgia", estadosUnidos));
			
			PaísDTO rusia = países.stream().filter(p -> p.getNombre().equals("Rusia")).findFirst().get();
			
			provincias.add(new ProvinciaDTO("Amur", rusia));
			provincias.add(new ProvinciaDTO("Arjángelsk", rusia));
			provincias.add(new ProvinciaDTO("Astracán", rusia));
			provincias.add(new ProvinciaDTO("Bélgorod", rusia));
			provincias.add(new ProvinciaDTO("Briansk", rusia));
			provincias.add(new ProvinciaDTO("Cheliábinsk", rusia));
			provincias.add(new ProvinciaDTO("Irkutsk", rusia));
			provincias.add(new ProvinciaDTO("Ivánovo", rusia));
			provincias.add(new ProvinciaDTO("Kaliningrado", rusia));
			provincias.add(new ProvinciaDTO("Moscú", rusia));
			
			PaísDTO alemania = países.stream().filter(p -> p.getNombre().equals("Alemania")).findFirst().get();
			
			provincias.add(new ProvinciaDTO("Baden-Wurtemberg", alemania));
			provincias.add(new ProvinciaDTO("Baviera", alemania));
			provincias.add(new ProvinciaDTO("Berlín", alemania));
			provincias.add(new ProvinciaDTO("Brandeburgo", alemania));
			provincias.add(new ProvinciaDTO("Bremen", alemania));
			provincias.add(new ProvinciaDTO("Hamburgo", alemania));
			provincias.add(new ProvinciaDTO("Hesse", alemania));
			provincias.add(new ProvinciaDTO("Mecklemburgo-Pomerania Occidental", alemania));
			provincias.add(new ProvinciaDTO("Sajonia", alemania));
			provincias.add(new ProvinciaDTO("Renania del Norte-Westfalia", alemania));
			
			PaísDTO noruega = países.stream().filter(p -> p.getNombre().equals("Noruega")).findFirst().get();
			
			provincias.add(new ProvinciaDTO("Agder", noruega));
			provincias.add(new ProvinciaDTO("Innlandet", noruega));
			provincias.add(new ProvinciaDTO("Møre og Romsdal", noruega));
			provincias.add(new ProvinciaDTO("Nordland", noruega));
			provincias.add(new ProvinciaDTO("Oslo", noruega));
			provincias.add(new ProvinciaDTO("Rogaland", noruega));
			provincias.add(new ProvinciaDTO("Svalbard", noruega));
			provincias.add(new ProvinciaDTO("Trøndelag", noruega));
			provincias.add(new ProvinciaDTO("Troms og Finnmark", noruega));
			provincias.add(new ProvinciaDTO("Vestfold og Telemark", noruega));
			provincias.add(new ProvinciaDTO("Vestland", noruega));
			provincias.add(new ProvinciaDTO("Viken", noruega));
			
			for (ProvinciaDTO provincia : provincias)
				provinciaDAO.insert(provincia);
			
			System.out.println("Provincias cargadas con éxito.");
			
		}
		
		return provincias;
		
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
					
					LocalidadDTO nuevaLocalidad = new LocalidadDTO(nombre, provincia);
					
					localidades.add(nuevaLocalidad);
					localidadDAO.insert(nuevaLocalidad);
					
				}
				
				List<LocalidadDTO> tmp = new ArrayList<LocalidadDTO>();
				
			// Estados Unidos
			
				ProvinciaDTO alabama = provincias.stream().filter(p -> p.getNombre().equals("Alabama")).findFirst().get();
				tmp.add(new LocalidadDTO("Abbeville", alabama));
				tmp.add(new LocalidadDTO("Hamilton", alabama));
				
				ProvinciaDTO georgia = provincias.stream().filter(p -> p.getNombre().equals("Georgia")).findFirst().get();
				tmp.add(new LocalidadDTO("Atlanta", georgia));
				tmp.add(new LocalidadDTO("Savannah", georgia));
			
			// Rusia
			
				ProvinciaDTO astracán = provincias.stream().filter(p -> p.getNombre().equals("Astracán")).findFirst().get();
				tmp.add(new LocalidadDTO("Jarabali", astracán));
				tmp.add(new LocalidadDTO("Tulugánovka", astracán));
				
				ProvinciaDTO moscú = provincias.stream().filter(p -> p.getNombre().equals("Moscú")).findFirst().get();
				tmp.add(new LocalidadDTO("Dmítrov", moscú));
				tmp.add(new LocalidadDTO("Istra", moscú));
			
			// Alemania
				
				ProvinciaDTO berlín = provincias.stream().filter(p -> p.getNombre().equals("Berlín")).findFirst().get();
				tmp.add(new LocalidadDTO("Dahlem", berlín));
				tmp.add(new LocalidadDTO("Gatow", berlín));
				
				ProvinciaDTO brandeburgo = provincias.stream().filter(p -> p.getNombre().equals("Brandeburgo")).findFirst().get();
				tmp.add(new LocalidadDTO("Friedrichswalde", brandeburgo));
				tmp.add(new LocalidadDTO("Ziethen", brandeburgo));
				
			// Noruega
			
				ProvinciaDTO nordland = provincias.stream().filter(p -> p.getNombre().equals("Nordland")).findFirst().get();
				tmp.add(new LocalidadDTO("Lurøy", nordland));
				tmp.add(new LocalidadDTO("Værøy", nordland));
				
				ProvinciaDTO vestfoldOgTelemark = provincias.stream().filter(p -> p.getNombre().equals("Vestfold og Telemark")).findFirst().get();
				tmp.add(new LocalidadDTO("Fyresdal", vestfoldOgTelemark));
				tmp.add(new LocalidadDTO("Porsgrunn", vestfoldOgTelemark));
				
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
			
			tiposDecontactos.add(new TipoContactoDTO("Familia"));
			tiposDecontactos.add(new TipoContactoDTO("Amigos"));
			tiposDecontactos.add(new TipoContactoDTO("Trabajo"));
			
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
			
			DomicilioDTO domicilio1 = new DomicilioDTO("Avenida Siempreviva", 742, "0", "", localidades.get(random.nextInt(localidades.size())));
			contactos.add(new PersonaDTO("Homero Simpson", "123456", "homer@donuts.com", dateFormat.parse("2020-03-01"), filterByName(tiposDeContacto, "Familia"), domicilio1, true));
			
			DomicilioDTO domicilio2 = new DomicilioDTO("Avenida Siempreviva", 742, "", "", localidades.get(random.nextInt(localidades.size())));
			contactos.add(new PersonaDTO("Marge Simpson", "987654", "marge@hotmail.com", dateFormat.parse("2020-03-04"), filterByName(tiposDeContacto, "Amigos"), domicilio2, false));
			
			DomicilioDTO domicilio3 = new DomicilioDTO("Calle Falsa", 123, "", "", localidades.get(random.nextInt(localidades.size())));
			contactos.add(new PersonaDTO("Bart Simpson", "666666", "elbarto@gmail.com", dateFormat.parse("2006-06-06"), filterByName(tiposDeContacto, "Familia"), domicilio3, true));
			
			DomicilioDTO domicilio4 = new DomicilioDTO("All Vegetables", 2718, "2", "e", localidades.get(random.nextInt(localidades.size())));
			contactos.add(new PersonaDTO("Lisa Simpson", "3141592653", "jazzrules@realponies", dateFormat.parse("2020-03-27"), filterByName(tiposDeContacto, "Trabajo"), domicilio4, true));
			
			for (PersonaDTO persona : contactos) {
//				domicilioDAO.insert(persona.getDomicilio());
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
