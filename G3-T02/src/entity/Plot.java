package entity;

/**
 * The Plot class represents an instance of an plot.
 *
 * @author G3-T02
 */
public class Plot {

    private String cropName;
    private String status;
    private String percentage;
    private String plantDate;

    /**
	 * Constructs an empty Plot for newly registered user
     *
     */
        public Plot() {
        cropName = "<empty>";
        status = " ";
        percentage = " ";
        plantDate = " ";
    }

    /**
	 * Constructs a Plot for user already registered.
     *
     * @param cropName The name of the crop.
     * @param status The status of the crop.
     * @param percentage The percentage of the Plot progress to harvest.
     * @param plantDate The date the crop was planted on the plot.
     */
        public Plot(String cropName, String status, String percentage, String plantDate) {
        this.cropName = cropName;
        this.status = status;
        this.percentage = percentage;
        this.plantDate = plantDate;
    }


    /**
     * Return the name of the crop.
	 *
     * @return cropName The name of the crop.
     */
        public String getName() {
        return cropName;
    }

    /**
     * Return the status of the crop.
	 *
     * @return status The status of the crop.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Return the percentage of the Plot progress to harvest.
	 *
     * @return percentage The percentage of the Plot progress to harvest.
     */
    public String getPercentage() {
        return percentage;
    }

    /**
     * Return the date the crop was planted on the plot.
	 *
     * @return plantDate The date the crop was planted on the plot.
     */
    public String getPlantDate() {
        return plantDate;
    }

    /**
     * Set the name of the crop.
	 *
     * @param newName The name of the crop.
     */
        public void setName(String newName) {
        cropName = newName;
    }

    /**
     * Set the status of the crop.
	 *
     * @param percent The percentage of the Plot progress to harvest.
     */
    public void setStatus(int percent) {
        int range = percent / 10;
        status = "[";
        for (int i = 0; i < range; i++) {
            status += "#";
        }
        for (int j = 0; j < 10 - range; j++) {
            status += "-";
        }
        status += "]";
    }

    /**
     * Set the status of the crop to wilted
	 *
     */
    public void setStatus() {
        status = "[  wilted  ]";
    }

    /**
     * Set the percentage of the Plot progress to harvest.
	 *
     * @param percent The percentage of the Plot progress to harvest.
     */
    public void setPercentage(int percent) {
        percentage = "" + percent + "%";
    }

    /**
     * Set percentage to zero.
	 *
     */
    public void setPercentage() {
        percentage = " ";
    }

    /**
     * Set the date the crop was planted on the plot.
	 *
     * @param plantDate The date the crop was planted on the plot.
     */
    public void setPlantDate(String plantDate) {
        this.plantDate = plantDate;
    }
}
