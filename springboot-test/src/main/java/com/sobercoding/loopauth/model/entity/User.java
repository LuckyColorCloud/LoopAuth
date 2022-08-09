package com.sobercoding.loopauth.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/08/09 15:53
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String secretKey;
}
