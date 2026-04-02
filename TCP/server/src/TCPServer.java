import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    private final static int serverPort = 12000;
    private final static int bufferSize = 2048;

    public static void main(String[] args) {
        try(
                ServerSocket welcomingSocket = new ServerSocket(serverPort)
        ){
            System.out.println("waiting for client");
            while(true){
                try(
                        Socket connectionSocket = welcomingSocket.accept();
                ){
                    InputStream in = connectionSocket.getInputStream();
                    OutputStream out = connectionSocket.getOutputStream();

                    byte[] buffer = new byte[bufferSize];

                    int byteSize = in.read(buffer);

                    if(byteSize != -1){
                        String sentence = new String(
                                buffer,
                                0,
                                byteSize,
                                StandardCharsets.UTF_8
                        );

                        String modifiedSentence = sentence.toUpperCase();

                        out.write(modifiedSentence.getBytes(StandardCharsets.UTF_8));
                        out.flush();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}