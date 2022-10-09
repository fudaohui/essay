package com.fdh.essay.chainresponsibility.handler;

import com.fdh.essay.chainresponsibility.LoadBalancer;

public interface LoadBalancerHandlerContext extends LoadBalancer {
    LoadBalancerPipeline pipeline();
}
