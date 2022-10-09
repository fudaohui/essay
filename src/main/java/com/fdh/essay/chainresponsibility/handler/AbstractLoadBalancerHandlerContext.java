package com.fdh.essay.chainresponsibility.handler;

import com.fdh.essay.chainresponsibility.Node;
import java.util.List;

public abstract class AbstractLoadBalancerHandlerContext implements LoadBalancerHandlerContext {
    private final LoadBalancerPipeline pipeline;

    volatile AbstractLoadBalancerHandlerContext prev;

    volatile AbstractLoadBalancerHandlerContext next;

    public AbstractLoadBalancerHandlerContext(LoadBalancerPipeline pipeline) {
                        this.pipeline = pipeline;
    }

    @Override
    public <T extends Node> T choose(List<T> nodes) {
        return handler().choose(nodes , findNextCtx());
    }

    protected abstract LoadBalancerHandler handler();

    @Override
    public final LoadBalancerPipeline pipeline() {
        return pipeline;
    }

    protected final AbstractLoadBalancerHandlerContext findNextCtx() {
        return next;
    }

    protected final AbstractLoadBalancerHandlerContext findPrevCtx() {
        return prev;
    }
}
