package ned.com.scripts.Negility.API;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.*;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.path.Path;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import static org.rspeer.runetek.api.input.menu.ActionOpcodes.INTERFACE_ACTION;


public class Traveler extends Task {

    private Position Falador = new Position(3033, 3340, 0);
    private Position Varrock = new Position(3223, 3413, 0);
    private Position Draynor = new Position(3109, 3263, 0);
    private Position Seer = new Position(2730, 3485, 0);
    private Position Gnome = new Position(2474, 3436, 0);
    private Position GnomeGateDone = new Position(2461, 3385, 0);
    private int GnomeDoor = 190;
    private int[] PassageCharges = {21155, 21153, 21151, 21150, 21149, 21146};
    private int[] GloryCharges = {1706, 1707, 1708, 1709, 1710, 1711, 1712, 11978, 11977};
    private boolean hasVarrockTab;
    private boolean hasFaladorTab;
    private boolean hasCamelotTab;
    private boolean hasGlory;
    private boolean hasPassage;
    private String RealCourse;



    @Override
    public int execute() {
        CheckTeleportOptions();
        CheckDistance();
        return 200;
    }


    private void CheckTeleportOptions() {
        RealCourse = Negility.CourseToDo;
        hasFaladorTab = Inventory.contains("Falador teleport");
        hasVarrockTab = Inventory.contains("Varrock teleport");
        hasCamelotTab = Inventory.contains("Camelot teleport");
        hasPassage = Inventory.contains(PassageCharges) || Equipment.contains(PassageCharges);
        hasGlory = Inventory.contains(GloryCharges) || Equipment.contains(GloryCharges);
    }

    private void homeTP() {
        if (Magic.canCast(Spell.Modern.HOME_TELEPORT)) {
            Magic.cast(Spell.Modern.HOME_TELEPORT);
            Time.sleep(1000, 1500);
            Time.sleepUntil(() -> Players.getLocal().getAnimation() == -1, 15000);
        } else {
            Negility.Close = true;
        }
    }

    private void canMove(Position pos, String where) {
        if (!Players.getLocal().isMoving() && pos.distance() > 50) {
            Path toDestination = Movement.buildPath(pos);
            if (toDestination != null) {
                toDestination.walk();
                Time.sleepUntil(() -> Players.getLocal().isMoving(), 2500);
            } else {
                Log.info(where + " can't be reached from here. Home TP time.");
                homeTP();
            }
        }
    }

    private void useTeleport(String tpName) {
        Item boolTP = Inventory.getFirst(tpName);
        if (boolTP != null) {
            boolTP.interact(a -> true);
            Time.sleep(1000, 1200);
            Time.sleepUntil(() -> Players.getLocal().getAnimation() == -1, 5000);
        }
    }


    private void shouldTP() {
        // Varrock
        if (RealCourse.equals("Varrock")) {
            if (Varrock.distance() > 150 && hasVarrockTab) {
                useTeleport("Varrock teleport");
            }
        }
        // Draynor
        if (RealCourse.equals("Draynor")) {
            if (Draynor.distance() > 150 && hasGlory) {
                Item Glory = Inventory.getFirst(GloryCharges);
                if (Glory != null) {
                    Glory.interact("Wear");
                    Time.sleep(1500, 2000);
                }
                if (hasGlory) {
                    Equipment.interact(EquipmentSlot.NECK.getItemName(), INTERFACE_ACTION, 3);
                    Time.sleep(1000, 1200);
                    Time.sleepUntil(() -> Players.getLocal().getAnimation() == -1, 5000);
                }
            }
        }
        //Gnome
        if (RealCourse.equals("Gnome")) {
            if (Gnome.distance() > 150 && hasPassage) {
                Item Pass = Inventory.getFirst(PassageCharges);
                if (Pass != null) {
                    Pass.interact("Wear");
                    Time.sleep(1500, 2000);
                }
                if (hasPassage) {
                    Equipment.interact(EquipmentSlot.NECK.getItemName(), INTERFACE_ACTION, 2);
                    Time.sleep(1000, 1200);
                    Time.sleepUntil(() -> Players.getLocal().getAnimation() == -1, 5000);
                }
            }
        }
        // Falador
        if (RealCourse.equals("Falador")) {
            if (Falador.distance() > 150 && hasFaladorTab) {
                useTeleport("Falador teleport");
            }
        }
        // Seer
        if (RealCourse.equals("Seer")) {
            if (Seer.distance() > 150 && hasCamelotTab) {
                useTeleport("Camelot teleport");
            }
        }
    }


    private void CheckDistance() {
        if (RealCourse.equals("Varrock")) {
            shouldTP();
            canMove(Varrock, "Varrock");
        }

        if (RealCourse.equals("Draynor")) {
            shouldTP();
            canMove(Draynor, "Draynor");
        }
        if (RealCourse.equals("Falador")) {
            shouldTP();
            canMove(Falador, "Falador");
        }
        if (RealCourse.equals("Seer")) {
            shouldTP();
            canMove(Seer, "Seer");
        }
        if(RealCourse.equals("Gnome")){
            shouldTP();
            SceneObject gnomegate = SceneObjects.getNearest(GnomeDoor);
            if(gnomegate != null && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1 && GnomeGateDone.distance() > 3){
                gnomegate.interact(a-> true);
                Time.sleepUntil(() -> Players.getLocal().isMoving(),5000);
            }else if(Interfaces.getFirst(i -> i.getText().contains("Okay then.")) != null){
                Interfaces.getFirst(i -> i.getText().contains("Okay then.")).interact("Continue");
            }
            canMove(Gnome,"Gnome");
        }
    }

    @Override
    public boolean validate() {
        if (Negility.guiDone && Negility.Chosen && Negility.CourseToDo.equals("Falador") && Falador.distance() > 40) {
            return true;
        } else if (Negility.guiDone && Negility.Chosen && Negility.CourseToDo.equals("Varrock") && Varrock.distance() > 40) {
            return true;
        } else if (Negility.guiDone && Negility.Chosen && Negility.CourseToDo.equals("Draynor") && Draynor.distance() > 50) {
            return true;
        } else if (Negility.guiDone && Negility.Chosen && Negility.CourseToDo.equals("Seer") && Seer.distance() > 80) {
            return true;
        }else if (Negility.guiDone && Negility.Chosen && Negility.CourseToDo.equals("Gnome") && Gnome.distance() > 40) {
            return true;
        }


        Negility.Nearby = true;
        return false;
    }

}
