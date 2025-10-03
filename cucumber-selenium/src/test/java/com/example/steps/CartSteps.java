package com.example.steps;

import com.example.pages.CartPage;
import com.example.utils.WebDriverFactory;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class CartSteps {
    private final WebDriver driver = WebDriverFactory.getDriver();
    private final CartPage cartPage = new CartPage(driver);

    @When("I add product {string} to cart")
    public void i_add_product_to_cart(String product) {
        cartPage.addProductToCartByName(product);
    }

    @Then("Verify that cart page is displayed")
    public void verify_that_cart_page_is_displayed() {
        boolean shown = cartPage.isCartPageDisplayed();
        Assert.assertTrue("Cart page not displayed", shown);
    }
}
