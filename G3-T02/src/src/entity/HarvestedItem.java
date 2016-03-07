package entity;

/**
 * The HarvestedItem class represents an instance of a item that is harvested.
 *
 * @author G3-T02
 */
public class HarvestedItem{
	
	private String cropName;
	private int unit;
	private int XP;
	private int gold;
	
    /**
     * Constructs a new HarvestedItem
     * 
     * @param cropName The name of the crop that is harvested.
     * @param unit The unit of crop harvested.
     * @param XP The amount of experience the farmer earn from harvesting the crop.
     * @param gold The amount of gold the farmer earn from harvesting the crop.
     */
    public HarvestedItem(String cropName, int unit, int XP, int gold){
		
		this.cropName = cropName;
		this.unit = unit;
		this.XP = XP;
		this.gold = gold;
	}
	
	/**
     * Returns the name of the crop that is harvested.
	 *
     * @return The name of the crop that is harvested.
     */
    public String getName(){
		return cropName;
	}
	
	/**
     * Returns the unit of crop harvested.
	 *
     * @return The unit of crop harvested.
     */
    public int getUnit() {
		return unit;
	}
	
 	/**
     * Returns the amount of experience the farmer earn from harvesting the crop.
	 *
     * @return The amount of XP the farmer earn from harvesting the crop.
     */
    public int getXP(){
		return XP;
	}
	
	/**
     * Returns the amount of gold the farmer earn from harvesting the crop.
	 *
     * @return The amount of gold the farmer earn from harvesting the crop.
     */
    public int getGold(){
		return gold;
	}
	
    /**
     * Adds unit to the current unit of crop harvested.
	 *
     * @param The number of units added.
     */
    public void setUnit(int u){
		unit +=  u;
	}
	
    /**
     * Adds XP to the current amount of experience the farmer earn from harvesting the crop.
	 *
     * @param The amount of experience added
     */
    public void setXP(int xp){
		XP += xp;
	}
	
    /**
     * Adds gold to the current amount of gold the farmer earn from harvesting the crop.
	 *
     * @param The amount of gold added.
     */
    public void setGold(int g){
		gold += g;
	}
}