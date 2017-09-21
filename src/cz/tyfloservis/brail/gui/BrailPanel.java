package cz.tyfloservis.brail.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cz.tyfloservis.brail.WordsProvider;

public class BrailPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final ResourceBundle BUNDLE = ResourceBundle
			.getBundle(BrailPanel.class.getName());

	private static final Logger LOGGER = Logger.getLogger(BrailPanel.class
			.getName());

	private JLabel charsLabel = new JLabel(BUNDLE.getString("label.chars"));

	private JTextField charsField = new JTextField("lakero");

	private JLabel preferredCharsLabel = new JLabel(
			BUNDLE.getString("label.preferredChars"));

	private JTextField preferredCharsField = new JTextField(20);

	private JLabel countLabel = new JLabel(BUNDLE.getString("label.count"));

	private JTextField countField = new JTextField("50");

	private JButton generateButton = new JButton(
			BUNDLE.getString("label.generate"));

	private JTextArea outputArea = new JTextArea(5, 10);

	private JButton copyToClipboardButton = new JButton(
			BUNDLE.getString("label.toClipboard"));

	private JButton saveToFileButton = new JButton(
			BUNDLE.getString("label.toFile"));
	
	private JFileChooser fileChooser = new JFileChooser();

	public BrailPanel() {
		createGui();
		createLayout();
		createListeners();
	}

	private void createGui() {
		charsField.setToolTipText(BUNDLE.getString("tooltip.chars"));
		preferredCharsField.setToolTipText(BUNDLE
				.getString("tooltip.preferredChars"));
		countField.setToolTipText(BUNDLE.getString("tooltip.count"));
		generateButton.setToolTipText(BUNDLE.getString("tooltip.generate"));
		copyToClipboardButton.setToolTipText(BUNDLE
				.getString("tooltip.toClipboard"));
		saveToFileButton.setToolTipText(BUNDLE.getString("tooltip.toFile"));
	}

	private void createListeners() {
		generateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				WordsProvider provider = new WordsProvider();
				provider.setCharacters(charsField.getText());
				provider.setPreferredChararacters(preferredCharsField.getText());
				provider.setCount(getCountAsInt());

				outputArea.setText(provider.generate());

			}
		});
		
	copyToClipboardButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(outputArea.getText()), null);
			
		}
	});
	
	saveToFileButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = outputArea.getText();
			if (text == null || text.length() == 0) {
				return;
			}
			if (fileChooser.showSaveDialog(BrailPanel.this) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()));
					writer.write(text);
					writer.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(BrailPanel.this, BUNDLE.getString("message.fileSavingFailed"), BUNDLE.getString("label.error"), ERROR);
				}
			}
			
		}
	});

	}
	
	

	private void createLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints relative = new GridBagConstraints();
		relative.gridwidth = GridBagConstraints.RELATIVE;
		relative.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints remainder = new GridBagConstraints();
		remainder.gridwidth = GridBagConstraints.REMAINDER;
		remainder.fill = GridBagConstraints.HORIZONTAL;
		remainder.weightx = 1;
		add(charsLabel, relative);
		add(charsField, remainder);
		add(preferredCharsLabel, relative);
		add(preferredCharsField, remainder);
		add(countLabel, relative);
		add(countField, remainder);
		add(generateButton, remainder);
		remainder.weighty = 1;
		remainder.fill = GridBagConstraints.BOTH;
		outputArea.setWrapStyleWord(true);
		outputArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(outputArea);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		add(scrollPane, remainder);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));
		buttonsPanel.add(copyToClipboardButton);
		buttonsPanel.add(saveToFileButton);
		remainder.weighty = 0;
		remainder.fill = GridBagConstraints.HORIZONTAL;
		add(buttonsPanel, remainder);

	}

	private int getCountAsInt() {
		int count = 50;
		try {
			count = Integer.valueOf(countField.getText());
			if (count < 1) {
				count = 50;
			}
		} catch (NumberFormatException exception) {
			LOGGER.log(Level.WARNING, "Couldn't parse count", exception);
		}
		return count;
	}

	public String getTitle() {
		return BUNDLE.getString("application.title");
	}
}
