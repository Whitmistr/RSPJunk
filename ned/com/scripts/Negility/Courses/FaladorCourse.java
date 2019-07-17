package ned.com.scripts.Negility.Courses;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;

public class FaladorCourse extends Course {

    // 13 total
    private boolean Start = false;
    private boolean First = false;
    private boolean Second = false;
    private boolean Third = false;
    private boolean Fourth = false;
    private boolean Fifth = false;
    private boolean Sixth = false;
    private boolean Seventh = false;
    private boolean Eighth = false;
    private boolean Ninth = false;
    private boolean Tenth = false;
    private boolean Eleventh = false;
    private boolean End = false;

    //Climb
    private int StartObstacle = 10833;
    //Cross
    private int FirstObstacle = 10834;
    //Cross
    private int SecondObstacle = 10836;
    //Jump
    private int ThirdObstacle = 11161;
    //Jump
    private int FourthObstacle = 11360;
    //Cross
    private int FifthObstacle = 11361;
    //Cross
    private int SixthObstacle = 11364;
    //Jump
    private int SeventhObstacle = 11365;
    //Jump
    private int EighthObstacle = 11366;
    //Jump
    private int NinthObstacle = 11367;
    //Jump
    private int TenthObstacle = 11368;
    //Jump
    private int EleventhObstacle = 11370;
    //Jump
    private int EndObstacle = 11371;


    private Position ThirdSpot = new Position(3049, 3357, 3);
    private Position FourthSpot = new Position(3045, 3361, 3);
    private Position FifthSpot = new Position(3035, 3361, 3);
    private Position SixthSpot = new Position(3026, 3352, 3);
    private Position EighthSpot = new Position(3017, 3345, 3);
    private Position NinthSpot = new Position(3012, 3344, 3);
    private Position TenthSpot = new Position(3012, 3336, 3);
    private Position EleventhSpot = new Position(3017, 3333, 3);
    private Position EndSpot = new Position(3023, 3333, 3);

    private void getPos() {
        Start = Check(StartObstacle, "Climb");
        First = Check(FirstObstacle, "Cross");
        Second = Check(SecondObstacle, "Cross");
        Third = Check(ThirdObstacle, ThirdSpot, "Jump");
        Fourth = Check(FourthObstacle, FourthSpot, "Jump");
        Fifth = Check(FifthObstacle, FifthSpot, "Cross");
        Sixth = Check(SixthObstacle, SixthSpot, "Cross");
        Seventh = Check(SeventhObstacle, "Jump");
        Eighth = Check(EighthObstacle, EighthSpot, "Jump");
        Ninth = Check(NinthObstacle, NinthSpot, "Jump");
        Tenth = Check(TenthObstacle, TenthSpot, "Jump");
        Eleventh = Check(EleventhObstacle, EleventhSpot, "Jump");
        End = Check(EndObstacle, EndSpot, "Jump");
    }

    private void Traverse() {
        Negility.status = "Traversing Falador Course";
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
            } else if (Eighth) {
                Interact(EighthObstacle);
            } else if (Ninth) {
                Interact(NinthObstacle);
            } else if (Tenth) {
                Interact(TenthObstacle);
            } else if (Eleventh) {
                Interact(EleventhObstacle);
            } else if (End) {
                Interact(EndObstacle);
            } else {
                farWalk(Falador);
            }

        }
    }

    @Override
    public boolean validate() {
        if (Negility.Eat.equals("None") && !Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1 && Falador.distance() < 40) {
            return true;
        } else if (!Negility.Eat.equals("None")) {
            return Inventory.contains(Negility.Eat) && Falador.distance() < 40;
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
