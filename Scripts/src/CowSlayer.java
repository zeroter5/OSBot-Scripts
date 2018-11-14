import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import org.osbot.rs07.api.model.NPC;

/*Kills cows at the lumbridge pen and will eat food when at 



@ScriptManifest(author = "RyanGould", info = "Kills Cows and Eats Food at Lumbridge", logo = "", name = "CowSlayer", version = 0)



public class CowSlayer extends Script {

private JFrame gui;

private boolean started = false;

String selectedFood = "Food";




private enum State { //creates a new type called State



ATTACK, WAIT,EAT

}

private State getState() { //returns the correct state based on conditions

NPC cow = npcs.closest("Cow", "Cow calf");



if (!myPlayer().isUnderAttack() && cow.isAttackable() && cow != null) { //Checks if player is able to attack a monster

return State.ATTACK; //returns eat state

}
else if (myPlayer().getHealthPercentage() < 50){
return State.EAT;
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

case EAT:
log("eat");

inventory.getItem("Salmon").interact("Eat");
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
