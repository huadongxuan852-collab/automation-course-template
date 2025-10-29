package com;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;
// import org.openqa.selenium.we;


public class Bai18_TabTest extends BasicTest {


    @DataProvider(name="SuccessLogin")
        public Object[][] SuccessLogin() {
        return new Object[][]{
            {"huadongxuan852@gmail.com", "xuanice123"},
          };
        }

    @Test(dataProvider = "SuccessLogin", priority = 1)
    public void tabTest(String emailInput, String passWord) throws Exception {
        // Mở trang đăng nhập
        String url = "https://bantheme.xyz/hathanhauto/tai-khoan/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);

        //Nhập email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='username']"))).sendKeys(emailInput);
        //Nhập password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='password']"))).sendKeys(passWord);

         //Click nút đăng nhập
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
        loginBtn.click();

         //Chờ URL đổi sang trang tài khoản 
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Xin chào')]")));

        // Kiểm đăng nhập thành công (nút login biến mất)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@name='login']")));
        System.out.println("Đăng nhập thành công");

        //Lưu lại handle của tab hiện tại (tab cũ)
         String oldTab = driver.getWindowHandle();

         //Mở tab mới 
         Utils.newtab(driver, "https://bantheme.xyz/hathanhauto");

         //Đóng tab cũ
         driver.switchTo().window(oldTab).close();

         //Chuyển sang tab mới vừa mở
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        //Click nút "Đăng nhập" trên tab mới
       WebElement lgBtn =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//strong[text()='Đăng nhập']")));
       lgBtn.click();

       //Kiểm tra user vẫn đang đăng nhập
       WebElement verifyAccount = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//p[contains(text(), 'Xin chào')]"))));
       Assert.assertTrue(verifyAccount.isDisplayed(),("Không tìm thấy tài khoản"));


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

