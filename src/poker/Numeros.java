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
public enum Numeros {
    UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, J, Q, K;

    public static Numeros seleccionarCarta(int nombreCarta) {
        if (nombreCarta < 0) {
            throw new IllegalArgumentException("Se espera que introduzca un numero entero positivo");
        }
        switch (nombreCarta) {
            case 0:
                return UNO;
            case 1:
                return DOS;
            case 2:
                return TRES;
            case 3:
                return CUATRO;
            case 4:
                return CINCO;
            case 5:
                return SEIS;
            case 6:
                return SIETE;
            case 7:
                return OCHO;
            case 8:
                return NUEVE;
            case 9:
                return DIEZ;
            case 10:
                return J;
            case 11:
                return Q;
            case 12:
                return K;
            default:
                throw new IllegalArgumentException("Esta carta no se encuentra en la baraja: " + nombreCarta);
        }
    }
}
