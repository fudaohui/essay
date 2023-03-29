package com.fdh.essay.chainresponsibility;

import com.foldright.auto.pipeline.AutoPipeline;

import java.util.List;

/**
 * 加上注解后在编译时期自动生成相关代码在target的generated-sources中
 */
@AutoPipeline
public interface LoadBalancer {

    <T extends Node> T choose(List<T> nodes);
}
