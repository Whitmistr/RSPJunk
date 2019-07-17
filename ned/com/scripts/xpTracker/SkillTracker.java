package ned.com.scripts.xpTracker;

import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;

import java.awt.*;

public class SkillTracker {

    private final Skill skill;

    private long time;
    private int xp;
    private int level;

    public SkillTracker(Skill skill) {
        this.skill = skill;
        this.xp = Skills.getExperience(skill);
        this.level = Skills.getLevel(skill);
        this.time = System.currentTimeMillis();
    }

    public void draw(Graphics src, int x, int y) {
        String xphr = String.format("%s experience: %d (%.2f)", skill.toString(), gainedExperience(), xphr());
        String xptl = String.format("%d experience to level %d", Skills.getExperienceToNextLevel(skill), Skills.getLevel(skill) + 1);

        int width = Math.max(src.getFontMetrics().stringWidth(xphr), src.getFontMetrics().stringWidth(xptl));

        src.setColor(new Color(255, 255, 255, 50));
        src.fillRect(x, y, width + 20, 60);

        src.setColor(Color.BLACK);
        src.drawRect(x, y, width + 20, 60);

        src.setColor(Color.WHITE);
        src.drawString(String.format("%s level: %d (%d)", skill.toString(), Skills.getLevel(skill), gainedLevels()), x + 5, y + 15);
        src.drawString(xphr, x + 5, y + 30);
        src.drawString(xptl, x + 5, y + 45);
    }

    public int gainedExperience() {
        return Skills.getExperience(skill) - xp;
    }

    public int gainedLevels() {
        return Skills.getLevel(skill) - level;
    }

    public long getTime() {
        return System.currentTimeMillis() - time;
    }

    public double xphr() { return gainedExperience() / ((double) getTime() / 3600000D); }
}