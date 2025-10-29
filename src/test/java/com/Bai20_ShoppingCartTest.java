package com;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;




public class Bai20_ShoppingCartTest extends BasicTest {

        // Dữ liệu đăng nhập

        @DataProvider(name="LoginDataFeed")
        public Object[][] LoginDataFeed() {
        return new Object[][]{
            {"huadongxuan852@gmail.com", "xuanice123", "merc", "Bơm nước xe Mercedes"}           };
        }

    @Test(dataProvider = "LoginDataFeed")

    
    public void shoppingCartTest(String userName, String passWord, String keyWord, String expectedProductName) throws Exception {
        loginAccount(userName, passWord);
        searchAndOpenProduct(keyWord, expectedProductName);
        storeBadgeBeforeAdd(expectedProductName);
        addProductToCart(expectedProductName);
        verifyProductInCart(expectedProductName);
        deleteProductFromCart();
    
    }
        
        private int countBefore;

        private void loginAccount(String userName, String passWord){
            // Mở trang đăng nhập
        String url = "https://bantheme.xyz/hathanhauto/tai-khoan/";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);
        //  Nhập username và password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='username']")))
        .sendKeys(userName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='password']")))
        .sendKeys(passWord);

        //Click nút "Đăng nhập"
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='login']")));
        loginBtn.click();

        //Chờ URL đổi sang trang tài khoản 
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Xin chào')]")));

        // Kiểm đăng nhập thành công (nút login biến mất)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@name='login']")));
        System.out.println("Đăng nhập thành công");
    }

        private void searchAndOpenProduct(String keyWord, String expectedProductName){
             //Tìm kiếm sản phẩm
         WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@id='s'])[1]")));
        searchInput.sendKeys(keyWord);
        searchInput.submit();

        //Chọn kết quả đầu tiên (Bơm nước xe)
        WebElement firstResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + expectedProductName + "')][1]")));
        firstResult.click();
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='pa_xuat-xu']")));
        dropdown.click();

        //Chọn option
        WebElement optionSelect= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='pa_xuat-xu']/option[2]")));
        optionSelect.click();
    }

        private void storeBadgeBeforeAdd(String expectedProductName){

        //Badge trước khi thêm
        WebElement badgeBeforeAdd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='d-table-cell link-cart']/a/b")));
        String badgeTextBefore = badgeBeforeAdd.getText().trim();
         if (badgeTextBefore.isEmpty()) {
            countBefore = 0;
        } else {
        countBefore = Integer.parseInt(badgeTextBefore);
    
    }

} 
        
        private void addProductToCart(String expectedProductName){
        //Thêm sản phẩm vào giỏ hàng
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Thêm')]")));
        addBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='product-name']/a")));

    }

        private void verifyProductInCart(String expectedProductName){

        //Kiểm tra badge tăng sau khi thêm
        WebElement badgeAfterAdd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='d-table-cell link-cart']/a/b")));
        int countAfter = Integer.parseInt(badgeAfterAdd.getText());
        int badgeAdd = 1;
        Assert.assertEquals(countAfter, countBefore + badgeAdd, "Badge giỏ hàng tăng không đúng!");

        //Kiểm tra mở giỏ hàng sau khi thêm sản phẩm
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/gio-hang/"), "Không mở trang giỏ hàng sau khi thêm!");

        //Kiểm tra sản phẩm trong giỏ hàng
        WebElement productName = driver.findElement(By.xpath("//td[@class='product-name']/a"));
        Assert.assertTrue(productName.getText().contains(expectedProductName), "Tên sản phẩm không đúng!");

        // Lấy giá sản phẩm
        WebElement priceProduct = driver.findElement(By.xpath("//td[@class='product-price']//bdi"));
        String priceText = priceProduct.getText().replaceAll("[^\\d]", ""); 
        int price = Integer.parseInt(priceText);
        Assert.assertTrue(price > 0, "Giá tiền hiển thị sai!");

        //Kiểm tra số lượng
        WebElement qtyInCart  = driver.findElement(By.xpath("//input[contains(@class,'qty')]"));
        int qty = Integer.parseInt(qtyInCart .getAttribute("value"));
        Assert.assertTrue(qty > 0, "Số lượng không đúng!");

        // Lấy subtotal và verify tổng
        WebElement subTotalProduct = driver.findElement(By.xpath("//td[@class='product-subtotal']//bdi"));
        String subtotalText = subTotalProduct.getText().replaceAll("[^\\d]", "");
        int subtotal = Integer.parseInt(subtotalText);
        Assert.assertEquals(subtotal, price * qty, "Tổng giá không đúng!");
    }

         private void deleteProductFromCart(){

        //kiểm tra noti trc khi xóa
        WebElement badgeBeforeDelete = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='d-table-cell link-cart']/a/b")));
        int countBeforeDelete = Integer.parseInt(badgeBeforeDelete.getText());

        //Kiểm tra nút xóa và bấm xóa
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.remove")));
        Assert.assertTrue(deleteBtn.isEnabled());
        deleteBtn.click();

        //kiểm tra khi xóa sp thành công

        WebElement deleteProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='woocommerce-message']")));
        Assert.assertTrue(deleteProduct.isDisplayed());
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='d-table-cell link-cart']/a/b"),
        String.valueOf(countBeforeDelete))));  

        //Kiểm tra noti sau khi xóa sản phẩm
        WebElement badgeAfterDelete = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='d-table-cell link-cart']/a/b")));
        int countAfterDeleted = Integer.parseInt(badgeAfterDelete.getText());
        Assert.assertEquals(countBeforeDelete - 1, countAfterDeleted);
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
