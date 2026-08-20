package com.example.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;

@UsePlaywright
public class ASimplePlaywrightTest {

/*   Playwright playwright;
    Browser browser;
    Page page;

@BeforeEach
void setup(){
    playwright=Playwright.create();
    browser=playwright.chromium().launch();
    page=browser.newPage();
}

@AfterEach
void tearDown(){
    browser.close();
    playwright.close();
}
*/
@Test
void  shouldShowTitlePage(Page page){
/*try (Playwright playwright = Playwright.create();
     //Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge"));
     Browser browser=playwright.chromium().launch();
     Page page = browser.newPage()) {
*/

page.navigate("https://practicesoftwaretesting.com/");
String title=page.title();
System.out.println(page.title());
Assertions.assertTrue(title.contains("Practice Software Testing"));
}

//}


@Test
void searchByKeyword(Page page){
   // Playwright playwright= Playwright.create();
   // Browser browser=playwright.chromium().launch();
    //Page page=browser.newPage();

    page.navigate("https://practicesoftwaretesting.com");
    page.locator("[placeholder=Search]").fill("Pliers");
    page.locator("button:has-text('Search')").click();

    int matchingNumberOfItems=page.locator(".card").count();

    Assertions.assertTrue(matchingNumberOfItems>0);

    //browser.close();
    //playwright.close();


}
}
