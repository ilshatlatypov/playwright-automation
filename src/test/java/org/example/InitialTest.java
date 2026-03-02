package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InitialTest {
    
    private static Playwright playwright;
    private static Browser browser;
    
    private BrowserContext context;
    private Page page;

    @BeforeAll
    static void beforeAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void afterAll() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void setUp() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    void shouldVerifyInitialPageTitle() {
        page.navigate("https://demoqa.com");
        String title = page.title();
        assertEquals("demosite", title, "The page title has unexpected value");
    }

    @Test
    void shouldNavigateToElementsPage() {
        page.navigate("https://demoqa.com");

        // Click on the first category card (Elements)
        page.locator(".card-body:has-text('Elements')").click();

        // Verify navigation by checking the URL
        String currentUrl = page.url();
        assertTrue(currentUrl.contains("elements"), "The URL should contain 'elements' after clicking the card");
    }
}
