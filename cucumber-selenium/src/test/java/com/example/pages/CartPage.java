package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addProductToCartByName(String productName) {
        // scroll to product and try multiple add-to-cart selectors
        String[] xpaths = new String[] {
                String.format("//div[contains(.,'%s')]//a[contains(.,'Add to cart')]|//h2[contains(.,'%s')]/ancestor::div[1]//a[contains(.,'Add to cart')]", productName, productName),
                String.format("//a[normalize-space()='%s']/ancestor::div[1]//a[contains(.,'Add to cart')]", productName),
                String.format("//h2[contains(normalize-space(.),'%s')]/following::a[contains(.,'Add to cart')][1]", productName),
                String.format("//div[contains(@class,'product') and .//h2[contains(.,'%s')]]//a[contains(.,'Add to cart')]", productName)
        };

        WebElement addBtn = null;
        for (String xp : xpaths) {
            try {
                addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xp)));
                break;
            } catch (Exception ignored) {}
        }

        if (addBtn == null) {
            throw new RuntimeException("Add to cart button not found for product: " + productName);
        }

        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addBtn);
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        addBtn.click();

        // wait for the cart modal or indicator to appear
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'cart_ajax_modal') or contains(@class,'modal-cart')]") ),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href,'/view_cart') and (contains(.,'Cart') or contains(.,'cart'))]"))
            ));
        } catch (Exception ignored) {}
    }

    public void clickCartButton() {
        // If a modal is shown after adding to cart, prefer using its 'View Cart' button
        try {
            WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='cartModal']//*[contains(normalize-space(.),'View Cart') or contains(normalize-space(.),'View cart') or contains(normalize-space(.),'View Cart')]")
            ));
            viewCart.click();
            return;
        } catch (Exception ignored) {
            // no modal 'View Cart' button found
        }

        // Try to close the modal if present
        try {
            WebElement close = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='cartModal']//button[contains(@class,'close') or contains(normalize-space(.),'Continue') or contains(normalize-space(.),'Close') or contains(.,'×')]")
            ));
            close.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("cartModal")));
        } catch (Exception ignored) {
            // ignore and proceed
        }

        // Finally click header Cart link via JS to avoid interception by overlays
        WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(.,'Cart') or contains(.,'cart') or @href='/view_cart']")));
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", cart);
        } catch (Exception e) {
            // fallback to normal click
            cart.click();
        }
    }

    public boolean isCartPageDisplayed() {
        try {
            // check for cart page indicators: specific heading, table of cart items, or URL path
            boolean heading = !driver.findElements(By.xpath("//h2[contains(.,'Shopping Cart') or contains(.,'Your Cart') or contains(.,'Cart')]")).isEmpty();
            boolean table = !driver.findElements(By.xpath("//table[contains(@class,'cart') or contains(@class,'table') and .//th[contains(.,'Product')]]")).isEmpty();
            boolean url = driver.getCurrentUrl().contains("/view_cart") || driver.getCurrentUrl().contains("/cart");
            return heading || table || url;
        } catch (Exception e) {
            return false;
        }
    }
}
