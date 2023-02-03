package client;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

public class MainFrame extends JFrame implements ActionListener, KeyListener {
    LoginDialog loginDialog;
    Client client;
    FriendList friendList;
    MessageField messageField;

    /*
     * 用来存储在线用户列表
     * 并对应与其的消息记录
     * 点击不同用户实现消息记录切换的基本逻辑是：点击用户，将其消息记录赋值给消息记录面板
     */
    HashMap<String, StringBuffer> userMessage;

    public static void main(String[] args) throws IOException {
        new MainFrame();
    }

    public MainFrame() {
        super("LiteMessage");
        loginDialog = new LoginDialog(this);
        userMessage = new HashMap<>();
        messageField = new MessageField(this);
        friendList = new FriendList(this);

        this.setLayout(null);
        this.add(friendList.scrollPane);
        this.add(messageField);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public String getUsername() {
        return loginDialog.getUsername();
    }

    public String getPassword() {
        return loginDialog.getPassword();
    }

    public String getRegiEmail() {
        return loginDialog.getRegiEmail();
    }

    public String getCaptcha() {
        return loginDialog.getCaptcha();
    }

    public String getRegipassword() {
        return loginDialog.getRegipassword();
    }

    public String getRegiConfirmPw() {
        return loginDialog.getRegiConfirmPw();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}