package DataObjects;

import Common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyTicketPage extends GeneralPage{
        //Locators
        private final By _lblMyTicket = By.xpath("//*[@id=\"content\"]/h1");
        public WebElement getLblMyTicket(){
        return Constant.WEBDRIVER.findElement(_lblMyTicket);
    }

}
