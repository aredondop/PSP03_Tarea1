import java.io.*;
import java.net.*;

/**
 * Clase que implementa un cliente para un juego de adivinanza de números.
 * El cliente se conecta a un servidor, intenta adivinar un número y recibe mensajes de retroalimentación.
 * 
 * @author Ángel Redondo
 */
public class Cliente {

    /**
     * Método principal que inicia la instancia del cliente.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        new Cliente();
    }
    
    // Dirección del servidor
    final String servidor = "localhost";
    
    // Puerto al que se conecta el cliente
    final int puerto = 2000;

    /**
     * Constructor de la clase Cliente. Inicia la lógica del juego del cliente.
     */
    public Cliente() {
        String datos = "";
        String numCliente = "";

        // Lectura desde el teclado
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Se conecta al servidor en la dirección y puerto especificados.
            Socket sCliente = new Socket(servidor, puerto);

            // Flujos de entrada y salida para comunicarse con el servidor.
            InputStream aux = sCliente.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(aux);

            OutputStream os = sCliente.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(os);

            // Muestra el mensaje inicial recibido del servidor.
            System.out.println(flujoEntrada.readUTF());

            // Lee y muestra el siguiente mensaje del servidor.
            datos = flujoEntrada.readUTF();
            System.out.println(datos);

            // Bucle principal del juego del cliente.
            do {
                System.out.println("Introduce un número:");
                numCliente = reader.readLine();
                flujoSalida.writeUTF(numCliente);
                flujoSalida.flush(); // Asegura que los datos se envíen al servidor inmediatamente.
            } while (!datos.equals("Correcto"));

            // Cierra la conexión con el servidor.
            sCliente.close();

        } catch (IOException e) {
            // Maneja las excepciones mostrando un mensaje en la consola.
            System.out.println("Error: " + e.getMessage());
        }
    }
}