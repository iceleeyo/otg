/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.uat;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

/**
 *
 * @author 001595
 */
public class ChannelAdminPage extends AbstractPageObject {

    private static final String URL = "otg/admin/channelAdmin.htm";
    @FindBy(id = "queryButton")
    private WebElement queryButton;
    @FindBy(id = "channelId")
    private WebElement channelSelector;
    private List<WebElement> channelOptions;
    @FindBy(id = "currencyTdDynamic")
    private WebElement availableCurrenciesTd;
//    @FindBy(id = "autoId")
//    private WebElement queryButton;
//    @FindBy(id = "after")
//    @CacheLookup
//    private WebElement gatewayList;

    public void open(WebDriver driver, String host) {
        this.driver = driver;
        this.driver.get(host + URL);
    }

    public ChannelAdminPage() {
        super();
    }

    private ChannelAdminPage(WebDriver driver) {
        super(driver);
    }

    public void query(String channelId) {
        this.channelOptions = this.channelSelector.findElements(By.tagName("option"));
        for (WebElement option : this.channelOptions) {
            if (option.getAttribute("value").equals(channelId)) {
                option.click();
                this.queryButton.click();
                return;
            }
        }

    }

    public void switchToUpateAvailableCurrenciesForm() {
        this.driver.findElement(By.id("updateCurrencyButton")).click();
        acceptAlert();
    }

    public void updateAvailableGateways(String availableGateways) throws InterruptedException {
        clearAndType(driver.findElement(By.id("newCurrencyInput")), availableGateways);
        this.availableCurrenciesTd.findElement(By.xpath(".//INPUT[@value='保存']")).click();
    }

    public String getAvailableCurrencies() {
        return driver.findElement(By.id("currencyTdDynamic")).getText().trim();
    }

    public ChannelAdminPage removeGateways() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void switchToSaveGatewayForm() throws InterruptedException {
        Thread.sleep(500L);
        this.driver.findElement(By.id("toAddNewGatewayButton")).click();
    }

    public void saveGateway(String gatewayCode, String dialect, int priority) {
        WebElement newGatewayTr = driver.findElement(By.id("addNewTr"));
        clearAndType(newGatewayTr.findElement(By.xpath(".//td/INPUT[@id='newTrCode']")), gatewayCode);
        clearAndType(newGatewayTr.findElement(By.xpath(".//td/INPUT[@id='newTrDialect']")), dialect);
        clearAndType(newGatewayTr.findElement(By.xpath(".//td/INPUT[@id='newTrPriority']")), String.valueOf(priority));
        newGatewayTr.findElement(By.xpath(".//td/INPUT[@value='保存']")).click();
    }

    public void removeGateway(String gatewayCode) {
        List<WebElement> gatewayTds = this.driver.findElements(By.xpath("//table[@id='gatewaysTb']/tbody/tr/td"));
        for (WebElement td : gatewayTds) {
            if (td.getText().trim().equals(gatewayCode)) {
                td.findElement(By.xpath("../td/input[@type='button']")).click();
                return;
            }
        }
    }
}
