package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* 用于检查用户名和密码 */
public class CheckLogin {
    public static boolean checkLogin(String userID, String userPassword) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/server/userInfo.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            String username = info[0];
            String password = info[1];
            if (username.equals(userID)) {
                if (password.equals(userPassword)) {
                    br.close();
                    return true;
                } else {
                    br.close();
                    return false;
                }
            }
        }
        br.close();
        return false;
    }
}
