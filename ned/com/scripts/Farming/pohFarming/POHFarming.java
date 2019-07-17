package ned.com.scripts.Farming.pohFarming;


import api.BankApi;
import api.MiscApi;

import api.SkillTracker;
import ned.com.scripts.screenshotHandler.ScreenshotSaver;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.path.Path;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.Scene;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import java.util.LinkedList;
import java.util.Queue;


@ScriptMeta(developer = "Ned", desc = "POH plants", name = "Plant builder")
public class POHFarming extends Script implements RenderListener {


    public SkillTracker cooking = new SkillTracker(Skill.COOKING);
    private final Queue<ScreenshotSaver> screenshotQueue = new LinkedList<ScreenshotSaver>();

    Player me = Players.getLocal();

    @Override
    public int loop() {

        if (IsContinueUp() == true) {
            takeScreenshot();
            Time.sleep(300, 700);
            Interfaces.getContinue();
        }

        //final InterfaceComponent[] chatOptions = Interfaces.getChatOptions();
        if (Dialog.getChatOptions().length != 0) {
            Dialog.process(0);
            //Interfaces.processDialog(0);
            Time.sleep(MiscApi.randnum(50) + 300);
        }

        SceneObject plantFrame = SceneObjects.getNearest("Small Plant space 1");
        SceneObject plant = SceneObjects.getNearest("Plant");
        Player me = Players.getLocal();

        if (Scene.isDynamic() && SceneObjects.getNearest("Door") != null) {
            SceneObjects.getNearest("Portal").interact("Enter");
        }

        if (!Scene.isDynamic() && !Inventory.contains("Teleport to house") && Inventory.contains("Bagged plant 1") && SceneObjects.getNearest("Portal") != null ) {
            SceneObjects.getNearest("Portal").interact("Build mode");
            Time.sleepUntil(() -> (Scene.isDynamic()), 3000);

        }

        SceneObject object = SceneObjects.getNearest("Small Plant space 1");
        if (object != null && Scene.isDynamic() && Inventory.contains("Bagged plant 1") && SceneObjects.getNearest("Door") == null) {
            BuildPlant();
            System.out.print("Interacting with plant space object 1");
        }
        if (object == null && Scene.isDynamic()  && SceneObjects.getNearest("Door") == null && me != null) {
            if (plantFrame == null && plant != null) {
                plant.interact("Remove");
                Time.sleep(200,300);
            }
        }

        if (Scene.isDynamic() && !Inventory.contains("Bagged plant 1") && !Inventory.contains("Watering can(8)") && me != null && !Inventory.isEmpty()) {
            Inventory.getFirst("Varrock teleport").interact("Break");
            System.out.print("Interacting with plant space object 2");
        }

        if (!Scene.isDynamic()) {
            System.out.print("Interacting with plant space object3");
            Bank();
        }

        return 600;

    }




    private void BuildPlant() {
        SceneObject plantFrame = SceneObjects.getNearest("Small Plant space 1");
        SceneObject plant = SceneObjects.getNearest("Plant");

        if(IsContinueUp()) {
            Interfaces.getContinue();
            Time.sleep(200,300);
        }


        if (plantFrame != null && !IsBuildingInterfaceUp() && Players.getLocal().getAnimation() == -1 && Inventory.contains("Bagged plant 1")) {
            plantFrame.interact("Build");
            Time.sleep(200,300);
            System.out.print("Interacting with plant space object");
        }

        if (plantFrame == null && plant != null) {
            plant.interact("Remove");
            Time.sleep(200,300);
        }

        if (plantFrame != null && IsBuildingInterfaceUp() && Inventory.contains("Bagged plant 1")) {
            Interfaces.getComponent(458, 4).interact(a -> true);
            System.out.print("Interacting with build interface");
            Time.sleep(500,600);
            Time.sleepUntil(() -> (Players.getLocal().getAnimation() == -1), 3000);
        }

    }

    private void Bank() {

        SceneObject bank = SceneObjects.getNearest("Bank booth");
        Position position = new Position(3181, 3438, 0);
        if (!Players.getLocal().isMoving() && position.distance() > 15) {
            Path toDestination = Movement.buildPath(position);
            if (toDestination != null) {
                toDestination.walk();
                Time.sleepUntil(() -> Players.getLocal().isMoving(), 2500);
                System.out.print("Walking to bank poisiton");
            }
        }
            else if (!Bank.isOpen() && bank != null) {
                BankApi.openBankObject("Bank booth");
                System.out.print("Opening bank");
            }
            else {
                Deposit();
                Time.sleep(300,400);
                Withdraw();
            }
        }


    private void Withdraw() {
        if (!Inventory.contains("Varrock teleport")) {
            BankApi.withdraw("Varrock teleport", 1);
            Time.sleep(200,300);
        }
        if (!Inventory.contains("Teleport to house")) {
            BankApi.withdraw("Teleport to house", 1);
            Time.sleep(200,300);
        }
        if (Inventory.getCount("Watering can(8)") < 3) {
            BankApi.withdraw("Watering can(8)", 3 - Inventory.getCount("Watering can(8)"));
            Time.sleep(200,300);
        }
        if(Inventory.getCount("Bagged plant 1") < 23) {
            BankApi.withdraw("Bagged plant 1", 25);
            Time.sleep(200,300);
        } else {
            Inventory.getFirst("Teleport to house").interact("Break");
        }
    }

    private void Deposit() {
        Bank.depositAllExcept("Varrock teleport", "Teleport to house", "Watering can(8)", "Bagged plant 1");
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

        if (!screenshotQueue.isEmpty()) {
            screenshotQueue.remove().accept(renderEvent.getProvider().getImage());
        }
    }

    private boolean IsBuildingInterfaceUp(){
        return Interfaces.getComponent(458, 4) != null;
    }

}
