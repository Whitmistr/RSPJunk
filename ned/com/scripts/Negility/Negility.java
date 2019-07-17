package ned.com.scripts.Negility;

import ned.com.scripts.Negility.API.FoodEat;
import ned.com.scripts.Negility.API.FoodGet;
import ned.com.scripts.Negility.API.Traveler;
import ned.com.scripts.Negility.Courses.*;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import javax.swing.*;
import java.awt.*;


@ScriptMeta(name = "Negility", version = 2.0, desc = "Traverses Various Courses", developer = "Ned")
public class Negility extends TaskScript implements RenderListener {

    public static Boolean Close = false;
    public static String Eat = "";
    public static String CourseToDo;
    public static boolean guiDone = false;
    public static boolean Chosen = false;
    public static boolean Nearby = false;
    public static String status = "Idle";
    private String[] BankFoods = {"None", "Lobster", "Swordfish", "Shark", "Trout", "Salmon", "Shrimps", "Jug of wine"};
    private long Began = 0;
    private long AgiBegan = Skills.getExperience(Skill.AGILITY);
    private long MarksStart = Inventory.getCount(true, 11849);
    private String[] Courses = {"Progressive", "Gnome", "Draynor", "Varrock", "Falador", "Seer"};
    private JFrame Title;
    private boolean GUIVisible = false;
    private boolean Started = false;
    private long timeRun = 0;
    public static boolean Progressive = false;
    public static StopWatch T = StopWatch.start();

    private static String msToString(long ms) {
        long totalSecs = ms / 1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        String minsString = (mins == 0)
                ? "00"
                : ((mins < 10)
                ? "0" + mins
                : "" + mins);
        String secsString = (secs == 0)
                ? "00"
                : ((secs < 10)
                ? "0" + secs
                : "" + secs);
        if (hours > 0)
            return hours + ":" + minsString + ":" + secsString;
        else if (mins > 0)
            return mins + ":" + secsString;
        else return ":" + secsString;
    }


    private void HandleRun() {
        if (Movement.getRunEnergy() > 10 && !Movement.isRunEnabled()) {
            Movement.toggleRun(true);
        }
    }


    private void GUIHANDLE() {
        GUIVisible = true;
        this.Title = new JFrame("ned/com/scripts/Negility");
        this.Title.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.Title.setLocationRelativeTo(Game.getCanvas());
        this.Title.setPreferredSize(new Dimension(140, 100));
        this.Title.setLayout(new GridLayout(0, 1));
        JComboBox<String> courseChooser = new JComboBox<>(Courses);
        JComboBox<String> foodChooser = new JComboBox<>(BankFoods);
        JButton init = new JButton("Start");
        init.addActionListener(e -> {
                    if (!Game.isLoggedIn()) {
                        Log.fine("Login first.");
                        return;
                    }
                    CourseToDo = (String) courseChooser.getSelectedItem();
                    Eat = (String) foodChooser.getSelectedItem();
                    guiDone = true;
                    this.Title.dispose();
                }
        );
        this.Title.add(courseChooser);
        this.Title.add(foodChooser);
        this.Title.add(init);
        this.Title.pack();
        this.Title.setResizable(false);
        this.Title.setVisible(true);
    }

    @Override
    public void onStart() {
        submit(new FoodGet(), new FoodEat(), new Traveler(), new VarrockCourse(), new FaladorCourse(), new DraynorCourse(), new SeersCourse(),new GnomeCourse(), new Course());

        HandleRun();
        if (status.contains("Traversing") && !Started) {
            Began = System.currentTimeMillis();
            Started = true;
        }
        if (!GUIVisible) {
            GUIHANDLE();
        }
        if (guiDone && !Chosen && CourseToDo != null) {
            switch (CourseToDo) {
                case "Varrock":
                    Chosen = true;
                    Log.fine("Course Selected: " + CourseToDo);
                    break;
                case "Seer":
                    Chosen = true;
                    Log.fine("Course Selected: " + CourseToDo);
                    break;
                case "Draynor":
                    Chosen = true;
                    Log.fine("Course Selected: " + CourseToDo);
                    break;
                case "Falador":
                    Chosen = true;
                    Log.fine("Course Selected: " + CourseToDo);
                    break;
                case "Gnome":
                    Chosen = true;
                    Log.fine("Course Selected: " + CourseToDo);
                    break;
                case "Progressive":
                    Progressive = true;
                    Chosen = true;
                    Log.fine("Getting 99 Agility now fully auto.");
            }
        }
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        long marksGained = Inventory.getCount(true, 11849) - MarksStart;
        if (Started) {
            timeRun = System.currentTimeMillis() - Began;
        }
        long agiGained = Skills.getExperience(Skill.AGILITY) - AgiBegan;
        long agiPerHour = (int) (agiGained / ((System.currentTimeMillis() - Began) / 3600000.0D));
        long MarksPerHour = (int) (marksGained / ((System.currentTimeMillis() - Began) / 3600000.0D));
        long agiExpLeft = Skills.getExperienceToNextLevel(Skill.AGILITY);
        int x = 20;
        int y = 20;
        int alpha = 120; // Transparency
        Color BGColor = new Color(0, 0, 0, alpha);
        Color TileColor = new Color(36, 165, 76, alpha);
        Color TextColor = new Color(255, 50, 100);
        Color AttackColor = new Color(255, 6, 0, alpha);
        Color nextColor = new Color(216, 255, 0, alpha);
        Graphics2D g = (Graphics2D) renderEvent.getSource();
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(BGColor);
        g.fillRect(25, 25, 180, 85);
        g.setColor(Color.white);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("[ned.com.scripts.Negility]", x += 10, y += 20);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Runtime: " + msToString(timeRun), x, y += 20);
        g.drawString("[" + Skills.getLevel(Skill.AGILITY) + "] Agility: " + formatValue(agiGained) + " [" + formatValue(agiPerHour) + "] /h" + " ((" + agiExpLeft + "))", x, y += 15);
        g.drawString("Marks Gained: " + marksGained + " [" + MarksPerHour + "] /h", x, y += 15);
        g.drawString("Status: " + status, x, y + 15);
    }

    private String formatValue(final long l) {
        return (l > 1_000_000) ? String.format("%.2fm", ((double) l / 1_000_000))
                : (l > 1000) ? String.format("%.1fk", ((double) l / 1000))
                : l + "";
    }
}
