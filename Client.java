package sockk;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the file name: ");
            String fileName = scanner.nextLine();
            writer.write(fileName);
            writer.newLine();
            writer.flush();

            System.out.println("Enter text to send to the server. Type 'exit' to quit.");
            String line;
            while (!(line = scanner.nextLine()).equalsIgnoreCase("exit")) {
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
