package com.sobercoding.loopauth.abac.prestrain;

import com.sobercoding.loopauth.abac.AbacStrategy;

/**
 * 预加载构建
 * @author Sober
 */
public class PrestrainBuild extends AbstractParsePath {

    public PrestrainBuild() {
    }

    /**
     * 开始构建
     * @return
     */
    public static PrestrainBuild Builder(){
        return new PrestrainBuild();
    }

    /**
     * 添加路径
     * @param path
     */
    public PrestrainBuild add(String path){
        this.paths.addAll(perse(path));
        return this;
    }

    /**
     * 构建MethodFactory单例 并加载
     */
    public void build(){
        this.paths.parallelStream().forEach(this::loadMethod);
        AbacStrategy.setMethodFactory(this.methodFactory);
    }

}
