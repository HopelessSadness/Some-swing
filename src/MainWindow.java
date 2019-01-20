import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Holly Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(352, 388); //52pix is a hat
        setLocationRelativeTo(null);
        add(new GameField());
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
