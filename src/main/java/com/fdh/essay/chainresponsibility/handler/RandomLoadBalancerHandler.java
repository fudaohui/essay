package com.fdh.essay.chainresponsibility.handler;

import com.fdh.essay.chainresponsibility.Node;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLoadBalancerHandler implements LoadBalancerHandler {

    @Override
    public <T extends Node> T choose(List<T> nodes, LoadBalancerHandlerContext loadBalancerHandlerContext) {
        int randomIndex = ThreadLocalRandom.current().nextInt(nodes.size());
        return nodes.get(randomIndex);
    }
}
