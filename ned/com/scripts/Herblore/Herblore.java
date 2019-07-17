package ned.com.scripts.Herblore;

import api.BankApi;
import api.InventoryApi;
import api.MiscApi;
import api.SkillTracker;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;

import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

/**
 * @Author Ned
 */
@ScriptMeta(developer = "Ned", desc = "herblore", name = "AIO herblore")
public class Herblore extends Script implements RenderListener {

    private SkillTracker herblore = new SkillTracker(Skill.HERBLORE);

    private HerbloreGUI herbGUI = new HerbloreGUI();

    @Override
    public void onStart() {
        herbGUI.setVisible(true);
    }

    @Override
    public int loop() {

        Player me = Players.getLocal();

        if (herbGUI.getSelectedOption().equals("None"))
            return 250;
        if (herbGUI.getSelectedHerb().equals("None"))
            return 250;
        if (herbGUI.getSelectedselectedUnf().equals("None"))
            return 250;
        if (herbGUI.getSelectedPotion().equals("None"))
            return 250;
        if (herbGUI.getSelectedTar().equals("None"))
            return 250;


        String option = herbGUI.getSelectedOption();
        switch (option) {
            case "Clean Herbs":
                //
                if (Inventory.contains(herbGUI.getSelectedHerb()) && Bank.isOpen()) {
                    BankApi.closeBank();
                    Time.sleep(MiscApi.randnum(20) + 200);
                }
                if (Inventory.contains(herbGUI.getSelectedHerb())) {
                    if (Inventory.contains(herbGUI.getSelectedHerb())) {
                        Time.sleep(MiscApi.randnum(15) + 200);
                        if (Inventory.getFirst(herbGUI.getSelectedHerb()).interact("Clean") && (Inventory.getFirst(herbGUI.getSelectedHerb()) != null) && (Inventory.getCount(herbGUI.getSelectedHerb()) > 0)) {
                            Item[] inventory = Inventory.getItems();

                            for (int i = 0; i <= 27; i += 4) {
                                if (inventory[i].containsAction("Clean") && (Inventory.getCount(herbGUI.getSelectedHerb()) > 0)) {
                                    inventory[i].interact("Clean");
                                    Time.sleep(MiscApi.randnum(20) + 15);
                                    if (!Inventory.contains(herbGUI.getSelectedHerb())) {
                                        Time.sleepUntil(() -> !(Inventory.contains(herbGUI.getSelectedHerb())), 5000);
                                    }
                                }
                            }
                            Time.sleep(MiscApi.randnum(20) + 20);
                            for (int i = 1; i <= 27; i += 4) {
                                if (inventory[i].containsAction("Clean") && (Inventory.getCount(herbGUI.getSelectedHerb()) > 0)) {
                                    inventory[i].interact("Clean");
                                    Time.sleep(MiscApi.randnum(20) + 20);
                                    if (!Inventory.contains(herbGUI.getSelectedHerb())) {
                                        Time.sleepUntil(() -> !(Inventory.contains(herbGUI.getSelectedHerb())), 5000);
                                    }
                                }
                            }
                            Time.sleep(MiscApi.randnum(20) + 20);
                            for (int i = 2; i <= 27; i += 4) {
                                if (inventory[i].containsAction("Clean") && (Inventory.getCount(herbGUI.getSelectedHerb()) > 0)) {
                                    inventory[i].interact("Clean");
                                    Time.sleep(MiscApi.randnum(20) + 25);
                                    if (!Inventory.contains(herbGUI.getSelectedHerb())) {
                                        Time.sleepUntil(() -> !(Inventory.contains(herbGUI.getSelectedHerb())), 5000);
                                    }
                                }
                            }


                            for (int i = 3; i <= 27; i += 4) {
                                Time.sleep(MiscApi.randnum(20) + 20);
                                if (inventory[i].containsAction("Clean") && (Inventory.getCount(herbGUI.getSelectedHerb()) > 0)) {
                                    inventory[i].interact("Clean");
                                    Time.sleep(MiscApi.randnum(20) + 30);
                                    if (!Inventory.contains(herbGUI.getSelectedHerb())) {
                                        Time.sleepUntil(() -> !(Inventory.contains(herbGUI.getSelectedHerb())), 5000);
                                    }
                                }
                            }
                            Time.sleep(MiscApi.randnum(50) + 150);
                        }
                    }
                } else {
                    if (!Bank.isOpen()) {
                        BankApi.openBankObject("Bank booth");
                        Time.sleep(MiscApi.randnum(20) + 100);
                    }
                    if (!Inventory.contains(herbGUI.getSelectedHerb()) && Inventory.getItems().length > 0 || Inventory.contains("^((?!" + herbGUI.getSelectedHerb() + ").)*$")) {
                        Bank.depositInventory();
                        Time.sleep(MiscApi.randnum(30) + 100);
                    } else if (Bank.isOpen() && !Inventory.contains(herbGUI.getSelectedHerb())) {
                        BankApi.banking28(herbGUI.getSelectedHerb(), herbGUI.getSelectedHerb().replace("Grimy ", ""));
                        Time.sleep(MiscApi.randnum(30) + 100);
                    }
                }


                break;
            case "Make Unfs":
                //
                String secondary = "";

                String guiReq = herbGUI.getSelectedselectedUnf();
                String deposit = herbGUI.getSelectedselectedUnf();
                guiReq = guiReq.replace(" potion (unf)", "");
                guiReq = guiReq.substring(0, 1).toLowerCase() + guiReq.substring(1);
                String[] herbs = {"Grimy guam leaf", "Grimy marrentill", "Grimy tarromin", "Grimy harralander", "Grimy ranarr weed", "Grimy toadflax", "Grimy irit leaf", "Grimy avantoe", "Grimy kwuarm", "Grimy snapdragon", "Grimy cadantine", "Grimy lantadyme", "Grimy dwarf weed", "Grimy torstol"};

                for (String value : herbs) {
                    if (value.matches("(.*)" + guiReq + "(.*)")) {
                        guiReq = value;
                        guiReq = guiReq.replace("Grimy ", "");
                        secondary = guiReq.substring(0, 1).toUpperCase() + guiReq.substring(1);
                    }

                    System.out.println(secondary);
                }

                System.out.println(secondary);
                if (Production.isOpen() && me.getAnimation() == -1) {
                    Production.initiate(0);
                    Time.sleep(400 - MiscApi.randnum(30));
                    Time.sleepUntil(() -> (!Inventory.contains("Vial of water")), 20000+MiscApi.randnum(500));
                }
                if (Inventory.contains("Vial of water") && Inventory.contains(secondary) && Bank.isOpen()) {
                    BankApi.closeBank();
                    Time.sleep(MiscApi.randnum(20) + 50);
                }
                if (me != null) {
                    System.out.println("starting up");
                    if (Inventory.contains("Vial of water") && Inventory.contains(secondary)) {
                        if (!(me.getAnimation() != -1) && !Production.isOpen()) {
                            Time.sleep(300 - MiscApi.randnum(30));
                            InventoryApi.combineTwoItems("Vial of water", secondary);
                            Time.sleep(300 - MiscApi.randnum(30));

                        } else {
                            Time.sleep(600);
                        }
                    } else {
                        BankApi.bankingTwoMaterials("Vial of water", 14, secondary, 14, deposit);
                    }

                }
                break;
            case "Make Potions":
                //


                Potion[] potions = Potion.values();

                String finalPotion = "";
                String ingredient1 = "";
                String ingredient2 = "";
                for (int j = 0; j < potions.length; j++) {
                    if (potions[j].getFinalPotion().equals(herbGUI.getSelectedPotion())) {
                        finalPotion = potions[j].getFinalPotion();
                        ingredient1 = potions[j].getInitialPotion();
                        ingredient2 = potions[j].getSecondary();
                    }
                }

                String ingred1 = ingredient1;
                if (Production.isOpen()) {
                    Production.initiate(0);
                    Time.sleep(400 - MiscApi.randnum(30));
                    Time.sleepUntil(() -> (!Inventory.contains(ingred1)), 20000+MiscApi.randnum(500));
                }
                System.out.println(ingredient1);
                System.out.println(ingredient2);
                System.out.println(finalPotion);
                if (Inventory.contains(ingredient1) && Inventory.contains(ingredient2) && Bank.isOpen()) {
                    BankApi.closeBank();
                    Time.sleep(MiscApi.randnum(20) + 50);
                }
                if (me != null) {
                    System.out.println("starting up");
                    if (Inventory.contains(ingredient1) && Inventory.contains(ingredient2)) {
                        if (!(me.getAnimation() != -1) && !Production.isOpen()) {
                            Time.sleep(400 - MiscApi.randnum(30));
                            InventoryApi.combineTwoItems(ingredient1, ingredient2);
                            Time.sleep(400 - MiscApi.randnum(30));
                        } else {
                            Time.sleepUntil(() -> (me.getAnimation() == -1), 8000 + MiscApi.randnum(500));
                        }
                    } else {
                        if (finalPotion == "Stamina potion(3)" || finalPotion == "Extended antifire(3)" || finalPotion == "Anti-venom(3)") {
                            BankApi.bankingTwoMaterials(ingredient1, 27, ingredient2, 27, finalPotion);
                        } else if (finalPotion == "Super combat potion(3)") {
                            BankApi.withdraw("Torstol", 8);
                            BankApi.withdraw("Super attack(4)", 8);
                            BankApi.withdraw("Super strength(4)", 8);
                            BankApi.withdraw("Super defence(4)", 8);
                        } else {
                            BankApi.bankingTwoMaterials(ingredient1, 14, ingredient2, 14, finalPotion);
                        }
                    }
                }


                break;
            case "Make Tar":
                //to do

                break;

        }
        return MiscApi.randnum(50) + 600;
    }


    @Override
    public void notify(RenderEvent renderEvent) {
        herblore.draw(renderEvent.getSource(), 20, 20);
    }
}
