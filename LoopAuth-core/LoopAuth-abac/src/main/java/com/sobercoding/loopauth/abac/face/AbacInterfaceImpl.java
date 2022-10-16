package com.sobercoding.loopauth.abac.face;

import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.exception.LoopAuthConfigException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;

import java.util.Set;

/**
 * @author Sober
 */
public class AbacInterfaceImpl implements AbacInterface{

    /**
     *  获取一个或多个路由/权限代码所属的 规则
     * @param route 路由
     * @param loopAuthHttpMode 请求方式
     * @return 去重后的集合
     */
    @Override
    public Set<Policy> getPolicySet(String route, LoopAuthHttpMode loopAuthHttpMode){
        throw new LoopAuthConfigException(LoopAuthExceptionEnum.CONFIGURATION_UNREALIZED, "AbacInterface.getPolicySet()");
    }

}
