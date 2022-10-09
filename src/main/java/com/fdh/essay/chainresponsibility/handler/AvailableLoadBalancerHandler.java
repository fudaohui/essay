package com.fdh.essay.chainresponsibility.handler;



import com.fdh.essay.chainresponsibility.Node;

import java.util.List;
import java.util.stream.Collectors;

public class AvailableLoadBalancerHandler implements LoadBalancerHandler {

    @Override
    public <T extends Node> T choose(List<T> nodes, LoadBalancerHandlerContext loadBalancerHandlerContext) {
        List<T> availableNodes = nodes.stream().filter(Node::available).collect(Collectors.toList());
        return loadBalancerHandlerContext.choose(availableNodes);
    }
}
