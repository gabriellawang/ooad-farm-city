package entity;

/**
 * The Crop class represents an instance of a crop.
 *
 * @author G3-T02
 */
public class Crop {

    private String name;
    private int cost;
    private int time;
    private int XP;
    private int minYield;
    private int maxYield;
    private int price;

    /**
     * Constructs a new Crop
     *
     * @param name The name of the crop.
     * @param cost The number of gold coins required to purchase one bag of seed of the crop.
     * @param time Time in minutes it takes for the specific seed planted to grow to maturity.
     * @param XP Number of Experience Points earned for one unit of crop produced.
     * @param minYield Minimum yield (in units) produced by planting one bag of seed of the given crop.
     * @param maxYield Maximum yield (in units) produced by planting one bag of seed of the given crop.
     * @param price Number of gold coins one unit of produce gives the farmer.
     */
    public Crop(String name, int cost, int time, int XP, int minYield, int maxYield, int price) {

        this.name = name;
        this.cost = cost;
        this.time = time;
        this.XP = XP;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.price = price;
    }

    /**
     * Return the name of the crop.
	 *
     * @return name The name of the crop.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the number of gold coins required to purchase one bag of seed of the crop.
	 *
     * @return cost The number of gold coins required to purchase one bag of seed of the crop.
     */
    public int getCost() {
        return cost;
    }

    /**
	 * Return the time in minutes it takes for the specific seed planted to grow to maturity.
     *
     * @return time The time in minutes it takes for the specific seed planted to grow to maturity.
     */
    public int getTime() {
        return time;
    }

    /**
	 * Return number of Experience Points earned for one unit of crop produced.
     *
     * @return XP The number of Experience Points earned for one unit of crop produced.
     */
    public int getXP() {
        return XP;
    }

    /**
     * Return the minimum yield (in units) produced by planting one bag of seed of the given crop.
	 *
     * @return minYield The minimum yield (in units).
     */
    public int getMinYield() {
        return minYield;
    }

    /**
	 * Return the maximum yield (in units) produced by planting one bag of seed of the given crop.
     *
     * @return maxYield The maximum yield.
     */
    public int getMaxYield() {
        return maxYield;
    }

    /**
	 * Return the number of gold coins one unit of produce gives the farmer.
     *
     * @return price The number of gold coins one unit of produce gives the farmer.
     */
    public int getPrice() {
        return price;
    }
}
