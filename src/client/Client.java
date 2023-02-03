package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.*;

public class Client extends Thread {
    private final static String host = "127.0.0.1";
    private final static int port = 9999;

    private MainFrame owner;// 父窗口

    private Socket socket;

    private String userID;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client(String userID, MainFrame owner) throws IOException {
        this.userID = userID;
        this.socket = new Socket(host, port);
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.owner = owner;
    }

    // 注册函数
    public void register(String password) throws IOException {
        Register register = new Register(this.userID, password);
        oos.writeObject(register);
        oos.flush();
    }

    // 登陆函数
    public void login(String password) throws IOException {
        Login login = new Login(this.userID, password);
        oos.writeObject(login);
        oos.flush();
    }

    // 发送消息函数
    public void sendMessage(String receiver, String msg) throws IOException {
        Message message = new Message(this.userID, receiver, msg);
        oos.writeObject(message);
        oos.flush();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object data = ois.readObject();
                /*
                 * 登陆数据包，用于验证登陆成功与否
                 * 消息数据包，用于刷新用户消息
                 * 上线数据包，用于刷新当前在线的用户列表
                 */
                if (data instanceof Login) {
                    Login login = (Login) data;
                    if (login.getLoginResult()) {
                        owner.loginDialog.loginResult(true);

                    } else {
                        owner.loginDialog.loginResult(false);
                    }
                } else if (data instanceof Message) {
                    Message message = (Message) data;

                    if (message.sendResult()) {// 发送消息成功

                        /*
                         * Receiver是本Client：代表是别的用户发给此用户，更新消息列表
                         * Receiver是其他Client：代表本Client发送消息的回执
                         */
                        if (message.getReceiver().equals(this.userID)) {
                            StringBuffer str = owner.userMessage.get(message.getOwner());
                            str.append(message.getMessage());
                            owner.userMessage.put(message.getOwner(), str);

                            if (message.getOwner().equals(owner.messageField.getCurrentReceiver()))
                                owner.messageField.setCurrentReceiver(message.getOwner());
                        } else
                            System.out.println(this.userID + " send message successfully");

                    } else {

                    }
                } else if (data instanceof SignedIn) {
                    SignedIn signedIn = (SignedIn) data;
                    owner.friendList.addFriend(signedIn.getOwner());

                } else {
                    System.out.println("Unknown type of data");
                }
            } catch (Exception e) {
                System.out.println("服务器断开连接");
                break;
            }
        }
    }
}