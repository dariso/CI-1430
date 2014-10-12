package com.puddle_slide.game;

/**
 * Created by kalam on 03/10/2014.
 */
//Variables para definir categoria de cada uno de los objetos presentes en el juego
//Son 2 bytes (0000 0000 0000 0000) podemos definir 16 posibles objetos
//Esto se utilizara para saber con cuales objetos de x categoria deberá colisionar cada uno

public class FigureId {

    public static final short BIT_BORDE = 2;
    public static final short BIT_GOTA = 4;
    public static final short BIT_HOJA = 8;
    public static final short BIT_TRONCO = 16;
    public static final short BIT_MANZANA = 32;

}
