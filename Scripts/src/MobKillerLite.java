import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;

import org.osbot.rs07.api.model.NPC;

import org.osbot.rs07.api.ui.RS2Widget;



@ScriptManifest(author = "Ryan Gould", info = "Kills NPCs", logo = "", name = "MobKillerLITE", version = 0)



public class MobKillerLite extends Script {

String mobName;


private enum State { //creates a new type called State



ATTACK, WAIT,TELEPORT

}

private State getState() { //returns the correct state based on conditions

NPC mob = npcs.closest(mobName);

if (myPlayer().getHealthPercent() <= 10 && inventory.contains("Varrock teleport"))  {

return State.TELEPORT; //returns teleport state

}

else if (!myPlayer().isUnderAttack() && mob.isAttackable() && mob != null) { //checks if health is below 80% and if the inventory is empty

return State.ATTACK; //returns eat state

}

else{

return State.WAIT;

}


}

@Override

public int onLoop() throws InterruptedException {//main body of program

 

switch(getState()) {// set actions based on state


case TELEPORT:

RS2Widget door = getWidgets().get(548, 33);

RS2Widget logout = getWidgets().get(182, 10);

log("Teleport");

inventory.getItem("Varrock teleport").interact("Cast");

sleep(random(500,1000));

door.interact("Logout");

sleep(random(500,1000));

logout.interact("Logout");

      break;

       

case ATTACK:

log("attack");

NPC mob = npcs.closest(mobName); //sets closest goblin as an NPC object called goblin

        if ((mob != null) && (mob.isAttackable() && //checks if goblin exists and is attackable and visible

        !mob.isUnderAttack() && mob.isVisible())) {

        mob.interact("Attack");//selects the NPC interaction option attack

        }

        sleep(random(1000,1500));

break;


case WAIT:

log("wait");

sleep(random(1000,1500)); //sleep

break;


}



return random(100,200);

}

@Override

public void onStart() throws InterruptedException {//actions done on script start



mobName = JOptionPane.showInputDialog("Enter Mob Name");



    do{

    sleep(random(500,1000));

    }while(mobName == null); 

log("MobKiller Started.");

}

public void onExit() {//actions done on script exit



log("MobKiller Ended.");

}



}