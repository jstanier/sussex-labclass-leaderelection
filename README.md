# Leader election lab class - part 2

So you made it this far? Well done. Remember that you should have completed [part 1](https://github.com/jstanier/sussex-labclass-jobsubmission) before getting to this point. 

## What are we doing?

In the previous part you made an application that allows you to submit "jobs" to Zookeeper by typing a name into the command prompt. In this part, we're going to write an application that watches for when new jobs are created, then partakes in a [leader election](http://en.wikipedia.org/wiki/Leader_election) process to win the jobs. When it's completed the "job", it will remove it from Zookeeper.

