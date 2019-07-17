package ned.com.scripts.Herblore;


/**
 * @Author Ned
 */

    public enum Potion {
        ATTACK_POTION(0,"Attack potion(3)","Guam potion (unf)","Eye of newt"),
        ANTIPOISON_POTION(1,"Antipoison potion(3)","Marrentill potion (unf)","Unicorn horn dust"),
        STRENGTH_POTION(2,"Strength potion(3)","Tarromin potion (unf)","Limpwurt root"),
        SERUM_207_POTION(3,"Serum 207","Tarromin potion (unf)","Ashes"),
        COMPOST_POTION(4,"Compost potion","Harralander potion (unf)","Volcanic ash"),
        RESTORE_POTION(5,"Restore potion(3)","Marrentill potion (unf)","Red spiders' eggs"),
        ENERGY_POTION(6,"Energy potion(3)","Harralander potion (unf)","Chocolate dust"),
        DEFENCE_POTION(7,"Defence potion(3)","Ranarr potion (unf)","White berries"),
        AGILITY_POTION(8,"Agility potion(3)","Toadflax potion (unf)","Toad's legs"),
        COMBAT_POTION(9,"Combat potion(3)","Harralander potion (unf)","Goat horn dust"),
        PRAYER_POTION(10,"ned.com.scripts.Prayer potion(3)","Ranarr potion (unf)","Snape grass"),
        SUPER_ATTACK_POTION(11,"Super attack(3)","Irit potion (unf)","Eye of newt"),
        SUPERANTIPOISON_POTION(12,"Superantipoison(3)","Irit potion (unf)","Unicorn horn dust"),
        FISHING_POTION(13,"Fishing potion(3)","Avantoe potion (unf)","Snape grass"),
        SUPER_ENERGY_POTION(14,"Super energy(3)","Avantoe potion (unf)","Mort myre fungi"),
        HUNTER_POTION(15,"Hunter potion(3)","Avantoe potion (unf)","Kebbit teeth dust"),
        SUPER_STRENGTH_POTION(16,"Super strength(3)","Kwuarm potion (unf)","Limpwurt root"),
        WEAPON_POISON_POTION(17,"Weapon poison","Kwuam potion (unf)","Dragon scale dust"),
        SUPER_RESTORE_POTION(18,"Super restore(3)","Snapdragon potion (unf)","Red spiders' eggs"),
        SUPER_DEFENCE_POTION(19,"Super defence(3)","Cadantine potion (unf)","White berries"),
        ANTIDOTE_PLUS_POTION(20,"Antidote+(3)","Antidote+ (unf)","Yew roots"),
        ANTIFIRE_POTION(21,"Antifire potion(3)","Lantadyme potion (unf)","Dragon scale dust"),
        RANGING_POTION(22,"Ranging potion(3)","Dwarf weed potion (unf)","Wine of Zamorak"),
        MAGIC_POTION(23,"Magic potion(3)","Lantadyme potion (unf)","Potato cactus"),
        STAMINA_POTION(24,"Stamina potion(3)","Super energy potion","Amylase crystal"),
        ZAMORAK_BREW_POTION(25,"Zamorak brew(3)","Torstol potion (unf)","Jangerberries"),
        ANTIDOTE_PLUS_PLUS_POTION(26,"Antidote++(3)","Antidote++ (unf)","Magic roots"),
        WEAPON_POISON_PLUS_POTION(27,"Weapon poison(+)","Weapon poison+ (unf)","Red spiders' eggs"),
        SARADOMIN_BREW_POTION(28,"Saradomin brew(3)","Toadflax potion (unf)","Crushed bird's nest"),
        WEAPON_POISON_PLUS_PLUS_POTION(29,"Weapon poison(++)","Weapon poison++ (unf)","Poison ivy berries"),
        EXTENDED_ANTIFIRE_POTION(30,"Extended antifire(3)","Antifire potion"+"(.*)","Lava scale shard"),
        ANTI_VENOM_POTION(31,"Anti-venom(3)","Antidote++"+"(.*)","Zulrah's scales"),
        SUPER_COMBAT_POTION(32,"Super combat potion(3)","Torstol","Super attack(4)"),
        SUPER_ANTIFIRE_POTION(33,"Super antifire potion(3)","Antifire potion"+"(.*)","Crushed superior dragon bones"),
        ANTI_VENOM_PLUS_POTION(34,"Anti-venom+(3)","Anti-venom"+"(.*)","Torstol"),
        EXTENDED_SUPER_ANTIFIRE_POTION(35,"Extended super antifire","Super entifire potion"+"(.*)","Lava scale shard"),
        BASTION_POTION(36,"Bastion potion(3)","Cadantine blood potion (unf)","Wine of Zamorak"),
        BATTLEMAGE_POTION(37,"Battlemage potion(3)","Cadantine blood potion (unf)","Potato cactus");


        private final String finalPotion, initialPotion, secondary;
        private final int id;
        private final Potion[] potions;

        Potion(final int id, final String finalPotion, final String initialPotion, final String rawMaterial) {
            this.id = id;
            this.finalPotion = finalPotion;
            this.initialPotion = initialPotion;
            this.secondary = rawMaterial;

            potions = null;
        }

        public String getInitialPotion() {
            return initialPotion;
        }

        public String getSecondary() {
            return secondary;
        }

        public String getFinalPotion() {
            return finalPotion;
        }

        public int getId() {
            return id;
        }

        public Potion[] getPotions() {
            return potions;
        }

    }

