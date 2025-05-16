package preentrega;

public class Pedido_articulo {
	
	private Articulo articulo;
	private Integer cantidad;
	private Double precioTotal;
	
	public Pedido_articulo(Articulo articulo, Integer cantidad) {
		this.articulo = articulo;
		this.cantidad = cantidad;
		this.precioTotal = articulo.getPrecio() * cantidad;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	@Override
	public String toString() {
		return  articulo.getNombre() + " X" + cantidad;
	}
	
	
	
}
