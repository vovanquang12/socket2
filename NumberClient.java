package Socket2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class NumberClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Kết nối đến server đang chạy trên cùng máy ở cổng 12345
            System.out.println("Connected to server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String number;
            while ((number = reader.readLine()) != null) {
                System.out.println("Received number: " + number);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
