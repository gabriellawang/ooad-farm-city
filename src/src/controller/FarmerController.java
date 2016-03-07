package controller;

import java.util.*;

import dataManager.*;
import entity.*;

/**
 * The FarmerController class coordinates the functions available in the Data Managers for the Menu class.
 * It manages PlotDataManager and RankDataManager.
 *
 * @author G3-T02
 */
 
public class FarmerController {

    private PlotDataManager pDM;
    private RankDataManager rDM;

	/**
     * Constructs a FarmerController object and Initialises Data Managers 
     */
    public FarmerController() {

        pDM = new PlotDataManager();
        rDM = new RankDataManager();
    }


    /**
     * This method retrieves the current rank of the user
	 *
     * @param f The Farmer object of user
     * @return The name of the user current ranking
     */
        public String currentRank(Farmer f) {

        ArrayList<Rank> rList = rDM.loadRank();
        int plotsAmt = plotAmt(f);
        for (Rank r : rList) {
            if (r.getPlotAmt() == plotsAmt) {
                return r.getRankName();
            }
        }
        return null;
    }

    /**
     * This method retrieves the amount of plots of the farmer.
	 *
     * @param f The Farmer object of user
     * @return The amount of plot user has
     */
        public int plotAmt(Farmer f) {

        String username = f.getUsername();
        ArrayList<Plot> pList = pDM.loadPlots(username);
        int amt = pList.size();
        return amt;
    }


    /**
     * This method retrieves the list of the graphical display of plots of the user.
	 *
     * @param f The Farmer object of user
     * @return The ArrayList of the display of plots of the user 
     */
    public ArrayList<String> plotsDisplay(Farmer f) {

        String username = f.getUsername();
        ArrayList<Plot> pList = pDM.loadPlots(username);
        ArrayList<String> display = new ArrayList<>();
        for (int i = 0; i < pList.size(); i++) {
            Plot p = pList.get(i);
            String name = p.getName();
            String status = p.getStatus();
            String percent = p.getPercentage();

            String disp = i + 1 + ". " + name + "\t" + status + "\t" + percent;
            display.add(disp);
        }
        return display;
    }
}
