package com.fdh.essay.chainresponsibility.handler;

import com.fdh.essay.chainresponsibility.LoadBalancer;
import com.fdh.essay.chainresponsibility.Node;
import java.util.List;

public class LoadBalancerPipeline implements LoadBalancer {
    private final AbstractLoadBalancerHandlerContext head;

    private final AbstractLoadBalancerHandlerContext tail;

    public LoadBalancerPipeline() {
        head = new HeadContext(this);
        tail = new TailContext(this);

        head.prev = null;
        head.next = tail;
        tail.prev = head;
        tail.next = null;
    }

    @Override
    public <T extends Node> T choose(List<T> nodes) {
        return head.choose(nodes);
    }

    public synchronized LoadBalancerPipeline addFirst(LoadBalancerHandler handler) {
        AbstractLoadBalancerHandlerContext newCtx = new DefaultLoadBalancerHandlerContext(this, handler);
        AbstractLoadBalancerHandlerContext nextCtx = head.next;
        head.next = newCtx;
        newCtx.prev = head;
        newCtx.next = nextCtx;
        nextCtx.prev = newCtx;

        return this;
    }

    public synchronized LoadBalancerPipeline addFirst(List<LoadBalancerHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return this;
        }

        int size = handlers.size();
        for (int i = 0; i < size; i++) {
            addFirst(handlers.get(size - i - 1));
        }

        return this;
    }

    public synchronized LoadBalancerPipeline addLast(LoadBalancerHandler handler) {
        AbstractLoadBalancerHandlerContext newCtx = new DefaultLoadBalancerHandlerContext(this, handler);
        AbstractLoadBalancerHandlerContext prevCtx = tail.prev;

        newCtx.prev = prevCtx;
        newCtx.next = tail;
        prevCtx.next = newCtx;
        tail.prev = newCtx;

        return this;
    }

    public synchronized LoadBalancerPipeline addLast(List<LoadBalancerHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return this;
        }

        for (LoadBalancerHandler handler : handlers) {
            addLast(handler);
        }

        return this;
    }

    private static class HeadContext extends AbstractLoadBalancerHandlerContext {
        HeadContext(LoadBalancerPipeline pipeline) {
            super(pipeline);
        }

        @Override
        protected LoadBalancerHandler handler() {
            return null;
        }

        @Override
        public <T extends Node> T choose(List<T> nodes) {
            AbstractLoadBalancerHandlerContext ctx = findNextCtx();
            if(ctx != null) {
                return ctx.choose(nodes);
            }

            return null;
        }
    }

    private static class TailContext extends AbstractLoadBalancerHandlerContext {
        TailContext(LoadBalancerPipeline pipeline) {
            super(pipeline);
        }

        @Override
        protected LoadBalancerHandler handler() {
            return null;
        }

        @Override
        public <T extends Node> T choose(List<T> nodes) {
            AbstractLoadBalancerHandlerContext ctx = findNextCtx();
            if(ctx != null) {
                return ctx.choose(nodes);
            }

            return null;
        }
    }
}
