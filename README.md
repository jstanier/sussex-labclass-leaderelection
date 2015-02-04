# Leader election lab class - part 2

So you made it this far? Well done. Remember that you should have completed [part 1](https://github.com/jstanier/sussex-labclass-jobsubmission) before getting to this point. We are going to be using that program in this 

## What are we doing?

In the previous part you made an application that allows you to submit "jobs" to Zookeeper by typing a name into the command prompt. In this part, we're going to write an application that watches for when new jobs are created, then partakes in a [leader election](http://en.wikipedia.org/wiki/Leader_election) process to win the jobs. When it's completed the job, it will remove it from Zookeeper.

## Get the code

Clone this repository locally by running the following command:

```
git clone https://github.com/jstanier/sussex-labclass-leaderelection.git
```

Check everything's OK by building it with maven:

```
cd sussex-labclass-leaderelection
mvn clean test
```

Then, as before, import the project into the IDE of your choice.

## Finding work to do

Have a look around at the code that's already there. Turn your attention to the `ZookeeperListener` class. There's a rough framework already set up. Have a look at the `startWatching()` method. This method does the following things:

 1. `initalisePathChildrenCache()` - Create a local cache of what Zookeeper looks like
 2. `attachListener` - Attaches a listener that will execute a callback when the state of Zookeeper changes (more on this later)
 3. Gets the list of children of the `/sussex/jobs` node until there are some there.

Your first task is to go to this part of the code and print out the list of nodes that exist at `/sussex/jobs`. If there aren't any, you may need to add some using your job submission program. Go from this part of the code:

```java
    private void beginLeaderElection() throws InterruptedException, ExecutionException {
        List<ChildData> currentData = pathChildrenCache.getCurrentData();
        if (currentData.isEmpty()) {
            System.out.println("Sleeping while there is no work to do...");
            Thread.sleep(1000);
        } else {
            gotInitialWork = true;
            // TODO: What data have you got from ZooKeeper here?
        }
    }
```

## Leader election

Next, we want to do leader election for each of those jobs. You'll find the outline of some code in the `LeaderLatchThread` class. For each job that you printed out before, create a new `LeaderLatchThread` and submit it to the thread pool defined in `ZookeeperListener`. 

Inside the `LeaderLatchThread` class, you'll need to write the code defined below:

```java
    public LeaderLatch call() {
        // TODO: Create, start and return a new LeaderLatch here.
        // You'll also want to create a new JobLeaderLatchListener and add the listener to the LeaderLatch.
        return null;
    }
```

When you've done this, you should be able to print out when your program wins a job. Run it and see what happens!

## Multiple instances

Try running multiple instances of the application. What happens when you add new jobs with the program you wrote in part 1?

## Doing a job

Now that you know that you've won a job, why not use the `JobProcessor` to actually do something with it?

## Dealing with change in the distributed system

Things can always change, but remember you had the callback method in `ZookeeperListener`?

```java
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event)
            throws Exception {
        // TODO: What events could we handle here?
    }
```

Have a look at the types of event that can happen. I'd recommend writing a `switch` statement and thinking about what you might want to do under the different circumstances. Two circumstances that we know our behavior for sure are:

  1. A child gets added. Here, we want to try and start leader election and win the job.
  2. A child gets removed. Here, we want to close and remove our `LeaderLatch` for it.

What are the other things that could happen? What should we do when they happen?

## Tidying up

Your system is great up until now, but when a job is finished, you should tidy up after yourself! Write some code that deletes the job node on Zookeeper once it has finished processing. 

## Further work

Run three instances of the application. This is your processing cluster. If a job completes and it achieves a score of 0.5 or below, then it should be worked on by one of the other nodes in the cluster. Can you implement this?
