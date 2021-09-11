package parque;

import static org.junit.Assert.*;

import org.junit.Test;

public class promocionesTest {
	@Test
	public void testAgregarAtracciones() {
		String nombre = "Promo Prueba";
		Atraccion atraccion = new Atraccion("Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		
		Promocion promoAbs = new PromocionAbsoluta(nombre, 10.0);
		promoAbs.agregarAtraccion(atraccion);
		assertEquals(atraccion,promoAbs.getAtracciones().get(0));
		
		Promocion promoPor = new PromocionPorcentual(nombre, 10.0);
		promoPor.agregarAtraccion(atraccion);
		assertEquals(atraccion,promoPor.getAtracciones().get(0));
		
		String[] atraccionesGratis = new String[]{"Atraccion De Prueba"};
		Promocion promoAxB = new PromocionAxB(nombre,atraccionesGratis);
		promoAxB.agregarAtraccion(atraccion);
		assertEquals(atraccion,promoAxB.getAtracciones().get(0));
	}
	
	@Test (expected = Error.class)
	public void testAgregarAtraccionesRepetidasEnAbs() {
		String nombre = "Promo Prueba";
		Atraccion atraccion = new Atraccion("Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		
		Promocion promoAbs = new PromocionAbsoluta(nombre, 10.0);
		promoAbs.agregarAtraccion(atraccion);
		promoAbs.agregarAtraccion(atraccion);
	}
	
	@Test (expected = Error.class)
	public void testAgregarAtraccionesRepetidasEnPor() {
		String nombre = "Promo Prueba";
		Atraccion atraccion = new Atraccion("Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		
		Promocion promoPor = new PromocionPorcentual(nombre, 10.0);
		promoPor.agregarAtraccion(atraccion);
		promoPor.agregarAtraccion(atraccion);
	}
	
	@Test (expected = Error.class)
	public void testAgregarAtraccionesRepetidasEnAxB() {
		String nombre = "Promo Prueba";
		Atraccion atraccion = new Atraccion("Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		String[] atraccionesGratis = new String[]{"Atraccion De Prueba"};
		
		Promocion promoAxB = new PromocionAxB(nombre,atraccionesGratis);
		promoAxB.agregarAtraccion(atraccion);
		promoAxB.agregarAtraccion(atraccion);
	}

	@Test
	public void testInicializarPromoAbsoluta() {
		String nombre = "Promo Prueba";
		Atraccion atraccion = new Atraccion("Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		double descAbs = 10.0;

		Promocion promoAbs = new PromocionAbsoluta(nombre, descAbs);
		promoAbs.agregarAtraccion(atraccion);

		assertEquals(nombre, promoAbs.getNombre());
		assertEquals(atraccion.getCosto() - descAbs, promoAbs.getCosto(), 0.01);
	}

	@Test(expected = Error.class)
	public void testInicializarPromoAbsolutaInvalida() {
		new PromocionAbsoluta("Promo Prueba", -10);
	}

	@Test
	public void testInicializarPromoPorcentual() {
		String nombre = "Promo Prueba";
		Atraccion atraccion = new Atraccion("Atraccion De Prueba", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		double descPor = 10.0;

		Promocion promoPor = new PromocionPorcentual(nombre, descPor);
		promoPor.agregarAtraccion(atraccion);
		double costoEsperado = atraccion.getCosto() * (1 - descPor / 100.0);

		assertEquals(nombre, promoPor.getNombre());
		assertEquals(costoEsperado, promoPor.getCosto(), 0.01);
	}
	
	@Test(expected = Error.class)
	public void testInicializarPromoPorcentualInvalida() {
		new PromocionPorcentual("Promo Prueba", -10);
	}
	
	@Test
	public void testInicializarPromoAxB() {
		String nombre = "Promo Prueba";
		Atraccion atraccion1 = new Atraccion("Atraccion De Prueba1", 20.0, 10.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		Atraccion atraccion2 = new Atraccion("Atraccion De Prueba2", 20.0, 20.0, TipoDeAtraccion.valueOf("ACCION"), 10);
		String[] atraccionesGratis = new String[]{"Atraccion De Prueba1"};

		Promocion promoAxB = new PromocionAxB(nombre,atraccionesGratis);
		promoAxB.agregarAtraccion(atraccion1);
		promoAxB.agregarAtraccion(atraccion2);

		assertEquals(nombre, promoAxB.getNombre());
		assertEquals(atraccion2.getCosto(), promoAxB.getCosto(), 0.01);
	}
	
	@Test(expected = Error.class)
	public void testInicializarPromoAxBInvalida() {
		String[] atraccionesGratis = new String[] {};
		new PromocionAxB("Promo Prueba", atraccionesGratis);
	}	
}
