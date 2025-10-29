package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;


public class Bai19_ICEHRM_LoginTest extends BasicTest {


    @DataProvider(name = "testDataFeed")

    public Object[][] testDataFeed() {

         return new Object[][]{

            {"admin", "admin", false},
            {"admin", "admin123",true},
            
         };
    }

    @Test(dataProvider = "testDataFeed")

    public void LoginTest(String username, String password, boolean expectedMesageErrorDisplay) throws Exception {
        // Mở trang đăng nhập
        String url = "https://icehrm-open.gamonoid.com/login.php";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);

        //Nhập email
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='username']"));
        emailInput.sendKeys(username);

        //Nhập password
         WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
         passwordInput.sendKeys(password);

         //Click nút đăng nhập
        WebElement loginBtn = driver.findElement(By.xpath("//button[normalize-space()='Log in']"));
        loginBtn.click();
        Utils.hardWait(3000);

        if(expectedMesageErrorDisplay == false){
            // Login thành công: Mất nút login
            boolean isLoginDisplay = isElementDisplayed(loginBtn);
            Assert.assertFalse(isLoginDisplay, "Login success");
        } else {
            // Login Fail: show error
            WebElement errorMessageText = driver.findElement(By.xpath("//div[@class='alert alert-danger']"));
            Assert.assertTrue(errorMessageText.isDisplayed());
            Assert.assertEquals(errorMessageText.getText().trim(), "Login failed");
        }
    }

    public boolean isElementDisplayed(WebElement element){

        try {
            return element.isDisplayed();
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        
    }
}