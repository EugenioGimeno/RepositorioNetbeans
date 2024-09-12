/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author eugid
 */
public enum Figuras {
    PICAS, CORAZONES, TREBOLES, DIAMANTES;

    public static Figuras seleccionarFigura(int posicionFigura) {
        if (posicionFigura < 0) {
            throw new IllegalArgumentException("Se espera que introduzca un numero entero positivo");
        }
        switch (posicionFigura) {
            case 1:
                return PICAS;
            case 2:
                return CORAZONES;
            case 3:
                return TREBOLES;
            case 4:
                return DIAMANTES;
            default:
                throw new IllegalArgumentException("Esta figura no se encuentra en la baraja: " + posicionFigura);
        }
    }
}
