package practice;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class KeyEventTest extends JFrame implements KeyListener { // (1)
	public KeyEventTest() {
		setTitle("이벤트 예제");
		setSize(300, 200);
		JTextField tf = new JTextField(20);
		tf.addKeyListener(this); // (2)
		add(tf);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void keyTyped(KeyEvent e) {
		display(e, "KeyTyped ");
	}

	public void keyPressed(KeyEvent e) {
		display(e, "KeyPressed ");
	}

	public void keyReleased(KeyEvent e) {
		display(e, "Key Released ");
	}
	protected void display(KeyEvent e, String s) {
		char c = e.getKeyChar();
		int keyCode = e.getKeyCode();
		String modifiers = e.isAltDown() + " " + e.isControlDown() + " " + e.isShiftDown();
		System.out.println(s + " " + c + " " + keyCode + " " + modifiers);
	}

	public static void main(String[] args) {
		KeyEventTest f = new KeyEventTest();
	}
}
