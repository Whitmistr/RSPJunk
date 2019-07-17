package ned.com.scripts.Negility.Courses;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.path.Path;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;


public class Course extends Task {
    Position Draynor = new Position(3105, 3279, 0);
    Position Falador = new Position(3033, 3340, 0);
    Position Varrock = new Position(3223, 3413, 0);
    Position Seer = new Position(2730, 3485, 0);
    Position Gnome = new Position(2474, 3436, 0);

    int overallDelay = 800;

    boolean WantGrace() {
        Pickable graceMark = Pickables.getNearest("Mark of grace");
        if (graceMark != null && graceMark.isPositionInteractable()){
            return graceMark.isPositionInteractable();
        }
        return false;
    }

    void getGrace() {
        Pickable grace = Pickables.getNearest("Mark of grace");
        grace.interact("Take");
        Time.sleepUntil(this::isMove, Random.mid(400, 600));
    }

    void Interact(int ObjID) {
        SceneObject ract = SceneObjects.getNearest(ObjID);
        if (ract != null && ract.isPositionInteractable()) {
            SceneObjects.getNearest(ObjID).interact(a -> true);
            Time.sleepUntil(() -> Players.getLocal().isMoving(), Random.mid(300, 500));
        }
    }

    void InteractIgnore(int ObjID) {
        SceneObject ract = SceneObjects.getNearest(ObjID);
        if (ract != null) {
            SceneObjects.getNearest(ObjID).interact(a -> true);
            Time.sleepUntil(() -> Players.getLocal().isMoving(), Random.mid(300, 500));
        }
    }

    boolean isMove() {
        return Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1;
    }

    void farWalk(Position pos) {
        if (!isMove()) {
            Path toDest = Movement.buildPath(pos);
            if (toDest != null) {
                toDest.walk();
                Time.sleepUntil(() -> Players.getLocal().isMoving(), Random.mid(400, 600));
            }
        }
    }


    boolean Check(int ObjecID, String a) {
        SceneObject Work = SceneObjects.getNearest(ObjecID);
        return Work != null && Work.isPositionInteractable() && Work.containsAction(a);
    }

    boolean Check(int ObjecID, Position Pos, String a) {
        SceneObject Work = SceneObjects.getNearest(ObjecID);
        return Work != null && Pos.isPositionWalkable() && Work.containsAction(a);
    }

    boolean CheckSpecial(int ObjecID, String a){
        SceneObject WorkSpecial = SceneObjects.getNearest(ObjecID);
        return WorkSpecial != null && WorkSpecial.isPositionInteractable() && WorkSpecial.containsAction(a) && WorkSpecial.distance() < 9 && WorkSpecial.distance() > 2;
    }

    boolean CheckSpecial(int ObjecID, Position Pos ,String a){
        SceneObject WorkSpecial = SceneObjects.getNearest(ObjecID);
        return WorkSpecial != null && Pos.isPositionWalkable() && WorkSpecial.containsAction(a) && WorkSpecial.distance() < 9 && WorkSpecial.distance() > 11;
    }

    private boolean isContinueUp() {
        return Interfaces.getFirst(i -> i.getText().contains("Click here to continue")) != null;
    }

    private void CheckLevel() {
        if (isContinueUp()) {
            Interfaces.getContinue();
        }
    }

    private void ProgressiveSwitcher(){
        int CharLVL = Skills.getCurrentLevel(Skill.AGILITY);
        if(Negility.Progressive){
            if(CharLVL < 10 && !Negility.CourseToDo.equals("Gnome")){
                Log.fine("Doing Gnome Course.");
                Negility.CourseToDo = "Gnome";
            }else if(CharLVL >= 10 && CharLVL < 30 && !Negility.CourseToDo.equals("Draynor")){
                Log.fine("Doing Draynor Course.");
                Negility.CourseToDo = "Draynor";
            }else if(CharLVL >= 30 && CharLVL < 50 && !Negility.CourseToDo.equals("Varrock")){
                Log.fine("Doing Varrock Course.");
                Negility.CourseToDo = "Varrock";
            }else if(CharLVL >= 50 && CharLVL < 60 && !Negility.CourseToDo.equals("Falador")){
                Log.fine("Doing Falador Course.");
                Negility.CourseToDo = "Falador";
            }else if(CharLVL >= 60 && !Negility.CourseToDo.equals("Seer")){
                Log.fine("Doing Seer Course.");
                Negility.CourseToDo = "Seer";
            }
        }
    }


    @Override
    public int execute() {
        ProgressiveSwitcher();
        CheckLevel();
        return overallDelay;
    }

    @Override
    public boolean validate() {
        return true;
    }
}

