/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.uat;

import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author 001595
 */
public class AvailableChannelsPage extends AbstractPageObject {

    private static final String WINDOW_NAME = "updateChannelWin";
    private String parentPageHandle;

    AvailableChannelsPage(WebDriver driver, String windowHandle) {
        super(driver);
        this.parentPageHandle = windowHandle;
        super.switchToWindow(WINDOW_NAME);
    }

    List<String> getActualChannelIds() throws InterruptedException {
        Thread.sleep(2000L);
        List<String> actual = new ArrayList();
        List<WebElement> checkboxes = checkboxes();
       for (WebElement checkbox : checkboxes) {
            if ("true".equals(
                    checkbox.getAttribute("checked"))) {
                actual.add(checkbox.getAttribute("value").trim());
            }
        }

        return actual;
    }

    PartnerAdminPage saveAvailableChannelsThenRefreshParent(List<String> expectedChannelIdsToBeSaved) throws InterruptedException {
        List<WebElement> checkboxes = checkboxes();
        for (WebElement checkbox : checkboxes) {
            if (expectedChannelIdsToBeSaved.contains(checkbox.getAttribute("value"))) {
                clearAndChecked(checkbox);
            }
        }
        this.driver.findElement(By.id("updateButton")).click();
        Thread.sleep(1000L);//等待提交处理完成
        this.driver.findElement(By.tagName("input")).click();
        this.driver.switchTo().window(parentPageHandle);
        System.out.println(driver.getCurrentUrl());
        return new PartnerAdminPage(driver);
    }

    private List<WebElement> checkboxes() {
        List<WebElement> result = new ArrayList<WebElement>();
        List<WebElement> trs = this.driver.findElement(By.xpath("//table[@id='tb1']")).findElements(By.tagName("tr"));
        for (int i = 1; i < trs.size(); i++) {//去除第一行标题
            result.add(trs.get(i).findElement(By.xpath("./td/input[@type='checkbox']")));

        }
        return result;
    }
}
