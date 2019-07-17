package ned.com.scripts.Herblore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author Ned
 */
public class HerbloreGUI extends JFrame {

    //These don't change save as a constant for better optimization
    private static final String[] OPTION_NAMES = {"Clean Herbs", "Make Unfs", "Make Potions", "Make Tar"};
    private static final String[] HERB_NAMES = {"Grimy guam leaf", "Grimy marrentill", "Grimy tarromin", "Grimy harralander", "Grimy ranarr weed", "Grimy toadflax", "Grimy irit leaf", "Grimy avantoe", "Grimy kwuarm", "Grimy snapdragon", "Grimy cadantine", "Grimy lantadyme", "Grimy dwarf weed", "Grimy torstol"};
    private static final String[] UNFINISHED_POTION_NAMES = {"Guam potion (unf)", "Marrentill potion (unf)", "Tarromin potion (unf)", "Harralander potion (unf)", "Ranarr potion (unf)", "Toadflax potion (unf)", "Irit potion (unf)", "Avantoe potion (unf)", "Kwuarm potion (unf)", "Snapdragon potion (unf)", "Cadantine potion (unf)", "Lantadyme potion (unf)", "Dwarf weed potion (unf)", "Torstol potion (unf)"};
    private static final String[] FINISHED_POTION_NAMES = {"Attack potion(3)", "Antipoison potion(3)", "Strength potion(3)", "Serum 207", "Compost potion", "Restore potion(3)", "Energy potion(3)", "Defence potion(3)", "Agility potion(3)", "Combat potion(3)", "ned.com.scripts.Prayer potion(3)", "Super attack(3)", "Superantipoison(3)", "Fishing potion(3)", "Super energy(3)", "Hunter potion(3)", "Super strength(3)", "Weapon poison", "Super restore(3)", "Super defence(3)", "Antidote+(3)", "Antifire potion(3)", "Ranging potion(3)", "Magic potion(3)", "Stamina potion(3)", "Zamorak brew(3)", "Antidote++(3)", "Weapon poison(+)", "Saradomin brew(3)", "Weapon poison(++)", "Extended antifire(3)", "Anti-venom(3)", "Super combat potion(3)", "Super antifire potion(3)", "Anti-venom+(3)", "Extended super antifire(3)", "Bastion potion(3)", "Battlemage potion(3)"};
    private static final String[] TAR_NAMES = {"Guam tar", "Marrentill tar", "Tarromin tar", "Harralander tar"};
    private static final String[] DEFAULT = {"PLEASE SELECT"};


    private static final JComboBox<String> selectOptions = new JComboBox<String>(OPTION_NAMES);
    private static final JComboBox<String> resultOptions = new JComboBox<String>(DEFAULT);
    private static final DefaultComboBoxModel herbOptions = new DefaultComboBoxModel(HERB_NAMES);
    private static final DefaultComboBoxModel unfOptions = new DefaultComboBoxModel(UNFINISHED_POTION_NAMES);
    private static final DefaultComboBoxModel potionOptions = new DefaultComboBoxModel(FINISHED_POTION_NAMES);
    private static final DefaultComboBoxModel tarOptions = new DefaultComboBoxModel(TAR_NAMES);


    private String selectedOption = "None";
    private String selectedHerb = "None"; //Initially none so the script knows when to start
    private String selectedUnf = "None";
    private String selectedPotion = "None";
    private String selectedTar = "None";


    private static final JButton initiate = new JButton("Initiate");

    public HerbloreGUI() {
        super("ned.com.scripts.Herblore Configuration"); //Set the title here, alternatively use setTitle
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setSize(450, 100);
        initiate.addActionListener(new ActionListener() { //A button that will start the script when pressed.
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedOption = String.valueOf(selectOptions.getSelectedItem());
                selectedHerb = String.valueOf(herbOptions.getSelectedItem()); //Sets the selectedHerb variable to the string inside the box that is selected at the time.
                selectedUnf = String.valueOf(unfOptions.getSelectedItem());
                selectedPotion = String.valueOf(potionOptions.getSelectedItem());
                selectedTar = String.valueOf(tarOptions.getSelectedItem());
            }
        });
        selectOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if ("None".equals(selectOptions.getSelectedIndex())){
                    resultOptions.setEnabled(false);
                } else if ("Clean Herbs".equals(selectOptions.getSelectedItem())) {
                    resultOptions.setModel(herbOptions);
                } else if ("Make Unfs".equals(selectOptions.getSelectedItem())) {
                    resultOptions.setModel(unfOptions);
                } else if ("Make Potions".equals(selectOptions.getSelectedItem())) {
                    resultOptions.setModel(potionOptions);
                } else if ("Make Tar".equals(selectOptions.getSelectedItem())) {
                    resultOptions.setModel(tarOptions);
                }
            }
        });

        add(selectOptions);
        add(resultOptions);

        add(initiate);

        setDefaultCloseOperation(HIDE_ON_CLOSE); //Use HIDE_ON_CLOSE, because the other one (in theory) should close entire bot.
        //pack(); //Hides initiate button on longer combobox results, function - sets size according to components.
    }

    public String getSelectedOption() {
        return selectedOption;
    }
    public String getSelectedHerb() {
        return selectedHerb;
    }
    public String getSelectedselectedUnf() {
        return selectedUnf;
    }
    public String getSelectedPotion() {
        return selectedPotion;
    }
    public String getSelectedTar() {
        return selectedTar;
    }
}