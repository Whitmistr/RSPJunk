package ned.com.scripts.Negility.Courses;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;

public class VarrockCourse extends Course {
    // 8 total
    private boolean Start = false;
    private boolean First = false;
    private boolean Second = false;
    private boolean Third = false;
    private boolean Fourth = false;
    private boolean Fifth = false;
    private boolean Sixth = false;
    private boolean Seventh = false;
    private boolean End = false;

    private int StartObstacle = 10586;

    // is reachable
    private int FirstObstacle = 10587;
    //melee
    private int SecondObstacle = 10642;
    private int ThirdObstacle = 10777;
    private int FourthObstacle = 10778;
    private int FifthObstacle = 10779;
    //melee
    private int SixthObstacle = 10780;
    private int SeventhObstacle = 10781;
    private int EndObstacle = 10817;


    private Position SecondSpot = new Position(3201, 3416, 3);
    private Position ThirdSpot = new Position(3194, 3416, 1);
    private Position FourthSpot = new Position(3194, 3404, 3);
    private Position SixthSpot = new Position(3232, 3402, 3);
    private Position SeventhSpot = new Position(3238, 3408, 3);
    private Position FifthSpot = new Position(3198, 3396, 3);


    private void getPos() {
        Start = Check(StartObstacle, "Climb");
        First = Check(FirstObstacle, "Cross");
        Second = Check(SecondObstacle, SecondSpot, "Leap");
        Third = Check(ThirdObstacle, ThirdSpot, "Balance");
        Fourth = Check(FourthObstacle, FourthSpot, "Leap");
        Fifth = Check(FifthObstacle, FifthSpot, "Leap");
        Sixth = Check(SixthObstacle, SixthSpot, "Leap");
        Seventh = Check(SeventhObstacle, SeventhSpot, "Hurdle");
        End = Check(EndObstacle, "Jump-off");
    }


    private void Traverse() {
        Negility.status = "Traversing Varrock Course";
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
            } else if (Sixth) {
                Interact(SixthObstacle);
            } else if (Seventh) {
                Interact(SeventhObstacle);
            } else if (End) {
                Interact(EndObstacle);
            } else {
                farWalk(Varrock);
            }

        }
    }

    @Override
    public boolean validate() {
        if (Negility.Eat.equals("None") && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1 && Varrock.distance() < 40) {
            return true;
        } else if (!Negility.Eat.equals("None")) {
            return Inventory.contains(Negility.Eat) && Varrock.distance() < 40;
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