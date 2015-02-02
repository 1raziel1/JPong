

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main extends JFrame{

	public static void main(String[] args) {
		Main frame=new Main();
		frame.setTitle("JPONG");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setLocation(400,200);
		frame.setResizable(false);
		
		
		PongPanel pongPanel=new PongPanel();
		frame.add(pongPanel, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setVisible(true);

		
		
	
		
	}

}
