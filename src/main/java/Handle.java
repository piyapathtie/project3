import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class Handle {

    static String torrent_name = "default.torrent";
    static String m_check= "M";
    static String tcp_port= "1234";
    static String path;
    static String file_name;
    static String windowMachineName = System.getProperty("user.name").toUpperCase();
    static String linuxMachineName = System.getProperty("user.name");

    static String get_master_ip(String substringg) {
        ConcurrentSkipListSet<String> temp = UDPserver.getrekt();
        for (String x : temp) {
            if (x.startsWith(substringg)) {
                String masterIP = x.substring(substringg.length());
                return masterIP;
            }
        }
        return "";
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Thread udpRegist = new Thread(new UDPregister());
        Thread udpRegistM = new Thread(new UDPregisterM(m_check));
        Thread serv = new Thread(new UDPserver(m_check));
        udpRegist.start();
        serv.start();

        System.out.println("linux or window?");
        Scanner a = new Scanner(System.in);
        String os_type = a.nextLine();

        System.out.println("<For master:enter file name>,<For client:type 'N' and press enter>");
        Scanner ujm = new Scanner(System.in);
        file_name = ujm.nextLine();

        if (os_type.equals("window")) {
            path = "C:/Users/" + windowMachineName + "/Desktop/";
        }
        else if (os_type.equals("linux")){
            path = "/home/"+ linuxMachineName + "/Desktop/";
        }

        String type = "";
        boolean found_status = false;
        while (found_status == false) {
            File f = new File(path + file_name);
            System.out.println(f.exists());
            if (f.exists()) {
                System.out.println("found file!!");
                System.out.println("Im a master!");
                found_status = true;
                type = "master";
            }

            if (get_master_ip(m_check).equals("")) {
                continue;
            }
            else {
                System.out.println("Im a client!");
                type = "client";
                break;
            }
        }

        if (type.equals("master")) {
            udpRegist.stop();
            udpRegistM.start();
            Thread give_torrent = new Thread(new Server(tcp_port,path+torrent_name));
            ToTorrent.makeTorrent(path+file_name,path,torrent_name,get_master_ip(m_check));
            give_torrent.start();
            Track.startIt(path+torrent_name,6969);
            Seed.loadIt(path+torrent_name,path);


        } else if (type.equals("client")) {
            String torrentGetter_url = get_master_ip(m_check);
            Thread.sleep(5000);
            Client s = new Client(torrentGetter_url,tcp_port,path+torrent_name);
            s.rq();
            //new Client(torrentGetter_url,tcp_port,path+torrent_name);
            Seed.loadIt(path+torrent_name,path);

        }

    }
}


