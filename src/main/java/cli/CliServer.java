    package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CliServer {

    private PrintWriter pw; 

    public CliServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(1235);
            Thread t = new Thread(() -> {
                Socket socket = null;
                while (socket == null){
                    try {
                        socket = serverSocket.accept();
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                        pw.println("Connection established");
                        this.pw = pw;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void printMessage(String userName, String message){
        if(pw != null){
            pw.println(userName + ":" + message);
        }
    }
}
