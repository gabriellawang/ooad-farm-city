package dataManager;

import java.util.*;
import java.io.*;

import entity.Rank;

/**
 * The RankDataManager class stores the information related to a Data Manager of Rank objects. It manages a list of ranks.
 *
 * @author G3-T02
 */
public class RankDataManager {

    private final String FILEPATH = "data/rank.csv";

    /**
     * This method initialises and returns a list of Rank objects.
	 *
     * @return An ArrayList of Rank, returns null if no ranks.
     */
	public ArrayList<Rank> loadRank() {

        Scanner sc = null;
        File rFile = null;

        String rankName = "";
        int XP = 0;
        int plotAmt = 0;

        ArrayList<Rank> rList = new ArrayList<>();

        try {
            rFile = new File(FILEPATH);
            sc = new Scanner(rFile);
            sc.useDelimiter(",|\r\n");

            sc.nextLine();
            while (sc.hasNext()) {
                rankName = sc.next();
                XP = Integer.parseInt(sc.next());
                plotAmt = Integer.parseInt(sc.next());

                rList.add(new Rank(rankName, XP, plotAmt));
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return rList;
    }

}
