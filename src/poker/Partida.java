/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author eugid
 */
public class Partida {

    protected Baraja cartas;
    protected Jugador[] participantes;
    protected List<Carta> cartasAleatorias;
    protected List<String[]> manosPosibles;

    public Partida(int participantes) throws Exception{
        if (participantes < 2) {
            throw new IllegalArgumentException("Una partida no se puede iniciar con menos de dos participantes");
        }
        this.cartas = new Baraja();
        this.participantes = new Jugador[participantes];
        this.manosPosibles = new ArrayList<>();
        leerCombinacionesPoker();
        listarManosPosibles();
        
    }

    public Jugador[] getParticipantes() {
        return participantes;
    }

    public Baraja getBaraja() {
        return cartas;
    }

    public boolean jugadorExiste(String dni) {
        if (!Jugador.esDniValido(dni)) {
            throw new IllegalArgumentException("El DNI no es valido.");
        }
        int primeraPosicionNula = -1;
        for (int i = 0; i < getParticipantes().length; i++) {
            if (getParticipantes()[i] == null) {
                primeraPosicionNula = i;
                break;
            }
        }
        if (primeraPosicionNula == -1) {
            primeraPosicionNula = getParticipantes().length;
        }
        Jugador[] jugadoresPartida = Arrays.copyOfRange(participantes, 0, primeraPosicionNula);
        boolean jugadorExiste = false;
        for (Jugador participante : jugadoresPartida) {
            if (participante.getDni().equals(dni)) {
                jugadorExiste = true;
            }
        }
        return jugadorExiste;
    }

    public List<Carta> barajarBaraja() {
        // Convierte el array en una lista para barajar
        this.cartasAleatorias = new ArrayList<>();
        Collections.addAll(cartasAleatorias, getBaraja().baraja);

        // Baraja la lista
        Collections.shuffle(cartasAleatorias);

        return cartasAleatorias;
    }

    public void listarBarajaBarajada() {
        // Imprime las cartas barajadas
        for (int i = 0; i < cartasAleatorias.size(); i++) {
            System.out.println("La " + (i + 1) + "ª carta es: " + cartasAleatorias.get(i));
        }
    }

    public List<Carta> cortarBaraja(int posicionCorte) {
        // Verificar que la posición de corte sea válida
        if (posicionCorte < 0 || posicionCorte >= cartasAleatorias.size()) {
            throw new IllegalArgumentException("Posición de corte inválida");
        }

        // Crear nuevas listas para las dos partes de la baraja
        List<Carta> parteSuperior = new ArrayList<>(cartasAleatorias.subList(0, posicionCorte));
        List<Carta> parteInferior = new ArrayList<>(cartasAleatorias.subList(posicionCorte, cartasAleatorias.size()));

        // Combinar las dos partes invirtiendo el orden
        cartasAleatorias.clear();
        cartasAleatorias.addAll(parteInferior);
        cartasAleatorias.addAll(parteSuperior);

        return cartasAleatorias;
    }

    public Jugador[] repartirCartas() {
        for (int i = 0; i < getParticipantes().length; i++) {
            for (int j = 0; j < 3; j++) {
                participantes[i].getMano().add(cartasAleatorias.get(0));
                cartasAleatorias.remove(0);
            }
        }
        for (int i = 0; i < getParticipantes().length; i++) {
            for (int j = 0; j < 2; j++) {
                participantes[i].getMano().add(cartasAleatorias.get(0));
                cartasAleatorias.remove(0);
            }
        }
        return participantes;
    }

    public Jugador[] completarDescartes() {
        for (int i = 0; i < participantes.length; i++) {
            for (int j = 0; j < 5; j++) {
                if (participantes[i].getMano().size() < 5) {
                    participantes[i].getMano().add(cartasAleatorias.get(0));
                    cartasAleatorias.remove(0);
                }
            }
        }
        return participantes;
    }
    
    public List<String[]> leerCombinacionesPoker() throws Exception {
        String[] mano;
        BufferedReader leerArchivo = null;
        try {
            leerArchivo = new BufferedReader(new FileReader("combinaciones_Poker.txt"));
            String leerLineaArchivo;
            do {
                leerLineaArchivo = leerArchivo.readLine();
                if (leerLineaArchivo != null) {
                    String linea = leerLineaArchivo;
                    mano = linea.split("\t");
                    this.manosPosibles.add(mano);
                }
            } while (leerLineaArchivo != null);
            System.out.println();
        } finally {
            if (leerArchivo != null) {
                leerArchivo.close();
            }
        }
        return manosPosibles;
    }
    
    public void listarManosPosibles(){
        for (int i = 0; i < manosPosibles.size(); i++) {
            String posibleMano = Arrays.toString(manosPosibles.get(i));
            System.out.println(posibleMano);
        }
    }

}
