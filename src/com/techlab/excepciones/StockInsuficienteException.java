package com.techlab.excepciones;

public class StockInsuficienteException extends Exception{

    public StockInsuficienteException(String mensaje) {
        super("\u001B[31m" + mensaje + "\u001B[0m");
    }
}
