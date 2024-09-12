/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    protected List<String[]> manosJugadores;

    public Partida(int participantes) throws Exception {
        if (participantes < 2) {
            throw new IllegalArgumentException("Una partida no se puede iniciar con menos de dos participantes");
        }
        this.cartas = new Baraja();
        this.participantes = new Jugador[participantes];
        this.manosPosibles = new ArrayList<>();
        this.manosJugadores = new ArrayList<>();
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

    public List<String[]> leerCombinacionesPoker() throws IOException {
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

    public void listarManosPosibles() {
        for (int i = 0; i < manosPosibles.size(); i++) {
            String posibleMano = Arrays.toString(manosPosibles.get(i));
            System.out.println(posibleMano);
        }
    }

    public void sobreescribirListaPosiblesManos() throws IOException {
        BufferedWriter escribirArchivo = null;
        try {
            escribirArchivo = new BufferedWriter(new FileWriter("archivoSalida.txt"));
            for (String[] mano : manosPosibles) {
                for (int i = 0; i < mano.length; i++) {
                    escribirArchivo.write(mano[i]);
                    escribirArchivo.write("\t");
                }
                escribirArchivo.newLine();
            }
            escribirArchivo.flush();
        } finally {
            try {
                if (escribirArchivo != null) {
                    escribirArchivo.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar los flujos. " + e.getMessage());
            }
        }
    }

    public void listarManosJugadores() {
        for (int i = 0; i < participantes.length; i++) {
            System.out.println("La mano de: " + participantes[i].getNombre() + " es: \n");
            System.out.println(participantes[i].getMano().toString());
            System.out.println();
        }
    }

    public Jugador[] ordenarManosJugadores() {

        List<Carta> copiarMano = new ArrayList();
        List<Carta> cartasRepetidas = new ArrayList();
        List<Carta> cartasNoRepetidas = new ArrayList();

        Numeros[] numerosPosibles = {Numeros.AS, Numeros.K, Numeros.Q, Numeros.J, Numeros.DIEZ, Numeros.NUEVE,
            Numeros.OCHO, Numeros.SIETE, Numeros.SEIS, Numeros.CINCO, Numeros.CUATRO, Numeros.TRES, Numeros.DOS};

        for (int i = 0; i < participantes.length; i++) {
            copiarMano.addAll(participantes[i].getMano());
            participantes[i].getMano().clear();
            for (int j = 0; j < numerosPosibles.length; j++) {
                for (int k = 0; k < copiarMano.size(); k++) {
                    for (int l = 0; l < copiarMano.size(); l++) {
                        if (copiarMano.get(k).numero.equals(numerosPosibles[j]) && !copiarMano.get(k).numero.equals(copiarMano.get(l).numero)) {
                            cartasRepetidas.add(copiarMano.get(k));
                        } else if (copiarMano.get(k).numero.equals(numerosPosibles[j])) {
                            cartasNoRepetidas.add(copiarMano.get(k));
                        }
                    }
                }
                for (int k = 0; k < cartasRepetidas.size(); k++) {
                    participantes[i].getMano().add(cartasRepetidas.get(k));
                }
                for (int k = 0; k < cartasNoRepetidas.size(); k++) {
                    participantes[i].getMano().add(cartasNoRepetidas.get(k));
                }
                copiarMano.clear();
            }
        }
        return participantes;
    }

    /*private List<Carta> priorizarCartasRepetidas(List<Carta> manoJugador) {
        List<Carta> copiarMano = new ArrayList();
        List<Carta> cartasRepetidas = new ArrayList();
        List<Carta> cartasNoRepetidas = new ArrayList();

        copiarMano.addAll(manoJugador);
        for (int j = 0; j < copiarMano.size(); j++) {
            manoJugador.clear();
            System.out.println("La carta de la mano es: \n");
            System.out.println(copiarMano.get(j).toString());
            for (int k = 0; k < copiarMano.size(); k++) {
                if (copiarMano.get(j).numero.equals(copiarMano.get(k).numero) && !cartasRepetidas.contains(copiarMano.get(k)) && j != k && !cartasRepetidas.contains(copiarMano.get(j))) {
                    System.out.println("Copiando " + copiarMano.get(k).toString() + " a cartas repetidas.");
                    cartasRepetidas.add(copiarMano.get(k));
                    cartasRepetidas.add(copiarMano.get(j));
                } else {
                    if (!cartasNoRepetidas.contains(copiarMano.get(k)) && j != k) {
                        System.out.println("Copiando " + copiarMano.get(k).toString() + " a cartas no repetidas.");
                        cartasNoRepetidas.add(copiarMano.get(k));
                    }
                }
            }
        }

        for (int j = 0; j < cartasRepetidas.size(); j++) {
            manoJugador.add(cartasRepetidas.get(j));
        }

        for (int j = 0; j < cartasNoRepetidas.size(); j++) {
            manoJugador.add(cartasNoRepetidas.get(j));
        }
        return manoJugador;
    }*/
    public List<String[]> manosJugadores() {
        StringBuilder palabraConstruida = new StringBuilder();
        String[] manoJugador = new String[10];
        String[] manoJugadorFinal = new String[8];
        int contador = 0;
        for (int i = 0; i < participantes.length; i++) {
            System.out.println("La mano de " + participantes[i].getNombre() + " es: \n");
            String manoJugador_tmp = participantes[i].getMano().toString();
            char[] manoJugador_tmpEnCaracteres = manoJugador_tmp.toCharArray();
            for (int j = 0; j < manoJugador_tmpEnCaracteres.length; j++) {
                if (Character.isUpperCase(manoJugador_tmpEnCaracteres[j])) {
                    palabraConstruida.append(manoJugador_tmpEnCaracteres[j]);
                } else {
                    if (palabraConstruida.length() > 0) {
                        String palabraParaAlmacenar = palabraConstruida.toString().toUpperCase();
                        manoJugador[contador] = palabraParaAlmacenar;
                        palabraConstruida.setLength(0);
                        contador++;
                    }
                    if (contador == 10) {
                        contador = 0;
                    }
                }
            }
            manoJugadorFinal[2] = manoJugador[0];
            manoJugadorFinal[3] = manoJugador[2];
            manoJugadorFinal[4] = manoJugador[4];
            manoJugadorFinal[5] = manoJugador[6];
            manoJugadorFinal[6] = manoJugador[8];
            manosJugadores.add(manoJugadorFinal);
            System.out.println(Arrays.toString(manosJugadores.get(i)));
            System.out.println();
        }
        return manosJugadores;
    }

    /*public Jugador[] ordenarManosJugadores() {

        Numeros[] numerosPosibles = {Numeros.AS, Numeros.K, Numeros.Q, Numeros.J, Numeros.DIEZ, Numeros.NUEVE,
            Numeros.OCHO, Numeros.SIETE, Numeros.SEIS, Numeros.CINCO, Numeros.CUATRO, Numeros.TRES, Numeros.DOS};
        List<Carta> copiarMano = new ArrayList();
        for (int i = 0; i < participantes.length; i++) {
            copiarMano.addAll(participantes[i].getMano());
            participantes[i].getMano().clear();
            for (int j = 0; j < numerosPosibles.length; j++) {
                for (int k = 0; k < copiarMano.size(); k++) {
                    if (copiarMano.get(k).numero == numerosPosibles[j]) {
                        participantes[i].getMano().add(copiarMano.get(k));
                    }
                }
            }
            priorizarCartasRepetidas(participantes[i].getMano());
            copiarMano.clear();
        }
        return participantes;
    }*/
}
