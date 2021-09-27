package idat.edu.pe.daa2.servicio;

import java.util.List;

import idat.edu.pe.daa2.entidades.Usuario;

public interface IUsuarioService {
	
	void guardar(Usuario usuario);
	void eliminar (Integer idUsuario);
	List<Usuario> buscarTodos();
}
