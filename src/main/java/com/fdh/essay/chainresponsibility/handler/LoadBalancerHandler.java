package com.fdh.essay.chainresponsibility.handler;

import com.fdh.essay.chainresponsibility.Node;
import java.util.List;

public interface LoadBalancerHandler {
    <T extends Node> T choose(List<T> nodes, LoadBalancerHandlerContext loadBalancerHandlerContext);
}
