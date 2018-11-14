import org.osbot.rs07.api.map.Area;

import org.osbot.rs07.api.map.Position;

import org.osbot.rs07.api.model.NPC;

import org.osbot.rs07.api.model.RS2Object;

import org.osbot.rs07.input.mouse.MiniMapTileDestination;

import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

/* Fishes at Barbarian village and moves the player between fishing spots when one is deplenished of fish*/



@ScriptManifest(author = "Zeroter5", info = "Fishes at barbarian village and banks at edgeville.", logo = " ", name = "Z5-BarbarianFisher", version = 0)



public class BarbarianFisher extends Script {


private Area spot1 = new Area(3109,3434, 3110,3432);

private Area spot2 = new Area(3104,3425, 3103,3424);

private Area bank = new Area(3093,3492, 3094,3488);

private Position spot1Tile =  new Position(3103, 3425, 0);

private Position pathStart =  new Position(3104, 3432, 0);

private Position spot2Tile =  new Position(3109, 3433, 0);

private Position[] pathSpots2Bank = {


    new Position(3100, 3439, 0),

    new Position(3093, 3443, 0),

    new Position(3094, 3448, 0),

    new Position(3093, 3455, 0),

    new Position(3088, 3461, 0),

    new Position(3095, 3464, 0),

    new Position(3099, 3480, 0),

    new Position(3096, 3486, 0),

    new Position(3094, 3491, 0),

};

private Position[] pathBank2Spots = {


    new Position(3097, 3486, 0),

    new Position(3100, 3481, 0),

    new Position(3099, 3474, 0),

    new Position(3098, 3465, 0),

    new Position(3088, 3463, 0),

    new Position(3092, 3448, 0),

    new Position(3101, 3438, 0),

    new Position(3103, 3434, 0),

   

};


private enum State {

FISH,WAIT,SPOTSWAP1,SPOTSWAP2,WALK2BANK,WALK2SPOTS,BANK

}

private State getState() {

NPC fish = npcs.closest("Fishing spot");


if (!myPlayer().isAnimating() && inventory.getEmptySlotCount() != 0 && (spot1.contains(myPlayer()) || spot2.contains(myPlayer())))

return State.FISH;

else if (inventory.getEmptySlotCount() == 0 && (spot1.contains(myPlayer()) || spot2.contains(myPlayer())))

return State.WALK2BANK;

else if (inventory.getEmptySlotCount() == 0 && bank.contains(myPlayer()))

return State.BANK;

else if (inventory.getEmptySlotCount() == 26 && bank.contains(myPlayer()))

return State.WALK2SPOTS;

else if (spot1.contains(fish) == false && !myPlayer().isAnimating() && inventory.getEmptySlotCount() > 0 && spot1.contains(myPlayer()))

return State.SPOTSWAP1;

else if ((spot2.contains(fish) == false && !myPlayer().isAnimating() && inventory.getEmptySlotCount() > 0 && spot2.contains(myPlayer())))

return State.SPOTSWAP2;

else

return State.WAIT;


}



public int onLoop() throws InterruptedException {//main body of program


switch(getState()) {// set actions based on state

case FISH:

NPC fish = npcs.closest("Fishing spot");

fish.interact("Lure");

sleep(random(300,500));

break;

case WAIT:

log("wait");

sleep(random(900,1200));

break;

case SPOTSWAP1:

log("spotswap1");

getWalking().webWalk(spot2Tile);

sleep(random(2000,2500));

break;

case SPOTSWAP2:

log("spotswap2");

getWalking().webWalk(spot1Tile);

sleep(random(2000,2500));

break;

case WALK2BANK:

log("walk2bank");

getWalking().webWalk(pathStart);

traversePath(pathSpots2Bank,false);

sleep(random(300,500));

log("walk2bank");

break;

case BANK:

log("Banking");

RS2Object bankBooth = objects.closest("Bank booth"); //Create an RS2Object looking for "Bank booth"

bankBooth.interact("Bank");

log("depositing");

sleep(random(1000,1100));

getBank().depositAll("Raw salmon","Raw trout");

sleep(random(1000,1100));

getBank().close();

sleep(random(300,500));

break;

case WALK2SPOTS:

log("walk2spots");

traversePath(pathBank2Spots,false);

log("webwalk");

getWalking().webWalk(spot2Tile);

log("walk2spots");

break;

}



return random(300,500);

}


private void traversePath(Position[] path, boolean reversed) throws InterruptedException {

if (!reversed) {

for (int i = 1; i < path.length; i++)

if (!walkTile(path[i]))

i--;

} else {

for (int i = path.length-2; i > 0; i--){

if (!walkTile(path[i]))

i++;

}

}

}

private boolean walkTile(Position p) throws InterruptedException {

mouse.move(new MiniMapTileDestination(bot, p), false);

sleep(random(150, 250));

mouse.click(false);

int failsafe = 0;

while (failsafe < 10 && myPlayer().getPosition().distance(p) > 2) {

sleep(200);

failsafe++;

if (myPlayer().isMoving())

failsafe = 0;

}

if (failsafe == 10)

return false;

return true;

}


public void onStart() throws InterruptedException {

log("Z5-BarbarianFisher Started.");

}

public void onExit() {

log("Z5-BarbarianFisher Ended.");

}

}



