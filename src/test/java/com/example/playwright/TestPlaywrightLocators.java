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
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;   
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

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
        playwright.selectors().setTestIdAttribute("data-test");
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

    @DisplayName("Nested Locators")
    @Nested 
    class elementUsingNestedLocators{

        @BeforeEach
        void openCataloguePage(){
            openPage();
        }

        @Test 
        void locateElementsUsingNestedLocators(){

            page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu")).getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Contact")).click();
            Locator contactForm=page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Contact").setLevel(3));
       PlaywrightAssertions.assertThat(contactForm).isVisible();

        }

        @Test 
        void locateElementUsingLocalNestedLocators(){

            page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu")).getByText("Contact").click();
            Locator contactForm=page.locator("h3:has-text('Contact')");
            PlaywrightAssertions.assertThat(contactForm).isVisible();

        }

        @Test 
        void locateElementsUsingFilterOptions(){
            page.getByPlaceholder("Search").fill("Wrench");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
            List <String> allProducts=page.getByTestId("product-name").filter(new Locator.FilterOptions().setHasText("Wrench")).allTextContents();
            Assertions.assertFalse(allProducts.isEmpty());
        }

        @Test 
        void locateElementWithoutText(){
            page.getByPlaceholder("Search").fill("Pliers");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
            List <String> allProducts=page.getByTestId("product-name").filter(new Locator.FilterOptions().setHasNotText("Drill")).allTextContents();
            Assertions.assertFalse(allProducts.stream().anyMatch(product ->product.contains("Drill")));
        }

        @Test 
        void locateOutOfStockOptions(){
                page.getByPlaceholder("Search").fill("Pliers");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
            List <String> outOfStockList=page.locator(".card").filter(new Locator.FilterOptions().setHas(page.getByTestId("out-of-stock"))).getByTestId("product-name").allTextContents();
        // PlaywrightAssertions.assertThat(page.getByTestId("out-of-stock")).isVisible();

           boolean found=false;
            for(String prodcuts: outOfStockList){
                if(prodcuts.contains("Out of stock")){
                    found=true;
                    break;
                }
            }
            Assertions.assertTrue(found);
        
        }


        @DisplayName("Search For Pliers")
        @Test 
        void searchForPliers(){
            page.getByPlaceholder("Search").fill("Pliers");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
            assertThat(page.locator(".card")).hasCount(4);

           List <String> productName= page.getByTestId("product-name").allTextContents();
           Assertions.assertTrue(productName.stream().allMatch(name -> name.contains("Pliers")));

           Locator outOfStockItem=page.locator(".card").filter(new Locator.FilterOptions().setHasText("Out of stock")).getByTestId("product-name");

           assertThat(outOfStockItem).hasCount(1);
           assertThat(outOfStockItem).hasText("Long Nose Pliers");

        }

        

        }




    }

    


    private void openPage(){
        page.navigate("https://practicesoftwaretesting.com");
    }
}
