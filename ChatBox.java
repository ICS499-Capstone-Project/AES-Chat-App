package com.test2.ics483.edu;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ChatBox {

	public static void main(String[] args) {
		try {
			System.out.println("Group-8 Chat Room");
			Client client = new Client(JOptionPane.showInputDialog("Enter your Name"));
			Thread t1 = new Thread(client);
			t1.start();
		} catch (Exception e) {
			e.getMessage();
		}

	}
}
