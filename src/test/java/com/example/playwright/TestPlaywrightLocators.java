package com.example.playwright;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;   
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;

@UsePlaywright
public class TestPlaywrightLocators {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setupBrowser(){
        playwright=Playwright.create();
        browser=playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
                .setArgs(Arrays.asList("--no-sandbox", "--no-extensions"))
        );
    }

    @BeforeEach
    void setUp(){
        browserContext=browser.newContext();
        page=browserContext.newPage();
    }
    
    @AfterEach
    void closeContext(){
        browserContext.close();
    }

    @AfterAll
    static void tearDown(){
        browser.close();
        playwright.close(); 
    }




    @DisplayName("Locating elements by text")
    @Nested
    class LocatingElementsByText {

            @BeforeEach
            void openCataloguePage(){
                openPage();
            }
            
        @DisplayName("Locating the element by text contents")
        @Test
        void byText(){
            page.getByText("Bolt Cutters").click();
            
            PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
        }


        @DisplayName("Using alt text")
        @Test
        void byAltText(){
            page.getByAltText("Combination Pliers").click();
            PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
        }



        @DisplayName("Using Title")
        @Test
            void byTitle(){
                page.getByAltText("Combination Pliers").click();
                page.getByTitle("Practice Software Testing - Toolshop").click();
            
            }

    }


    @DisplayName("Locate by TestId")
    @Nested
    class LocateElementByTestId{
        
        @BeforeEach
        void openPageCatalogue(){
            openPage();
        }
        
        @Test
        void exampleTestId(){
            playwright.selectors().setTestIdAttribute("data-test");
            page.getByTestId("search-query").fill("Pliers");
            page.getByTestId("search-submit").click();


        }    


    }

    @DisplayName("CSS Locators")
    @Nested
    class LocateElementByCSS{
        @BeforeEach
        void openPageCatalogue(){
          page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @Test
        void elementByUsingID(){
                page.locator("#first_name").fill("Jill");
                PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("Jill");
        }

        @Test
        void elementByUsingClass(){
            page.locator("#first_name").fill("Jill");
            page.locator(".btnSubmit").click();
            List<String> alertMessages= page.locator(".alert").allTextContents();
            Assertions.assertTrue(!alertMessages.isEmpty());
        }
        @Test
            void locateElementByAttribute(){
                page.locator("[placeholder='Your last name *']").fill("Smith");
                // page.locator("input[placeholder='Your last name *']").fill("Smith");
                
                PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Smith");
            }


    }

    


    private void openPage(){
        page.navigate("https://practicesoftwaretesting.com");
    }
}
