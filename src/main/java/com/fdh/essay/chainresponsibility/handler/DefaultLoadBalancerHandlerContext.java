package com.fdh.essay.chainresponsibility.handler;

public class DefaultLoadBalancerHandlerContext extends AbstractLoadBalancerHandlerContext {
    private final LoadBalancerHandler handler;

    public DefaultLoadBalancerHandlerContext(LoadBalancerPipeline pipeline,
            LoadBalancerHandler handler) {
        super(pipeline);
        this.handler = handler;
    }

    @Override
    protected LoadBalancerHandler handler() {
        return handler;
    }
}
