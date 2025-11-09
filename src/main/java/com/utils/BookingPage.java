package com.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingPage extends BasicTest {
   //protected WebDriver driver;
    
    By requestBtn = By.xpath("//button[contains(.,'Yêu cầu đặt')]"); 
    
   public BookingPage(WebDriver driver) {
        this.driver = driver; 
        // KHỞI TẠO BIẾN 'wait' ĐƯỢC KẾ THỪA
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60)); 
        PageFactory.initElements(driver, this);
    }


    public void requestBooking() {
        // 1. Chờ phần tử hiển thị (Visible) trước. Lệnh này cũng tìm lại phần tử.
        
        WebElement requestButton = waitElementVisible(requestBtn); 

        // 2. Cuộn xuống (ScrollIntoView) để đảm bảo nút nằm trong khung nhìn và không bị che.
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", requestButton);
        
        // 3. Chờ phần tử có thể click được (lệnh này sẽ tìm lại lần nữa để đảm bảo) và click.
        waitElementClickable(requestBtn).click(); 

        // Chờ form yêu cầu đặt hiện ra
        waitElementVisible(By.xpath("//span[text()='Yêu cầu đặt Combo']"));
    }
}