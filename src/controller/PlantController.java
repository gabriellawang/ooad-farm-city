package controller;

import java.util.*;
import java.text.*;
import java.io.*;

import entity.*;
import dataManager.*;
import exception.*;

/**
 * The HarvestController class coordinates the functions available in the Data Managers for the Menu class.
 * It manages PlotDataManager, FarmerDataManager, CropDataManager, RankDataManager, and InventoryDataManager.
 *
 * @author G3-T02
 */
public class PlantController {

    private PlotDataManager pDM;
    private FarmerDataManager fDM;
    private CropDataManager cDM;
    private RankDataManager rDM;
    private InventoryDataManager iDM;

    /**
     * Constructs a PlantController object and Initialises Data Managers 
     */
    public PlantController() {

        pDM = new PlotDataManager();
        fDM = new FarmerDataManager();
        cDM = new CropDataManager();
        rDM = new RankDataManager();
        iDM = new InventoryDataManager();
    }

	/**
     * This method plantings crop on a plot.
	 *
     * @param f The Farmer object that is currently logging on.
     * @param plotNo The choice of plot user decides to plant
     * @param cropName The name of crop user decides to plant
     * @throws IOException Throw exception when there is no plots.csv or inventory.csv
     */
    public void plant(Farmer f, int plotNo, String cropName) throws IOException {

        Crop c = retrieveCrop(cropName);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = new Date();
        String d = sdf.format(date);

        String username = f.getUsername();
        ArrayList<Plot> pList = pDM.loadPlots(username);
        pList.remove(plotNo - 1);
        pList.add(plotNo - 1, new Plot(cropName, "[----------]", "0%", d));

        try {
            // Update the Inventory.
            ArrayList<Inventory> iList = iDM.loadInventory(username);
            for (Inventory iList1 : iList) {
                if (iList1.getCropName().equals(cropName)) {
                    iList1.setAmount(-1);
                }
            }
            iDM.saveInventory(iList, username);

            // Update the information of farmer's plots.
            pDM.savePlot(pList, username);
        } catch (IOException e) {
            throw e;
        }
    }

	
    /**
     * This method returns a list of crop can be planted according to user inventory
	 *
     * @param f The Farmer object that is currently logging on.
     * @return list The ArrayList of crop can be planted.
     */
        public ArrayList<String> plantList(Farmer f) {

        ArrayList<String> list = new ArrayList<>();
        ArrayList<Inventory> iList = iDM.loadInventory(f.getUsername());
        for (Inventory iList1 : iList) {
            list.add(iList1.getCropName());
        }
        return list;
    }

    // Check whether the plot farmer chooses is available or exists.

    /**
     * This method takers the Farmer object and the 
	 *
     * @param f The Farmer object that is currently logging on.
     * @param plotNo The choice corresponding to the plot for checking
     * @return true if the plot is available or exists, otherwise false
     */
        public boolean plotAvailable(Farmer f, int plotNo) {

        String username = f.getUsername();
        ArrayList<Plot> pList = pDM.loadPlots(username);
        if (plotNo > pList.size() || plotNo <= 0) {
            return false;
        } else {
            Plot p = pList.get(plotNo - 1);
			
            return p.getName().equals("<empty>");
        }
    }

    /**
     * This method returns the Crop object by taking in the cropName
	 *
     * @param cropName The name of crop
     * @return c The Crop object corresponding to the crop name, otherwise null if not found
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
	
	
    /**
     * This method updates the plot status before displaying to user
	 *
     * @param f The Farmer object that is currently logging on.
     * @throws FarmCityControlException Throw exception when fail to parse the plant date
     * @throws IOException Throw exception when there is no plots.csv
     */
        public void updatePlot(Farmer f) throws FarmCityControlException, IOException {

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        ArrayList<Plot> pList = pDM.loadPlots(f.getUsername());
        for (Plot pList1 : pList) {
            if (!pList1.getPlantDate().equals(" ")) {
                Date plantDate = null;
                try {
                    plantDate = sdf.parse(pList1.getPlantDate());
                } catch (ParseException e) {
                    throw new FarmCityControlException("Fail to parse the plant date. Please try again.");
                }
                long time = plantDate.getTime();
                Crop c = retrieveCrop(pList1.getName());
                long t = (long) c.getTime() * 60 * 1000; // Time the crop need to be mature, convert minute to millisecond.
                time += t;
                Date matureDate = new Date(time);
                time += t;
                Date wiltedDate = new Date(time);
                if (currentDate.after(matureDate)) {
                    if (currentDate.before(wiltedDate)) {
                        // crops that can be harvest
                        pList1.setStatus(100);
                        pList1.setPercentage(100);
                    } else {
                        // crops that have been wilted.
                        pList1.setStatus();
                        pList1.setPercentage();
                    }
                } else {
                    // crops that is not mature yet.
                    long difference = currentDate.getTime() - plantDate.getTime();
                    int percent = (int) ((double) difference / t * 100);
                    pList1.setStatus(percent);
                    pList1.setPercentage(percent);
                }
            }
        }
        try {
            // Update the information.
            pDM.savePlot(pList, f.getUsername());
        } catch (IOException e) {
            throw e;
        }
    }
}
