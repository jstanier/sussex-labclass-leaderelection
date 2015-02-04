package org.jstanier.sussex.labclass.jobprocessor;

import org.apache.log4j.Logger;

public class JobProcessor {

    private Logger logger = Logger.getLogger(JobProcessor.class);

    private static final int COMPUTATION_STEPS = 2000;

    public int processJob(String jobName) {
        logger.info("Processing " + jobName);
        Double random = null;
        for (int i = 0; i < COMPUTATION_STEPS; i++) {
            random = Math.random();
        }
        logger.info("Finished " + jobName + ". Result is " + random);
        return random.intValue();
    }

}
