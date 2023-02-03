package client;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import util.ImageScale;

public class MessageField extends JPanel {
	private MainFrame owner;

	private JTextArea messageHistory;
	private JTextArea sendField;

	private JButton sendButton;
	private JLabel emoji;

	/* 当前的对话框是和哪个用户的 */
	private String currentReceiver = null;

	MessageField(MainFrame owner) {
		this.owner = owner;
		this.setLayout(null);
		this.setBounds(260, -5, 1005, 800);

		Font font = new Font("黑体", Font.PLAIN, 18);

		messageHistory = new JTextArea("");
		messageHistory.setFont(font);
		messageHistory.setOpaque(false);
		messageHistory.setLineWrap(true);
		messageHistory.setWrapStyleWord(true);

		JScrollPane sp = new JScrollPane(messageHistory);
		sp.setBorder(null);
		sp.setBounds(30, 30, 975, 680);

		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		JPanel backdrop = new JPanel() {
			{
				this.setOpaque(false);
				this.setLayout(null);
			}

			public void paintComponent(Graphics g) {
				ImageIcon imageIcon = ImageScale.getImage(new ImageIcon("img\\BackgroudPic.jpg"), 1050, 710);
				g.drawImage(imageIcon.getImage(), 0, 0, this);
				super.paintComponents(g);
			}
		};
		backdrop.add(sp);
		backdrop.setBounds(0, 0, 1005, 710);
		this.add(backdrop);

		emoji = new JLabel();
		emoji.setVisible(true);
		emoji.setIcon(ImageScale.getImage(new ImageIcon("img\\emoji.png"), 50, 50));
		emoji.setBounds(0, 717, 50, 50);
		this.add(emoji);

		sendField = new JTextArea("");
		sendField.setLineWrap(true);
		sendField.setWrapStyleWord(true);
		sendField.setFont(font);
		sendField.setBounds(50, 717, 903, 50);
		this.add(sendField);

		/* 发送消息按钮 */
		sendButton = new JButton();
		sendButton.setUI(new BasicButtonUI());
		sendButton.setIcon(ImageScale.getImage(new ImageIcon("img\\SendButton.jpg"), 50, 50));
		sendButton.setBounds(955, 717, 50, 50);
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendMsg();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.add(sendButton);

		sendField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					sendField.append("\n");
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					try {
						sendMsg();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	public void setCurrentReceiver(String receiver) {
		this.currentReceiver = receiver;
		this.messageHistory.setText(owner.userMessage.get(receiver).toString());
	}

	public void updateHistory(String msg) {
	}

	public void sendMsg() throws IOException {
		if (currentReceiver != null) {
			/* 打上时间戳 */
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd '-' HH:mm");
			Date date = new Date(System.currentTimeMillis());
			String msg = formatter.format(date) + "\n";

			msg += this.sendField.getText() + "\n\n";

			/* 调用后端函数，发送消息到服务器 */
			owner.client.sendMessage(currentReceiver, msg);

			/* 将发送的消息添加进消息记录Map */
			StringBuffer sBuffer = owner.userMessage.get(currentReceiver);
			sBuffer.append(msg);
			owner.userMessage.put(currentReceiver, sBuffer);

			/* 在当前消息记录面板显示发送的消息，并清空编辑框 */
			this.messageHistory.append("Me  @" + msg);
			this.sendField.setText("");
		}
	}

	public String getCurrentReceiver() {
		return this.currentReceiver;
	}
}
