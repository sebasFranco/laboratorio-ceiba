package com.jsfrancor.springboot.app.models.entity;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import com.jsfrancor.springboot.app.excepciones.ExcepcionPrestamo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	private String nombrePersona;

	@NotEmpty
	private String isbnLibro;

	@Column(name = "fecha_prestamo")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaPrestamo;

	@Column(name = "fecha_entrega_maxima")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEntregaMaxima;

	private static final long serialVersionUID = 1L;
	
	public static final String EL_LIBRO_ES_PALINDROMO = "Los libros palíndromos solo se pueden utilizar en la biblioteca";

	@PrePersist
	public void prePersist() {
		fechaPrestamo = new Date();
	}

	/*
	 * Generar prestamo con validaciones 
	 * 
	 */


	public void generarPrestamo() {
		if(!sumaNumISBN()){
			fechaEntregaMaxima = generarFechaEntrega();
		}else {
			fechaEntregaMaxima = null;
		}
	}

	/*
	 * Genera la fecha de entrega de los libros palindromos 
	 * @return fecha Date de entrega del libro
	 */
	public Date generarFechaEntrega() {

		LocalDate fecha = fechaPrestamo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		if(fecha.getDayOfWeek()==DayOfWeek.SUNDAY){
			fecha = fecha.plusDays(1);
    	}
		
		for (int i = 0; i < 14; i++) {
			if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
				i--;
			}
			fecha = fecha.plusDays(1);
		}

		Date fechaEntrega = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());

		return fechaEntrega;
	}

	/**
	 *  Valida si el ISBN del libro es palindromo
	 *
	 * @param isbn codigo ISBN del libro
	 * @return valor obtenido de la validacion, sera true si es palindromo
	 */
	public boolean validarPalindromoStream() {
		Function<String, String> reverse = s -> new StringBuilder(s).reverse().toString();
		return (0 == (reverse.apply(isbnLibro).compareTo(isbnLibro)));
	}

	/*
	 * Valia si el ISBN del libro suma mas de 30 Sale True si el libro sumas mas de
	 * 30 - False si es menos de 30
	 */
	public boolean sumaNumISBN(){
		
		List<Character> list = new ArrayList<>();
		for (char ch : isbnLibro.toCharArray()) {
			list.add(ch);
		}

		int sumaISBN=0;

		for (int i = 0; i < list.size(); i++) {
			int c = (int) list.get(i);
			if (c > 48 && c < 58) {
				sumaISBN += (c - 48);
			}
		}
		return (sumaISBN > 30);
	}

	// Get and Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getIsbnLibro() {
		return isbnLibro;
	}

	public void setIsbnLibro(String isbnLibro) {
		this.isbnLibro = isbnLibro;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Date getFechaEntregaMaxima() {
		return fechaEntregaMaxima;
	}

	public void setFechaEntregaMaxima(Date fechaEntregaMaxima) {
		this.fechaEntregaMaxima = fechaEntregaMaxima;
	}

}
