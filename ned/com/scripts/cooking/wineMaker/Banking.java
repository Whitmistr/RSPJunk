package ned.com.scripts.cooking.wineMaker;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.function.Predicate;


public class Banking extends Task {

    private String grapes = "Grapes";
    private String jug = "Jug of water";
    private static final Predicate<Item> AXE_PREDICATE = item -> item.getName().contains("Raw");

    @Override
    public boolean validate() {
        return Inventory.getCount(jug) != 14 || Inventory.getCount(grapes) != 14;
    }


    @Override
    public int execute() {

        if (!Bank.isOpen()) {
            openNearestBank();
        }

        if (Bank.isOpen() && Bank.getCount(grapes) < 14 || Bank.getCount(jug) < 14) {
            Bank.close();
            Time.sleepUntil(() -> !Bank.isOpen(), 1000);
            Time.sleep(500, 1000);
            return -1;
        }

        if (Inventory.contains("Unfermented wine") && Bank.isOpen()) {
            DepositMaterials();
            Time.sleep(30, 60);
        } else if (Inventory.contains("Jug of wine") && Bank.isOpen()) {
            DepositMaterials();
            Time.sleep(20, 40);
        } else if (Bank.isOpen() && !Inventory.contains(grapes) || !Inventory.contains(jug)) {
            GetMaterials();
            Time.sleep(20, 60);
        }
        if (Inventory.getCount(grapes) > 14 || Inventory.getCount(jug) > 14 || Inventory.contains("Unfermented wine") || Inventory.contains("Just of wine")) {
            DepositMaterials();
        }

        return 0;
    }

    private void DepositMaterials() {
        Bank.depositInventory();
        Time.sleepUntil(() -> Inventory.getCount() == 0, 2000);
        Time.sleep(20, 60);
    }

    private void GetMaterials() {
        if (Inventory.contains(grapes) && Bank.getCount(jug) > 14) {
            Bank.withdraw(jug, 14);
            Time.sleepUntil(() -> Inventory.contains(jug), 2000);
            Time.sleep(20, 60);
        } else if (Bank.getCount(grapes) > 14) {
            Bank.withdraw(grapes, 14);
            Time.sleepUntil(() -> Inventory.contains(grapes), 2000);
            Time.sleep(20, 60);
        }

    }


    private void openNearestBank() {
        Npc banker = Npcs.getNearest(npc -> npc.containsAction("Bank"));
        SceneObject bankObj = SceneObjects.getNearest(SceneObject -> SceneObject.containsAction("Bank"));
        if (banker != null) {
            do {
                banker.interact("Bank");
                Time.sleepUntil(Bank::isOpen, 1750);
                Time.sleep(20, 60);
            } while (!Bank.isOpen());
        } else if (bankObj != null) {
            do {
                bankObj.interact("Bank");
                Time.sleepUntil(Bank::isOpen, 1750);
                Time.sleep(20, 60);
            } while (!Bank.isOpen());
        } else {
            Log.info("Bank not found?");
        }
    }
}

