package com.test2.ics483.edu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

public class Client implements Runnable {
	public static JTextArea textField;
	public static JTextArea mainTextArea;
	public static String login = "Team 8";
	public static SecretKey sharedKey;
	public static BufferedWriter writer;
	public static BufferedReader reader;
	public boolean enc = true;

	
	// Logs messages for the chat App
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

	public Client(String l) throws Exception {
		login = l;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("AEScodeInput.txt")));
			SecretKeySpec secret = new SecretKeySpec(sharedKey.getEncoded(), "AES");
			sharedKey = SecretKeyFactory.getInstance(in.readLine()).generateSecret(secret);
			in.close();

		} catch (Exception e) {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("AEScodeOutput.txt")));
			sharedKey = AESEncryption.getSecretEncryptionKey();

			out.write(new String(sharedKey.getEncoded()));
			out.close();
		}
		// Main Chat Frame
		final JFrame frame1 = new JFrame("Chat v0.1");
		frame1.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 17));
		frame1.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
		frame1.setBounds(100, 100, 578, 312);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		// send text area
		textField = new JTextArea();
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));

		// Send Button
		final JButton sendButton = new JButton("Send");
		sendButton.setFont(new Font("Tahoma", Font.BOLD, 16));

		// chat window area
		mainTextArea = new JTextArea();
		mainTextArea.setEnabled(false);
		mainTextArea.setFont(new Font("Tahoma", Font.BOLD, 14));
		// mainTextArea.setLineWrap(true);
		// JScrollPane scroll = new JScrollPane(mainTextArea);
		// Toggle Encryption Button
		final JToggleButton encButton = new JToggleButton("Encrypt");
		encButton.setFont(new Font("Tahoma", Font.HANGING_BASELINE, 12));
		encButton.setBounds(459, 13, 89, 44);
		frame1.getContentPane().add(encButton);

		// Potential Feature to add Save Button
		final JPanel southPanel = new JPanel();
		JButton save = new JButton("Save");
		southPanel.setLayout(new GridLayout(0, 5));
		southPanel.add(save);
		frame1.add(southPanel, BorderLayout.NORTH);

		// Clear Button
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearButton.setBounds(459, 139, 89, 44);
		frame1.getContentPane().add(clearButton);
		// Clear Login
		final JFrame frame2 = new JFrame("Login___");
		frame2.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 17));
		frame2.setBounds(100, 100, 450, 300);
		frame2.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Client.class.getResource("/com/sun/java/swing/plaf/motif/icons/TreeOpen.gif")));
		GroupLayout groupLayout = new GroupLayout(frame2.getContentPane());
		frame2.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(true);
		// UseName Label
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		usernameLabel.setBounds(12, 46, 82, 30);
		frame2.getContentPane().add(usernameLabel);

		final JTextField usernameText = new JTextField();
		usernameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameText.setBounds(123, 38, 265, 38);
		usernameText.setColumns(10);
		frame2.getContentPane().add(usernameText);
		// Password Label
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		passwordLabel.setBounds(12, 89, 82, 30);

		final JPasswordField passwordText = new JPasswordField();
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordText.setBounds(123, 81, 265, 38);
		frame2.getContentPane().add(passwordText);

		final JButton submitButton = new JButton("Login");
		submitButton.setFont(new Font("Tahoma", Font.PLAIN, 17));

		submitButton.setBounds(291, 201, 97, 39);
		JButton exitButton = new JButton("Exit");
		exitButton.setForeground(new Color(255, 0, 0));
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		/////////
		// Button for Opening Dialog Box
		final JButton open = new JButton("Open");
		open.setFont(new Font("Tahoma", Font.PLAIN, 16));
		open.setBounds(459, 82, 89, 44);
		frame1.getContentPane().add(open);
		GroupLayout groupLayout1 = new GroupLayout(frame1.getContentPane());
		groupLayout1.setHorizontalGroup(groupLayout1.createParallelGroup(Alignment.LEADING).addGroup(groupLayout1
				.createSequentialGroup().addGap(12)
				.addGroup(groupLayout1.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout1
						.createSequentialGroup()
						.addComponent(mainTextArea, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE).addGap(8)
						.addGroup(groupLayout1.createParallelGroup(Alignment.LEADING)
								.addComponent(encButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(open, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout1.createSequentialGroup()
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
				.addGap(12)));
		groupLayout1.setVerticalGroup(groupLayout1.createParallelGroup(Alignment.LEADING).addGroup(groupLayout1
				.createSequentialGroup().addGap(13)
				.addGroup(groupLayout1.createParallelGroup(Alignment.LEADING)
						.addComponent(mainTextArea, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addGroup(groupLayout1.createSequentialGroup()
								.addComponent(encButton, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE).addGap(25)
								.addComponent(open, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE).addGap(13)
								.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
				.addGap(13)
				.addGroup(groupLayout1.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
						.addComponent(sendButton, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
				.addGap(25)));
		frame1.getContentPane().setLayout(groupLayout1);

		/////////
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(passwordLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addGap(33)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
								.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addComponent(passwordText, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
						.addComponent(usernameText, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
				.addGap(84)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(63)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameText, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(31)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(passwordText, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(28)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		frame2.getContentPane().setLayout(groupLayout);
		frame2.getContentPane().add(submitButton);
		frame2.getContentPane().add(passwordLabel);
		frame2.getContentPane().add(passwordLabel);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser jfile = new JFileChooser();
				int show = jfile.showOpenDialog(null);
				if (show == JFileChooser.APPROVE_OPTION) {

					try {
						Scanner infile = new Scanner(new File(jfile.getSelectedFile().getAbsolutePath()));
						String content = "";
						while (infile.hasNextLine()) {
							if (!content.isEmpty()) {
								content += "\n";
							}
							content += infile.nextLine();
						}
						infile.close();
						textField.setText(content);
					} catch (FileNotFoundException e) {

						LOGGER.error("was not found.", e);
					}

				} else {
					JOptionPane.showMessageDialog(frame1, "No File Choosen");

				}
			}
		});
		// Save Button, feature that might be added
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String value = evt.getActionCommand();
				if (value.equals("save")) {
					JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					int show = j.showSaveDialog(null);
					if (show == JFileChooser.APPROVE_OPTION) {
						textField.setText(j.getSelectedFile().getAbsolutePath());

					}
				}
			}
		});

		try {
			@SuppressWarnings("resource")
			String port = "";
			Socket socketClient = new Socket("localhost", 999);
			System.out.println("Client is running on IP address: " + socketClient.getInetAddress() + ", on port: "
					+ socketClient.getLocalPort());
			writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String value1 = usernameText.getText();
				@SuppressWarnings("deprecation")
				String value2 = passwordText.getText();
				if (value1.equals("Admin") && value2.equals("Admin")) {
					JOptionPane.showMessageDialog(null, "Successfuly Connected");
					frame2.dispose();
					frame1.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Wrong Password / Username");
					usernameText.setText("");
					passwordText.setText("");
					usernameText.requestFocus();

				}
			}
		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				clearButtonActionPerformed(actionEvent);
			}

			private void clearButtonActionPerformed(ActionEvent actionEvent) {
				mainTextArea.setText("");
				textField.requestFocus();

			}
		});

		encButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ev) {
				enc = !enc;
				if (enc) {

					encButton.setLabel("On");

				} else {
					encButton.setLabel("Off");
				}
			}
		});
		// Key Pressed Enter
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendButton.doClick();

				}
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				frame2.dispose();
			}
		});

		// Key Pressed Enter - Password
		passwordText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					submitButton.doClick();

				}
			}
		});
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String s = login + " : " + textField.getText();
				textField.setText("");
				try {
					if (enc) {
						byte[] test = AESEncryption.encryptText(s, sharedKey);
						s = Base64.getEncoder().encodeToString(test);
					}

					writer.write(s);
					writer.newLine();
					writer.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//frame1.setVisible(true);
	}

	public void run() {
		try {

			String serverMsg;
			while ((serverMsg = reader.readLine()) != null) {
				try {
					if (enc) {
						serverMsg = AESEncryption.decryptText(Base64.getDecoder().decode(serverMsg), sharedKey);
					}
				} catch (Exception e) {
					LOGGER.error("Make sure both Users have Encryption On");
				}
				System.out.println("from server: " + serverMsg);
				mainTextArea.append(serverMsg + "\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}