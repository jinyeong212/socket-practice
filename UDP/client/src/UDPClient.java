import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient{
    static final String serverName = "localhost";
    static final int serverPort = 12000;

    public static void main(String[] args){
        try(
                DatagramSocket clientSocket = new DatagramSocket();
                Scanner sc = new Scanner(System.in);
        ){
            System.out.print("Input lowercase sentence : ");
            String message = sc.nextLine();

            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            InetAddress serverAddress = InetAddress.getByName(serverName); //DNS host name -> IP 주소 변환

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);

            String modifiedMessage = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.print(modifiedMessage);
        } catch (SocketException e) {
            System.out.println("소켓 생성 에러");
            throw new RuntimeException(e);
        } catch(UnknownHostException e){
            System.out.println("호스트 네임 오류 IP 주소 변환 불가");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}