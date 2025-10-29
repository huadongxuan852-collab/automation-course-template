package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;


public class Bai16_LoginTest extends BasicTest {


    @DataProvider(name="SuccessLogin")
        public Object[][] SuccessLogin() {
        return new Object[][]{
            {"huadongxuan852@gmail.com", "xuanice123"},

              };
        }

         @DataProvider(name="FailedLogin")
        public Object[][] FailedLogin() {
        return new Object[][]{
             {"huadongxuan852@gmail.com", ""}
              };
        }

    @Test(dataProvider = "SuccessLogin", priority = 1)
    public void loginTestSuccess(String emailInput, String passWord) throws Exception {
        // Mở trang đăng nhập
        String url = "https://bantheme.xyz/hathanhauto/tai-khoan/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);

        //Nhập email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='username']"))).sendKeys(emailInput);
        //Nhập password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']"))).sendKeys(passWord);

         //Click nút đăng nhập
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[@name='login']"))));
        loginBtn.click();


         //Chờ URL đổi sang trang tài khoản 
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Xin chào')]")));

        // Kiểm đăng nhập thành công (nút login biến mất)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@name='login']")));
        System.out.println("Đăng nhập thành công");
        }


   @Test(dataProvider = "FailedLogin", priority = 2)
    public void loginTestFailed(String emailInput, String passWord) throws Exception{
         // Mở trang đăng nhập
        String url ="https://bantheme.xyz/hathanhauto/tai-khoan/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);
        
       //Nhập email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='username']"))).sendKeys(emailInput);
        //Nhập password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']"))).sendKeys(passWord);
        
         //Click nút đăng nhập
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
        loginBtn.click();

        // Kiểm tra lỗi
         WebElement errorrMessageText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='woocommerce-error']/li")));
        Assert.assertTrue(errorrMessageText.isDisplayed());

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

