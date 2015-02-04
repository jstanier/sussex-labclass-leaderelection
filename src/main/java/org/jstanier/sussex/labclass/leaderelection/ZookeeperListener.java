package org.jstanier.sussex.labclass.leaderelection;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.log4j.Logger;
import org.jstanier.sussex.labclass.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class ZookeeperListener implements PathChildrenCacheListener {

    private Logger logger = Logger.getLogger(PathChildrenCacheListener.class);

    private Map<String, LeaderLatch> leaderLatches = Maps.newHashMap();
    private ExecutorService pool = Executors.newFixedThreadPool(10);
    private boolean gotInitialWork = false;
    private PathChildrenCache pathChildrenCache;

    @Autowired
    private CuratorFramework curatorFramework;

    @PostConstruct
    public void startWatching() throws Exception {
        initialisePathChildrenCache();
        attachListener();
        while (!gotInitialWork) {
            beginLeaderElection();
        }
    }

    private void beginLeaderElection() throws InterruptedException, ExecutionException {
        List<ChildData> currentData = pathChildrenCache.getCurrentData();
        if (currentData.isEmpty()) {
            logger.info("Sleeping while there is no work to do...");
            Thread.sleep(1000);
        } else {
            gotInitialWork = true;
            // TODO: What nodes have we got on Zookeeper?
        }
    }

    private void initialisePathChildrenCache() throws Exception {
        pathChildrenCache = new PathChildrenCache(curatorFramework, Constants.JOB_PATH, true);
        pathChildrenCache.start(StartMode.BUILD_INITIAL_CACHE);
    }

    private void attachListener() {
        pathChildrenCache.getListenable().addListener(this);
    }

    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event)
            throws Exception {
        // TODO: What events could we handle here?
    }

    @PreDestroy
    public void close() throws IOException {
        for (LeaderLatch leaderLatch : leaderLatches.values()) {
            leaderLatch.close();
        }
        pool.shutdown();
    }
}
