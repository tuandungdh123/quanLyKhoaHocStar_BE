package com.example.coursemanagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseManagementApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Set properties for Spring Environment
//        System.setProperty("SV_DB_URL", dotenv.get("SV_DB_URL"));
//        System.setProperty("SV_DB_USERNAME", dotenv.get("SV_DB_USERNAME"));
//        System.setProperty("SV_DB_PASSWORD", dotenv.get("SV_DB_PASSWORD"));
        System.setProperty("L_DB_URL", dotenv.get("L_DB_URL"));
        System.setProperty("L_DB_USERNAME", dotenv.get("L_DB_USERNAME"));
        System.setProperty("L_DB_PASSWORD", dotenv.get("L_DB_PASSWORD"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
        System.setProperty("AWS_S3_BUCKET", dotenv.get("AWS_S3_BUCKET"));
        System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
        System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));

        SpringApplication.run(CourseManagementApplication.class, args);
    }
}
