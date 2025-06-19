package org.acme.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

import java.util.List;

@ConfigMapping(prefix = "server")
public interface ApplicationConfig {

    List<Environment> environments();

    String foo();

    interface Environment {
        String name();
        String services();
    }

    default String stringify() {
        StringBuilder sb = new StringBuilder();
        sb.append("Application Configuration:\n");
        environments().forEach(env -> {
            sb.append("Environment: ").append(env.name())
              .append(", Services: ").append(env.services())
              .append("\n");
        });
        sb.append("Foo: ").append(foo()).append("\n");
        return sb.toString();
    }
}
