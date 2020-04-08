package modelo;

import persistencia.conexion.Conexion;

public class ControladorDeConexion {

	//test
	String nombre_base_de_datos = "grupo_14";
	String usuario = "root";
	String contraseña = "root";
	
	public static void conectar(String baseDeDatos, String usuario, String contraseña) {
		Conexion.getConexion(baseDeDatos, usuario, contraseña).getSQLConexion();
	}
}