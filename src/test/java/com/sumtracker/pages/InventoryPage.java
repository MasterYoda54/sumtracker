package com.sumtracker.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class InventoryPage extends BasePage {
    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart(String productName) {
        inventoryItems.stream()
                .filter(item -> item.getText().contains(productName))
                .findFirst()
                .ifPresent(item -> {
                    WebElement addToCartButton = item.findElement(org.openqa.selenium.By.cssSelector("[data-test^='add-to-cart']"));
                    addToCartButton.click();
                });
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void navigateToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }
}