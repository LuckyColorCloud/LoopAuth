package com.sobercoding.loopauth.auth.model;

import com.sobercoding.loopauth.abac.annotation.PropertyPretrain;
import org.springframework.stereotype.Component;

/**
 * @author Sober
 */
@PropertyPretrain
@Component
public class UserModel {

    public String getId() {
        return "12312421";
    }

}
