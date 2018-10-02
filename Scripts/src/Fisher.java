import org.osbot.rs07.api.model.NPC;

import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;



import java.awt.Color;

import java.awt.Dimension;

import java.awt.Toolkit;



import javax.swing.JButton;

import javax.swing.JComboBox;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPanel;



@ScriptManifest(author = "Zeroter5", info = "Fishes any spot.", logo = "", name = "Fisher", version = 0)



public class Fisher extends Script {

private JFrame gui;

private boolean started = false;

String fishType = "tool";


private enum State {

FISH,DROP,WAIT

}

private State getState() {



if (!myPlayer().isAnimating() && inventory.getEmptySlotCount() != 0)

return State.FISH;

else if (inventory.getEmptySlotCount() == 0)

return State.DROP; 

else

return State.WAIT;


}



public int onLoop() throws InterruptedException {//main body of program

if (started == true) {
switch(getState()) {// set actions based on state

case FISH: 

log("fish");
NPC fish = npcs.closest("Fishing spot");
 if (fishType == "Net") {


fish.interact("Net");

}

else if (fishType == "Lure") {



fish.interact("Lure");

}

else if (fishType == "Harpoon") {



fish.interact("Harpoon");

}

else if (fishType == "Cage") {



fish.interact("Cage");

}

else if (fishType == "Bait") {



fish.interact("Bait");

}



break;

case WAIT:


log("wait");

sleep(random(900,1200));

break;

case DROP:

log("drop");

if (fishType == "Net") {

inventory.dropAllExcept("Small fishing net");

sleep(random(200,300));

}

else if (fishType == "Lure") {

inventory.dropAllExcept("Fly fishing rod","Feather");

sleep(random(200,300));

}

else if (fishType == "Harpoon") {

inventory.dropAllExcept("Harpoon");

sleep(random(200,300));

}

else if (fishType == "Cage") {

inventory.dropAllExcept("Lobster pot");

sleep(random(200,300));

}

else if (fishType == "Bait") {

inventory.dropAllExcept("fishing rod","Bait");

sleep(random(200,300));

}


}


}
return random(2000,2500);

}

private void createGUI(){



final int guiWidth = 350, guiHieght = 75;


Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


// Calculating x and y coordinates

        final int gX = (int) (screenSize.getWidth() / 2) - (guiWidth / 2);

        final int gY = (int) (screenSize.getHeight() / 2) - (guiHieght / 2);



        // create a new JFrame with the title "GUI"

        gui = new JFrame("GUI");

       

        // set the x coordinate, y coordinate, width and height of the GUI

        gui.setBounds(gX, gY, guiWidth, guiHieght);

gui.setResizable(false);

// Create a sub container JPanel

JPanel panel = new JPanel();

gui.add(panel);

 

JLabel label = new JLabel("Select a fishing method"); // create a label

label.setForeground(Color.white);

panel.add(label); // add it to the JPanel 

       


// create a select box for food options

        JComboBox<String> methodSelector = new JComboBox<>(new String[]{"Net","Lure","Cage","Harpoon"});



        // add an action listener, to listen for user's selections, assign to a variable called selectedFood on selection.

        methodSelector.addActionListener(e -> fishType = methodSelector.getSelectedItem().toString());



        // add the select box to the JPanel

        panel.add(methodSelector);

        

        JButton startButton = new JButton("Start");

// add an action listener to the button

startButton.addActionListener(e -> { // This code is called when the user clicks the button

    started  = true; // Set the boolean variable started to true

    gui.setVisible(false); // Hide the GUI

});

panel.add(startButton); // Add the button to the panel


gui.setVisible(true); //makes GUI visible

}

public void onStart() throws InterruptedException {

    createGUI();

log("Fisher Started. 1");

}

public void onExit() {

if(gui != null) { // If the JFrame has been created



    gui.setVisible(false); // Hide GUI

    gui.dispose(); // Dispose

} 

log("Fisher Ended.");

}



}