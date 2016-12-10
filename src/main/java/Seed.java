import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;



public class Seed {
    public static void loadIt(String torrent_file_path,String original_file_dir) throws InterruptedException {
        try {

            //Client client = new Client(InetAddress.getLocalHost(), SharedTorrent.fromFile(new File(path_to_desktop+"seed.torrent"), new File(path_to_desktop)));
            System.out.println("Client: input .torrent file = "+torrent_file_path);
            System.out.println("Client: to seed file path = "+original_file_dir);
            Client client = new Client(InetAddress.getLocalHost(), SharedTorrent.fromFile(new File(torrent_file_path), new File(original_file_dir)));
            client.setMaxDownloadRate(0);
            client.setMaxUploadRate(0);
            client.addObserver(new Observer() {
                @Override
                public void update(Observable observable, Object data) {
                    Client client = (Client) observable;
                    float progress = client.getTorrent().getCompletion();
                    long downloaded = client.getTorrent().getDownloaded();
                    long uploaded = client.getTorrent().getUploaded();

                }
            });
            client.download();
            client.share(3600);
            client.waitForCompletion();

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
