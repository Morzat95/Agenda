package persistencia.dao.interfaz;

public interface DAOAbstractFactory 
{
	public PersonaDAO createPersonaDAO();
	public LocalidadDAO createLocalidadDAO();
	public TipoContactoDAO createTipoContactoDAO();
	public DomicilioDAO createDomicilioDAO();
	public PaísDAO createPaísDAO();
	public ProvinciaDAO createProvinciaDAO();
}
