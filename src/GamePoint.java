import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePoint extends JFrame {
    private int bPoint;
    private int rPoint;
    private Font defaultFont = new Font("tahoma", Font.BOLD, 60);
    private int seconds;

    public GamePoint(){
        setTitle("Points");
        setSize(200,200);
        setResizable(false);
        setLocation(0,0);
        bPoint =0;
        rPoint =0;
        seconds=0;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Timer t=new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                repaint();
            }
        });
        t.start();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0,0,g.getClipBounds().width,g.getClipBounds().height);
        g.setFont(defaultFont);
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(bPoint),30,100);
        g.setColor(Color.RED);
        g.drawString(String.valueOf(rPoint),130,100);
        g.setColor(Color.BLACK);
        g.drawString(String.format("%02d:%02d",seconds/60,seconds%60),
                15,150);
    }

    public void setBPoint(int bPoint) {
        this.bPoint = bPoint;
        repaint();
    }

    public void setRPoint(int rPoint) {
        this.rPoint = rPoint;
        repaint();
    }
}
