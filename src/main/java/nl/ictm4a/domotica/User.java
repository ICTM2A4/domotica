package nl.ictm4a.domotica;

/**
 * The user class, used for the logged in user.
 */
import java.util.ArrayList;

public class User {
    private String username;
    private int userID, heatingInputText, lightingInputText;
    private DatabaseFunction databaseFunction = new DatabaseFunction();
    private ArrayList<Tracklist> userTracklists;

    /**
     * Constructor of the User class, will be called for when user is logged in.
     * @param username username of the logged in user
     * @param userID userid of the logged in user
     */
    public User(String username, int userID) {
        this.userID = userID;
        this.username = username;
        this.heatingInputText = databaseFunction.selectUserChoice("heatValue", userID); //Integer.parseInt(databaseFunction.selectRow("heatValue", "usersetting", "user_id", String.valueOf(getUserID())).get(0));
        this.lightingInputText = databaseFunction.selectUserChoice("ldrValue", userID); //Integer.parseInt(databaseFunction.selectRow("ldrValue", "usersetting", "user_id", String.valueOf(getUserID())).get(0));
        this.userTracklists = databaseFunction.selectPlaylists(userID);
        // succes login, change screen to main screen
        MainScreenFrame mainScreenFrame = new MainScreenFrame(this); //sends the user to the main screen
        mainScreenFrame.setVisible(true);
    }

    /**
     * @return Returns the userID of the user
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return Returns the username of the user
     */
    public String getUserName() {
        return username;
    }

    /**
     * @return heatingInputText Returns the heatingInputText of the user, if the temperature is below this value, the heating will be turned on
     */
    public int getHeatingInputText() {
        return heatingInputText;
    }

    /**
     * @return lightingInputText Returns lightingInputText of the user, if the ldr-sensor is below this value, the lighting will be turned on
     */
    public int getLightingInputText() {
        return lightingInputText;
    }

    public ArrayList<Tracklist> getUserTracklists(){
        return userTracklists;
    }

    /**
     * Updates the database with the wanted values of the user
     * @param heatingInputText heatingInputText done by the user
     * @param lightingInputText lightingInputText done by the user
     */
    public void setUserSetting(int heatingInputText, int lightingInputText) {
        // db update
        databaseFunction.updateUserSetting(lightingInputText, heatingInputText, getUserID());
        this.heatingInputText = heatingInputText;
        this.lightingInputText = lightingInputText;
    }
}
