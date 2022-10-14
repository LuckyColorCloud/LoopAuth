package com.sobercoding.loopauth.abac.model;

import com.sobercoding.loopauth.function.MaFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Sober
 */
public class AbacPoAndSu {

    /**
     * ABAC鉴权匹配方式
     */
    public MaFunction maFunction;

    /**
     *  ABAC鉴权获取属性值方式
     */
    public Supplier supplierMap;

    public MaFunction getMaFunction() {
        return maFunction;
    }

    public AbacPoAndSu setMaFunction(MaFunction maFunction) {
        this.maFunction = maFunction;
        return this;
    }

    public Supplier getSupplierMap() {
        return supplierMap;
    }

    public AbacPoAndSu setSupplierMap(Supplier supplierMap) {
        this.supplierMap = supplierMap;
        return this;
    }
}
