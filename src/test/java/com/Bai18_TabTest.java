package com;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;
// import org.openqa.selenium.we;


public class Bai18_TabTest extends BasicTest {


    @Test()
    public void tabTest() throws Exception {
        // Mở trang đăng nhập
        String url = "https://bantheme.xyz/hathanhauto/tai-khoan/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);

        //Nhập email
        WebElement emailInput = driver.findElement(By.xpath("//*[@id='username']"));
        emailInput.sendKeys("huadongxuan852@gmail.com");

        //Nhập password
         WebElement passwordInput = driver.findElement(By.xpath("//*[@id='password']"));
         passwordInput.sendKeys("xuanice123");

         //Click nút đăng nhập
        WebElement loginBtn = driver.findElement(By.xpath("//button[@name='login']"));
        loginBtn.click();
        Utils.hardWait(3000);

        // Kiểm tra đăng nhập thành công
        boolean isLoginDisplay  = isElementDisplayed(loginBtn);
        Assert.assertFalse(isLoginDisplay);
        Utils.hardWait(3000);
        

        // WebElement body = driver.findElement(By.tagName("body"));

        // Object[] windowHandles=driver.getWindowHandles().toArray();
        // driver.switchTo().window((String) windowHandles[1]);
        // driver.switchTo().newWindow(WindowType.TAB);

        String oldTab = driver.getWindowHandle();

        //mở tab mới
       ((JavascriptExecutor) driver).executeScript("window.open('https://bantheme.xyz/hathanhauto','_blank');");
       ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
       driver.switchTo().window(tabs.get(1));
       Assert.assertEquals(driver.getCurrentUrl(), "https://bantheme.xyz/hathanhauto/");
       Utils.hardWait(2000);

       driver.switchTo().window(oldTab);
       driver.close();
       System.out.println("Closed the old tab.");
       Utils.hardWait(2000);

       driver.switchTo().window(tabs.get(1));
       Utils.hardWait(2000);

       WebElement lgBtn = driver.findElement(By.xpath("//strong[text()='Đăng nhập']"));
       lgBtn.click();
       Utils.hardWait(2000);

       WebElement verifyAccount = driver.findElement(By.xpath("//p/strong[1]"));
       Assert.assertTrue(verifyAccount.isDisplayed(),("Không tìm thấy tài khoản"));


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

