package org.jstanier.sussex.labclass.leaderelection;

import java.util.concurrent.Callable;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;

public class LeaderLatchThread implements Callable<LeaderLatch> {

    private CuratorFramework curatorFramework;
    private String path;

    public LeaderLatchThread(String path, CuratorFramework curatorFramework) {
        this.path = path;
        this.curatorFramework = curatorFramework;
    }

    public LeaderLatch call() throws Exception {
        // TODO: Create, start and return a new LeaderLatch here.
        // You'll also want to create a new JobLeaderLatchListener and add the listener to the
        // LeaderLatch.
        return null;
    }
}
