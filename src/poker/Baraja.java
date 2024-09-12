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
public class Baraja {

    protected Carta[] baraja;

    protected Carta[] picas = new Carta[13];
    protected Carta[] corazones = new Carta[13];
    protected Carta[] treboles = new Carta[13];
    protected Carta[] diamantes = new Carta[13];

    public Baraja() {

        this.baraja = new Carta[52];
        crearbaraja();
    }

    public Carta[] getBaraja() {
        return baraja;
    }
    
    private Carta[] crearCorazones() {
        for (int i = 0; i < corazones.length; i++) {
            corazones[i] = new Carta(Numeros.seleccionarCarta(i), Figuras.CORAZONES);
        }
        return corazones;
    }

    private Carta[] crearDiamantes() {
        for (int i = 0; i < diamantes.length; i++) {
            diamantes[i] = new Carta(Numeros.seleccionarCarta(i), Figuras.DIAMANTES);
        }
        return diamantes;
    }

    private Carta[] crearTreboles() {
        for (int i = 0; i < treboles.length; i++) {
            treboles[i] = new Carta(Numeros.seleccionarCarta(i), Figuras.TREBOLES);
        }
        return treboles;
    }

    private Carta[] crearPicas() {
        for (int i = 0; i < picas.length; i++) {
            picas[i] = new Carta(Numeros.seleccionarCarta(i), Figuras.PICAS);
        }
        return picas;
    }

    private Carta[] crearbaraja() {
        crearCorazones();
        crearDiamantes();
        crearTreboles();
        crearPicas();
        System.arraycopy(picas, 0, baraja, 0, 13);
        System.arraycopy(corazones, 0, baraja, 13, 13);
        System.arraycopy(treboles, 0, baraja, 26, 13);
        System.arraycopy(diamantes, 0, baraja, 39, 13);
        return baraja;
    }
    
    public void listarBaraja(){
        for (Carta carta : baraja) {
            System.out.println(carta);
        }
    }
}
