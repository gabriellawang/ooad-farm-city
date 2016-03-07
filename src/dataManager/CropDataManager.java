package dataManager;

import java.util.*;
import java.io.*;

import entity.Crop;

/**
 * The CropDataManager class stores the information related to a Data Manager of Crop objects. It manages a list of crops.
 *
 * @author G3-T02
 */
public class CropDataManager {

    private final String FILEPATH = "data/crop.csv";

    /**
     * This method initialises and returns a list of Crop objects.
	 *
     * @return cList An ArrayList of Crop
     */
	public ArrayList<Crop> loadCrop() {

        Scanner sc = null;
        File cFile = null;

        String name;
        int cost;
        int time;
        int XP;
        int minYield;
        int maxYield;
        int price;

        ArrayList<Crop> cList = new ArrayList<>();

        try {
            cFile = new File(FILEPATH);
            sc = new Scanner(cFile);
            sc.useDelimiter(",|\r\n");

            sc.nextLine();
            while (sc.hasNext()) {
                name = sc.next();
                cost = Integer.parseInt(sc.next());
                time = Integer.parseInt(sc.next());
                XP = Integer.parseInt(sc.next());
                minYield = Integer.parseInt(sc.next());
                maxYield = Integer.parseInt(sc.next());
                price = Integer.parseInt(sc.next());

                cList.add(new Crop(name, cost, time, XP, minYield, maxYield, price));
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return cList;
    }

}
