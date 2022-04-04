import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    public JButton getSend() {
        return send;
    }

    public void setSend(JButton send) {
        this.send = send;
    }

    public JTextField getTf() {
        return tf;
    }

    public void setTf(JTextField tf) {
        this.tf = tf;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public JTextArea getGameText() {
        return gameText;
    }

    public void setGameText(JTextArea gameText) {
        this.gameText = gameText;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public void setInputPanel(JPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public void setGridPanel(JPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public JPanel getTextPanel() {
        return textPanel;
    }

    public void setTextPanel(JPanel textPanel) {
        this.textPanel = textPanel;
    }

    public Object getInputReceived() {
        return inputReceived;
    }

    public JPanel landGridToGuiGrid(String currLandGrid) {
        GridCoords gridCoords;

        frame.getContentPane().remove(gridPanel);
        gridPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        StringBuilder box = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            gridCoords = new GridCoords((i % 10) + 1, (i / 10) + 1);

            JTextArea gridLabel = new JTextArea("" + currLandGrid.charAt(Land.posOnLandToIndex(gridCoords)));
            gridLabel.setBackground(Color.lightGray);
            gridPanel.add(gridLabel);
        }
        frame.getContentPane().add(gridPanel);

        frame.invalidate();
        frame.validate();
        frame.repaint();
        return gridPanel;
    }

    public JPanel replaceTextPanel(String newString, boolean append) {
        textPanel.remove(gameText);

        if (append) {
            gameText.append(newString);
        } else {
            gameText = new JTextArea(newString);
        }

        textPanel.add(gameText);

        frame.invalidate();
        frame.validate();
        frame.repaint();
        return textPanel;
    }

    public void updateJFrame() {
        frame.getContentPane().add(BorderLayout.NORTH, inputPanel);
        frame.getContentPane().add(gridPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, textPanel);

        if (!frame.isVisible()) {
            frame.setVisible(true);
        }
    }

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
    }
}

