package nl.ictm4a.domotica;

public class User {
    private String username;
    private int userID, heatingInputText, lightingInputText;

    DatabaseFunction databaseFunction = new DatabaseFunction();

    public User(String username, int userID) {
        this.userID = userID;
        this.username = username;
        this.heatingInputText = databaseFunction.selectUserChoice("heatValue", userID); //Integer.parseInt(databaseFunction.selectRow("heatValue", "usersetting", "user_id", String.valueOf(getUserID())).get(0));
        this.lightingInputText = databaseFunction.selectUserChoice("ldrValue", userID); //Integer.parseInt(databaseFunction.selectRow("ldrValue", "usersetting", "user_id", String.valueOf(getUserID())).get(0));
        // succes login, change screen to main screen
        MainScreenFrame mainScreenFrame = new MainScreenFrame(this); //sends the user to the main screen
        mainScreenFrame.setVisible(true);
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return username;
    }

    public int getHeatingInputText() {
        return heatingInputText;
    }

    public int getLightingInputText() {
        return lightingInputText;
    }

    public void setUserSetting(int heatingInputText, int lightingInputText) {
        // db update
        databaseFunction.updateUserSetting(lightingInputText, heatingInputText, getUserID());
        this.heatingInputText = heatingInputText;
        this.lightingInputText = lightingInputText;
    }
}
