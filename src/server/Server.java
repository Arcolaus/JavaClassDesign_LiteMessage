package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* 客户端主程序 */
public class Server {
    public static Map<String, ServerMonitor> matchList = new ConcurrentHashMap<>();
    public static boolean canBeClosed = false;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        Socket socket = null;

        while (true) {
            socket = server.accept();
            new ServerMonitor(socket).start();
            Server.canBeClosed = true;
            if(!Server.canBeClosed)
                break;
        }
        server.close();
    }

    public static void addMatch(String userID, ServerMonitor serverMonitor) {
        matchList.put(userID, serverMonitor);
    }

    public static Map<String, ServerMonitor> getMatchList() {
        return matchList;
    }
}
