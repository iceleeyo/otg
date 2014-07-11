/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.uat;

import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.*;

/**
 *
 * @author 001595
 */
public abstract class AbstractPageObject {

    protected WebDriver driver;
    private final static int MAX_WAIT_SECONDS = 10;

    public AbstractPageObject(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public AbstractPageObject() {
        //for PageFactory
    }

    protected String waitThenConfirm(long milliseconds) throws InterruptedException {
        Thread.sleep(500L);
        String text = acceptAlert();
        Thread.sleep(milliseconds);
        return text;
    }

    protected String acceptAlert() {
        Alert alert = driver.switchTo().alert();
        String text = alert.getText().trim();
        alert.accept();
        return text;
    }

    protected void clearAndType(WebElement input, String text) {
        input.clear();
        input.sendKeys(text);
    }

    protected void clearAndChecked(WebElement checkbox) {
        if ("true".equals(
                checkbox.getAttribute("checked"))) {
            checkbox.click();
        }
        checkbox.click();
    }

    protected String switchToWindow(String windowName) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, MAX_WAIT_SECONDS);
        final Date until = c.getTime();

        while (Calendar.getInstance().getTime().before(until)) {
            try {
                this.driver.switchTo().window(windowName);
            } catch (NoSuchWindowException e) {
                continue;
            }
            break;
        }
        return driver.getWindowHandle();
    }
}
