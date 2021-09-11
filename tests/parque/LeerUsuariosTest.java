package parque;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LeerUsuariosTest {
	List<Usuario> usuarios = ManejadorDeArchivos.leerUsuarios();

	@Test
	public void Usuario0Test() {
		Usuario obtenido = usuarios.get(0);
		Usuario esperado = new Usuario("Eowyn", 1000.0, 800.0, TipoDeAtraccion.valueOf("AVENTURA"));
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Usuario1Test() {
		Usuario obtenido = usuarios.get(1);
		Usuario esperado = new Usuario("Gandalf", 100.0, 5.0, TipoDeAtraccion.valueOf("PAISAJE"));
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Usuario2Test() {
		Usuario obtenido = usuarios.get(2);
		Usuario esperado = new Usuario("Sam", 36.0, 8.0, TipoDeAtraccion.valueOf("DEGUSTACION"));
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Usuario3Test() {
		Usuario obtenido = usuarios.get(3);
		Usuario esperado = new Usuario("Galadriel", 120.0, 2.0, TipoDeAtraccion.valueOf("PAISAJE"));
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Usuario0TestNombreIncorrecto() {
		Usuario obtenido = usuarios.get(0);
		Usuario esperado = new Usuario("Eowin", 10.0, 8.0, TipoDeAtraccion.valueOf("AVENTURA"));
		assertFalse(obtenido.equals(esperado));
	}

	@Test
	public void Usuario1TestPresupuestoIncorrecto() {
		Usuario obtenido = usuarios.get(1);
		Usuario esperado = new Usuario("Gandalf", 95.0, 5.0, TipoDeAtraccion.valueOf("PAISAJE"));
		assertFalse(obtenido.equals(esperado));
	}
	
	@Test
	public void Usuario2TestTiempoDisponibleIncorrecto() {
		Usuario obtenido = usuarios.get(2);
		Usuario esperado = new Usuario("Sam", 36.0, 12.0, TipoDeAtraccion.valueOf("DEGUSTACION"));
		assertFalse(obtenido.equals(esperado));
	}
	
	@Test
	public void Usuario3TestTipoDeAtraccionPreferidaIncorrecta() {
		Usuario obtenido = usuarios.get(3);
		Usuario esperado = new Usuario("Galadriel", 120.0, 2.0, TipoDeAtraccion.valueOf("AVENTURA"));
		assertFalse(obtenido.equals(esperado));
	}
}
