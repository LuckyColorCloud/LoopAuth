package com.sobercoding.loopauth;

import com.sobercoding.loopauth.abac.annotation.PropertyPretrain;
import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Sober
 */
@PropertyPretrain("com.sobercoding.loopauth.auth.property.*")
@VerifyPrestrain("com.sobercoding.loopauth.auth.verify.*")
@SpringBootApplication
public class SpringbootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestApplication.class, args);
    }


}
