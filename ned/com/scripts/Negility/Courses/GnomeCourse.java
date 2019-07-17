package ned.com.scripts.Negility.Courses;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;


public class GnomeCourse extends Course {
    // 6 total
    private boolean Start = false;
    private boolean First = false;
    private boolean Second = false;
    private boolean Third = false;
    private boolean Fourth = false;
    private boolean Fifth = false;
    private boolean End = false;

    private int StartObstacle = 23145;
    private int FirstObstacle = 23134;
    private int SecondObstacle = 23559;
    private int ThirdObstacle = 23557;
    private int FourthObstacle = 23560;
    private int FifthObstacle = 23135;
    private int EndObstacle = 23138;

    private Position ThirdSpot = new Position(2475,3420,2);
    private Position SecondSpot = new Position(2473,3423,1);

    private void getPos() {
        Start = CheckSpecial(StartObstacle, "Walk-across");
        First = CheckSpecial(FirstObstacle, "Climb-over");
        Second = CheckSpecial(SecondObstacle, "Climb");
        Third = CheckSpecial(ThirdObstacle,ThirdSpot, "Walk-on");
        Fourth = CheckSpecial(FourthObstacle, "Climb-down");
        Fifth = CheckSpecial(FifthObstacle, "Climb-over");
        End = CheckSpecial(EndObstacle, "Squeeze-through");
    }

    private void Traverse() {
        Negility.status = "Traversing Gnome Course";
        if (!isMove()) {
            if (Gnome.distance() <2) {
                InteractIgnore(StartObstacle);
            } else if (First) {
                Interact(FirstObstacle);
            } else if (SecondSpot.distance() <2) {
                Interact(SecondObstacle);
            } else if (Third) {
                Interact(ThirdObstacle);
            } else if (Fourth) {
                Interact(FourthObstacle);
            } else if (Fifth) {
                Interact(FifthObstacle);
            } else if (End) {
                Interact(EndObstacle);
            } else {
                farWalk(Gnome);
            }
        }
    }

    @Override
    public boolean validate() {
        if (Negility.Eat.equals("None") && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1 && Gnome.distance() < 60) {
            return true;
        } else if (!Negility.Eat.equals("None")) {
            return Inventory.contains(Negility.Eat) && Gnome.distance() < 60;
        }
        else{
            return false;
        }
    }

    @Override
    public int execute() {
        if (!WantGrace()) {
            getPos();
            Traverse();
        } else if (WantGrace()) {
            Negility.status = "Fetching Mark";
            getGrace();
        }
        return overallDelay;
    }


}
