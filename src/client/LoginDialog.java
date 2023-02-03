package client;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

public class LoginDialog extends JDialog implements ActionListener {
	private final JPanel contentPanel = new JPanel();
	
	private MainFrame owner;
	private JLabel welcomeLabel;
	private ImageIcon welcomePic;

	/* 用户名和密码输入区域 */
	private JTextField usernameField;
	private JLabel usernameLabel;
	private JPasswordField passwordField;
	private JLabel passwordLabel;

	/* 登陆和注册按钮 */
	private JButton loginButton;
	private JButton registerButton;

	/* 注册面板 */
	private JPanel regiPanel;
	private JTextField regiEmail;
	private JTextField regiCaptcha;
	private JPasswordField regiPassword;
	private JPasswordField regiConfirmPw;
	private JButton regiCancel;
	private JButton regiComfirm;

	/* 窗口尺寸 */
	private static final int DIALOG_WIDTH = 475;
	private static final int DIALOG_HEIGHT = 400;
	private static final int DIALOG_HEIGHT_EXTEND = 700;

	public LoginDialog(MainFrame owner) {
		this.owner = owner;
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		this.setLocationRelativeTo(owner);

		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.contentPanel.setLayout(null);

		/* 欢迎界面图片 */
		welcomeLabel = new JLabel();
		welcomeLabel.setBounds(0, 0, 475, 204);
		welcomeLabel.setToolTipText("By Arcolaus");
		welcomePic = new ImageIcon("img/LoginPic.png");
		welcomeLabel.setIcon(welcomePic);
		this.contentPanel.add(welcomeLabel);

		Font infoFont = new Font("Microsoft Yahei UI", Font.PLAIN, 18);

		/* 登陆区域 */
		loginButton = new JButton("登 录");
		loginButton.setBackground(new Color(113, 150, 159));
		loginButton.setFont(infoFont);
		loginButton.setUI(new BasicButtonUI());
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					owner.client = new Client(usernameField.getText(), owner);
					owner.client.start();
					owner.client.login(String.valueOf(passwordField.getPassword()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		loginButton.setBounds(100, 320, 100, 30);
		this.contentPanel.add(loginButton);

		/* 注册区域 */
		registerButton = new JButton("注 册");
		registerButton.setBackground(new Color(36, 169, 225));
		registerButton.setFont(infoFont);
		registerButton.setUI(new BasicButtonUI());
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (LoginDialog.this.getHeight() == DIALOG_HEIGHT_EXTEND) {
					LoginDialog.this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
				} else {
					LoginDialog.this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT_EXTEND);
				}
			}
		});
		registerButton.setBounds(270, 320, 100, 30);
		this.contentPanel.add(registerButton);

		usernameField = new JTextField("", 20);
		usernameField.setBounds(180, 220, 190, 30);
		this.contentPanel.add(usernameField);

		passwordField = new JPasswordField("123", 10);
		passwordField.setBounds(180, 270, 190, 30);
		this.contentPanel.add(passwordField);

		Font font1 = new Font("黑体", Font.BOLD, 16);
		usernameLabel = new JLabel("用户名");
		usernameLabel.setFont(font1);
		usernameLabel.setBounds(100, 220, 50, 30);
		this.contentPanel.add(usernameLabel);

		passwordLabel = new JLabel("密  码");
		passwordLabel.setFont(font1);
		passwordLabel.setBounds(100, 270, 50, 30);
		this.contentPanel.add(passwordLabel);

		// 注册面板
		final int labelLeftAlign = 80;
		final int textLeftAlign = 150;
		Font registerFont = new Font("宋体", Font.BOLD, 18);
		regiPanel = new JPanel();
		regiPanel
				.setBorder(new TitledBorder(null, "注册用户", TitledBorder.LEADING, TitledBorder.TOP, registerFont, null));
		regiPanel.setBounds(20, 390, 425, 240);
		contentPanel.add(regiPanel);
		regiPanel.setLayout(null);

		Font btnFont = new Font("宋体", Font.BOLD, 14);

		JLabel emailLabel = new JLabel("昵 称");
		emailLabel.setFont(btnFont);
		emailLabel.setBounds(labelLeftAlign, 33, 55, 18);
		regiPanel.add(emailLabel);

		JLabel cpatchaLabel = new JLabel("验证码");
		cpatchaLabel.setFont(btnFont);
		cpatchaLabel.setBounds(labelLeftAlign, 74, 55, 18);
		regiPanel.add(cpatchaLabel);

		JLabel passwordLabel = new JLabel("密 码");
		passwordLabel.setFont(btnFont);
		passwordLabel.setBounds(labelLeftAlign, 115, 55, 18);
		regiPanel.add(passwordLabel);

		JLabel confirmPwLabel = new JLabel("确认密码");
		confirmPwLabel.setFont(btnFont);
		confirmPwLabel.setBounds(labelLeftAlign, 157, 58, 18);
		regiPanel.add(confirmPwLabel);

		regiEmail = new JTextField("", 10);
		regiEmail.setBounds(textLeftAlign, 30, 180, 25);
		regiPanel.add(regiEmail);

		regiCaptcha = new JTextField("", 10);
		regiCaptcha.setBounds(textLeftAlign, 70, 180, 25);
		regiPanel.add(regiCaptcha);

		regiPassword = new JPasswordField("", 10);
		regiPassword.setBounds(textLeftAlign, 110, 180, 25);
		regiPanel.add(regiPassword);

		regiConfirmPw = new JPasswordField("", 10);
		regiConfirmPw.setBounds(textLeftAlign, 150, 180, 25);
		regiPanel.add(regiConfirmPw);

		regiComfirm = new JButton("确 认");
		regiComfirm.setUI(new BasicButtonUI());
		
		regiComfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (regiEmail.getText().equals("")
						|| String.valueOf(regiPassword.getPassword()).equals("")
						|| String.valueOf(regiConfirmPw.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(
							regiComfirm.getParent(),
							"您似乎有必填项未填写",
							"注册失败",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (!String.valueOf(regiPassword.getPassword())
						.equals(String.valueOf(regiConfirmPw.getPassword()))) {
					JOptionPane.showMessageDialog(
							regiComfirm.getParent(),
							"两次输入的密码不一致",
							"注册失败",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					try {
						Client regi = new Client(regiEmail.getText(), owner);
						regi.register(String.valueOf(regiPassword.getPassword()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		regiComfirm.setBounds(80, 200, 80, 30);
		regiPanel.add(regiComfirm);

		regiCancel = new JButton("取 消");
		regiCancel.setUI(new BasicButtonUI());
		regiCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				owner.dispose();
				dispose();
			}
		});
		regiCancel.setBounds(250, 200, 80, 30);
		regiPanel.add(regiCancel);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void loginResult(boolean result) {
		if (result) {
			owner.setVisible(true);
			dispose();// 关闭此LoginDialog
		} else {
			JOptionPane.showMessageDialog(
					contentPanel.getParent(),
					"您的登陆信息有误",
					"登录失败",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return String.valueOf(passwordField);
	}

	public String getRegiEmail() {
		return regiEmail.getText();
	}

	public String getCaptcha() {
		return regiCaptcha.getText();
	}

	public String getRegipassword() {
		return String.valueOf(regiPassword.getPassword());
	}

	public String getRegiConfirmPw() {
		return String.valueOf(regiConfirmPw.getPassword());
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}