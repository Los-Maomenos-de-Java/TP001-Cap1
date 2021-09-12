package parque;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class boleteriaTest {
	@Test
	public void testObtenerAtraccionPorNombre() {
		new Boleteria();
		
		String nombre = "Luigi’s Pizza";
		double costoVisita = 150.0;
		double tiempoVisita = 2.0;
		TipoDeAtraccion tipoAtraccion = TipoDeAtraccion.valueOf("DEGUSTACION");
		int cupo = 10;
		
		Atraccion atraccionPrueba = new Atraccion(nombre,costoVisita,tiempoVisita,tipoAtraccion,cupo);
		
		assertEquals(atraccionPrueba,Boleteria.obtenerAtraccionPorNombre(nombre));
	}
	
	@Test
	public void testFiltradoDeOfertasPorCosto() {
		Boleteria boleteria = new Boleteria();
		
		//usuario que cuenta con $ solo para la atraccion más barata
		Usuario usuario = new Usuario("Usuario De Prueba",5.0,5.0,TipoDeAtraccion.valueOf("DEGUSTACION")); 
		//atraccion con menor valor de precio
		Ofertable ofertaEsperada = new Atraccion("La cabaña de Willy",5.0,0.5,TipoDeAtraccion.valueOf("RECORRIDO"),2); 
		List<Ofertable> ofertasFiltradas = boleteria.filtradorParaTest(usuario);

		assertEquals(1,ofertasFiltradas.size());
		assertEquals(ofertaEsperada, ofertasFiltradas.get(0).getAtracciones().get(0));
	}
	
	@Test
	public void testFiltradoDeOfertasPorTiempo() {
		Boleteria boleteria = new Boleteria();
		
		//usuario que cuenta con tiempo solo para las atracciones que lleven menor cantidad de tiempo
		Usuario usuario = new Usuario("Usuario De Prueba",50000.0,0.5,TipoDeAtraccion.valueOf("DEGUSTACION")); 
		
		List<Ofertable> ofertasFiltradasPorTiempo = boleteria.filtradorParaTest(usuario);
		assertEquals(2,ofertasFiltradasPorTiempo.size()); //2 es la cantidad de atracciones que se pueden recorrer con solo media hora disponible
	}
	
	@Test
	public void testFiltradoDeOfertasSinFiltros() {
		Boleteria boleteria = new Boleteria();
		Usuario usuarioConPlataYTiempo = new Usuario("Usuario De Prueba",500000000.0,500000000.0,TipoDeAtraccion.valueOf("DEGUSTACION"));
		List<Ofertable> ofertasSinFiltro = boleteria.filtradorParaTest(usuarioConPlataYTiempo);
		assertEquals(57,ofertasSinFiltro.size()); //57 es la cantidad de atracciones+promociones cargadas
	}
}
