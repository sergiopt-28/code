package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
	public static void main(String[] args) {

//		CREAR TABLEROS DE AMBOS JUGADORES
//		@formatter:off
		boolean[][] tableroJugadorX = {
				{ false, false, false }, 
				{ false, false, false }, 
				{ false, false, false }, };
		
		boolean[][] tableroJugadorO = {
				{ false, false, false }, 
				{ false, false, false }, 
				{ false, false, false }, };
//		@formatter:on

//		CREAR SCANNER PARA INTRODUCIR MOVIMIENTO
		Scanner scanner = new Scanner(System.in);

//		PEDIR TECLA PARA EMPEZAR A JUGAR
		limpiarConsola();
		System.out.println("Los movimientos en este 3 en raya funcionan usando el" + System.lineSeparator()
				+ "primer numero como fila y el segundo como columna :c, ahora" + System.lineSeparator()
				+ System.lineSeparator() + "¡¡¡ Presiona ENTER para empezar a jugar !!!");
		scanner.nextLine();

//		BUCLE
		bucleDeJuego(scanner, tableroJugadorX, tableroJugadorO);

		String repetir = "";
		while (true) {
			System.out.print(System.lineSeparator() + "¿Volver a jugar? [S] Sí [N] No ");
			repetir = scanner.next();
//			SI SE RESPONDE SI SE REPITE BUCLE
			if (repetir.equals("S") || repetir.equals("s") || repetir.equals("SI") || repetir.equals("Si")
					|| repetir.equals("si")) {

//				REINICIAR TABLEROS
				for (int f = 0; f < tableroJugadorX.length; f++) {
					for (int c = 0; c < tableroJugadorX[0].length; c++) {
						tableroJugadorX[f][c] = false;
						tableroJugadorO[f][c] = false;
					}
				}
//				LLAMAR A BUCLE DE JUEGO DE NUEVO
				bucleDeJuego(scanner, tableroJugadorX, tableroJugadorO);
			} else
				break;
		}
		
//		CERRAR SCANER AL FINALIZAR PROGRAMA
		scanner.close();
	}

	public static void limpiarConsola() {
		try {
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				// COMANDO PARA WINDOWS
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				// COMANDO PARA LINUX Y MAC
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			System.out.println("Error al limpiar la consola: " + e.getMessage());
		}
	}

	public static int comprobarMovimiento(Scanner scanner, boolean[][] tableroJugadorX, boolean[][] tableroJugadorO,
			int numeroMovimiento) {

		int f = numeroMovimiento / 10 - 1;
		int c = numeroMovimiento % 10 - 1;

//		COMPROBAR SI ES CORRECTA LA POSICION
		if ((f >= 0 && f < 3) && (c >= 0 && c < 3)) {
//				COMPROBAR SI LA POSICION ESTÁ OCUPADA
			if (!(tableroJugadorX[f][c] || tableroJugadorO[f][c]))
				return numeroMovimiento;
		}

//		PEDIR OTRO MOVIMIENTO
		System.out.print("¡Movimiento incorrecto! Introduce otro movimiento: ");

//		COMPROBAR SI SE INTRODUCE UN NÚMERO
		try {
			numeroMovimiento = scanner.nextInt();
		} catch (InputMismatchException e) {
			 // SI NO SE ESCRIBE UN NÚMERO REINICIA CACHE DE SCANNER
			scanner.nextLine();
		}

		return comprobarMovimiento(scanner, tableroJugadorX, tableroJugadorO, numeroMovimiento);
	}

	public static void realizarMovimiento(boolean[][] tableroJugadorX, boolean[][] tableroJugadorO, char jugador,
			int numeroMovimiento) {

		int f = numeroMovimiento / 10 - 1;
		int c = numeroMovimiento % 10 - 1;

		if (jugador == 'x')
			tableroJugadorX[f][c] = true;
		else if (jugador == 'o')
			tableroJugadorO[f][c] = true;

	}

	public static void imprimirTablero(boolean[][] tableroJugadorX, boolean[][] tableroJugadorO) {
//		IMPRIMIR TABLERO
		for (int f = 0; f < tableroJugadorX.length; f++) {
//			IMPRIMIR BORDES
			System.out.println(System.lineSeparator() + "+---+---+---+");
//			COMPROBAR LAS COLUMNAS
			for (int c = 0; c < tableroJugadorX[0].length; c++) {

				System.out.print("|");

				if (tableroJugadorX[f][c])
					System.out.print(" X ");
				else if (tableroJugadorO[f][c])
					System.out.print(" O ");
				else
					System.out.print("   ");
			}

			System.out.print("|");
		}

		System.out.println(System.lineSeparator() + "+---+---+---+");
	}

	public static String terminarJuego(boolean[][] tableroJugadorX, boolean[][] tableroJugadorO) {
//		TERMINAR JUEGO PORQUE TODAS LAS POSICIONES ESTÁN OCUPADAS
		boolean empatar = false;
		int contadorDeCasillas = 0;

		for (int f = 0; f < tableroJugadorX.length; f++) {
			for (int c = 0; c < tableroJugadorX[0].length; c++) {
//				CONTAR CUANTAS CASILLAS ESTÁN OCUPADAS
				if (tableroJugadorX[f][c])
					contadorDeCasillas++;
				else if (tableroJugadorO[f][c])
					contadorDeCasillas++;
			}
		}

		if (contadorDeCasillas == 9)
			empatar = true;

//		TERMINAR JUEGO POR 3 EN RAYA DE IZQUIERDA A DERECHA
		boolean ganarJugadorX = false;
		boolean ganarJugadorO = false;

		for (int f = 0; f < tableroJugadorX.length; f++) {
//			CONTADORES QUE SE REINICIAN CADA NUEVA FILA
			int contadorX = 0;
			int contadorO = 0;
//			COMPRUEBA LAS COLUMNAS			
			for (int c = 0; c < tableroJugadorX[0].length; c++) {
//				SUMA LAS POSICIONES EN MISMA FILA
				if (tableroJugadorX[f][c])
					contadorX++;
				if (tableroJugadorO[f][c])
					contadorO++;

//				SI CONTADOR ES 3 EN LA MISMA FILA UN JUGADOR GANA
				if (contadorX == 3)
					ganarJugadorX = true;
				if (contadorO == 3)
					ganarJugadorX = true;
			}
		}

//		TERMINAR JUEGO POR 3 EN RAYA DE ARRIBA A ABAJO
		for (int c = 0; c < tableroJugadorX.length; c++) {
			int contadorX = 0;
			int contadorO = 0;
//			CONTADORES DE COLUMNAS			
			for (int f = 0; f < tableroJugadorX.length; f++) {
//				SUMA LAS POSICIONES EN MISMA COLUMNA
				if (tableroJugadorX[f][c])
					contadorX++;
				if (tableroJugadorO[f][c])
					contadorO++;

//				SI CONTADOR ES 3 EN LA MISMA FILA UN JUGADOR GANA
				if (contadorX == 3)
					ganarJugadorX = true;
				if (contadorO == 3)
					ganarJugadorX = true;
			}
		}

//		TERMINAR JUEGO POR 3 EN RAYA A LOS LADOS
		if (tableroJugadorX[0][0] && tableroJugadorX[1][1] && tableroJugadorX[2][2])
			ganarJugadorX = true;
		else if (tableroJugadorX[2][0] && tableroJugadorX[1][1] && tableroJugadorX[0][2])
			ganarJugadorX = true;

		if (tableroJugadorO[0][0] && tableroJugadorO[1][1] && tableroJugadorO[2][2])
			ganarJugadorO = true;
		else if (tableroJugadorO[2][0] && tableroJugadorO[1][1] && tableroJugadorO[0][2])
			ganarJugadorO = true;

//		MENSAJE QUE SE DEVUELVE AL GANAR EL JUEGO
		if (ganarJugadorX)
			return System.lineSeparator() + "El jugador X ha ganado la partida :D";
		else if (ganarJugadorO)
			return System.lineSeparator() + "El jugador O ha ganado la partida :D";
		else if (empatar)
			return System.lineSeparator() + "Noooo, habeis empatado :O";
		else
			return "Siguiente turno :)";

	}

	public static void bucleDeJuego(Scanner scanner, boolean[][] tableroJugadorX, boolean[][] tableroJugadorO) {
		while (true) {
			// LIMPIAR SALIDA Y IMPRIMIR TABLERO
			limpiarConsola();
			imprimirTablero(tableroJugadorX, tableroJugadorO);

			// PEDIR SCANNER X
			System.out.print(System.lineSeparator() + "Introduce un movimiento jugador X: ");
			int movimientoX = 0;

			try {
				movimientoX = scanner.nextInt();

			} catch (InputMismatchException e) {
				/*
				 * SI NO SE ESCRIBE UN NÚMERO REINICIA CACHE DE SCANNER
				 */
				scanner.nextLine();
			}

			// FUNCION REALIZAR MOVIMIENTO
			movimientoX = comprobarMovimiento(scanner, tableroJugadorX, tableroJugadorO, movimientoX);
			realizarMovimiento(tableroJugadorX, tableroJugadorO, 'x', movimientoX);

			// COMPROBAR SI SE HA TERMINADO EL JUEGO
			if (!(terminarJuego(tableroJugadorX, tableroJugadorO).equals("Siguiente turno :)"))) {
				// IMPRIME EL MENSAJE DE QUE EL JUEGO SE HA TERMINADO
				limpiarConsola();
				imprimirTablero(tableroJugadorX, tableroJugadorO);
				System.out.println(terminarJuego(tableroJugadorX, tableroJugadorO) + System.lineSeparator());
				// ROMPER BUCLE
				break;
			}

			// LIMPIAR SALIDA Y IMPRIMIR TABLERO
			limpiarConsola();
			imprimirTablero(tableroJugadorX, tableroJugadorO);

			// PEDIR SCANNER O
			System.out.print(System.lineSeparator() + "Introduce un movimiento jugador O: ");
			int movimientoO = 0;

			try {
				movimientoO = scanner.nextInt();

			} catch (InputMismatchException e) {
				/*
				 * SI NO SE ESCRIBE UN NÚMERO REINICIA CACHE DE SCANNER
				 */
				scanner.nextLine();
			}

			// FUNCION REALIZAR MOVIMIENTO
			movimientoO = comprobarMovimiento(scanner, tableroJugadorX, tableroJugadorO, movimientoO);
			realizarMovimiento(tableroJugadorX, tableroJugadorO, 'o', movimientoO);

			// COMPROBAR SI SE HA TERMINADO EL JUEGO
			if (!(terminarJuego(tableroJugadorX, tableroJugadorO).equals("Siguiente turno :)"))) {
				// IMPRIME EL MENSAJE DE QUE EL JUEGO SE HA TERMINADO
				limpiarConsola();
				imprimirTablero(tableroJugadorX, tableroJugadorO);
				System.out.println(terminarJuego(tableroJugadorX, tableroJugadorO) + System.lineSeparator());
				// ROMPER BUCLE
				break;
			}

		}

	}

}
