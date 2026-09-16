package com.example.playwright;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.SelectOption;

@UsePlaywright(HeadlessChromeOptions.class)
public class FormTestField {

    @BeforeEach 
    void openTestingWebsite(Page page){
        page.navigate("https://practicesoftwaretesting.com");
    }

    @Nested 
    class TestingContactPage{

        @BeforeEach 
        void openContactPage(Page page){
            page.navigate("https://practicesoftwaretesting.com/contact");
        }


    @DisplayName("Interacting With Text Fields")
    @Test 
    void fieldValues(Page page){
        var firstNameField= page.getByLabel("First name");
        firstNameField.fill("Jane");
        assertThat(firstNameField).hasValue("Jane");
      
    }

    @Test 
    void completeForm(Page page){

        var firstName=page.getByLabel("First name");
        var lastName=page.getByLabel("Last name");
        var emailAddress=page.getByLabel("Email");
        var messageField=page.getByLabel("Message");
        var subjectField=page.getByLabel("Subject");

        firstName.fill("Jane");
        lastName.fill("Smith");
        emailAddress.fill("jane-smith@gmail.com");
        messageField.fill("Hello, world!");
        //subjectField.selectOption("Warranty");
        subjectField.selectOption(new SelectOption().setIndex(5));

        assertThat(firstName).hasValue("Jane");
        assertThat(lastName).hasValue("Smith");
        assertThat(emailAddress).hasValue("jane-smith@gmail.com");
        assertThat(messageField).hasValue("Hello, world!");
        assertThat(subjectField).hasValue("warranty");

    }
}
}