package com.jetbrains.intellij.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/string-check")
public class StringLengthController {
    private static final Logger logger = LoggerFactory.getLogger(StringLengthController.class);

    @GetMapping("/string-check.html")
    public String stringCheckPage() {
        return "string-check"; // returner navn på Thymeleaf-skabelonen uden ".html"
    }
    @PostMapping("/check")
    public String sumOfSubstringsLength(@RequestParam("strings") String strings,
                                        @RequestParam("maxLength") int maxLength,
                                        Model model) {
        List<String> stringList = Arrays.asList(strings.split(","));
        int totalLength = 0;

        // Beregn den samlede længde af alle strenge, inklusive mellemrum
        for (String str : stringList) {
            totalLength += str.trim().length();
            logger.info("Streng: {}, Længde: {}", str, str.length());
            if (totalLength > maxLength) {
                model.addAttribute("result", "Samlet længde overstiger maxLength.");
                return "result"; // returner "result" skabelonen
            }
        }
        // Hvis det samlede antal er mindre eller lig med maxLength, returner true
        model.addAttribute("result", "Tilladt: samlet længde er inden for grænsen.");
        return "result";
    }


    // Klasse til at holde svar-data
    public static class Response {
        private boolean allowed;
        private String message;

        public Response(boolean allowed, String message) {
            this.allowed = allowed;
            this.message = message;
        }

        public boolean isAllowed() {
            return allowed;
        }

        public String getMessage() {
            return message;
        }
    }

}