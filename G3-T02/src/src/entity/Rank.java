package entity;

/**
 * The Rank class represents an instance of an rank.
 *
 * @author G3-T02
 */
public class Rank {

    private String rankName;
    private int XP;
    private int plotAmt;


    /**
	 * Constructs a new Rank
     * 
     * @param rankName The name of rank
     * @param XP The XP required to attained the rank
     * @param plotAmt The amount of plot assigned to the rank
     */
        public Rank(String rankName, int XP, int plotAmt) {

        this.rankName = rankName;
        this.XP = XP;
        this.plotAmt = plotAmt;
    }


    /**
     * Returns the name of rank
	 *
     * @return The name of rank
     */
        public String getRankName() {
        return rankName;
    }

    /**
     * Return required XP
	 *
     * @return The required XP
     */
    public int getXP() {
        return XP;
    }

    /**
     * Return amount of plot assign to rank
	 *
     * @return The amount of plot assign to rank
     */
    public int getPlotAmt() {
        return plotAmt;
    }
}
