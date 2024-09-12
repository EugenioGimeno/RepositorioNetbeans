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
public class Carta {

    protected Figuras palo;
    protected Numeros numero;

    public Carta(Numeros numero, Figuras palo) {
        if (palo == null || numero == null) {
            throw new IllegalArgumentException("Alguno de Los parámetros introducidos no son válidos");
        } else {
            this.palo = palo;
            this.numero = numero;
        }
    }

    public Figuras getPalo() {
        return palo;
    }

    public Numeros getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return numero + " de " + palo;
    }
    
    
}
