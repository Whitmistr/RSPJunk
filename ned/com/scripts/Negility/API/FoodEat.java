package ned.com.scripts.Negility.API;

import ned.com.scripts.Negility.Negility;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class FoodEat extends Task {
    private int eatAt = Random.mid(30, 40);

    @Override
    public boolean validate() {
        return !Negility.Eat.equals("None") && !Inventory.contains(Negility.Eat) && Players.getLocal().getHealthPercent() < eatAt;
    }

    @Override
    public int execute() {
        Item Food = Inventory.getFirst(Negility.Eat);
        if(Food != null && Food.containsAction("Eat")){
            Food.interact("Eat");
        }else if(Food != null && Food.containsAction("Drink")){
            Food.interact("Drink");
        }
        Time.sleep(1000, 1500);
        eatAt = Random.mid(30, 40);
        return 500;
    }
}
