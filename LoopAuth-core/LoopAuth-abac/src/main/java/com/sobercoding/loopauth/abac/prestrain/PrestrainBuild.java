package com.sobercoding.loopauth.abac.prestrain;

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
     * 构建
     */
    public void build(){

    }

}
