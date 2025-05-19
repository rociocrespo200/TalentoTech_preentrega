package com.techlab.entidades;

import java.text.NumberFormat;
import java.util.Locale;

public class Articulo {
	private Integer id;
	private String nombre;
	private Double precio;
	private Integer stock;
	private Categoria categoria;
	
	public Articulo(Integer id, String nombre, Double precio, Integer stock, Categoria categoria) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getInstance(new Locale("es", "ES"));
		return "ID: " + id +
                " | Nombre: " + nombre +
                " | Precio: $" + nf.format(precio) +
                " | Stock: " + stock +
                " | Categoría: " + categoria.getNombre();
	}
}
