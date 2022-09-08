package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;

import java.util.Set;

/**
 * @author Sober
 */
public class AbacInterfaceImpl implements AbacInterface{

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param permission 权限代码
     * @return 去重后的集合
     */
    public Set<Policy> getPolicySet(String route, String permission){
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED, "AbacInterface.getPolicySet()");
    }

}
