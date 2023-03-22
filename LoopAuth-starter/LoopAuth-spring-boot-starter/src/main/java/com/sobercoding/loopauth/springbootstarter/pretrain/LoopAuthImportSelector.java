package com.sobercoding.loopauth.springbootstarter.pretrain;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 注册预加载
 * @author Sober
 */
//ImportSelector
public class LoopAuthImportSelector implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(LoopAuthPretrain.class.getName()));
        if (annotationAttributes != null) {
            List<String> paths = Arrays.stream(annotationAttributes.getStringArray("value"))
                    .filter(StringUtils::hasText)
                    .collect(Collectors.toList());

        }
    }
}