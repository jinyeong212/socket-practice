import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    private static final String serverName = "localhost";
    private static final int port = 12000;
    private static final int bufferSize = 2048;

    public static void main(String[] args) {
        try(
                Socket clientSocket = new Socket(serverName, port); //해당 부분에서 UDP와 달리 DNS 및 3-wayhandShake 진행
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ){
            System.out.print("input lowercase sentence : ");
            String sentence = br.readLine();

            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();

            out.write(sentence.getBytes(StandardCharsets.UTF_8));
            out.flush();

            byte[] buffer = new byte[bufferSize];

            int byteRead = in.read(buffer);

            if(byteRead != -1){
                String modifiedSentence = new String(
                        buffer,
                        0,
                        byteRead,
                        StandardCharsets.UTF_8
                );

                System.out.println("modified sentence : " + modifiedSentence);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}