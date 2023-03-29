package com.fdh.essay.chainresponsibility;

import com.fdh.essay.chainresponsibility.handler.AvailableLoadBalancerHandler;
import com.fdh.essay.chainresponsibility.handler.RandomLoadBalancerHandler;
import com.fdh.essay.chainresponsibility.pipeline.LoadBalancerPipeline;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LBTest {


    static class DefaultNode implements Node {

        private String id;
        private int weight;
        private boolean available;

        public DefaultNode(String id, int weight, boolean available) {
            this.id = id;
            this.weight = weight;
            this.available = available;
        }

        @Override
        public String id() {
            return this.id;
        }

        @Override
        public int weight() {
            return this.weight;
        }

        @Override
        public boolean available() {
            return this.available;
        }


    }

    @Test
    void testDefaultHandler() {


        LoadBalancerPipeline balancerPipeline = new LoadBalancerPipeline().addLast(new AvailableLoadBalancerHandler())
                .addLast(new RandomLoadBalancerHandler());
        List<Node> nodes = new ArrayList<>();
        nodes.add(new DefaultNode("1", 1, true));
        nodes.add(new DefaultNode("2", 2, true));
        nodes.add(new DefaultNode("3", 3, false));
        Node node = balancerPipeline.choose(nodes);
        System.out.println("id:" + node.id() + " weight:" + node.weight() + " avaliable:" + node.available());
    }
}
