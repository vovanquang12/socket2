package Socket2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NumberServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Tạo một ServerSocket ở cổng 12345
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Chấp nhận kết nối từ client
                System.out.println("Client connected: " + clientSocket);

                // Tạo một thread mới để xử lý kết nối từ client
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                OutputStream outputStream = clientSocket.getOutputStream();
                for (int i = 1; i <= 1000; i++) {
                    outputStream.write((i + "\n").getBytes()); // Gửi số từ 1 đến 1000 tới client
                    outputStream.flush();
                    Thread.sleep(1000); // Chờ 1 giây
                }
                clientSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

