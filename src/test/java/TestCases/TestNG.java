package TestCases;

import Common.Constant;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class TestNG {
    WebDriver driver;

    @BeforeMethod
    public void login() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://railwayb1.somee.com/Page/HomePage.cshtml");
    }

    @AfterTest
    public void close() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }

    @Test
    //User can log into Railway with valid username and password
    public void tc01() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

    }

    //User can't login with blank "Username" textbox
    @Test
    public void tc02() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        WebElement button = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p/input"));
        button.click();

    }

    //User cannot log into Railway with invalid password
    @Test
    public void tc03() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("312321");
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

    }

    //Login page displays when un-logged User clicks on "Book ticket" tab
    @Test
    public void tc04() {
        driver.get("http://railwayb1.somee.com/Page/HomePage.cshtml");
        driver.findElement(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']")).click();

    }

    //System shows message when user enters wrong password several times
    @Test
    public void tc05() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        for (int i = 0; i < 5; i++) {
            WebElement email = driver.findElement(By.id("username"));
            email.sendKeys(Constant.USERNAME);
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("11111111");
            WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
            button.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                driver.quit();
            }

        }
    }

    //Additional pages display once user logged in
    @Test
    public void tc06() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        WebElement ticket = driver.findElement(By.xpath("//a[@href='/Page/ManageTicket.cshtml']"));
        ticket.click();

        //Kiểm tra xem đã vào đúng trang ticket chưa
        boolean headerpost = driver.findElement(By.xpath("//h1[normalize-space()='Manage ticket']")).isDisplayed();
        if (headerpost == true) {
            System.out.println("Đã đến trang My ticket");
        } else {
            System.out.println("Không đến trang My ticket");
        }
        // Click "Chane password"
        WebElement change_password = driver.findElement(By.xpath("//span[normalize-space()='Change password']"));
        change_password.click();
        boolean headerpost2 = driver.findElement(By.xpath("//h1[normalize-space()='Change password']")).isDisplayed();
        if (headerpost2 == true) {
            System.out.println("Đã đến trang change password");
        } else {
            System.out.println("Không đến trang change password");

        }
    }

    //User can create new account
    @Test
    public void tc07() {
        driver.findElement(By.xpath("//span[normalize-space()='Register']")).click();
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys(Constant.PASSWORD);
        WebElement repassword = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        repassword.sendKeys(Constant.PASSWORD);
        WebElement passwordnumber = driver.findElement(By.xpath("//input[@id='pid']"));
        passwordnumber.sendKeys(Constant.PASSWORD);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", passwordnumber);
        WebElement register = driver.findElement(By.xpath("//input[@title='Register']"));
        register.click();

    }

    //User can't login with an account hasn't been activated
    @Test
    public void tc08() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123123");
        //Click Login button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();
    }

    //User can change password
    @Test
    public void tc09() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p/input")).click();
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[8]")).click();
        WebElement cpassword = driver.findElement(By.xpath("//*[@id=\"currentPassword\"]"));
        cpassword.sendKeys(Constant.PASSWORD);
        WebElement newpass = driver.findElement(By.xpath("//input[@id='newPassword']"));
        newpass.sendKeys(Constant.PASSWORD);
        WebElement confirmpass = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        confirmpass.sendKeys(Constant.PASSWORD);
        WebElement changepass = driver.findElement(By.xpath("//*[@id=\"ChangePW\"]/fieldset/p/input"));
        changepass.click();
    }

    //User can't create account with "Confirm password" is not the same with "Password"
    @Test
    public void tc10() {
        driver.findElement(By.xpath("//span[normalize-space()='Register']")).click();
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys(Constant.PASSWORD);
        WebElement repassword = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        repassword.sendKeys("3213123");
        WebElement passwordnumber = driver.findElement(By.xpath("//input[@id='pid']"));
        passwordnumber.sendKeys(Constant.PASSWORD);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", passwordnumber);
        WebElement register = driver.findElement(By.xpath("//input[@title='Register']"));
        register.click();
    }

    //User can't create account while password and PID fields are empty
    @Test
    public void tc11() {
        driver.findElement(By.xpath("//span[normalize-space()='Register']")).click();
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys(Constant.USERNAME);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", email);

        WebElement pass = driver.findElement(By.xpath("//input[@id='password']"));
        pass.sendKeys(Constant.PASSWORD);
        WebElement repass = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        repass.sendKeys(Constant.PASSWORD);
        WebElement pid = driver.findElement(By.xpath("//input[@id='pid']"));
        pid.sendKeys("");

        WebElement register = driver.findElement(By.xpath("//input[@title='Register']"));
        register.click();
    }

    //Errors display when password reset token is blank
    @Test
    public void tc12() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        driver.findElement(By.xpath("(//a[normalize-space()='Forgot Password page'])[1]")).click();
        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
        email.sendKeys(Constant.USERNAME);
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/p[2]/input")).click();
        driver.quit();
    }

    @Test
    public void tc13(){

    }

    //User can book 1 ticket at a time
    @Test
    public void tc14() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        //Click Login button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();
        WebElement bookticket = driver.findElement(By.xpath("//span[normalize-space()='Book ticket']"));
        bookticket.click();
        WebElement bookticketform = driver.findElement(By.xpath("//legend[normalize-space()='Book ticket form']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", bookticketform);
        //handle dropdown tĩnh
        //lấy element của thẻ depart date

        WebElement element = driver.findElement(By.xpath("//select[@name='Date']"));
        Select depart_date = new Select(element);
        depart_date.selectByVisibleText("12/11/2023");

        WebElement element2 = driver.findElement(By.xpath("//select[@name='DepartStation']"));
        Select depart_from1 = new Select(element2);
        depart_from1.selectByIndex(0);


        WebElement element3 = driver.findElement(By.xpath("//select[@name='ArriveStation']"));
        Select arrive_at = new Select(element3);
        arrive_at.selectByIndex(1);

        WebElement element4 = driver.findElement(By.xpath("//select[@name='SeatType']"));
        Select seat_type = new Select(element4);
        seat_type.selectByIndex(2);

        WebElement element5 = driver.findElement(By.xpath("//select[@name='TicketAmount']"));
        Select ticket_amount = new Select(element5);
        ticket_amount.selectByVisibleText("1");

        WebElement button1 = driver.findElement(By.xpath("//input[@value='Book ticket']"));
        button1.click();
        driver.quit();
    }

    //User can open "Book ticket" page by clicking on "Book ticket" link in "Train timetable" page
    @Test
    public void tc15() throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        //Click Login button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        WebElement timetable = driver.findElement(By.xpath("//a[@href='TrainTimeListPage.cshtml']"));
        timetable.click();


        WebElement train_timetable = driver.findElement(By.xpath("//h1[normalize-space()='Train Timetable']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", train_timetable);

        boolean Check_price = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table/tbody/tr[2]/td[7]/a")).isDisplayed();
        Thread.sleep(2000);
        if (Check_price == true) {
            driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/table/tbody/tr[2]/td[7]/a")).click();
            System.out.println("Đã click Check_price ");
        } else {
            System.out.println("Không tìm thấy Check_price ");

        }
    }

    //User can cancel a ticket
    @Test
    public void tc16() {
        driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']")).click();
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(Constant.USERNAME);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Constant.PASSWORD);
        //Click Login button
        WebElement button = driver.findElement(By.xpath("//input[@type='submit']"));
        button.click();

        WebElement book_ticket = driver.findElement(By.xpath("//span[normalize-space()='My ticket']"));
        book_ticket.click();

        WebElement manage_ticket = driver.findElement(By.xpath("//h1[normalize-space()='Manage ticket']"));
        manage_ticket.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", manage_ticket);

        WebElement deleted_ticket = driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div/table/tbody/tr[2]/td[11]/input"));
        deleted_ticket.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();

    }
}






