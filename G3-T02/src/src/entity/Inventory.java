package entity;

/**
 * The Inventory class represents an instance of an inventory.
 *
 * @author G3-T02
 */
public class Inventory {

    private int amount;
    private String cropName;

    /**
	 * Constructs a new Inventory.
     *
     * @param amount The amount of crop the farmer has.
     * @param cropName The name of crop the farmer own.
     */
    public Inventory(int amount, String cropName) {

        this.amount = amount;
        this.cropName = cropName;
    }

	/**
     * Returns the amount of crop the farmer has.
	 *
     * @return The amount of crop the farmer has.
     */
    public int getAmount() {
        return amount;
    }

	/**
     * Returns the name of crop the farmer own.
	 *
     * @return The name of crop the farmer own.
     */
    public String getCropName() {
        return cropName;
    }

    /**
     * Adds crop to the current the amount of crop the farmer has.
	 *
     * @param The amount of crop added.
     */
    public void setAmount(int x) {
        amount += x;
    }
}