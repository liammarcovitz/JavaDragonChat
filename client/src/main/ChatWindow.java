package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ChatWindow {
	
	public static String username = "";
	
	private static int startHeight = 30;
	private static int spacing = 40;
	private static int currentMessageIndex = 0;
	private static JLabel chatMessage;
	private static ArrayList<JLabel> messages = new ArrayList<>();
	private static JFrame frame;
	private static JTextField chatEntry;
	private static JButton sendButton;
	private static JPanel sendPanel;
	private static Font defaultFont = new Font(Font.SANS_SERIF,  Font.PLAIN, 25);

	public static void main(String[] args) {
		
		int width = 1000;
		int height = 700;
		
		frame = new JFrame("DragonChat");
		frame.setTitle("DragonChat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loadComponents(frame);
		
		frame.pack();
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);;
		frame.setResizable(false);
		
		username = UUID.randomUUID().toString().substring(0, 6);
		
		sendMessage("Your username is " + username + ".", false);
	}
	
	private static void loadComponents(JFrame frame) {
		sendPanel = new JPanel();
		sendPanel.setBackground(Color.LIGHT_GRAY);
		chatEntry = new JTextField(24);
		chatEntry.setFont(defaultFont);
		sendButton = new JButton("Send");
		sendButton.setFont(defaultFont);
		sendButton.addActionListener(e -> {
			sendChat(e, chatEntry.getText());
			chatEntry.setText("");
			chatEntry.requestFocus();
		});
		Dimension buttonSize = sendButton.getPreferredSize();
		sendButton.setBounds(750, 600, buttonSize.width, buttonSize.height);
		Dimension textFieldSize = chatEntry.getPreferredSize();
		chatEntry.setBounds(200, 600, textFieldSize.width, textFieldSize.height);
		
		sendPanel.setLayout(null);
		sendPanel.add(chatEntry);
		sendPanel.add(sendButton);
		
		frame.add(sendPanel);
	}
	
	public static void sendMessage(String message, boolean fromUser) {
		if (message.equals("")) {
			return;
		}
		if (fromUser) {
			message = username + ": " + message;
		}
		chatMessage = new JLabel(message);
		chatMessage.setFont(defaultFont);
		
		messages.add(chatMessage);
		currentMessageIndex++;
		
		updateMessages();
	}
	
	public static void sendChat(ActionEvent event, String message) {
		sendMessage(message, true);
	}
	
	public static void updateMessages() {
		if (currentMessageIndex >= 14) {
			sendPanel.remove(messages.remove(0));
		}
		
		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).setBounds(150, startHeight + spacing * i, messages.get(i).getPreferredSize().width, messages.get(i).getPreferredSize().height);
			sendPanel.add(messages.get(i));
		}
		
		SwingUtilities.updateComponentTreeUI(sendPanel);
	}
}