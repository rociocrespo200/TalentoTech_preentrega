package preentrega;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class Pedido {
	
	private Integer id;
    private LocalDateTime fechaHora;
	private ArrayList<Pedido_articulo> articulos = new ArrayList<>();
	private double precioFinal;
	
	public Pedido(Integer id, ArrayList<Pedido_articulo> articulos) {
		this.id = id;
		this.fechaHora = LocalDateTime.now();;
		this.articulos = articulos;
		this.precioFinal = calcularPrecioTotal();
	}

	private double calcularPrecioTotal() {
		Double suma = 0.0;
		for (Pedido_articulo a : articulos) {
			suma += a.getPrecioTotal();
		}
		return suma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public ArrayList<Pedido_articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(ArrayList<Pedido_articulo> articulos) {
		this.articulos = articulos;
	}

	public double getPrecioFinal() {
		return precioFinal;
	}

	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	@Override
	public String toString() {
	    NumberFormat nf = NumberFormat.getInstance(new Locale("es", "ES"));
	    return "ID: " + id +
	           " | Fecha y hora: " + fechaHora +
	           " | Artículos: " + traerListaDeProductos() +
	           " | Precio total: $" + nf.format(precioFinal);
	}

	private String traerListaDeProductos() {
		String articulosText = articulos.get(0).toString();		
		for (int i = 1; i < articulos.size(); i++) {
			articulosText += ", " + articulos.get(i).toString();
		}
		return articulosText;
	}

	
	
	
}
