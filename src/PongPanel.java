

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener {

	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean wPressed = false;
	private boolean sPressed = false;
	private boolean start = false;
	private boolean color = true;
	private boolean end = false;
	private boolean pause = false;

	private String winner = "";

	private long clock0 = System.currentTimeMillis();

	private int playerOneX = 25;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 50;
	private int numPlayers = 1;
	private int selectP = 190;
	private int colores=0;

	private int playerTwoX = 465;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 50;

	private int playerOneScore = 0;
	private int playerTwoScore = 0;

	private int paddleSpeed = 5;

	private int ballX = 250;
	private int ballY = 250;
	private int diametro = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;
	private Timer timer;

	// private Clip sonido = AudioSystem.getClip();

	public PongPanel() {
		this.setBackground(Color.BLACK);

		setFocusable(true);
		addKeyListener(this);

		timer = new Timer(1000 / 200, this);

		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		step();

	}

	public void step() {
		if (!start || end) {
			if ((System.currentTimeMillis() - clock0) >= 500) {
				if (!color) {
					color = true;
				}

				else if (color) {
					color = false;
				}
				clock0 = System.currentTimeMillis();
			}

		} else {

			// move player 2
			if (numPlayers == 2) {
				if (upPressed) {
					if (playerTwoY - paddleSpeed > 0) {
						playerTwoY -= paddleSpeed;
					}
				}
				if (downPressed) {
					if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
						playerTwoY += paddleSpeed;
					}
				}

			} else {
				playerTwoY = ballY - 10;
			}

			// move player 1
			if (wPressed) {

				if (playerOneY - paddleSpeed > 0) {
					playerOneY -= paddleSpeed;
				}
			}
			if (sPressed) {

				if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
					playerOneY += paddleSpeed;
				}
			}

			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diametro + ballDeltaX;
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diametro + ballDeltaY;

			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom
						|| nextBallBottom < playerOneTop) {

					playerTwoScore++;
					if (playerTwoScore == 3) {
						winner = "Player 2";
						end = true;

					}

					ballX = 250;
					ballY = 250;
				} else {
					ballDeltaX *= -1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom
						|| nextBallBottom < playerTwoTop) {

					playerOneScore++;
					if (playerOneScore == 3) {
						winner = "Player 1";
						end = true;

					}
					ballX = 250;
					ballY = 250;
				} else {
					ballDeltaX *= -1;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;

		}
		repaint();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch(colores){
			case 0:
				g.setColor(Color.WHITE);
				break;
			case 1:
				g.setColor(Color.YELLOW);
				break;
			case 2:
				g.setColor(Color.GREEN);
				break;
			case 3:
				g.setColor(Color.PINK);
				break;
			case 4:
				g.setColor(Color.MAGENTA);
				break;
			case 5:
				g.setColor(Color.ORANGE);
				break;
			case 6:
				g.setColor(Color.CYAN);
				break;
		}
		
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
		// PLAY SCREEN
		if (start && !end) {

			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
			g.drawString("Player 1", 64, 64);
			if (numPlayers == 1) {
				g.drawString("CPU", 364, 64);
			} else {
				g.drawString("Player 2", 364, 64);
			}

			g.drawString(String.valueOf(playerOneScore), 100, 100);

			g.drawString(String.valueOf(playerTwoScore), 400, 100);

			g.fillOval(ballX, ballY, diametro, diametro);

			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);

			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);

			g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			g.drawString("ESC to PAUSE", 214, 40);

			// MAIN SCREEN
		} else if (!start && !end) {

			g.drawString("JPong", 190, 190);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			g.drawString("1 Player        2 Player", 201, 264);

			if (color) {
				g.drawString("Press -Enter- to Start", 190, 230);
				g.drawString(">                  <", selectP, 264);

			} else {
				g.drawString(">               <", selectP + 5, 264);
			}
			// END SCREEN
		} else {
			g.drawString("GAME OVER", 132, 190);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			g.drawString(winner + " WIN!", 164, 230);
			if (color) {
				g.drawString("-Retry?-", 164, 264);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_C) {
			if(colores==6){
				colores=0;
			}else{
				colores++;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (pause) {
				timer.restart();
				pause = false;
			} else if (!end && start && !pause) {
				timer.stop();
				pause = true;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!end) {
				start = true;
			} else {
				playerTwoScore = 0;
				playerOneScore = 0;
				start = false;
				end = false;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if (!end && !start) {
				
				if (numPlayers == 1) {
					numPlayers = 2;
					selectP = 260;
				} else {
					selectP = 190;
					numPlayers = 1;

				}

			}

		}
		if (start) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (start) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = false;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = false;
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
