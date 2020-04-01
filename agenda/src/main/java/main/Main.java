package main;

import modelo.Agenda;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.Controlador;
import presentacion.vista.Vista;

import modelo.SeedData;

public class Main 
{

	public static void main(String[] args) 
	{
		// ====================================================================================================
		// = 											 SEED DATA								 			  =
		// ====================================================================================================
		SeedData.Initialize(new DAOSQLFactory()); // Initialize test data
		
		// ====================================================================================================
		// = 											RUN PROGRAM								 			  =
		// ====================================================================================================
		Vista vista = new Vista();
		Agenda modelo = new Agenda(new DAOSQLFactory());
		Controlador controlador = new Controlador(vista, modelo);
		controlador.inicializar();	
	}
	
}
