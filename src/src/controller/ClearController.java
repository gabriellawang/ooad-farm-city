package controller;

import java.util.*;
import java.io.*;

import entity.*;
import dataManager.*;
import exception.*;

/**
 * The ClearController class coordinates the functions available in the Data Managers for the Menu class.
 * It manages PlotDataManager and FarmerDataManager.
 *
 * @author G3-T02
 */
public class ClearController {

    private PlotDataManager pDM;
    private FarmerDataManager fDM;

    /**
     * Constructs a ClearController object and Initialises Data Managers 
     */
    public ClearController() {

        pDM = new PlotDataManager();
        fDM = new FarmerDataManager();
    }

    /**
     * This method clears the plot of wilted crop corresponding to the choice specified by the user.
	 *
     * @param f The Farmer object of user
     * @param choice The choice of plot user decide to clear
     * @return 
	 * 1 for successful clearing when user have less than 5 gold.
	 * 0 for successful clearing of plot
	 * -1 for unsuccessful clearing of plat
     * @throws FarmCityControlException Throw exception when plot does not exist
     * @throws IOException Throw exception when there is no plots.csv or no friends.csv
     */
	public int clear(Farmer f, int choice) throws FarmCityControlException, IOException {

        ArrayList<Plot> pList = pDM.loadPlots(f.getUsername());

        int size = pList.size();
        if (size < choice) {
            throw new FarmCityControlException("The plot you choose does not exist.");
        }

        int balance = f.getGold();

        try {
            if (balance < 5) {
                for (int i = 0; i < size; i++) {
                    String status = pList.get(i).getStatus();
                    if (status.equals("[  wilted  ]")) {
                        pList.remove(i);
                        pList.add(i, new Plot());
                    }
                }
                pDM.savePlot(pList, f.getUsername());
                return 1;
            } else {
                Plot p = pList.get(choice - 1);
                if (p.getStatus().equals("[  wilted  ]")) {
                    pList.remove(choice - 1);
                    pList.add(choice - 1, new Plot());
                    f.setGold(-5);

                    fDM.saveFarmer(f);

                    pDM.savePlot(pList, f.getUsername());
                    return 0;
                } else {
                    return -1;
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
