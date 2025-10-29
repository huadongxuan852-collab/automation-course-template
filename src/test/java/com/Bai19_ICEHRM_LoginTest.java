package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.BasicTest;


public class Bai19_ICEHRM_LoginTest extends BasicTest {


    @DataProvider(name = "testDataFeed")

    public Object[][] testDataFeed() {

         return new Object[][]{

            {"admin", "admin", false},
            {"admin", "admin123",true},
            
         };
    }

    @Test(dataProvider = "testDataFeed")

    public void LoginTest(String userName, String passWord, boolean expectedMesageErrorDisplay) throws Exception {
        // Mở trang đăng nhập
        String url = "https://icehrm-open.gamonoid.com/login.php";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);

        //Nhập email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='username']"))).sendKeys(userName);

        //Nhập password
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']"))).sendKeys(passWord);

         //Click nút đăng nhập
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Log in']")));
        loginBtn.click();


        if(expectedMesageErrorDisplay == false){
            // Login thành công: Mất nút login
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[normalize-space()='Log in']")));
        } else {
            // Login Fail: show error
            WebElement errorMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger']")));
            Assert.assertTrue(errorMessageText.getText().contains("Login failed"));
        }
    }

    /*public boolean isElementDisplayed(WebElement element){

        try {
            return element.isDisplayed();
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        
    }*/

}