/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eugid
 */
public class Jugador {

    protected String nombre;
    protected String primerApellido;
    protected int edad;
    protected String dni;
    protected List<Carta> mano;

    public Jugador(String nombre, String primerApellido, int edad, String dni) {
        if (nombre == null || " ".equals(nombre) || primerApellido == null || " ".equals(primerApellido) || !esDniValido(dni)) {
            throw new IllegalArgumentException("Alguno de los parametros introducidos no son correctos.");
        } else if (edad < 18) {
            throw new IllegalArgumentException("Imposible crar la partida, no se permiten menores de edad.");
        } else {
            this.nombre = nombre;
            this.primerApellido = primerApellido;
            this.edad = edad;
            this.dni = dni;
            this.mano = new ArrayList<>(5);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getDni() {
        return dni;
    }

    public List<Carta> getMano() {
        return mano;
    }

    @Override
    public String toString() {
        return "\n\tNombre: " + getNombre() + "\n\tPrimer apellido: " + getPrimerApellido() + "\n\tEdad: " + getEdad() + "\n\tDNI: " + getDni() + "\n";
    }

    public static boolean esDniValido(String dni) {
        if (dni == null || dni.trim().length() != 9) {
            throw new IllegalArgumentException("El DNI debe ser una cadena de texto de 9 caracteres.");
        }

        String expresionRegular = "^[0-9]{8}[A-Z]{1}$";

        if (dni == null || !dni.matches(expresionRegular)) {
            throw new IllegalArgumentException("El DNI debe ser una cadena de texto de nueve caracteres, con 8 números seguidos de una letra.");
        }

        char[] letrasDni = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        int posicionletraRealDni = cogerNumeroDni(dni) % 23;

        if (dni == null || letrasDni[posicionletraRealDni] != dni.charAt(8)) {
            throw new IllegalArgumentException("La letra de DNI no corresponde con el número proporcionado.");
        }
        return true;
    }

    private static int cogerNumeroDni(String dni) {
        if (dni == null) {
            throw new IllegalArgumentException("El DNI no puede ser una cadena vacia.");
        }

        StringBuilder nuemeroDniString = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            nuemeroDniString.append(dni.charAt(i));
        }
        return Integer.parseInt(nuemeroDniString.toString());
    }

    public void decartarCarta(int posicionCartaDescartada) {
        mano.remove(posicionCartaDescartada - 1);
    }
    
    public void listarMano(){
        System.out.println(getMano().toString());
    }
}

