import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;

import java.awt.*;

import org.osbot.rs07.api.model.NPC;





@ScriptManifest(author = "RyanGould", info = "Kills Cows and Eats Food at Lumbridge", logo = "", name = "CowSlayer", version = 0)



public class CowSlayer extends Script {

private JFrame gui;

private boolean started = false;

String selectedFood = "Food";




private enum State { //creates a new type called State



ATTACK, WAIT,

}

private State getState() { //returns the correct state based on conditions

NPC cow = npcs.closest("Cow", "Cow calf");



if (!myPlayer().isUnderAttack() && cow.isAttackable() && cow != null) { //checks if health is below 80% and the inventory is empty

return State.ATTACK; //returns eat state

}

else{

return State.WAIT;

}


}

@Override

public int onLoop() throws InterruptedException {//main body of program



switch(getState()) {// set actions based on state



       

case ATTACK:

log("attack");

NPC cow = npcs.closest("Cow","Cow calf"); //locates nearest cow

cow.interact("Attack");

        sleep(random(400,500));

log("attack");

break;

       

case WAIT:

log("wait");

sleep(random(300,400)); //sleep

break;


}




return random(100,200);

}

@Override

public void onStart() {//actions done on script start


log("CowKiller Started.");

}

public void onExit() {//actions done on script exit


log("CowKiller Ended.");

}

}
