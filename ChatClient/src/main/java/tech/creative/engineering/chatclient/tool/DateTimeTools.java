package tech.creative.engineering.chatclient.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.LocalDateTime;

public class DateTimeTools
{
    @Tool(description = "Returns the current date and time")
    public String getCurrentDateTime() {
        System.out.println("Fetching the current date and time");
        return java.time.LocalDateTime.now().toString();
    }

    @Tool(description = "set the user alarm at a particular date an time")
    void setAlarm(@ToolParam String dateTime) {
        LocalDateTime dateTimeValue = LocalDateTime.parse(dateTime);
        System.out.println("Set the alarm at " + dateTimeValue);
    }

}
