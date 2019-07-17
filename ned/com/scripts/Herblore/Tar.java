package ned.com.scripts.Herblore;


/**
 * @Author Ned
 */

public enum Tar {
    GUAM_TAR(0,"Guam tar","Swamp tar","Guam leaf"),
    MARRENTILL_TAR(0,"Marrentill tar","Swamp tar","Marrentill"),
    TARROMIN_TAR(0,"Tarromin tar","Swamp tar","Tarromin"),
    HARRALANDER_TAR(0,"Harralander tar","Swamp tar","Harralander");


    private final String finalProduct, tar, herb;
    private final int id;
    private final Tar[] tars;

    Tar(final int id, final String finalPotion, final String tar, final String herb) {
        this.id = id;
        this.finalProduct = finalPotion;
        this.tar = tar;
        this.herb = herb;

        tars = null;
    }

    public String getTar() {
        return tar;
    }

    public String getHerb() { return herb; }

    public String getFinalProduct() { return finalProduct; }

    public int getId() {
        return id;
    }

    public Tar[] getBows() {
        return tars;
    }
}

