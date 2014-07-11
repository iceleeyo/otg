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
public class RecommendedGatewaysPage extends AbstractPageObject {

    private static final String WINDOW_NAME = "updateRecommendedGatewaysWin";
    private String parentPageHandle;
    private List<WebElement> partnerOptions;

    RecommendedGatewaysPage(WebDriver driver, String windowHandle) {
        super(driver);
        this.parentPageHandle = windowHandle;
        super.switchToWindow(WINDOW_NAME);
    }

    List<String> getAvaiableChannelIds() throws InterruptedException {
        List<String> channelIds = new ArrayList<String>();
        for (WebElement option : options()) {
            channelIds.add(option.getAttribute("value").trim());
        }
        return channelIds;
    }

    private List<WebElement> options() {
        return driver.findElements(By.xpath("//select[@id='channelId']/option"));
    }

    void addRecommendedGateways(String channelId, List<String> expectedGatewayCodes) throws InterruptedException {
        selectGatewaysCandidates(channelId);
        add(expectedGatewayCodes);
    }

    private void selectGatewaysCandidates(String channelId) throws InterruptedException {
        for (WebElement option : options()) {
            if (option.getAttribute("value").trim().equals(channelId)) {
                option.click();
                this.driver.findElement(By.id("queryAllGatewaysButton")).click();
            }
        }
        Thread.sleep(2000L);
    }

    private void add(List<String> expectedGatewayCodes) {
        List<WebElement> trs = this.driver.findElements(By.xpath("//table[@id='allGatewaysTb']/tbody/tr"));
        for (int i = 1; i < trs.size(); i++) {//跳过标题tr
            WebElement tr = trs.get(i);
            WebElement firstTd = tr.findElement(By.tagName("td"));
            if (expectedGatewayCodes.contains(firstTd.getText().trim())) {
                firstTd.click();
                this.driver.findElement(By.id("addButton")).click();
            }
        }
    }

    String submit() throws InterruptedException {
        this.driver.findElement(By.id("updateButton")).click();
        return this.waitThenConfirm(1000L);
    }

    void up(String channelId, String gatewayCode) throws InterruptedException {
        clickRecommended(channelId, gatewayCode, driver.findElement(By.id("upButton")));
    }

    void down(String channelId, String gatewayCode) throws InterruptedException {
        clickRecommended(channelId, gatewayCode, driver.findElement(By.id("downButton")));
    }

    void remove(String channelId, String gatewayCode) throws InterruptedException {
        clickRecommended(channelId, gatewayCode, this.driver.findElement(By.id("deleteButton")));
    }

    private void clickRecommended(String channelId, String gatewayCode, WebElement button) throws InterruptedException {
        List<WebElement> trs = this.driver.findElements(By.xpath("//table[@id='recommendedGatewaysTb']/tbody/tr"));
        for (int i = 1; i < trs.size(); i++) {//跳过标题tr
            WebElement tr = trs.get(i);
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            final String actualChannel = tds.get(1).getText().trim();

            final String actualGatewayCode = tds.get(2).getText().trim();
            if (channelId.equals(actualChannel)
                    && gatewayCode.equals(actualGatewayCode)) {
                tr.click();
                button.click();
                Thread.sleep(500L);//每次点击后要等待页面刷新，需要重新对推荐网关排序
                break;
            }
        }
    }
}
