package sockk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Sockett {
    private static final int PORT = 12345;
    private static final String DIRECTORY = "server_files";

    public static void main(String[] args) {
        // Create the directory if it doesn't exist
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Directory 'server_files' created.");
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Client connected");
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String fileName = input.readLine();
                    String text;
                    String outputFile = DIRECTORY + "/" + fileName;

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                        while ((text = input.readLine()) != null && !text.equalsIgnoreCase("exit")) {
                            writer.write(text);
                            writer.newLine();
                        }
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                    }
                } catch (IOException e) {
                    System.out.println("Server exception: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
