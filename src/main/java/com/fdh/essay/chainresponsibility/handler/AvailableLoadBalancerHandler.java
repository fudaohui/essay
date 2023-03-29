package com.fdh.essay.chainresponsibility.handler;

;

import com.fdh.essay.chainresponsibility.Node;
import com.fdh.essay.chainresponsibility.pipeline.LoadBalancerHandler;
import com.fdh.essay.chainresponsibility.pipeline.LoadBalancerHandlerContext;

import java.util.List;
import java.util.stream.Collectors;

public class AvailableLoadBalancerHandler implements LoadBalancerHandler {

    @Override
    public <T extends Node> T choose(List<T> nodes, LoadBalancerHandlerContext loadBalancerHandlerContext) {
        List<T> availableNodes = nodes.stream().filter(Node::available).collect(Collectors.toList());
        //最后一个链上的处理方法不会在调用下一个的choose了，结束了
        return loadBalancerHandlerContext.choose(availableNodes);
    }
}
