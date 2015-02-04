package org.jstanier.sussex.labclass.leaderelection;

import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

public class JobLeaderLatchListener implements LeaderLatchListener {

    private String path;

    public JobLeaderLatchListener(String path) {
        this.path = path;
    }

    public void notLeader() {
        // Do nothing.
    }

    public void isLeader() {
        // TODO: What do we do if we win leadership?
    }
}