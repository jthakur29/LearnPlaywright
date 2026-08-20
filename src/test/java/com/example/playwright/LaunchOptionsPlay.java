package com.example.playwright;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;

@UsePlaywright(LaunchOptionsPlay.MyOptions.class)
public class LaunchOptionsPlay {

    public static class MyOptions implements OptionsFactory{
@Override
public Options  getOptions() {
    return new Options().setHeadless(false)
            .setLaunchOptions(new BrowserType.LaunchOptions().setSlowMo(50)
            .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu"))
        );


    }
    }
    
@Test
void  testGetTitle(Page page){
    page.navigate("https://www.youtube.com");
    String title= page.title();

    System.out.println(title);
}

}

