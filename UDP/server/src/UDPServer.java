import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    private static final int serverPort = 12000;
    private static final int bufferSize = 2048;

    public static void main(String[] args) {
        try(
                DatagramSocket serverSocket = new DatagramSocket(serverPort)
        ){
         while(true){
             byte[] receiveData = new byte[bufferSize];
             DatagramPacket receivePacket = new DatagramPacket(
                     receiveData,
                     receiveData.length
             );

             serverSocket.receive(receivePacket);

             String message = new String(
                     receivePacket.getData(),
                     0,
                     receivePacket.getLength(),
                     StandardCharsets.UTF_8
             );

             String modifiedMessage = message.toUpperCase();

             byte[] sendData = modifiedMessage.getBytes(StandardCharsets.UTF_8);

             DatagramPacket sendPacket = new DatagramPacket(
                     sendData,
                     sendData.length,
                     receivePacket.getAddress(),
                     receivePacket.getPort()
             );

             serverSocket.send(sendPacket);
         }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
