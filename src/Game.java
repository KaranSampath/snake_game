/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    final static JButton PAUSE = new JButton("Pause");

    public void run() {
        
        //High Scores Window Code
        final JFrame high = new JFrame("High Scores");
        high.setLocation(300, 300);
        high.setBounds(10, 10, 300, 300);
        high.setTitle("High Scores");
        high.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        high.setResizable(true);
        final JPanel end_panel = new JPanel();
        end_panel.setLayout(new GridLayout(1,3));
        final JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                high.dispose();
            }
        });
        end_panel.add(ok);
        high.add(end_panel, BorderLayout.PAGE_END);
        
        final JPanel wel_panel = new JPanel();
        high.add(wel_panel, BorderLayout.PAGE_START);
        final JLabel wel = new JLabel("High Scores");
        wel_panel.add(wel);
        
        final JPanel display_panel = new JPanel();
        high.add(display_panel, BorderLayout.CENTER);
        Reader a = new Reader(true);
        a.highScore();
        String[][] scores = a.highestscores();

        int len = 0;
        if (scores != null && scores.length > 0) {
            if (scores.length < 10) {                
                len = scores.length;
            } else {
                len = 10;
            }
            display_panel.setLayout(new GridLayout(len + 1,2));
            final JLabel hs1 = new JLabel("Name");
            final JLabel hs2 = new JLabel("Score");
            display_panel.add(hs1);
            display_panel.add(hs2);
            for (int v = 0; v < len; v++) {
                String c = scores[v][0];
                final JLabel hs3 = new JLabel(c);
                String d = scores [v][1];
                final JLabel hs4 = new JLabel(d);
                display_panel.add(hs3);
                display_panel.add(hs4);
            } 
        } else {
            final JLabel hs1 = new JLabel("There are no Scores");
            display_panel.add(hs1);            
        }

        // Home Window Code
        
        final JFrame home = new JFrame("Snake");
        home.setLocation(200, 200);
        home.setBounds(10, 10, 500, 500);
        home.setTitle("Snake");
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setResizable(true);
        final JPanel home_panel = new JPanel();
        home_panel.setLayout(new GridLayout(2,2));
        home.add(home_panel, BorderLayout.PAGE_END);
        
        final JPanel welcome_panel = new JPanel();
        home.add(welcome_panel, BorderLayout.PAGE_START);
        final JLabel welcome = new JLabel("Welcome to Snake!");
        welcome_panel.add(welcome);
        
        final JPanel instruction_panel = new JPanel();
        instruction_panel.setLayout(new GridLayout(11,1));
        home.add(instruction_panel, BorderLayout.CENTER);
        final JLabel ins1 = new JLabel("Instructions:");
        final JLabel ins2 = new JLabel("This is a game in which you control a snake "
                + "which eats food and grows longer.");
        final JLabel ins3 = new JLabel("You win by eating the most food "
                + "and achieving a high score.");
        final JLabel ins4 = new JLabel("If you come into contact with your own body or the "
                + "bounding box, you lose.");
        final JLabel insc = new JLabel("Controls: ");
        final JLabel ins5 = new JLabel("Use the arrow keys to move the snake body.");
        final JLabel ins6 = new JLabel("Pressing left or right while going right or left will not "
                + "change your direction.");
        final JLabel ins7 = new JLabel("The same applies for up and down.");
        final JLabel ins8 = new JLabel("Use escape to pause the game.");
        final JLabel ins9 = new JLabel("Best of Luck!");
        
        final JPanel name_panel = new JPanel();
        name_panel.setLayout(new GridLayout(1,2));
        JTextField t = new JTextField(16);
        final JLabel insn = new JLabel("Enter your Name");
        name_panel.add(insn);
        name_panel.add(t);
        
        instruction_panel.add(ins1);
        instruction_panel.add(ins2);
        instruction_panel.add(ins3);
        instruction_panel.add(ins4);
        instruction_panel.add(insc);
        instruction_panel.add(ins5);
        instruction_panel.add(ins6);
        instruction_panel.add(ins7);
        instruction_panel.add(ins8);
        instruction_panel.add(ins9);
        instruction_panel.add(name_panel);
        final JButton load = new JButton("Load Game");
        final JButton highscores = new JButton("High Scores");
        highscores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (scores != null && scores.length > 0) {
                    high.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No Games Have Been Played",
                            "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        final JButton play = new JButton("Play");
        final JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        t.setFocusable(true);
        t.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    play.doClick();
                } 
            } 
            });

        home_panel.add(load);
        home_panel.add(highscores);
        home_panel.add(play);
        home_panel.add(exit);
        
        // Game Window Code    
        
        final JFrame frame = new JFrame("Snake");   
        frame.setLocation(200, 200);

        // Status panel
        final JPanel status_panel = new JPanel();
        status_panel.setLayout(new GridLayout(1,3));
        frame.add(status_panel, BorderLayout.NORTH);
        final JLabel name = new JLabel("Name");
        status_panel.add(name);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        final JLabel score = new JLabel("Score: ");
        status_panel.add(score);
        
        // Main playing area
        final GameCourt court = new GameCourt(status, score, name, 3);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        control_panel.setLayout(new GridLayout(1,3));
        frame.add(control_panel, BorderLayout.SOUTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
                court.setHighScore(Integer.parseInt(scores[0][1]));
            }
        });
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Writer a = new Writer(court);
                a.gameWriter();
                JOptionPane.showMessageDialog(null, "Game has been Saved");
                
            }
        });
        PAUSE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.setPlaying(false);
                String[] possibleValues = { "Resume", "Reset", "Return to Main Menu", 
                    "Save and Exit", "Exit"};
                String selectedValue = (String) JOptionPane.showInputDialog(null,
                        "Options", "GamePause",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        possibleValues, possibleValues[0]);
                if (selectedValue.equals("Resume")) {
                    court.setPlaying(true);
                    court.setFocus();
                } else if (selectedValue.equals("Return to Main Menu")) {
                    String a = court.getName();
                    int v = court.getscore();
                    Writer w = new Writer(a, v);
                    w.scoreWriter();
                    frame.dispose();
                    run();                  
                } else if (selectedValue.equals("Save and Exit")) {
                    save.doClick();
                    System.exit(0);
                } else if (selectedValue.equals("Reset")) {
                    court.reset();
                    court.setHighScore(Integer.parseInt(scores[0][1]));
                } else {
                    System.exit(0);
                }

            }
        });

        control_panel.add(reset);
        control_panel.add(PAUSE);
        control_panel.add(save);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Play button to put the frame on the screen
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((t.getText() == null) || (t.getText().length() == 0) || 
                        (t.getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "No Name Entered",
                                "Error", JOptionPane.ERROR_MESSAGE);
                }   else if (t.getText().matches("^[a-zA-Z0-9 ]*$") && t.getText().length() > 0) {
                    home.dispose();
                    frame.setVisible(true);
                    name.setText(t.getText());
                    if (scores != null && scores.length > 0) {
                        court.setHighScore(Integer.parseInt(scores[0][1]));
                    } else {
                        court.setHighScore(0);
                    }
                    court.reset();                       
                }   else {
                    JOptionPane.showMessageDialog(null, "Name must contain only alphanumeric "
                            + "characters and spaces",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        
                    }
                }

            });
        // Load button to load the saved game
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Reader c = new Reader(false);
                if (c.getCheck()) {
                    home.dispose();
                    c.snake();
                    c.food();
                    List<Snakeunit> a = c.getSnake();
                    int[][] f = c.getFood();
                    c.highScore();
                    String name = c.getName();
                    int val = c.getScore();
                    Snakeunit sn = c.getHead();
                    court.setSnake(a);
                    court.setScore(val);
                    court.setName(name);
                    court.setFood(f);
                    court.setHead(sn);
                    court.setHighScore(Integer.parseInt(scores[0][1]));
                    frame.setVisible(true);
                    court.setPlaying(true);
                    court.setFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "No Game Saved",
                            "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int r = JOptionPane.showInternalConfirmDialog(null,
                        "Would you like to save the game before Closing?", "Alert",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    save.doClick();
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {         
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {                
            }

        });

    }


    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}