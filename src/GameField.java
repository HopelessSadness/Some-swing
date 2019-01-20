import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean inPause = false;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        timer = new Timer(200, this);
        timer.start();
        if (inGame) {
            initGame();
        }
        addKeyListener(new FieldKeyListner());
        setFocusable(true);
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 160;
        }
        createApple();
    }

    public void createApple() {
        appleX = DOT_SIZE + new Random().nextInt(18) * DOT_SIZE;
        appleY = DOT_SIZE + new Random().nextInt(19) * DOT_SIZE;
        System.out.println(appleX);
        System.out.println(appleY);
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("C:\\Users\\ASKovalev\\IdeaProjects\\Snake\\src\\apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("C:\\Users\\ASKovalev\\IdeaProjects\\Snake\\src\\dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame && !inPause) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
                String str1 = "Press P to stop this shit_^";
                g.setColor(Color.white);
                g.drawString(str1, 16, 348);
            }
        }
        if (!inGame) {
            String str1 = "Game Over";
            String str2 = "Press R to restart this shit_^";
            //Font f = new Font("Arial", 14, Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str1, 125, SIZE / 2);
            g.drawString(str2, 85, SIZE / 2 + 16);
        }
        if (inPause) {
            String str1 = "Pause";
            String str2 = "Press C to continue this shit_^";
            //Font f = new Font("Arial", 14, Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(f);
            g.drawString(str1, 125, SIZE / 2);
            g.drawString(str2, 85, SIZE / 2 + 16);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
            repaint();
        }
    }

    class FieldKeyListner extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode(); //catch input keys
            if (!inPause) {
                if (key == KeyEvent.VK_LEFT && !right) {
                    left = true;
                    up = false;
                    down = false;
                }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                right = false;
                down = true;
            }
        }
            if (key == KeyEvent.VK_P) {
                timer.stop();
                inPause = true;
                updateUI();
                System.out.println(inPause);
            }
            if (key == KeyEvent.VK_C && !timer.isRunning()) {
                timer.start();
                inPause = false;
                System.out.println(inPause);
            }
            if (key == KeyEvent.VK_R && !inGame) {
                inGame = true;
                left = false;
                right = true;
                up = false;
                down = false;
                initGame();
            }
        }
    }
}