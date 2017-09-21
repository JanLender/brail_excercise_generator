package cz.tyfloservis.brail.gui;

import javax.swing.JFrame;

public class BrailFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public BrailFrame() {
		super ();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BrailPanel panel = new BrailPanel();
		setContentPane(panel);
		setTitle(panel.getTitle());
		pack();
		setVisible(true);
	}
	
	public static void main (String[] args) {
		new BrailFrame();
	}

}
