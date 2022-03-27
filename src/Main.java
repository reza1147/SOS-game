import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Main extends JFrame {
    private boolean gameType;// true = PvE   false= PvP
    private int gameWidth, gameHeight;
    private Font defaultFont = new Font("tahoma", Font.BOLD, 20);
    private JTextField heightTxt, widthTxt;
    private Main me;

    public Main() {
        setSize(600, 600);
        setLocationRelativeTo(null);
        setTitle("SOS game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        me = this;
        gameWidth = 3;
        gameHeight = 3;
        initTopPanel();
        initCenterPanel();
        initBotPanel();

        setVisible(true);
    }

    private void initBotPanel() {
        JButton startGame = new JButton("Start!!");
        startGame.setFont(defaultFont);
        add(startGame, BorderLayout.PAGE_END);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkSize()) {
                    GameBoard gameBoard = new GameBoard(gameWidth, gameHeight, gameType);
                    Game game = new Game(gameBoard);
                    setVisible(false);
                    game.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            setVisible(true);
                        }
                    });
                    game.addPropertyChangeListener("Points", new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ((int) evt.getNewValue() > (int) evt.getOldValue())
                                JOptionPane.showMessageDialog(me,
                                        "Red Won!! point=" + evt.getNewValue()
                                        , "Winner",
                                        JOptionPane.PLAIN_MESSAGE);
                            else if ((int) evt.getNewValue() < (int) evt.getOldValue())
                                JOptionPane.showMessageDialog(me,
                                        "Blue Won!! point=" + evt.getOldValue()
                                        , "Winner",
                                        JOptionPane.PLAIN_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(me,
                                        "tie", "tie",
                                        JOptionPane.PLAIN_MESSAGE);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(me,
                            "Enter number between 3 and 10!!"
                            , "Error"
                            , JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private boolean checkSize() {
        if (gameWidth == -1)
            widthTxt.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        else
            widthTxt.setBorder(new JTextField().getBorder());
        if (gameHeight == -1)
            heightTxt.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        else
            heightTxt.setBorder(new JTextField().getBorder());
        return gameWidth != -1 && gameHeight != -1;
    }

    private void initCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel widthLbl = new JLabel("Width");
        widthLbl.setFont(defaultFont);
        widthLbl.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel heightLbl = new JLabel("Height");
        heightLbl.setFont(defaultFont);
        heightLbl.setHorizontalAlignment(SwingConstants.CENTER);

        DocumentListener isValidNumberListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    int sizeTemp = -1;
                    if (isValidNumber(e.getDocument().getText(0, e.getDocument().getLength())))
                        sizeTemp = Integer.parseInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    if (e.getDocument().getProperty("owner").equals("width"))
                        gameWidth = sizeTemp;
                    else
                        gameHeight = sizeTemp;
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            public boolean isValidNumber(String str) {
                if (isNumber(str))
                    return isValid(str);
                return false;
            }

            public boolean isNumber(String str) {
                return str.matches("[\\d]+");
            }

            public boolean isValid(String str) {
                int i = Integer.parseInt(str);
                return i >= 3 && i <= 10;
            }
        };

        widthTxt = new JTextField("3");
        widthTxt.getDocument().addDocumentListener(isValidNumberListener);
        widthTxt.setFont(defaultFont);
        widthTxt.setHorizontalAlignment(JTextField.CENTER);
        widthTxt.getDocument().putProperty("owner", "width");

        heightTxt = new JTextField("3");
        heightTxt.setFont(defaultFont);
        heightTxt.setHorizontalAlignment(JTextField.CENTER);
        heightTxt.getDocument().addDocumentListener(isValidNumberListener);
        heightTxt.getDocument().putProperty("owner", "height");

        centerPanel.add(widthLbl);
        centerPanel.add(heightLbl);
        centerPanel.add(widthTxt);
        centerPanel.add(heightTxt);

        centerPanel.setBorder(new EmptyBorder(100, 100, 100, 100));
        add(centerPanel, BorderLayout.CENTER);

    }

    private void initTopPanel() {
        JPanel topPnl = new JPanel(new FlowLayout());

        JToggleButton gamePvETypeBtn = new JToggleButton("PvE");
        JToggleButton gamePvPTypeBtn = new JToggleButton("PvP");
        gamePvETypeBtn.setSelected(true);
        gameType = true;
        topPnl.add(gamePvETypeBtn);
        topPnl.add(gamePvPTypeBtn);
        gamePvETypeBtn.setActionCommand("PvE");
        gamePvPTypeBtn.setActionCommand("PvP");
        gamePvETypeBtn.setFont(defaultFont);
        gamePvPTypeBtn.setFont(defaultFont);
        ActionListener gameTypeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("PvE")) {
                    gamePvPTypeBtn.setSelected(!gamePvPTypeBtn.isSelected());
                } else {
                    gamePvETypeBtn.setSelected(!gamePvETypeBtn.isSelected());
                }
                gameType = gamePvETypeBtn.isSelected();
            }
        };
        gamePvETypeBtn.addActionListener(gameTypeListener);
        gamePvPTypeBtn.addActionListener(gameTypeListener);
        add(topPnl, BorderLayout.PAGE_START);

    }


    public static void main(String[] args) {
        new Main();
    }

}
