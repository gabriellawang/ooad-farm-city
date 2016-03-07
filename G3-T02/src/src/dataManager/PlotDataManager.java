package dataManager;

import java.util.*;
import java.io.*;

import entity.Plot;

/**
 * The PlotDataManager class stores the information related to a Data Manager of Plot objects. It manages a list of plots.
 *
 * @author G3-T02
 */
public class PlotDataManager {

    private final String FILEPATH = "data/Farmer/";

    /**
     * This method initialises and returns a list of Plot objects.
	 *
	 * @param username The username of the user that is currently logging on.
     * @return An ArrayList of Plot, returns null if no plots.
     */
	public ArrayList<Plot> loadPlots(String username) {

        String path = FILEPATH + username + "/plots.csv";
        File fPlot = null;
        Scanner sc = null;
        String cropName = "";
        String status = "";
        String percentage = "";
        String plantDate = "";

        ArrayList<Plot> pList = new ArrayList<>();

        try {
            fPlot = new File(path);
            sc = new Scanner(fPlot);
            sc.useDelimiter(",|\r\n");

            sc.nextLine();
            while (sc.hasNext()) {
                cropName = sc.next();
                status = sc.next();
                percentage = sc.next();
                plantDate = sc.next();
                pList.add(new Plot(cropName, status, percentage, plantDate));
            }
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return pList;
    }


/**
     * This method updates the list Plot object of the specified Farmer object
	 *
     * @param pList The list of Plot which needs to updated
     * @param username The username of the user that is currently logging on.
     * @throws IOException Throw exception when there is no plots.csv
     */
	public void savePlot(ArrayList<Plot> pList, String username) throws IOException {
        String path = FILEPATH + username + "/plots.csv";

        PrintStream fileOut = null;
        try {
            fileOut = new PrintStream(new FileOutputStream(path, false));

            fileOut.println("cropName,status,percentage,plantDate");
            for (Plot p : pList) {
                fileOut.print(p.getName());
                fileOut.print(",");
                fileOut.print(p.getStatus());
                fileOut.print(",");
                fileOut.print(p.getPercentage());
                fileOut.print(",");
                fileOut.println(p.getPlantDate());
            }
        } catch (IOException e) {
            throw new IOException("Fail to update the information! Please try again.");
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }
}
