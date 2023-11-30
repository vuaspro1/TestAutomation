package TestCases;

import Common.Constant;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestNG {
    WebDriver driver;

    @BeforeMethod
    public void login(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");
    }

    @Test
    //User can log into Railway with valid username and password
    public void TC01() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"content\"]/h1")).isDisplayed());
    }

    //User can't login with blank "Username" textbox
    @Test
    public void TC02() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        WebElement button = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p/input"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/p"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "There was a problem with your login and/or errors exist in your form.");
    }

    //User cannot log into Railway with invalid password
    @Test
    public void TC03() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("312321");
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/p"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "There was a problem with your login and/or errors exist in your form.");
    }

    //Login page displays when un-logged User clicks on "Book ticket" tab
    @Test
    public void TC04() {
        driver.findElement(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']")).click();
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/h1"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "Login Page");
    }

    //System shows message when user enters wrong password several times
    @Test
    public void TC05() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();

        // 3. Enter valid information into "Username" textbox except "Password" textbox.
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);

        for (int i = 0; i < 4; i++) {
            // Clear the password field for the next iteration
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("11111111");
            WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
            button.click();
        }
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("11111111");
        // 5. Repeat step 3 three more times.
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        // Assertion to check if the message appears
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id='content']/p"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.");
    }

    //Additional pages display once user logged in
    @Test
    public void TC06() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();

        // 3. Login with valid account
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);

        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        // Assertion to check if "My ticket", "Change password", and "Logout" tabs are displayed
        Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/Page/ManageTicket.cshtml']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//span[normalize-space()='Change password']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/Account/Logout']")).isDisplayed());

        // Click "My ticket" tab, user will be directed to My ticket page
        WebElement ticket = driver.findElement(By.xpath("//a[@href='/Page/ManageTicket.cshtml']"));
        ticket.click();

        // Assertion to check if the user is on the "My ticket" page
        Assert.assertTrue(driver.findElement(By.xpath("//h1[normalize-space()='Manage Tickets']")).isDisplayed());

        // Click "Change password" tab
        WebElement changePasswordTab = driver.findElement(By.xpath("//span[normalize-space()='Change password']"));
        changePasswordTab.click();

        // Assertion to check if the user is on the "Change password" page
        Assert.assertTrue(driver.findElement(By.xpath("//h1[normalize-space()='Change password']")).isDisplayed());
    }

    //User can create new account
    @Test
    public void TC07() {
        driver.findElement(By.xpath("//span[normalize-space()='Register']")).click();
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys("vuaspro3@gmail.com" );

        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys(Constant.PASSWORD);

        WebElement repassword = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        repassword.sendKeys(Constant.PASSWORD);

        WebElement passwordnumber = driver.findElement(By.xpath("//input[@id='pid']"));
        passwordnumber.sendKeys(Constant.PASSWORD);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", passwordnumber);

        // 4. Click on "Register" button
        WebElement register = driver.findElement(By.xpath("//input[@title='Register']"));
        register.click();

        // Assertion to check if the new account is created
        WebElement successMessage = driver.findElement(By.xpath("//div[@id='content']/p"));
        Assert.assertTrue(successMessage.isDisplayed());
        Assert.assertEquals(successMessage.getText(), "Thank you for registering your account.");
    }

    //User can't login with an account hasn't been activated
    @Test
    public void TC08() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123123");

        // 4. Click on "Login" button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        // Assertion to check if the expected error message is displayed
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/p"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "Invalid username or password. Please try again.");
    }

    //User can change password
    @Test
    public void TC09() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p/input")).click();
        driver.findElement(By.xpath("//a[@href='/Account/ChangePassword.cshtml']")).click();
        WebElement cpassword = driver.findElement(By.xpath("//*[@id=\"currentPassword\"]"));
        cpassword.sendKeys(Constant.PASSWORD);
        WebElement newpass = driver.findElement(By.xpath("//input[@id='newPassword']"));
        newpass.sendKeys(Constant.PASSWORD);
        WebElement confirmpass = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        confirmpass.sendKeys(Constant.PASSWORD);
        WebElement changepass = driver.findElement(By.xpath("//*[@id=\"ChangePW\"]/fieldset/p/input"));
        changepass.click();
        WebElement successMessage = driver.findElement(By.xpath("//*[@id=\"ChangePW\"]/fieldset/p[1]"));
        Assert.assertTrue(successMessage.isDisplayed());
        Assert.assertEquals(successMessage.getText(), "Your password has been updated.");
    }

    //User can't create account with "Confirm password" is not the same with "Password"
    @Test
    public void TC10() {
        driver.findElement(By.xpath("//span[normalize-space()='Register']")).click();

        // 3. Enter valid information into all fields except "Confirm password" is not the same with "Password"
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys(Constant.USERNAME);

        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys(Constant.PASSWORD);

        WebElement repassword = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        repassword.sendKeys("3213123");  // Different from the Password

        WebElement passwordnumber = driver.findElement(By.xpath("//input[@id='pid']"));
        passwordnumber.sendKeys(Constant.PASSWORD);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", passwordnumber);

        // 4. Click on "Register" button
        WebElement register = driver.findElement(By.xpath("//input[@title='Register']"));
        register.click();

        // Assertion to check if the expected error message is displayed
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "There're errors in the form. Please correct the errors and try again.");
    }

    //User can't create account while password and PID fields are empty
    @Test
    public void TC11() {
        driver.findElement(By.xpath("//span[normalize-space()='Register']")).click();

        // 3. Enter a valid email address and leave other fields empty
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys(Constant.USERNAME);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", email);

        WebElement pass = driver.findElement(By.xpath("//input[@id='password']"));
        pass.sendKeys("");  // Empty password

        WebElement repass = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        repass.sendKeys("");  // Empty confirm password

        WebElement pid = driver.findElement(By.xpath("//input[@id='pid']"));
        pid.sendKeys("");  // Empty PID

        // 4. Click on "Register" button
        WebElement register = driver.findElement(By.xpath("//input[@title='Register']"));
        register.click();

        // Assertion to check if the expected error messages are displayed
        WebElement errorMessage = driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]"));
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "There're errors in the form. Please correct the errors and try again.");

        // Assertion to check if the error messages for password and PID are displayed
        WebElement passwordErrorMessage = driver.findElement(By.xpath("//label[normalize-space()='Invalid password length']"));
        Assert.assertTrue(passwordErrorMessage.isDisplayed());

        WebElement pidErrorMessage = driver.findElement(By.xpath("//label[normalize-space()='Invalid ID length']"));
        Assert.assertTrue(pidErrorMessage.isDisplayed());
    }

    //Errors display when password reset token is blank
//    @Test
//    public void TC12() {
//        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
//        driver.findElement(By.xpath("(//a[normalize-space()='Forgot Password page'])[1]")).click();
//        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
//        email.sendKeys(Constant.USERNAME);
//        driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p[2]/input")).click();
//        driver.quit();
//    }

    //User can book 1 ticket at a time
    @Test
    public void TC14() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        //Click Login button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();
        WebElement bookTicketTab = driver.findElement(By.xpath("//span[normalize-space()='Book ticket']"));
        bookTicketTab.click();

        // 4. Select a "Depart date" from the list
        WebElement departDateElement = driver.findElement(By.xpath("//select[@name='Date']"));
        Select departDate = new Select(departDateElement);
        departDate.selectByVisibleText("12/11/2023");

        // 5. Select "Sài Gòn" for "Depart from" and "Nha Trang" for "Arrive at"
        WebElement departFromElement = driver.findElement(By.xpath("//select[@name='DepartStation']"));
        Select departFrom = new Select(departFromElement);
        departFrom.selectByVisibleText("Sài Gòn");

        WebElement arriveAtElement = driver.findElement(By.xpath("//select[@name='ArriveStation']"));
        Select arriveAt = new Select(arriveAtElement);
        arriveAt.selectByVisibleText("Nha Trang");

        // 6. Select "Soft bed with air conditioner" for "Seat type"
        WebElement seatTypeElement = driver.findElement(By.xpath("//select[@name='SeatType']"));
        Select seatType = new Select(seatTypeElement);
        seatType.selectByVisibleText("Soft bed with air conditioner");

        // 7. Select "1" for "Ticket amount"
        WebElement ticketAmountElement = driver.findElement(By.xpath("//select[@name='TicketAmount']"));
        Select ticketAmount = new Select(ticketAmountElement);
        ticketAmount.selectByVisibleText("1");

        // 8. Click on "Book ticket" button
        WebElement bookTicketButton = driver.findElement(By.xpath("//input[@value='Book ticket']"));
        bookTicketButton.click();

        // Assertion to check if the success message is displayed
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='Ticket Booked Successfully!']")));
        Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed");

        // 10. Assertion to check if the ticket information is correctly displayed
        WebElement departStation = driver.findElement(By.xpath("//tr[@class='OddRow']/td[1]"));
        WebElement arriveStation = driver.findElement(By.xpath("//tr[@class='OddRow']/td[2]"));
        WebElement seatTypeOnTicket = driver.findElement(By.xpath("//tr[@class='OddRow']/td[3]"));
        WebElement departDateOnTicket = driver.findElement(By.xpath("//tr[@class='OddRow']/td[4]"));
        WebElement amountOnTicket = driver.findElement(By.xpath("//tr[@class='OddRow']/td[7]"));

        Assert.assertEquals(departStation.getText(), "Sài Gòn");
        Assert.assertEquals(arriveStation.getText(), "Nha Trang");
        Assert.assertEquals(seatTypeOnTicket.getText(), "Soft bed with air conditioner");
        Assert.assertEquals(departDateOnTicket.getText(), "12/11/2023");
        Assert.assertEquals(amountOnTicket.getText(), "1");
    }

    //User can open "Book ticket" page by clicking on "Book ticket" link in "Train timetable" page
    @Test
    public void TC15() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();
        WebElement timetable = driver.findElement(By.xpath("//span[normalize-space()='Timetable']"));
        timetable.click();

        // Click on "book ticket" link of the route from "Huế" to "Sài Gòn"
        WebElement bookTicketLink = driver.findElement(By.xpath("//td[text()='Huế' and following-sibling::td[text()='Sài Gòn']]/following-sibling::td/a[text()='book ticket']"));

        // Use JavascriptExecutor to click the element
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", bookTicketLink);

        // Verify "Book ticket" page is loaded with correct "Depart from" and "Arrive at" values
        WebElement departFromElement = driver.findElement(By.xpath("//select[@name='DepartStation']"));
        WebElement arriveAtElement = driver.findElement(By.xpath("//span[@id='ArriveStation']"));

        String departFromValue = departFromElement.getText();
        String arriveAtValue = arriveAtElement.getText();

        // Perform assertions
        Assert.assertEquals("Huế", departFromValue);
        Assert.assertEquals("Sài Gòn", arriveAtValue);
    }

    //User can cancel a ticket
    @Test
    public void TC16() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        //Click Login button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        WebElement myTicketTab = driver.findElement(By.xpath("//span[normalize-space()='My ticket']"));
        myTicketTab.click();

        // Use JavascriptExecutor to click the "Cancel" button
        WebElement cancelTicketButton = driver.findElement(By.xpath("//*[@id='content']/form/div/div/table/tbody/tr[2]/td[11]/input"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", cancelTicketButton);

        // Switch to the alert and accept it (click "OK" button)
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    @AfterMethod
    public void tearDown() {
        // Đóng trình duyệt sau khi mỗi test case
        driver.quit();
    }
}






