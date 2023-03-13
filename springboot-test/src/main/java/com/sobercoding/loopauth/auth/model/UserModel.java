package com.sobercoding.loopauth.auth.model;

import org.springframework.stereotype.Component;

/**
 * @author Sober
 */
@Component
public class UserModel {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
