package ned.com.scripts.cooking.wineMaker;

import api.SkillTracker;
import ned.com.scripts.screenshotHandler.ScreenshotSaver;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;

import java.util.LinkedList;
import java.util.Queue;


@ScriptMeta(name = "WineMaker", version = 2.0, desc = "Make wines", developer = "Ned")
public class wineMaker extends TaskScript implements RenderListener {

    private SkillTracker cooking = new SkillTracker(Skill.COOKING);
    private final Queue<ScreenshotSaver> screenshotQueue = new LinkedList<ScreenshotSaver>();

    private Player me = Players.getLocal();

    @Override
    public void onStart() {
        submit(new Production(), new Banking());

        if (IsContinueUp() == true) {
            takeScreenshot();
            Time.sleep(300, 700);
        }

        Interfaces.getContinue();

    }


    private void takeScreenshot() {
        String name = me.getName() + "Cooking" + String.valueOf(Skills.getCurrentLevel(Skill.COOKING));
        screenshotQueue.add(new ScreenshotSaver(name));
    }


    public boolean IsContinueUp() {
        return Interfaces.getFirst(i -> i.getText().contains("Click here to continue")) != null;
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        cooking.draw(renderEvent.getSource(), 20, 20);
    }

}
