package ned.com.scripts.cooking.progressiveFishCooker;

import api.MiscApi;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;
//1
import java.util.function.Predicate;


public class Banking extends Task {

    private Player me = Players.getLocal();
    private String fish;
    private static final Predicate<Item> RAW_PREDICATE = item -> item.getName().contains("Raw");


    @Override
    public boolean validate() {
        if (Skills.getCurrentLevel(Skill.COOKING) < 15) {
            fish = "Raw shrimps";
        } else if (Skills.getCurrentLevel(Skill.COOKING) < 30) {
            fish = "Raw trout";
        } else if (Skills.getCurrentLevel(Skill.COOKING) < 50) {
            fish = "Raw salmon";
        } else if (Skills.getCurrentLevel(Skill.COOKING) < 80) {
            fish = "Raw lobster";
        } else {
            fish = "Raw shark";
        }
        return Inventory.getCount(fish) != 28 && progressiveFishCooker.BANK_AREA.contains(me);
    }


    @Override
    public int execute() {

        System.out.print("Banking");

        if (!Bank.isOpen() && progressiveFishCooker.BANK_AREA.contains(me)) {
            openNearestBank();
        }

        if (Bank.isOpen() && Bank.getCount(fish) == 28) {
            Bank.close();
            Time.sleepUntil(() -> !Bank.isOpen(), 1000);
            Time.sleep(500, 1000);
            return -1;
        }

        if (Bank.isOpen() && !Inventory.isEmpty() && Inventory.getCount(RAW_PREDICATE) < 28 && !Inventory.getItemAt(0).getName().equals(fish)) {
            DepositMaterials();
            Time.sleep(30, 60);
        } else if (Bank.isOpen() && !Inventory.contains(fish)) {
            GetMaterials();
            Time.sleep(20, 60);
        }

        return MiscApi.randnum(20) + 10;
    }

    private void DepositMaterials() {
        Bank.depositInventory();
        Time.sleepUntil(() -> Inventory.getCount() == 0, 2000);
        Time.sleep(20, 60);
    }

    private void GetMaterials() {
        if (!Inventory.contains(fish) && Bank.getCount(fish) > 29) {
            Bank.withdraw(fish, 28);
            Time.sleepUntil(() -> Inventory.contains(fish), 2000);
            Time.sleep(20, 60);
        } else {
            Time.sleep(5000,10000);
        }

    }


    private void openNearestBank() {
        Npc banker = Npcs.getNearest(npc -> npc.containsAction("Bank"));
        SceneObject bankObj = SceneObjects.getNearest(SceneObject -> SceneObject.containsAction("Bank"));
        if (me != null && !me.isMoving()) {
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
                Log.info("NPC Banker not found?");
            }
        }
    }
}

