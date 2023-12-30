import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Clase que implementa un servidor para un juego de adivinanza de números.
 * El servidor genera un número aleatorio y el cliente intenta adivinarlo.
 * Proporciona mensajes de retroalimentación al cliente.
 * 
 * @author Ángel Redondo
 */
public class Servidor {

    /**
     * Método principal que inicia la instancia del servidor.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        new Servidor();
    }

    /**
     * Constructor de la clase Servidor. Inicia la lógica del juego.
     */
    public Servidor() {
        // Puerto en el que el servidor escucha las conexiones.
        final int puerto = 2000;

        try {
            // Inicia el servidor en el puerto especificado.
            ServerSocket skServidor = new ServerSocket(puerto);
            System.out.println("Escuchando en el puerto " + puerto);

            // Espera la conexión de un cliente.
            Socket skCliente = skServidor.accept();
            System.out.println("Cliente conectado");

            // Flujos de entrada y salida para comunicarse con el cliente.
            InputStream is = skCliente.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(is);

            OutputStream os = skCliente.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(os);

            // Genera un número aleatorio para que el cliente lo adivine.
            Random randomGenerator = new Random();
            int numSecreto = randomGenerator.nextInt(100);
            int encontrado = 0;
            String numCliente;

            // Envía un mensaje inicial al cliente.
            flujoSalida.writeUTF("Tienes que adivinar un número del 1 al 100");

            // Bucle principal del juego.
            while (encontrado == 0) {
                // Lee el número enviado por el cliente.
                numCliente = flujoEntrada.readUTF();
                System.out.println("\tEl cliente ha dicho " + numCliente);

                // Compara el número del cliente con el número secreto.
                if (numSecreto == Integer.parseInt(numCliente)) {
                    flujoSalida.writeUTF("Correcto");
                    encontrado = 1;
                } else {
                    // Proporciona pistas al cliente sobre la relación del número secreto.
                    flujoSalida.writeUTF(numSecreto > Integer.parseInt(numCliente) ?
                            "El número secreto es mayor" :
                            "El número secreto es menor");
                }
            }

            // Envía un mensaje de fin al cliente y cierra la conexión.
            flujoSalida.writeUTF("FIN");
            skCliente.close();
            System.out.println("Cliente desconectado");

        } catch (IOException e) {
            // Maneja las excepciones mostrando un mensaje en la consola.
            System.out.println("Error: " + e.getMessage());
        }
    }
}
