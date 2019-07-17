package ned.com.scripts.Prayer;

import api.MiscApi;
import api.ObjectApi;
import api.SkillTracker;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.runetek.providers.RSWorld;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import java.util.Objects;

/**
 * @Author Ned
 */

@ScriptMeta(developer = "Auschwitz Scripts", desc = "Cooks niggers", name = "Chaos altar")
    public class ChaosPrayer extends Script implements RenderListener {

    private SkillTracker herblore = new SkillTracker(Skill.PRAYER);

    @Override
    public int loop() {


        if (Players.getLocal() != null) {
            System.out.println("starting up");
            if (Players.getLoaded().length == 1) {
                if (Inventory.contains(536)) {
                    if (!Inventory.isItemSelected()) {
                        Inventory.getFirst(536).interact("Use");
                        Time.sleep(MiscApi.randnum(3) + 2);

                    } else {
                        SceneObject door = SceneObjects.getNearest("Large door");
                        if (door != null && door.containsAction("Open")){
                            door.interact("Open");
                            Time.sleepUntil(() -> !(Players.getLocal().isMoving()), 2000);

                        } else {
                            ObjectApi.useItemIdOnObject(536, "Chaos altar");
                        }
                        }
                    } else{
                        {
                            SceneObject door = SceneObjects.getNearest("Large door");
                            if (door != null && door.containsAction("Open")){
                                door.interact("Open");
                            }
                            else {
                                MiscApi.chaosTempleHutUnnoter(537);
                            }
                        }
                    }
                }
            }



        if (Players.getLoaded().length != 1) {
            if(!WorldHopper.isOpen()) {
                WorldHopper.open();
            }
            if(!WorldHopper.isOpen()) {
                WorldHopper.open();
            } else if(WorldHopper.isOpen() && Game.isLoggedIn() && !Game.isLoadingRegion() && Game.getState() != Game.STATE_HOPPING_WORLD ){
                RSWorld[] worlds = Worlds.getLoaded(e ->  e.isMembers() && !e.isDeadman() && !e.isTournament() && !e.isSkillTotal() && !e.isSeasonDeadman() && e.getPopulation() < 1600 && !Objects.equals(e, Worlds.get(Worlds.getCurrent())));
                RSWorld newWorld = Random.nextElement(worlds);
                WorldHopper.hopTo(newWorld);
                Time.sleep(500,1000);
                Time.sleepUntil(() -> Game.getState() != Game.STATE_HOPPING_WORLD,3000);
            }

        }


            return api.MiscApi.randnum(2);
        }


        @Override
        public void notify (RenderEvent renderEvent){
            herblore.draw(renderEvent.getSource(), 20, 20);
        }
    }

