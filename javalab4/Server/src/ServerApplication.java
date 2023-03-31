import javax.swing.*;
import java.io.*;
import java.net.*;

public class ServerApplication {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5555);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

                String input = reader.readLine();
                int vowelCount = 0;
                int consonantCount = 0;

                for (int i = 0; i < input.length(); i++) {
                    char c = Character.toLowerCase(input.charAt(i));
                    if ((c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') || (c == 'а' || c == 'о' || c == 'у' || c == 'э' || c == 'ы' || c == 'я' || c == 'ё' || c == 'е' || c == 'ю' || c == 'и')) {
                        vowelCount++;
                    } else if (Character.isLetter(c)) {
                        consonantCount++;
                    }
                }

                String outputVowels = "Количество гласных букв: " + vowelCount;
                String outputConsonants = "Количество согласных букв: " + consonantCount;
                writer.println(outputVowels);
                writer.println(outputConsonants);
                writer.flush();

                writer.close();
                reader.close();
                clientSocket.close();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}