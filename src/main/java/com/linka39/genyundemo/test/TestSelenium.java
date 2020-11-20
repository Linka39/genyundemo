package com.linka39.genyundemo.test;


import com.linka39.genyundemo.entity.Article;
import com.linka39.genyundemo.util.DateUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

public class TestSelenium {
    public static void main(String[] args) {
        //设置驱动位置
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        //设置浏览器参数 不加载图片
        Map<String,Object> preferences = new HashMap<String,Object>();
        ChromeOptions options=new ChromeOptions();
        preferences.put("profile.managed_default_content_settings.images",2);
        options.setExperimentalOption("prefs",preferences);
        String shareUrl="https://pan.baidu.com/s/1-eQp61mhOwpAUIVEPJ7QfQ";
        String password="h684";


        WebDriver driver = new ChromeDriver();
        driver.get(shareUrl);
        //设置最多等待5s
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver d){
                boolean loadcomplete = d.findElement(By.tagName("body")).isDisplayed();
                return loadcomplete;
            }
        });

        boolean hasPassword = false; // 是否有密码
        String title = driver.getTitle();
        String title2 = "百度网盘 请输入提取码";
        hasPassword = title2.equals(title);
        /*if(.equals(title)){
            hasPassword = true;
        }*/
        if(hasPassword){
            WebElement pInput = driver.findElement(By.cssSelector(".QKKaIE.LxgeIt")); //选取的为class
            WebElement btn = driver.findElement(By.cssSelector(".g-button-right"));
            pInput.sendKeys(password);
            btn.click();
        }
        System.out.println(title);
        Article article=new Article();
        article.setShare_url(shareUrl);
        article.setPassword(password);
        try {
            genPageData(driver,article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(article);
        //driver.close(); // 浏览器关闭
        //driver.quit();  // 释放资源
    }

    /**
     * 生成数据
     * @param driver
     * @param article
     * @throws Exception
     */
    public static void genPageData(WebDriver driver, Article article)throws Exception{
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        WebElement fileNameEle = driver.findElement(By.cssSelector(".file-name"));
        article.setName(fileNameEle.getText());
        WebElement shareDateEle = driver.findElement(By.cssSelector(".share-file-info span"));
        article.setShare_date(shareDateEle.getText());
        WebElement shareUserEle = driver.findElement(By.cssSelector(".share-person-data-top a.share-person-username.global-ellipsis"));
        article.setShare_user(shareUserEle.getAttribute("textContent"));
        article.setContent(fileNameEle.getText());  // 预先设置
        article.setState(1);
        article.setincludeDate(DateUtil.getCurrentDateStr());
    }
}
