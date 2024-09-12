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
    DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, J, Q, K, AS;

    public static Numeros seleccionarCarta(int nombreCarta) {
        if (nombreCarta < 0) {
            throw new IllegalArgumentException("Se espera que introduzca un numero entero positivo");
        }
        switch (nombreCarta) {
            case 0:
                return DOS;
            case 1:
                return TRES;
            case 2:
                return CUATRO;
            case 3:
                return CINCO;
            case 4:
                return SEIS;
            case 5:
                return SIETE;
            case 6:
                return OCHO;
            case 7:
                return NUEVE;
            case 8:
                return DIEZ;
            case 9:
                return J;
            case 10:
                return Q;
            case 11:
                return K;
            case 12:
                return AS;
            default:
                throw new IllegalArgumentException("Esta carta no se encuentra en la baraja: " + nombreCarta);
        }
    }
}
