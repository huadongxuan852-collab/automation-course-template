package com.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class ShoppingSteps {

    WebDriver driver;
    WebDriverWait wait;

    private int countBefore;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I login with {string} and {string}")
    public void login(String userName, String passWord) {

        String url = "https://bantheme.xyz/hathanhauto/tai-khoan/";
        driver.get(url);

        waitElementVisible("//input[@id='username']").sendKeys(userName);
        waitElementVisible("//input[@id='password']").sendKeys(passWord);
        waitElementClickable("//button[@name='login']").click();

        waitElementVisible("//p[contains(text(), 'Xin chào')]");
        System.out.println("Đăng nhập thành công");
    }

    @When("I search product {string} and open {string}")
    public void searchAndOpen(String keyword, String expectedProductName) {

    WebElement searchInput = waitElementVisible("(//input[@id='s'])[1]");
    searchInput.clear();
    searchInput.sendKeys(keyword + Keys.ENTER);

    WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(text(),'" + expectedProductName + "')][1]")));
    productLink.click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pa_xuat-xu")));

    WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pa_xuat-xu")));
    Select select = new Select(dropdown);
    select.selectByIndex(1);

    wait.until(driver -> new Select(driver.findElement(By.id("pa_xuat-xu"))).getFirstSelectedOption().isDisplayed());
}


    @When("I store badge number before adding")
    public void storeBadgeBeforeAdd() {

        WebElement badgeBeforeAdd = waitElementVisible("//div[@class='d-table-cell link-cart']/a/b");
        String text = badgeBeforeAdd.getText().trim();

        countBefore = text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    @When("I add product to cart")
    public void addProductToCart() {
        waitElementClickable("//button[contains(text(),'Thêm')]").click();
        waitElementVisible("//td[@class='product-name']/a");
    }

    @Then("I verify product {string} in cart")
    public void verifyProduct(String expectedProductName) {

        WebElement badgeAfterAdd = waitElementVisible("//div[@class='d-table-cell link-cart']/a/b");
        int countAfter = Integer.parseInt(badgeAfterAdd.getText());

        Assert.assertEquals(countAfter, countBefore + 1, "Badge giỏ hàng tăng không đúng!");

        Assert.assertTrue(driver.getCurrentUrl().contains("/gio-hang/"));

        WebElement productName = waitElementVisible("//td[@class='product-name']/a");
        Assert.assertTrue(productName.getText().contains(expectedProductName));

        WebElement priceProduct = waitElementVisible("//td[@class='product-price']//bdi");
        int price = Integer.parseInt(priceProduct.getText().replaceAll("[^\\d]", ""));
        Assert.assertTrue(price > 0);

        WebElement qtyInCart = waitElementVisible("//input[contains(@class,'qty')]");
        int qty = Integer.parseInt(qtyInCart.getAttribute("value"));

        WebElement subTotalProduct = waitElementVisible("//td[@class='product-subtotal']//bdi");
        int subtotal = Integer.parseInt(subTotalProduct.getText().replaceAll("[^\\d]", ""));

        Assert.assertEquals(subtotal, price * qty);
    }

    @Then("I delete product from cart")
    public void deleteProductFromCart() {

        WebElement badgeBeforeDelete = waitElementVisible("//div[@class='d-table-cell link-cart']/a/b");
        int countBeforeDelete = Integer.parseInt(badgeBeforeDelete.getText());

        waitElementClickable("a.remove").click();

        waitElementVisible("//div[@class='woocommerce-message']");

        if (countBeforeDelete == 1) {
            waitElementVisible("//p[contains(@class,'cart-empty')]");
        } else {
            WebElement badgeAfterDelete = waitElementVisible("//div[@class='d-table-cell link-cart']/a/b");
            int countAfterDelete = Integer.parseInt(badgeAfterDelete.getText());
            Assert.assertEquals(countAfterDelete, countBeforeDelete - 1);
        }
    }


    protected WebElement waitElementVisible(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    protected WebElement waitElementVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitElementClickable(String locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }

    protected WebElement waitElementClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
}
