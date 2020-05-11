package nl.ictm4a.domotica;

public class User {
    private String userName;
    private int userID, heatingInputText, lightingInputText;

    DatabaseFunction databaseFunction = new DatabaseFunction();

    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        this.heatingInputText = Integer.parseInt(databaseFunction.selectRow("heatValue", "usersetting", "user_id", String.valueOf(getUserID())).get(0));
        this.lightingInputText = Integer.parseInt(databaseFunction.selectRow("ldrValue", "usersetting", "user_id", String.valueOf(getUserID())).get(0));
        // succes login, change screen to main screen
        MainScreenFrame mainScreenFrame = new MainScreenFrame(this); //sends the user to the main screen
        mainScreenFrame.setVisible(true);
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public int getHeatingInputText() {
        return heatingInputText;
    }

    public int getLightingInputText() {
        return lightingInputText;
    }

    public void setUserSetting(int heatingInputText, int lightingInputText) {
        // db update
        if(databaseFunction.updateUserSetting("usersetting", "ldrValue", "heatValue", lightingInputText, heatingInputText, getUserID())) { // if the update worked
            this.heatingInputText = heatingInputText;
            this.lightingInputText = lightingInputText;
        }
    }
}
