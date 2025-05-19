package com.techlab.excepciones;

public class ProductoSinStockException extends Exception {

    public ProductoSinStockException(String mensaje) {
        super("\u001B[31m" + mensaje + "\u001B[0m");
    }
}
