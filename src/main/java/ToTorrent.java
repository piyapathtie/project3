
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.URI;

public class ToTorrent {


    public static void makeTorrent(String filepath,String outfiledir,String file_name,String master) {
        // File parent = new File("d:/echo-insurance.backup");
        //String sharedFile = "d:/echo-insurance.backup";
        String sharedFile = filepath;

        try {
            System.out.println("master "+master);
            String urll = "http://"+master+":6969"+"/"+"announce";
            URI lol = new URI(urll);
            Tracker tracker = new Tracker( InetAddress.getLocalHost() );
            tracker.start();
            System.out.println("TrackerA_mac running.");

            System.out.println( "create new .torrent metainfo from "+filepath );
            System.out.println("anouncer "+tracker.getAnnounceUrl().toURI());
            //Torrent torrent = Torrent.create(new File(sharedFile), tracker.getAnnounceUrl().toURI(), "createdByAuthor");
            Torrent torrent = Torrent.create(new File(sharedFile),lol , "createdByAuthor");
            System.out.println("save .torrent to "+outfiledir+file_name);

            FileOutputStream fos = new FileOutputStream(outfiledir+file_name);
            torrent.save( fos );
            fos.close();
            System.out.println("done creating .torrent");
            tracker.stop();


        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}
