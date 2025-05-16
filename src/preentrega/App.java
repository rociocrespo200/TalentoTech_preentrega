package preentrega;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
Scanner sc = new Scanner(System.in);
		
		ArrayList<Categoria> categorias = cargarCategorias();
		ArrayList<Articulo> articulos = cargarArticulos(categorias);
		ArrayList<Pedido> pedidos = cargarPedidos(articulos);
		Boolean continuarBucle = true;
		
		do {
			msj("titulo", "Bienvenido!! Seleccione una opcion"); 
			System.out.println(
				    "1 - Crear un artículo nuevo\n" +
				    "2 - Consultar un artículo\n" +
				    "3 - Listar artículos\n" +
				    "4 - Modificar un artículo\n" +
				    "5 - Borrar un artículo\n" +
				    "6 - Crear un pedido\n" +
				    "7 - Listar pedidos\n" +
				    "8 - Salir"
				);
							
			Integer accionIng = pedirNumero(sc);
				
			switch (accionIng) {
			case 1: crearArticulo(sc,articulos,categorias); break;
			case 2: consultarArticulo(sc,articulos, categorias); break;
			case 3: listarArticulos(sc,articulos); break;
			case 4: modificarArticulo(sc,articulos,categorias); break;
			case 5: borrarArticulo(sc,articulos); break;
			case 6: crearPedido(sc,articulos, pedidos); break;
			case 7: listarPedidos(sc,articulos, pedidos); break;
			case 8: msj("amarillo", "FIN DEL PROGRAMA");continuarBucle = false; break;
			default: msj("rojo", "OPCION INVALIDA");	
			}			
			
		}while(continuarBucle);
		
		sc.close();
	}
	

	private static void listarPedidos(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Pedido> pedidos) {
		for (Pedido pedido : pedidos) {
			System.out.println(pedido);
		}
		
	}

	private static void crearPedido(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Pedido> pedidos) {
		
		ArrayList<Pedido_articulo> articulosDelPedido = new ArrayList<>();
		
		msj("titulo", "CREAR PEDIDO"); 
		Integer id = pedidos.get(pedidos.size() -1).getId() + 1;
			
		while(true) {
			Articulo articulo = seleccionarArticulo(sc, articulos);
			
			while(true) {
				System.out.println("Ingrese la cantidad: ");
				Integer cantidad = pedirNumero(sc);
				try {	
					validarStock(articulo, cantidad);
					articulosDelPedido.add(new Pedido_articulo(articulo, cantidad));
					articulo.setStock(articulo.getStock() - cantidad);
					break;
				}catch (StockInsuficienteException e) {
					System.out.println(e.getMessage());
				}catch (ProductoSinStockException e) {
					System.out.println(e.getMessage());
					break;
				}
			}
			
			System.out.print("¿Desea agregar otro producto? 1 - Si / 2 - No ");
			if (pedirNumero(sc)  == 2) break;	
		}

		if (articulosDelPedido.isEmpty()) {
		    msj("rojo", "No se agregaron artículos. Pedido cancelado.");
		    return;
		}
	
		pedidos.add(new Pedido(id, articulosDelPedido));	
		
		msj("verde", "PEDIDO AGREGADO: "); 
		System.out.println(pedidos.get(id - 1).toString());	
		
	}

	private static void validarStock(Articulo articulo, Integer cantidad) throws StockInsuficienteException, ProductoSinStockException {	
		if(cantidad > articulo.getStock()) throw new StockInsuficienteException("STOCK INSUFICIENTE");
		if(articulo.getStock() == 0) throw new ProductoSinStockException("PRODUCTO SIN STOCK");
	}


	private static void crearArticulo(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias) {

		msj("titulo", "Crear un nuevo articulo"); 

		System.out.println("Ingrese el nombre: ");
		String nombre = sc.nextLine();
			
		System.out.println("Ingrese el precio: ");
		Double precio = pedirNumero(sc).doubleValue();
			
		System.out.println("Ingrese el stock disponible: ");
		Integer stock = pedirNumero(sc);
			
		Categoria categoria = seleccionarCategoria(sc, categorias);
			
		Integer id = articulos.get(articulos.size() -1).getId() + 1;
			
		articulos.add(new Articulo(id, nombre, precio, stock, categoria));	
		
		msj("verde", "PRODUCTO AGREGADO: "); 
		System.out.println(articulos.get(id - 1).toString());		
	}

	private static void consultarArticulo(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias) {
		// id | nombre | categoria | ordenar por precio | articulos sin stock 
		Boolean continuarBucle = true;
		do {		
			msj("titulo", "CONSULTAR ARTICULOS"); 
			System.out.println(
				    "1 - Buscar por nombre\n" +
				    "2 - Filtra por categoria\n" +
				    "3 - Ordenar por mayor precio\n" +
				    "4 - Ordenar por menor precio\n" +
				    "5 - Salir"
				);			
			Integer accionIng = pedirNumero(sc);
			
			switch (accionIng) {
			case 1: buscarArticuloPorNombre(sc,articulos); break;
			case 2: filtrarPorCategoria(sc,articulos, categorias); break;
			case 3: ordenarArticulos(sc,articulos, "DESC"); break;
			case 4: ordenarArticulos(sc,articulos, "ASC"); break;
			case 5: msj("amarillo", "FIN DEL PROGRAMA");continuarBucle = false; break;
			default: msj("rojo", "OPCION INVALIDA");	
			}
		}while(continuarBucle);
	}
	
	private static void ordenarArticulos(Scanner sc, ArrayList<Articulo> articulos, String orden) {
		 ArrayList<Articulo> articulosCopia = new ArrayList<>(articulos);
		 
		if (orden.equalsIgnoreCase("ASC"))
			articulosCopia.sort(Comparator.comparingDouble(Articulo::getPrecio));
		else 
			articulosCopia.sort(Comparator.comparingDouble(Articulo::getPrecio).reversed());	
		
		for (Articulo a : articulosCopia) {
	        System.out.println(a);
	    }
	}

	private static void filtrarPorCategoria(Scanner sc, ArrayList<Articulo> articulos,
			ArrayList<Categoria> categorias) {
		Categoria categoriaIng = seleccionarCategoria(sc, categorias);
		
		for (Articulo a : articulos) {
			if(a.getCategoria() == categoriaIng) System.out.println(a);;
	    }
		
	}

	private static void buscarArticuloPorNombre(Scanner sc, ArrayList<Articulo> articulos) {	
		int cont = 0;
		
		System.out.println("Ingrese el nombre: ");
		String nombreIng = sc.nextLine();
		
		for (Articulo a : articulos) {
		    if (a.getNombre().toLowerCase().contains(nombreIng.toLowerCase())) {
		    	System.out.println(a);
				cont++;
		    }
		}
		
		if(cont == 0) msj("rojo","NO EXISTE ningun articulo con: '" + nombreIng + "'");
	}

	private static void listarCategorias(Scanner sc, ArrayList<Categoria> categorias) {
		for (Categoria categoria : categorias) {
			System.out.println(categoria.toString());
		}
		
	}

	private static void modificarArticulo(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias) {
		Articulo articulo = seleccionarArticulo(sc, articulos);
		Boolean continuarBucle = true;
		do {
			
			msj("titulo", "Seleccione el campo que desea modificar"); 
			msj("cursiva", articulo.toString());
			System.out.println( "1 - Nombre\n" + "2 - Precio\n" + "3 - Stock\n" + "4 - Categoria\n" + "5 - Salir");
			
			Integer accionIng = pedirNumero(sc);
			
			switch (accionIng) {
			case 1: 
				System.out.print("Ingrese el nuevo nombre: ");
				articulo.setNombre(sc.nextLine()); 
				break;
			case 2: 
				System.out.print("Ingrese el nuevo precio: ");
				articulo.setPrecio(pedirNumero(sc).doubleValue()); 
				break;
			case 3: 
				System.out.print("Ingrese el nuevo Stock: ");
				articulo.setStock(pedirNumero(sc)); 
				break;
			case 4: 
				Categoria categoriaIng = seleccionarCategoria(sc, categorias);
				articulo.setCategoria(categoriaIng);
				break;
			case 5: 
				continuarBucle = false; 
				break;
			default: msj("rojo", "OPCION INVALIDA"); break;	
			}			
		} while (continuarBucle);
		
	}

	private static void borrarArticulo(Scanner sc, ArrayList<Articulo> articulos) {
		Articulo articulo = seleccionarArticulo(sc, articulos);	
		
		msj("verde", "PRODUCTO ELIMINADO: "); 
		System.out.println(articulo.toString());
		
		articulos.remove(articulo);		
	}
	
	private static Categoria seleccionarCategoria(Scanner sc, ArrayList<Categoria> categorias) {
		do {
			msj("titulo", "Ingrese el ID de la categoria"); 
			listarCategorias(sc, categorias);
							
			Integer categoriaIng = pedirNumero(sc);
			
			Categoria categoria = buscarCategoriaPorId(categoriaIng, categorias);
			
			if(categoria != null) return categoria;
				
			msj("rojo", "NO SE ENCONTRO LA CATEGORIA");
			
		}while(true);
	}

	private static Categoria buscarCategoriaPorId(Integer categoriaIng, ArrayList<Categoria> categorias) {
		for (Categoria categoria : categorias) {
			if(categoria.getId() == categoriaIng) return categoria;
		}
		return null;
	}

	private static void listarArticulos(Scanner sc, ArrayList<Articulo> articulos) {
		for (Articulo articulo : articulos) {
			System.out.println(articulo.toString());
		}		
	}

	private static Articulo seleccionarArticulo(Scanner sc, ArrayList<Articulo> articulos) {	
		do {
			msj("titulo", "Ingrese el ID del articulo"); 
			listarArticulos(sc, articulos);
							
			Integer articuloIng = pedirNumero(sc);
			
			Articulo articulo = buscarArticuloPorId(articuloIng, articulos);
			
			if(articulo != null) return articulo;
				
			msj("rojo", "NO SE ENCONTRO EL PRODUCTO");
			
		}while(true);
	}

	private static Articulo buscarArticuloPorId(Integer articuloIng, ArrayList<Articulo> articulos) {
		for (Articulo articulo : articulos) {
			if(articulo.getId() == articuloIng) return articulo;
		}
		return null;
	}

	private static Integer pedirNumero(Scanner sc) {
	    while (true) {
	        try {
	            return Integer.parseInt(sc.nextLine());
	        } catch (NumberFormatException e) {
	            msj("rojo", "INGRESE UN NUMERO VALIDO");
	        }
	    }
	}
	
	private static void msj(String color, String msj) {		
		switch (color) {
		case "rojo": System.out.println("\u001B[31m" + msj + "\u001B[0m"); break;
		case "verde": System.out.print("\u001B[32m" + msj + "\u001B[0m"); break;
        case "amarillo": System.out.println("\u001B[33m" + msj + "\u001B[0m"); break;
        case "cursiva": System.out.println("\033[3m" + msj + "\033[0m"); break;
        case "negrita": System.out.println("\033[3m" + msj + "\033[0m"); break;
        case "titulo": 
        	String guiones = "-".repeat(msj.length()); // genera tantos '-' como caracteres tiene el mensaja
			System.out.println(guiones + "\n\u001B[33m" + msj + "\u001B[0m\n" + guiones); 
        	break;
		default: System.out.println(msj); break;
		}	
	}
	
	private static ArrayList<Categoria> cargarCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        
        categorias.add(new Categoria(1, "Electrónica"));
        categorias.add(new Categoria(2, "Hogar"));
        categorias.add(new Categoria(3, "Juguetes"));
        categorias.add(new Categoria(4, "Ropa"));

        return categorias;
	}

	private static ArrayList<Articulo> cargarArticulos(ArrayList<Categoria> categorias) {
        ArrayList<Articulo> articulos = new ArrayList<>();

        articulos.add(new Articulo(1, "Smartphone", 150000.00, 20, categorias.get(0))); // Electrónica
        articulos.add(new Articulo(2, "Televisor", 200000.00, 10, categorias.get(0)));
        articulos.add(new Articulo(3, "Auriculares Bluetooth", 25000.00, 30, categorias.get(0)));

        articulos.add(new Articulo(4, "Aspiradora", 50000.00, 15, categorias.get(1))); // Hogar
        articulos.add(new Articulo(5, "Lámpara LED", 8000.00, 40, categorias.get(1)));
        articulos.add(new Articulo(6, "Set de sábanas", 12000.00, 20, categorias.get(1)));

        articulos.add(new Articulo(7, "Osito de Peluche", 3000.00, 50, categorias.get(2))); // Juguetes
        articulos.add(new Articulo(8, "Auto a control remoto", 7000.00, 30, categorias.get(2)));
        articulos.add(new Articulo(9, "Bloques de construcción", 5500.00, 40, categorias.get(2)));

        articulos.add(new Articulo(10, "Remera", 8000.00, 40, categorias.get(3))); // Ropa
        articulos.add(new Articulo(11, "Jeans", 15000.00, 30, categorias.get(3)));
        articulos.add(new Articulo(12, "Campera", 25000.00, 20, categorias.get(3)));

        return articulos;
	}
	
	private static ArrayList<Pedido> cargarPedidos(ArrayList<Articulo> articulos) {
		ArrayList<Pedido> pedidos = new ArrayList<>();
		
		ArrayList<Pedido_articulo> articulosPedido1 = new ArrayList<>();
		articulosPedido1.add(new Pedido_articulo(articulos.get(0), 4));
		articulosPedido1.add(new Pedido_articulo(articulos.get(1), 3));
		
		pedidos.add(new Pedido(1, articulosPedido1));
		
		ArrayList<Pedido_articulo> articulosPedido2 = new ArrayList<>();
		articulosPedido2.add(new Pedido_articulo(articulos.get(0), 1));
		articulosPedido2.add(new Pedido_articulo(articulos.get(1), 1));
		
		pedidos.add(new Pedido(2, articulosPedido2));
		
		return pedidos;
	}

}
