package DataObjects;

import Common.Constant;

public class HomePage extends GeneralPage{
    // Locations
    // Elements
    // Methods
    public HomePage open()
    {
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
    }

}