package parque;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LeerAtraccionesTest {
    List<Ofertable> atracciones = ManejadorDeArchivos.leerAtracciones();

	@Test
	public void Atraccion0Test() {
		Ofertable obtenido = atracciones.get(0);
		Atraccion esperado = new Atraccion("Moria", 10.0, 2.0, TipoDeAtraccion.valueOf("AVENTURA"), 6);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion1Test() {
		Ofertable obtenido = atracciones.get(1);
		Atraccion esperado = new Atraccion("Minas Tirith", 5.0, 2.5, TipoDeAtraccion.valueOf("PAISAJE"), 25);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion2Test() {
		Ofertable obtenido = atracciones.get(2);
		Atraccion esperado = new Atraccion("La Comarca", 3.0, 6.5, TipoDeAtraccion.valueOf("DEGUSTACION"), 150);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion3Test() {
		Ofertable obtenido = atracciones.get(3);
		Atraccion esperado = new Atraccion("Mordor", 25.0, 3.0, TipoDeAtraccion.valueOf("AVENTURA"), 4);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion4Test() {
		Ofertable obtenido = atracciones.get(4);
		Atraccion esperado = new Atraccion("Abismo de Helm", 5.0, 2.0, TipoDeAtraccion.valueOf("PAISAJE"), 15);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion5Test() {
		Ofertable obtenido = atracciones.get(5);
		Atraccion esperado = new Atraccion("Lothlarien", 35.0, 1.0, TipoDeAtraccion.valueOf("DEGUSTACION"), 30);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion6Test() {
		Ofertable obtenido = atracciones.get(6);
		Atraccion esperado = new Atraccion("Erebor", 12.0, 3.0, TipoDeAtraccion.valueOf("PAISAJE"), 32);
		assertTrue(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion7Test() {
		Ofertable obtenido = atracciones.get(7);
		Atraccion esperado = new Atraccion("Bosque Negro", 3.0, 4.0, TipoDeAtraccion.valueOf("AVENTURA"), 12);
		assertTrue(obtenido.equals(esperado));
	}
	
	@Test
	public void Atraccion0TestNombreIncorresto() {
		Ofertable obtenido = atracciones.get(0);
		Atraccion esperado = new Atraccion("Morodor", 10.0, 2.0, TipoDeAtraccion.valueOf("AVENTURA"), 6);
		assertFalse(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion2TestCostoVisitaIncorrecto() {
		Ofertable obtenido = atracciones.get(2);
		Atraccion esperado = new Atraccion("La Comarca", 4.5, 6.5, TipoDeAtraccion.valueOf("DEGUSTACION"), 150);
		assertFalse(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion4TestTiempoPromedioIncorrecto() {
		Ofertable obtenido = atracciones.get(4);
		Atraccion esperado = new Atraccion("Abismo de Helm", 5.0, 3.0, TipoDeAtraccion.valueOf("PAISAJE"), 15);
		assertFalse(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion6TestTipoDeAtraccionIncorrecto() {
		Ofertable obtenido = atracciones.get(6);
		Atraccion esperado = new Atraccion("Erebor", 12.0, 3.0, TipoDeAtraccion.valueOf("AVENTURA"), 32);
		assertFalse(obtenido.equals(esperado));
	}

	@Test
	public void Atraccion7TestCupoIncorrecto() {
		Ofertable obtenido = atracciones.get(7);
		Atraccion esperado = new Atraccion("Bosque Negro", 3.0, 4.0, TipoDeAtraccion.valueOf("AVENTURA"), 5);
		assertFalse(obtenido.equals(esperado));
	}

}
