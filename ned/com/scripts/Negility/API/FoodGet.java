package ned.com.scripts.Negility.API;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;


public class FoodGet extends Task {
    private Position bankPos = BankLocation.getNearestBooth().getPosition();
    private boolean isMove;

    @Override
    public boolean validate() {
        return Negility.guiDone && Negility.Chosen && !Negility.Eat.equals("None") && !Inventory.contains(Negility.Eat);
    }

    @Override
    public int execute() {
        Negility.status = "Getting food.";
        isMove = Players.getLocal().isMoving();
        if (bankPos.distance() > 5) {
            MoveBank();
        } else {
            openWithdraw();
        }
        return 500;
    }

    private void openWithdraw() {
        isMove = Players.getLocal().isMoving();
        if (!isMove && !Bank.isOpen()) {
            Bank.open();
        } else if (Bank.isOpen() && !Inventory.contains(Negility.Eat) && Bank.contains(Negility.Eat)) {
            Bank.withdraw(Negility.Eat, 20);
            Time.sleepUntil(() -> Inventory.contains(Negility.Eat), 2000);
        } else if (Bank.isOpen() && !Bank.contains(Negility.Eat)) {
            Log.info("Bank does not contain food.");
            Game.logout();
            Negility.Close = true;
        }
    }

    private void MoveBank() {
        isMove = Players.getLocal().isMoving();
        if (!isMove) {
            if (Movement.buildPath(bankPos) != null) {
                Movement.walkTo(bankPos);
                Time.sleepUntil(() -> Players.getLocal().isMoving(), 1500);
            } else {
                Log.severe("Can't reach bank from here.");
                Negility.Close = true;
            }
        }
    }
}
