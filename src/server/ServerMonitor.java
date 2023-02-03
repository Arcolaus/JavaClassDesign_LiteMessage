package server;

import util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerMonitor extends Thread {
    private Socket socket;
    String userID;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerMonitor(final Socket socket) throws IOException {
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (this.socket != null) {
            try {
                Object data = ois.readObject();

                /*
                 * 登陆数据包
                 * 消息数据包
                 * 用户注册数据包
                 */
                if (data instanceof Login) {//
                    Login login = (Login) data;

                    if (CheckLogin.checkLogin(login.getOwner(), login.getUserPassword())) {// 登陆通过

                        this.userID = login.getOwner();

                        // 设置数据包为登陆成功状态
                        login.setLoginResult(true);

                        if (Server.getMatchList().get(this.userID) == null)
                            Server.addMatch(this.userID, this);

                        /*
                         * 遍历Server端在线用户
                         * 并向各在线用户通知当前在线用户
                         * 便于更新Client端在线用户列表
                         */
                        for (String key : Server.getMatchList().keySet()) {
                            if (!key.equals(this.userID)) {

                                // 向已在线用户通知此新上线用户
                                SignedIn signedIn = new SignedIn(this.userID);
                                Server.getMatchList().get(key).oos.writeObject(signedIn);
                                Server.getMatchList().get(key).oos.flush();

                                // 将所有在线的用户发送给此新上线用户
                                signedIn = new SignedIn(key);
                                oos.writeObject(signedIn);
                                oos.flush();
                            }
                        }

                    } else
                        login.setLoginResult(false);

                    oos.writeObject(login);
                    oos.flush();
                } else if (data instanceof Message) {
                    Message message = (Message) data;

                    if (Server.getMatchList().get(message.getReceiver()) == null) {
                        // 如果消息的接收者不在线则发送失败
                        message.setSendResult(false);
                    } else {
                        // 设置消息数据包为发送成功状态
                        message.setSendResult(true);

                        // 附加发送者消息
                        message.message = message.getOwner() + "  @" + message.getMessage();

                        // socket发送
                        Server.getMatchList().get(message.getReceiver()).oos.writeObject(message);
                        Server.getMatchList().get(message.getReceiver()).oos.flush();
                    }

                    oos.writeObject(message);
                    oos.flush();
                } else if (data instanceof Register) {
                    Register register = (Register) data;

                    // 打开本地验证文件，写入注册信息
                    BufferedWriter bw = new BufferedWriter(new FileWriter("src/server/userInfo.txt", true));
                    String writeStr = register.getOwner() + "," + register.getPassword() + "\n";

                    bw.write(writeStr);
                    bw.flush();
                    bw.close();
                } else {
                    // 异常，接受到未知类型的数据包
                    System.out.println("Unknown type of data");
                }
            } catch (Exception e) {
                // 当前用户下线，从Server端的在线列表中删除当前用户
                Server.matchList.remove(this.userID);
                System.out.println(this.userID + " is log off");
                break;
            }
        }
    }
}
