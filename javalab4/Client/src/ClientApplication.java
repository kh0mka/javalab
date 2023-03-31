import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientApplication {
    private final JTextField inputField;
    private final JTextArea outputArea;

    public ClientApplication() {
        JFrame frame = new JFrame("Client-server appx.");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputField = new JTextField(20);
        JButton sendButton = new JButton("Send & Check");
        sendButton.addActionListener(new SendButtonListener());

        outputArea = new JTextArea(10, 20);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.NORTH, inputPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                Socket socket = new Socket("localhost", 5555);
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String input = inputField.getText();
                if (input.isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Error: Word text field empty");
                    writer.println(input);
                    writer.flush();
                    return;
                }

                if (input.matches(".*\\d+.*"))
                {
                    JOptionPane.showMessageDialog(null, "Error: Word contains digits");
                    writer.println(input);
                    writer.flush();
                    return;
                }

                writer.println(input);
                writer.flush();

                String outputVowels = reader.readLine();
                String outputConsonants = reader.readLine();
                outputArea.append(outputVowels + "\n");
                outputArea.append(outputConsonants + "\n");


                writer.close();
                reader.close();
                socket.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ClientApplication();
    }
}