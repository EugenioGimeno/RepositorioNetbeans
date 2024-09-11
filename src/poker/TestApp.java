/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;


import java.util.Scanner;


/**
 *
 * @author eugid
 */
public class TestApp {

    public static void main(String[] args) throws Exception {
        Scanner lectura = new Scanner(System.in);
        System.out.println("****************************");
        System.out.println("***Bienvenido a Poker_DAM***");
        System.out.println("****************************");

        System.out.println("Introduzca el numero de participantes");
        int numeroParticipantes = Integer.parseInt(lectura.nextLine());
        Partida nuevaPartida = new Partida(numeroParticipantes);
        Jugador[] participantes = inscribirJugadoresPorTeclado(nuevaPartida);

        System.out.println("\nLa baraja antes de de barajar.\n");
        nuevaPartida.getBaraja().listarBaraja();

        System.out.println("\nLista de participantes de la partida: \n");
        for (int i = 0; i < participantes.length; i++) {
            System.out.println("El " + (i + 1) + "º participante es: " + participantes[i].toString());
        }
        System.out.println();

        System.out.println("!!Que comience el juego!!\n");

        nuevaPartida.barajarBaraja();

        nuevaPartida.listarBarajaBarajada();
        System.out.println("¿Desea cortar?");
        String respuesta = lectura.nextLine().toUpperCase();
        if (respuesta.equals("SI")) {
            System.out.println("¿Por donde quiere cortar?");
            int posicion = Integer.parseInt(lectura.nextLine());
            nuevaPartida.cortarBaraja(posicion);
        }
        nuevaPartida.listarBarajaBarajada();
        nuevaPartida.repartirCartas();

        for (int i = 0; i < participantes.length; i++) {
            System.out.println("Jugador: " + participantes[i].getNombre());
            boolean salir = false;
            do {
                System.out.println("¿Que desea hacer?"
                        + "\n\t(P)asar."
                        + "\n\t(V)er mis cartas"
                        + "\n\t(D)escartar cartas."
                        + "\n\n\tRespuesta: ");
                char eleccion = lectura.nextLine().toUpperCase().charAt(0);
                switch (eleccion) {
                    case 'P':
                        salir = true;
                        break;
                    case 'V':
                        nuevaPartida.participantes[i].listarMano();
                        break;
                    case 'D':
                        System.out.println("¿Que carta desea descartar?");
                        int posicionCarta = Integer.parseInt(lectura.nextLine());
                        nuevaPartida.participantes[i].decartarCarta(posicionCarta);
                        break;
                }

            } while (!salir);

        }

        nuevaPartida.completarDescartes();

        for (int i = 0; i < participantes.length; i++) {
            System.out.println("La mano de: " + participantes[i].getNombre() + " es: \n");
            System.out.println(participantes[i].getMano().toString());
            System.out.println();
        }

        System.out.println("Continuará....");
    }

    public static Jugador[] inscribirJugadoresPorTeclado(Partida nuevaPartida) {
        Scanner lectura = new Scanner(System.in);
        for (int i = 0; i < nuevaPartida.getParticipantes().length; i++) {
            System.out.println("Introduzca el nombre del " + (i + 1) + "º participante.");
            String nombre = lectura.nextLine().toUpperCase();
            System.out.println("Introduzca el primer apellido del " + (i + 1) + "º participante.");
            String primerApellido = lectura.nextLine().toUpperCase();
            System.out.println("Introduzca la edad del " + (i + 1) + "º participante.");
            int edad = Integer.parseInt(lectura.nextLine());
            System.out.println("Introduzca el DNI del " + (i + 1) + "º participante.");
            String dni = lectura.nextLine().toUpperCase();
            if (nuevaPartida.jugadorExiste(dni)) {
                throw new RuntimeException("El jugador que intenta registrar ya esta registrado.");
            } else {
                nuevaPartida.participantes[i] = new Jugador(nombre, primerApellido, edad, dni);
            }
        }
        return nuevaPartida.participantes;
    }
    
    

}
