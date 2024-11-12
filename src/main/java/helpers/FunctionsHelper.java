package helpers;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FunctionsHelper {

    /*
    * This class will be used over all the framework and will contain reusable functions
    */

    public static String getCurrentDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return currentDateTime.format(formatter);
    }


    public void waitToBeVisible(Page page,String selector){
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE));
        page.waitForFunction("() => document.querySelector('"+selector+"').disabled === false");
    }




    }



