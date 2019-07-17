package ned.com.scripts.cooking.wineMaker;

import api.InventoryApi;
import api.MiscApi;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class Production extends Task {
    @Override
    public boolean validate() {
        return Inventory.getCount("Jug of water") > 1 && Inventory.getCount("Grapes") > 1;
    }

    @Override
    public int execute() {

        Player me = Players.getLocal();
        if (me != null) {
            if (org.rspeer.runetek.api.component.Production.isOpen()) {
                org.rspeer.runetek.api.component.Production.initiate(0);
                Time.sleep(100 - MiscApi.randnum(30));
                Time.sleepUntil(() -> (!Inventory.contains("Jug of water")) && !Inventory.contains("Grapes"), 20000 + MiscApi.randnum(500));
                Time.sleep(50 - MiscApi.randnum(30));
            }

            if (Inventory.contains("Jug of water") && Inventory.contains("Grapes")) {
                if (!(me.getAnimation() != -1) && !org.rspeer.runetek.api.component.Production.isOpen()) {
                    Time.sleep(400 - MiscApi.randnum(30));
                    InventoryApi.combineTwoItems("Jug of water", "Grapes");
                    Time.sleepUntil(() -> (org.rspeer.runetek.api.component.Production.isOpen()), 3000 + MiscApi.randnum(500));
                } else {
                    Time.sleep(600);
                }
            }
        }
        return MiscApi.randnum(20) + 10;
    }
}