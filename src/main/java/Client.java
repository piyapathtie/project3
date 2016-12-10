import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    static String IP ;
    static Integer PORT;
    static String PATHFILE;
    public Client(String IP, String port, String PF){
        Integer p = Integer.valueOf(port);
        this.PORT = p;
        this.IP = IP;
        this.PATHFILE = PF;
    }

    public static void rq() throws IOException {
        System.out.println("Download from" + IP);
        System.out.println(IP+PORT+PATHFILE);
        int filesize = 15022386;
        int bytesRead;
        int currentTot = 0;

        Socket socket = new Socket(IP,PORT);
        byte [] bytearray = new byte [filesize];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream(PATHFILE);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(bytearray,0,bytearray.length);
        currentTot = bytesRead;
        do {
            bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot));
            if(bytesRead >= 0) currentTot += bytesRead;
        } while(bytesRead > -1);
        {
            bos.write(bytearray, 0, currentTot);
            bos.flush();
            bos.close();
            socket.close();
        }
    }


    }


