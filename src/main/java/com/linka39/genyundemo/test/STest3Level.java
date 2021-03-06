package com.linka39.genyundemo.test;


import com.linka39.genyundemo.entity.Article;
import com.linka39.genyundemo.entity.TreeLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lika39
 */
public class STest3Level {

    private static Integer currentLevel=1; // 当前处理的层次
    private static Integer MaxLevel=3; // 当前处理的层次

    private static StringBuffer treeInfo=null; // 树形结构内容信息

    private static Map<String, TreeLevel> allTreeInfo=new HashMap<String,TreeLevel>(); // 记录所有层次的所有结构信息

    private static boolean forward=true; // 执行方向 true为前进false为后退


    public static void main(String[] args) {
        STest3Level test3Level=new STest3Level();
        String shareUrl = "https://pan.baidu.com/s/1-ZWnyaCbdfHR_WNLhvqBUw";
        String password = "1n1t";
        /*String shareUrl = "https://pan.baidu.com/s/1A1sFHYrzc13vpoK4x6jtbw";
        String password = "9i4n";*/
        //String shareUrl="https://pan.baidu.com/s/1-eQp61mhOwpAUIVEPJ7QfQ";
        //String password="h684";
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        Map<String,Object> preferences=new HashMap<String,Object>();
        ChromeOptions options=new ChromeOptions();
        preferences.put("profile.managed_default_content_settings.images",2);
        options.setExperimentalOption("prefs",preferences);
        WebDriver driver = new ChromeDriver(options);
        driver.get(shareUrl);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.tagName("body")).isDisplayed();
                return loadcomplete;
            }
        });
        String title = driver.getTitle();
        boolean hasPassword = "百度网盘 请输入提取码".equals(title); // 是否有密码
        if(hasPassword){
            WebElement pInput = driver.findElement(By.cssSelector(".QKKaIE.LxgeIt"));
            WebElement btn = driver.findElement(By.cssSelector(".g-button-right"));
            pInput.sendKeys(password);
            btn.click();
        }

        Article article=new Article();
        article.setShare_url(shareUrl);
        article.setPassword(password);
        try {
            test3Level.gen3Level(driver,article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(article);
        driver.close(); // 浏览器关闭
        driver.quit(); // 释放资源
    }

    /**
     * 生成三层结构的树结构资源内容信息
     * @param driver
     * @param article
     * @throws Exception
     */
    public static void gen3Level(WebDriver driver, Article article)throws Exception{

        treeInfo=new StringBuffer();
        try {
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.cssSelector(".EgMMec"));
            System.out.println("是目录");
            delCatalog(driver);
            System.out.println(treeInfo.toString());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("是文件");
            dealFile(driver);
        }
    }

    /**
     * 处理文件
     */
    public static void dealFile(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean loadcomplete = d.findElement(By.cssSelector(".file-name")).isDisplayed();
                return loadcomplete;
            }
        });
        WebElement fileNameEle = driver.findElement(By.cssSelector(".file-name"));
        treeInfo.append(fileNameEle.getText());
    }

    /**
     * 打印层次
     */
    private static void printLine(int n){
        for(int i=2;i<=n;i++) {
            if(i<=n-1){
                System.out.print("    ");
                treeInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            }else{
                System.out.print("|____");
                treeInfo.append("|____");
            }
        }
    }

    private static boolean backParent(WebDriver driver){
        if(currentLevel==1) {
            return true;
        }else {
            --currentLevel;
            forward=false;
            driver.navigate().back();
            delCatalog(driver);// 返回后递归调用
        }
        return false;
    }

    /**
     * 处理目录
     */
    public static void delCatalog(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            //延迟响应，看看是否加载成功
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d){
                    boolean loadcomplete = d.findElement(By.cssSelector(".g-clearfix.AuPKyz")).isDisplayed();
                    return loadcomplete;
                }
            });
        } catch (Exception e) {
            allTreeInfo.remove(String.valueOf(currentLevel));
            if (backParent(driver)) return;
            return;
        }
        List<WebElement> allElement = driver.findElements(By.cssSelector(".g-clearfix.AuPKyz"));
        List<WebElement> elements = driver.findElements(By.cssSelector(".filename"));

        int catalogNumber = 0; // 目录数据
        List<WebElement> fileEleList = new LinkedList<WebElement>();
        List<WebElement> fileNameList = new LinkedList<WebElement>();
        for (int i = 0; i < allElement.size(); i++) {
            WebElement webElement = allElement.get(i);
            WebElement element = null;
            try {
                element = webElement.findElement(By.cssSelector(".JS-fileicon.dir-small"));
            } catch (Exception e){
            } finally {// 如果获取的不是文件夹 或者 结构目录为3时，执行打印操作
                if (element == null || currentLevel == MaxLevel) {
                    WebElement webElement1 = elements.get(i);
                    if (forward || currentLevel == MaxLevel) {
                        printLine(currentLevel);
                        System.out.println(webElement1.getText());
                        treeInfo.append(webElement1.getText() + "<br/>");
                    }
                    fileNameList.add(webElement.findElement(By.cssSelector(".filename")));
                    fileEleList.add(webElement);
                } else {
                    catalogNumber++;
                }
                continue;
            }
        }
        // 去除为文件的标签对象
        /*for (WebElement ele : fileEleList) {
            allElement.remove(ele);
        }*/
        for (WebElement ele : fileNameList) {
            elements.remove(ele);
        }
        // 如果当前文件夹数目为0，并且为主目录时结束递归
        if (catalogNumber == 0 && currentLevel == 1) {
            return;
        }
        // 如果当前文件夹数目为0时，浏览器后退，allTreeInfo删除这一目录索引（因为文件已经打印了）
        if (catalogNumber == 0) {
            allTreeInfo.remove(String.valueOf(currentLevel));
            --currentLevel;
            forward = false;
            driver.navigate().back();
            delCatalog(driver);
        } else {// allTreeInfo并没有添加此索引时
            if (allTreeInfo.get(String.valueOf(currentLevel)) == null) {
                List<String> allInfo = new LinkedList<String>();
                for (WebElement e : elements) {
                    String text = e.getText();
                    allInfo.add(text);
                }
                allTreeInfo.put(String.valueOf(currentLevel), new TreeLevel(0, allInfo));
            }
        }

        TreeLevel treeLevel = allTreeInfo.get(String.valueOf(currentLevel));
        if (treeLevel == null) {
            return;
        }
        //如果当前节点不为null时
        Integer currentIndex = treeLevel.getCurrentIndex();
        if(currentIndex<elements.size()){
            WebElement webElement = elements.get(currentIndex);
            printLine(currentLevel);
            treeInfo.append(webElement.getText()+"<br/>");
            System.out.println(webElement.getText());
            String winHandleBefore = driver.getWindowHandle();
            webElement.click();
            for(String winhandle:driver.getWindowHandles()){
                if(winhandle.equals(winHandleBefore)){
                    continue;
                }
                // 进行窗口的句柄切换，实现点击的跳转
                driver.switchTo().window(winhandle);
                break;
            }
            treeLevel.setCurrentIndex(currentIndex+1);
            allTreeInfo.put(String.valueOf(currentLevel),treeLevel);
            //如果是第一层内容时，将删除下一索引，以便重新塑造
            if(currentLevel != MaxLevel)
                allTreeInfo.remove(String.valueOf(currentLevel+1));
            ++currentLevel;

            forward=true;
            delCatalog(driver);
        }else{
            // 当遍历的只是一个文件夹时，删除当前索引
            if(currentLevel==1){
                allTreeInfo.remove(String.valueOf(currentIndex));
                return;
            }else{
                allTreeInfo.remove(String.valueOf(currentIndex+1));
                --currentLevel;
                forward=false;
                driver.navigate().back();
                delCatalog(driver);
            }
        }
    }
}
