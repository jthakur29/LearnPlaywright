package com.example.playwright;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;


public class BrowserContextTest {

    public static Playwright playwright;
    public static Browser browser;
    public static BrowserContext browserContext;

    Page page;

    @BeforeAll
    public static void setUpBrowser(){
        playwright =Playwright.create();
         browser=playwright.chromium().launch(
            new BrowserType.LaunchOptions()
            .setHeadless(false)
            .setArgs(Arrays.asList("--no-sandbox" , "--disable-extensions"))
        );
        browserContext= browser.newContext();

    }

    @BeforeEach
    public void setUp(){
        page=browserContext.newPage();
    }

    @AfterAll
    public static void tearDown(){
        browser.close();
        playwright.close();
    }

    @Test
    void testPageTitle(){
        page.navigate("https://practicesoftwaretesting.com/");
        String title=page.title();
        System.out.println(title);
        Assertions.assertTrue(title.contains("Practice Software Testing"));
    }   
}
