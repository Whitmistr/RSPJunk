package ned.com.scripts.Negility.Courses;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;


public class DraynorCourse extends Course {
    // 6 total
    private boolean Start = false;
    private boolean First = false;
    private boolean Second = false;
    private boolean Third = false;
    private boolean Fourth = false;
    private boolean Fifth = false;
    private boolean End = false;

    private int StartObstacle = 10073;
    private int FirstObstacle = 10074;
    private int SecondObstacle = 10075;
    private int ThirdObstacle = 10077;
    private int FourthObstacle = 10084;
    private int FifthObstacle = 10085;
    private int EndObstacle = 10086;


    private Position FourthSpot = new Position(3088, 3258, 3);

    private void getPos() {
        Start = Check(StartObstacle, "Climb");
        First = Check(FirstObstacle, "Cross");
        Second = Check(SecondObstacle, "Cross");
        Third = Check(ThirdObstacle, "Balance");
        Fourth = Check(FourthObstacle, FourthSpot, "Jump-up");
        Fifth = Check(FifthObstacle, "Jump");
        End = Check(EndObstacle, "Climb-down");
    }

    private void Traverse() {
        Negility.status = "Traversing Draynor Course";
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
            } else if (Fifth) {
                Interact(FifthObstacle);
            } else if (End) {
                Interact(EndObstacle);
            } else {
                farWalk(Draynor);
            }
        }
    }

    @Override
    public boolean validate() {
        if (Negility.Eat.equals("None") && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1 && Draynor.distance() < 50) {
            return true;
        } else if (!Negility.Eat.equals("None")) {
            return Inventory.contains(Negility.Eat) && Draynor.distance() < 50;
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
