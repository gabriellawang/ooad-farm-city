package controller;

import java.util.*;
import java.io.*;

import entity.*;
import dataManager.*;
import exception.*;

/**
 * The HarvestController class coordinates the functions available in the Data Managers for the Menu class.
 * It manages PlotDataManager, FarmerDataManager, CropDataManager, and RankDataManager.
 *
 * @author G3-T02
 */
public class HarvestController {

    private PlotDataManager pDM;
    private FarmerDataManager fDM;
    private CropDataManager cDM;
    private RankDataManager rDM;

    /**
     * Constructs a HarvestController object and Initialises Data Managers 
     */
    public HarvestController() {

        pDM = new PlotDataManager();
        fDM = new FarmerDataManager();
        cDM = new CropDataManager();
        rDM = new RankDataManager();
    }

	
    /**
     * Process of user harvesting plot
	 *
     * @param f The Farmer object of user
     * @return An ArrayList of messages for successful harvesting each plot
     * @throws FarmCityControlException Throw exception when there is no plot to harvest 
     * @throws IOException Throw exception when there is no plots.csv or information.csv
     */
    public ArrayList<String> harvest(Farmer f) throws FarmCityControlException, IOException {

        String username = f.getUsername();
        ArrayList<Plot> pList = pDM.loadPlots(username);
        ArrayList<Plot> harvestedPlot = new ArrayList<>();

        for (int i = 0; i < pList.size(); i++) {
            Plot p = pList.get(i);
            if (p.getPercentage().equals("100%")) {
                harvestedPlot.add(p);

                // Replace the plot with an empty plot.
                pList.remove(i);
                pList.add(i, new Plot("<empty>", " ", " ", " "));
            }
        }

        // When there is no plot can be harvested.
        if (harvestedPlot.isEmpty()) {
            throw new FarmCityControlException("No empty plot is available.");
        }

		// Put the information of unit, XP and gold from 
        // different plots with the same crop into one
        // HarvestedItem object.
        ArrayList<HarvestedItem> itemList = new ArrayList<>();
        for (Plot p : harvestedPlot) {
            int unit = calculateUnit(p);
            int XP = calculateXP(p);
            int gold = calculateGold(p);

            boolean hasExisted = false;

            for (HarvestedItem i : itemList) {
                if (p.getName().equals(i.getName())) {
                    i.setUnit(unit);
                    i.setXP(XP);
                    i.setGold(gold);
                    hasExisted = true;
                }
            }
            if (!hasExisted) {
                itemList.add(new HarvestedItem(p.getName(), unit, XP, gold));
            }
        }

        ArrayList<String> output = new ArrayList<>();
        for (HarvestedItem item : itemList) {
            String name = item.getName();
            int unit = item.getUnit();
            int XP = item.getXP();
            int gold = item.getGold();

            // Generate the information displayed after harvesting.
            String s = "You have harvested " + unit + " units of " + name + " for " + XP + " XP and " + gold + " gold.";
            output.add(s);

            // Update farmer's information.
            f.setXP(XP);
            f.setGold(gold);
        }

        checkPlotAmt(f, pList);
        try {
            // Save the change of farmer's information.
            fDM.saveFarmer(f);

            // Update the farmer's plots.
            pDM.savePlot(pList, f.getUsername());

        } catch (IOException e) {
            throw e;
        }

        return output;
    }

	
    /**
     * This method checks whether farmer can add plot according to current XP.
	 *
     * @param f The Farmer object of user
     * @param pList The ArrayList of Plot
     */
	public void checkPlotAmt(Farmer f, ArrayList<Plot> pList) {

        ArrayList<Rank> rList = rDM.loadRank();
        int XP = f.getXP();
        int index = 0;
        Rank r = null;
        int rXP = 0;

        do {
            r = rList.get(index);
            rXP = r.getXP();
            index++;
        } while (rXP < XP);

        int plotAmt = rList.get(index - 1).getPlotAmt();
        if (plotAmt > pList.size()) {
            int difference = plotAmt - pList.size();
            for (int i = 0; i < difference; i++) {
                pList.add(new Plot("<empty>", " ", " ", " "));
            }
        }
    }


    /**
     * This method calculates the amount of units the farmer will harvest from a certain plot.
	 * 
     * @param p The Plot object that user wants to harvest
     * @return amount The amount of units the farmer harvest from the plot.
     */
        public int calculateUnit(Plot p) {

        String cropName = p.getName();
        Crop c = retrieveCrop(cropName);
        int max = c.getMaxYield();
        int min = c.getMinYield();

        Random rd = new Random();
        int num = rd.nextInt(max - min + 1);

        return min + num;
    }

	
    /**
     * This method calculates the XP the farmer can earn by harvesting a certain plot.
	 * 
     * @param p The Plot object that user wants to harvest
     * @return xp The amount of xp the farmer harvest from the plot
     */
        public int calculateXP(Plot p) {

        String cropName = p.getName();
        Crop c = retrieveCrop(cropName);
        return c.getXP();
    }


    /**
     * This method calculates the gold the farmer can earn by harvesting a certain plot.
	 *
     * @param p The Plot object that user wants to harvest
     * @return amount The amount of gold the farmer harvest from the plot
     */
        public int calculateGold(Plot p) {

        String cropName = p.getName();
        Crop c = retrieveCrop(cropName);
        return c.getPrice();
    }


    /**
     * This method retrieves the Crop object by crop name.
	 *
     * @param cropName The name of the crop
     * @return c The Crop object, or null if Crop not found
     */
        public Crop retrieveCrop(String cropName) {

        ArrayList<Crop> cList = cDM.loadCrop();
        for (Crop c : cList) {
            if (c.getName().equals(cropName)) {
                return c;
            }
        }
        return null;
    }
}
