import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    GameBoard board;
    public Game(GameBoard board){
        this.board=board;

        setSize(800, 800);
        setLocationRelativeTo(null);
        setTitle("game");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


        setVisible(true);

    }



}
