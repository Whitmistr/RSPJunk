package ned.com.scripts.cooking.progressiveFishCooker;

import api.MiscApi;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
//1
public class Cooking extends Task {

    private Player me = Players.getLocal();
    private String fish;

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
        return Inventory.contains(fish) && progressiveFishCooker.RANGE_AREA.contains(me);

    }

    @Override
    public int execute() {

        System.out.println("Cooking");

        if (me != null) {
            if (org.rspeer.runetek.api.component.Production.isOpen()) {
                org.rspeer.runetek.api.component.Production.initiate(0);
                Time.sleep(100 - MiscApi.randnum(30));
                Time.sleepUntil(() -> (!Inventory.contains(fish) || !me.isAnimating()), 20000 + MiscApi.randnum(500));
                Time.sleep(50 - MiscApi.randnum(30));
            }

            if (Inventory.contains(fish)) {
                if ((me.getAnimation() == -1) && !org.rspeer.runetek.api.component.Production.isOpen()) {
                    Time.sleep(200 - MiscApi.randnum(30));
                    SceneObject range = SceneObjects.getNearest("Range");
                    range.interact("Cook");
                    Time.sleepUntil(() -> (org.rspeer.runetek.api.component.Production.isOpen()), 3000 + MiscApi.randnum(500));
                } else {
                    Time.sleep(600);
                }
            }
        }
        return MiscApi.randnum(20) + 10;
    }
}