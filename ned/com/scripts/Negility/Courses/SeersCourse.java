package ned.com.scripts.Negility.Courses;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;

public class SeersCourse extends Course {
    // 6 total
    private boolean Start = false;
    private boolean First = false;
    private boolean Second = false;
    private boolean Third = false;
    private boolean Fourth = false;
    private boolean End = false;

    //Climb-up
    private int StartObstacle = 11373;
    //Jump
    private int FirstObstacle = 11374;
    //Cross
    private int SecondObstacle = 11378;
    //Jump
    private int ThirdObstacle = 11375;
    //Jump
    private int FourthObstacle = 11376;
    //Jump
    private int EndObstacle = 11377;

    private Position FourthSpot = new Position(2702, 3471, 3);

    private void getPos() {
        Start = Check(StartObstacle, "Climb-up");
        First = Check(FirstObstacle, "Jump");
        Second = Check(SecondObstacle, "Cross");
        Third = Check(ThirdObstacle, "Jump");
        Fourth = Check(FourthObstacle, FourthSpot, "Jump");
        End = Check(EndObstacle, "Jump");
    }


    private void Traverse() {
        Negility.status = "Traversing Seers Course";
        if (!isMove()) {
            if (Start) {
                Interact(StartObstacle);
            } else if (First) {
                Interact(FirstObstacle);
            } else if (Second) {
                Interact(SecondObstacle);
            } else if (Third) {
                Interact(ThirdObstacle);
            } else if (Fourth) {
                Interact(FourthObstacle);
            } else if (End) {
                Interact(EndObstacle);
            } else {
                farWalk(Seer);
            }
        }
    }

    @Override
    public boolean validate() {
        if (Negility.Eat.equals("None") && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1 && Seer.distance() < 80) { // Need to check a position near each course
            return true;
        } else if (!Negility.Eat.equals("None")) {
            return Inventory.contains(Negility.Eat) && Seer.distance() < 80;
        }
        return false;
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
