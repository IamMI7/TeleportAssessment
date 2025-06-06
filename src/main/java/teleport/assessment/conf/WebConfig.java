package teleport.assessment.conf;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.ZonedDateTime;

@ControllerAdvice
public class WebConfig {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ZonedDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null) {
                    // Fix the space issue by replacing space with '+'
                    String fixed = text.replace(" ", "+");
                    setValue(ZonedDateTime.parse(fixed));
                } else {
                    setValue(null);
                }
            }
        });
    }
}
