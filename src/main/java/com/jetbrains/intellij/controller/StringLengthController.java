package com.jetbrains.intellij.controller;

import com.jetbrains.intellij.entity.LengthRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/string-check")
public class StringLengthController {
    private static final Logger logger = LoggerFactory.getLogger(StringLengthController.class);
    @PostMapping("/check")
    public Response  sumOfSubstringsLength(@RequestBody LengthRequest request) {

        logger.info("Received request: {}", request);
        List<String> strings = request.getStrings();
        int maxLength = request.getMaxLength();

        int totalLength = 0;

        // Beregn den samlede længde af alle strenge, inklusive mellemrum
        for (String str : strings) {
            // Tæl længden af strengen inklusiv mellemrum
            totalLength += str.length();
            logger.info("Streng: {}, Længde: {}", str, str.length());
            // Hvis det samlede antal overstiger den givne grænse, returner false
            if (totalLength > maxLength) {
                return new Response(false, "Samlet længde overstiger maxLength.");
            }
        }
        // Hvis det samlede antal er mindre eller lig med maxLength, returner true
        return new Response(true, "Tilladt: samlet længde er inden for grænsen.");
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