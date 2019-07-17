package ned.com.scripts.cooking.progressiveFishCooker;


import api.MiscApi;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;




public class Walker extends Task {

    Player me = Players.getLocal();
    public String fish;

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


        return walkToBank(fish) || walkToRange(fish);
    }


    @Override
    public int execute() {
        System.out.println("Walking");
        if (!me.isMoving()) {
            Movement.walkTo(walkToBank(fish) ? progressiveFishCooker.BANK_AREA.getCenter() : progressiveFishCooker.RANGE_AREA.getCenter());
            return MiscApi.randnum(50) + 50;
        }


        return MiscApi.randnum(20) + 10;
    }


    private boolean walkToBank(String fish) {
        return !Inventory.contains(fish) && !progressiveFishCooker.BANK_AREA.contains(me);
    }

    private boolean walkToRange(String fish) {
        return Inventory.contains(fish) && !progressiveFishCooker.RANGE_AREA.contains(me);
    }

}

