import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GuiTest {
    Gui gui;
    String samplePopulatedGrid = "" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n" +
            "|G|G|G|G|G|G|G|G|G|G|\n" +
            "---------------------\n";

    @BeforeEach
    void setUp() {
        gui = new Gui();
    }

    //tests method that converts a land grid as a string to a JPanel representation
    @Test
    void landGridToGuiGrid() {
        //test jpanel output is and empty land grid
        JPanel testPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        for (int i = 0; i < 100; i++) {
            JLabel testLabel = new JLabel(" ");
            testPanel.add(testLabel);
        }
        JPanel actual = gui.landGridToGuiGrid(Land.initLandGrid);
        assertEquals(testPanel.getUI(), actual.getUI(), "blank landGridToGuiGrid Failed");

        //tests jpanel output is filled with goblins
        testPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        ImageIcon testGoblinImg = gui.readImageFiles("C:\\GenSpark-Projects\\project 8 HumansVsGoblins GUI\\src\\goblinPic.png");
        for (int i = 0; i < 100; i++) {
            JLabel testLabel = new JLabel(testGoblinImg);
            testPanel.add(testLabel);
        }
        actual = gui.landGridToGuiGrid(samplePopulatedGrid);
        assertEquals(testPanel.getUI(), actual.getUI(), "goblin filled landGridToGuiGrid Failed");
    }

    //tests altering the display of the text panel on the JFrame
    @Test
    void replaceTextPanel() {
        //tests replacing textarea in jpanel
        JPanel expected = new JPanel();
        JTextArea expectedText = new JTextArea("testReplacement");
        expected.add(expectedText);
        JPanel actual = gui.replaceTextPanel("testReplacement", false);
        assertEquals(expected.getUI(), actual.getUI(), "replaceTextPanel append=false Failed");

        //tests appending to textarea in jpanel
        expected = new JPanel();
        expectedText = new JTextArea("testReplacement\ntestReplacement2");
        expected.add(expectedText);
        actual = gui.replaceTextPanel("\ntestReplacement2", true);
        assertEquals(expected.getUI(), actual.getUI(), "replaceTextPanel append=false Failed");
    }


}