/**
 * Created by Thanapat on 11/30/2016 AD.
 */
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

public class Track {


    public static void startIt(String torrent_file_path,Integer port) throws InterruptedException {
        try {
            // First, instantiate a TrackerA_mac object with the port you want it to listen on.
// The default tracker port recommended by the BitTorrent protocol is 6969.
            Tracker tracker = new Tracker(new InetSocketAddress(port));

// Then, for each torrent you wish to announce on this tracker, simply created
// a TrackedTorrent object and pass it to the tracker.announce() method:
//            FilenameFilter filter = new FilenameFilter() {
//                @Override
//                public boolean accept(File dir, String name) {
//                    return name.endsWith(".torrent");
//                }
//            };
//            String machine_name = InetAddress.getLocalHost().getHostName();
//            String path_to_destop = "C:/Documents and Settings/"+machine_name+"/Desktop/";
            tracker.announce((TrackedTorrent.load(new File(torrent_file_path))));
//            tracker.announce((TrackedTorrent.load(new File(path_to_destop))));


// Once done, you just have to start the tracker's main operation loop:
            System.out.println("starting tracker with port "+port.toString());
            tracker.start();


// You can stop the tracker when you're done with:
            //tracker.stop();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

