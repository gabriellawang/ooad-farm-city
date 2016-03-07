package entity;

/**
 * The Farmer class represents an instance of a farmer.
 *
 * @author G3-T02
 */
public class Farmer {

    private String username;
    private String fullName;
    private String password;
    private int XP;
    private int gold;

    /**
     * Constructs a new Farmer with 0 XP and 0 Gold.
     * 
     * @param username The username of a farmer's account.
     * @param fullName The full name of the user.
     * @param password The password of a farmer's account.
     */
    public Farmer(String username, String fullName, String password) {

        this.username = username;
        this.fullName = fullName;
        this.password = password;
        XP = 0;
        gold = 1000;
    }

    /**
     * Constructs a new Farmer.
     * 
     * @param username The username of a farmer's account.
     * @param fullName The full name of the user.
     * @param password The password of a farmer's account.
     * @param XP The experience on a farmer's account.
     * @param gold The amount of gold on a farmer's account.
     */
    public Farmer(String username, String fullName, String password, int XP, int gold) {

        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.XP = XP;
        this.gold = gold;
    }

    /**
     * Returns the username of a farmer's account.
	 *
     * @return The username of a farmer's account.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the full name of the user.
	 *
     * @return The full name of the user.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Returns the password of a farmer's account.
	 *
     * @return The password of a farmer's account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the experience on a farmer's account.
	 *
     * @return The XP on a farmer's account.
     */
    public int getXP() {
        return XP;
    }

    /**
     * Returns the amount of gold on a farmer's account.
	 *
     * @return The amount of gold on a farmer's account.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Adds XP to the current XP farmer has.
	 *
     * @param the amount of experience that will be added.
     */
    public void setXP(int x) {
        XP += x;
    }

    /**
     * Adds gold to the current amount of gold farmer has.
	 *
     * @param The amount of gold that will be added.
     */
    public void setGold(int g) {
        gold += g;
    }
}
