/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.uat;

import com.google.common.base.Function;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author 001595
 */
public class PartnerAdminPage extends AbstractPageObject {

    private static final String URL = "otg/admin/partnerChannelAdmin.htm";
    @FindBy(name = "queryButton")
    private WebElement queryButton;
    @FindBy(xpath = "//select[@id='partnerId']/option")
    private List<WebElement> partnerOptions;
    @FindBy(name = "updateChannelButton")
    private WebElement switchToAvailableChannelsButton;    
    @FindBy(name = "updateRecommendedGatewaysButton")
    private WebElement switchToRecommendedGatewaysButton;
//    @FindBy(id = "autoId")
//    private WebElement queryButton;
//    @FindBy(id = "after")
//    @CacheLookup
//    private WebElement gatewayList;

    public void open(WebDriver driver, String host) {
        this.driver = driver;
        this.driver.get(host + URL);
    }

    public PartnerAdminPage() {
        super();
    }

    protected PartnerAdminPage(WebDriver driver) {
        super(driver);
    }

    public void query(String partnerId) {
        for (WebElement option : this.partnerOptions) {
            if (option.getAttribute("value").equals(partnerId)) {
                option.click();
                this.queryButton.click();
                return;
            }
        }

    }

    public AvailableChannelsPage switchToAvailableChannelsPage() throws InterruptedException {
        this.switchToAvailableChannelsButton.click();
        return new AvailableChannelsPage(driver, driver.getWindowHandle());
    }
    
    public RecommendedGatewaysPage switchToRecommendedGatewaysPage() throws InterruptedException {
        this.switchToRecommendedGatewaysButton.click();
        return new RecommendedGatewaysPage(driver, driver.getWindowHandle());
    }

    public void select(String partnerId) {
        List<WebElement> gatewayTds = this.driver.findElements(By.xpath("//table[@id='tb']/tbody/tr/td"));
        for (WebElement td : gatewayTds) {
            if (td.getText().trim().equals(partnerId)) {
                td.findElement(By.xpath("../td/input[@type='button']")).click();
                return;
            }
        }
    }

    List<String> getActualChannelIds() {
        List<String> actual = new ArrayList();
        List<WebElement> channelTrs = this.driver.findElements(By.xpath("//table[@id='tb']/tbody/tr[@value != '']"));
        for (WebElement tr : channelTrs) {
            // if (tr.getText().trim().equals(partnerId)) {
            actual.add(tr.getAttribute("value").trim());
            //}
        }

        return actual;
    }
}
