package ned.com.scripts.Negility.API;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.script.task.Task;

public class Hopper extends Task {
    private double ElapseRandom = Random.mid(1.00,1.25);

    @Override
    public int execute() {
        WorldHopper.open();
        if(WorldHopper.isOpen() && Game.isLoggedIn() && !Game.isLoadingRegion() && Game.getState() != Game.STATE_HOPPING_WORLD){
        WorldHopper.randomHopInP2p();
            Time.sleep(1000,2000);
            Time.sleepUntil(() -> Game.getState() != Game.STATE_HOPPING_WORLD,6000);
        }
        Negility.T.reset();
        ElapseRandom = Random.mid(1.00,1.25);
        return 200;
    }

    @Override
    public boolean validate() {
        return Negility.T.getElapsed().toMinutes() >= ElapseRandom;
    }
}
