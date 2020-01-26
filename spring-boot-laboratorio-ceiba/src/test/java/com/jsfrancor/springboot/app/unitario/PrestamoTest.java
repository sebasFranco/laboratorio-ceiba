package com.jsfrancor.springboot.app.unitario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

import com.jsfrancor.springboot.app.models.entity.Libro;
import com.jsfrancor.springboot.app.models.entity.Prestamo;
import com.jsfrancor.springboot.app.testdatabuilder.LibroTestDataBuilder;

public class PrestamoTest {
	
	@Test
	public void validarSumaMayor30() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conIsbn("9999");
		Libro libro = libroTestDataBuilder.build();
		
		Prestamo prestamo = new Prestamo();
		
		// act
		boolean tieneMasDe30 = prestamo.sumaNumISBN(libro);

		// assert
		assertTrue(tieneMasDe30);

	}
	
	@Test
	public void validarSumaMenor30() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conIsbn("111");
		Libro libro = libroTestDataBuilder.build();
		
		Prestamo prestamo = new Prestamo();
		
		// act
		boolean tieneMenosDe30 = prestamo.sumaNumISBN(libro);

		// assert
		assertFalse(tieneMenosDe30);

	}
	
	@Test
	public void fechaEntrega15Dias() {
		
		// arrange
		Prestamo prestamo = new Prestamo();
		
		LocalDate fechaPrestamo = LocalDate.of(2071, 05, 24);
		Date fechaPrestamoDate = Date.from(fechaPrestamo.atStartOfDay(ZoneId.systemDefault()).toInstant());
		prestamo.setFechaPrestamo(fechaPrestamoDate);
		
		LocalDate fechaEntrega = LocalDate.of(2071, 05, 24);
		Date fechaEntregaDate = Date.from(fechaEntrega.atStartOfDay(ZoneId.systemDefault()).toInstant());
		// act
		Date fechaRetornada = prestamo.generarFechaEntrega();

		// assert
		assertEquals(fechaEntregaDate, fechaRetornada);

	}
}
		