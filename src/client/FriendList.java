package client;

import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FriendList {
	MainFrame owner;

	private static Vector<Object> head = new Vector<>();
	private static Vector<Vector<Object>> content = new Vector<>();
	private JTable table;
	private DefaultTableModel tableModel;
	JScrollPane scrollPane;

	public FriendList(MainFrame owner) {
		this.owner = owner;
		head.add("Friends");
		tableModel = new DefaultTableModel(content, head);
		table = new JTable(tableModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 20));
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				String str = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
				owner.messageField.setCurrentReceiver(str);
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				table.requestFocus();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(60);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 260, 800);
	}

	// 向在线列表组件中添加用户，并刷新显示
	public void addFriend(Object obj) {
		Vector<Object> t = new Vector<>();
		t.add(obj);
		content.add(t);
		if (!owner.userMessage.containsKey((String) obj))
			owner.userMessage.put((String) obj, new StringBuffer(""));

		tableModel = new DefaultTableModel(content, head);
		table.setModel(tableModel);
	}

	public void deleteFriend() {

	}
}

class singleFriend {

}