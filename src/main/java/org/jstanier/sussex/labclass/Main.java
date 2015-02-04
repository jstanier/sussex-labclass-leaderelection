package org.jstanier.sussex.labclass;

import org.jstanier.sussex.labclass.config.Config;
import org.springframework.boot.SpringApplication;

public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }
}
