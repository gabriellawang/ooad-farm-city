package entity;

/**
 * The Gift class represents an instance of a gift.
 *
 * @author G3-T02
 */
public class Gift {

    private String receiver;
    private String date;

    /**
     * Constructs a new Gift.
     * 
     * @param receiver The username of the farmer who will receive the gift.
     * @param date The date when theg gift is sent.
     */
    public Gift(String receiver, String date) {
        this.receiver = receiver;
        this.date = date;
    }

	/**
     * Returns the username of the farmer who will receive the gift.
	 *
     * @return The username of the farmer who will receive the gift.
     */
    public String getReceiver() {
        return receiver;
    }

	/**
     * Returns the date when theg gift is sent.
	 *
     * @return The date when theg gift is sent.
     */
    public String getDate() {
        return date;
    }
}
