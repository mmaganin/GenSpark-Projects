import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Gui implements ActionListener {
    private JFrame frame;
    private JPanel inputPanel;
    private JPanel gridPanel;

    private JPanel textPanel;
    private JTextArea gameText;

    private JButton send;
    private JTextField tf;
    private String input;
    private JLabel inputLabel;
    private final Object inputReceived = new Object();
    public static ImageIcon humanIcon;
    public static ImageIcon goblinIcon;
    public static ImageIcon treasureIcon;


    //configures GUI to display land grid, input field, and text field. Gets necessary icons for game
    public Gui() {
        frame = new JFrame("Humans Vs. Goblins!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);

        textPanel = new JPanel();
        gridPanel = new JPanel(new GridLayout(10, 10, 10, 10));

        inputPanel = new JPanel();
        inputLabel = new JLabel("Enter Text");
        tf = new JTextField(10); // accepts upto 10 characters
        send = new JButton("Send");

        send.addActionListener(this);
        inputPanel.add(inputLabel); // Components Added using Flow Layout
        inputPanel.add(tf);
        inputPanel.add(send);

        input = "";
        gameText = new JTextArea("");

        if(humanIcon == null || goblinIcon == null || treasureIcon == null) {
            humanIcon = readImageFiles("C:\\GenSpark-Projects\\project 8 HumansVsGoblins GUI\\src\\humanPic.png");
            goblinIcon = readImageFiles("C:\\GenSpark-Projects\\project 8 HumansVsGoblins GUI\\src\\goblinPic.png");
            treasureIcon = readImageFiles("C:\\GenSpark-Projects\\project 8 HumansVsGoblins GUI\\src\\treasurePic.png");
        }

        frame.getContentPane().add(BorderLayout.NORTH, inputPanel);
        frame.getContentPane().add(gridPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, textPanel);
    }

    //reads image icon files from a path input, returns new, scaled imageicon
    public ImageIcon readImageFiles(String path){
        File file;
        BufferedImage image;
        ImageIcon imageIcon;

        try {
            file = new File(path);
            image = ImageIO.read(file);
            imageIcon = new ImageIcon(image);
        } catch (Exception e) {
            imageIcon = null;
            System.out.println("read human or goblin or treasure image failed");
        }

        if(imageIcon != null) {
            Image scaledImage = imageIcon.getImage();
            scaledImage = scaledImage.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
        }
        return imageIcon;
    }


    public JPanel getGridPanel() {
        return gridPanel;
    }

    public void setGridPanel(JPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    //takes a land grid string, returns a JPanel representation which is added to the JFrame
    public JPanel landGridToGuiGrid(String currLandGrid) {
        GridCoords gridCoords;
        char currChar;

        frame.getContentPane().remove(gridPanel);
        gridPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        StringBuilder box = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            gridCoords = new GridCoords((i % 10) + 1, (i / 10) + 1);

            currChar = currLandGrid.charAt(Land.posOnLandToIndex(gridCoords));
            JLabel gridLabel;
            //gets the proper image icon for the game or ' ' if empty space on grid
            if(currChar == 'G') {
                gridLabel = new JLabel(goblinIcon);
            } else if(currChar == 'H'){
                gridLabel = new JLabel(humanIcon);
            } else if(currChar == 'X'){
                gridLabel = new JLabel(treasureIcon);
            } else {
                gridLabel = new JLabel(" ");
            }


            gridPanel.add(gridLabel);

        }
        frame.getContentPane().add(gridPanel);

        //refreshes the JFrame
        frame.invalidate();
        frame.validate();
        frame.repaint();
        return gridPanel;
    }

    //takes a string to either replace or append to the text panel on the JFrame, returns the new text panel
    public JPanel replaceTextPanel(String newString, boolean append) {
        textPanel.remove(gameText);

        if (append) {
            gameText.append(newString);
        } else {
            gameText = new JTextArea(newString);
        }

        textPanel.add(gameText);

        //refreshes the JFrame
        frame.invalidate();
        frame.validate();
        frame.repaint();
        return textPanel;
    }

    public void makeJFrameVisible() {
        if (!frame.isVisible()) {
            frame.setVisible(true);
        }
    }

    //returns user input from GUI
    //waits for input synchronized on inputReceived, notify() called when input sent
    public String getUserInput() {
        try {
            synchronized (inputReceived) {
                inputReceived.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }

    //upon clicking "Send" in GUI, records user input into input variable, notify()s game to continue execution
    @Override
    public void actionPerformed(ActionEvent ae) {
        String input = tf.getText().trim().toLowerCase();

        if (input.equals("y") || input.equals("n") ||
                input.equals("s") || input.equals("e") || input.equals("w") ||
                input.equals("i") || input.equals("q")) {
            inputLabel.setText("Enter Text");
            this.input = input;
            tf.setText("");
        } else {
            this.input = "";
            inputLabel.setText("Invalid input!");
        }
        try {
            synchronized (inputReceived) {
                inputReceived.notifyAll();
            }
        } catch (Exception e) {
            System.out.println("notify input failed");
        }

        send.requestFocus();
    }
}

