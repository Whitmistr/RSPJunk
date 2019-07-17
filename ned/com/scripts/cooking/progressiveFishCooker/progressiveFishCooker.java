package ned.com.scripts.cooking.progressiveFishCooker;

import api.SkillTracker;
import ned.com.scripts.screenshotHandler.ScreenshotSaver;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;

import java.util.LinkedList;
import java.util.Queue;

@ScriptMeta(developer = "Ned", desc = "have raw shrimp,trout,salmon,lobster, shark", name = "Progressive fish cooker")

public class progressiveFishCooker extends TaskScript implements RenderListener {

    public SkillTracker cooking = new SkillTracker(Skill.COOKING);
    private final Queue<ScreenshotSaver> screenshotQueue = new LinkedList<>();

    Player me = Players.getLocal();

    public static final Area RANGE_AREA = Area.rectangular(3272, 3180, 3274, 3179);
    public static final Area BANK_AREA = Area.rectangular(3269, 3171, 3271, 3164);


    @Override
    public void onStart() {
        submit(new Banking(), new Walker(), new Cooking());

        if (IsContinueUp()) {
            takeScreenshot();
            Time.sleep(300, 700);
        }
    }


    private void takeScreenshot() {
        String name = me.getName() + "Cooking" + String.valueOf(Skills.getCurrentLevel(Skill.COOKING));
        screenshotQueue.add(new ScreenshotSaver(name));
    }


    private boolean IsContinueUp() {
        return Interfaces.getFirst(i -> i.getText().contains("Click here to continue")) != null;
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        cooking.draw(renderEvent.getSource(), 20, 20);

        if (!screenshotQueue.isEmpty()) {
            screenshotQueue.remove().accept(renderEvent.getProvider().getImage());
        }
    }


}