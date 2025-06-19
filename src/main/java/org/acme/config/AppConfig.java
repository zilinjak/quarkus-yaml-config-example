package org.acme.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import java.util.List;

@ConfigMapping(prefix = "application")
public interface AppConfig {
    @WithDefault("false")
    @WithName("eoc-mode")
    boolean eocMode();

    @WithName("log-level")
    String logLevel();

    ProcessingConfig processing();

    default String stringify(){
        StringBuilder sb = new StringBuilder();
        sb.append("Application Configuration:\n");
        sb.append("EOC Mode: ").append(eocMode()).append("\n");
        sb.append("Log Level: ").append(logLevel()).append("\n");
        sb.append("Processing Config:\n");
        sb.append("  Always Success: ").append(processing().alwaysSuccess()).append("\n");
        sb.append("  Keep Token: ").append(processing().keepToken()).append("\n");
        sb.append("  Admin Paths: ").append(String.join(" || ", processing().adminPaths())).append("\n");
        return sb.toString();
    }
}

interface ProcessingConfig {
    @WithName("always-success")
    @WithDefault("false")
    boolean alwaysSuccess();

    @WithName("keep-token")
    @WithDefault("false")
    boolean keepToken();

    @WithName("admin-paths")
    default List<String> adminPaths(){
        return List.of("/admin/", "/api/admin/", "/foo/admin");
    }
}

interface ProlongConfig {
    @WithDefault("1")
    long retries();

    @WithName("retry-delay")
    @WithDefault("100")
    long retryDelay();

    @WithName("timeout-millis")
    @WithDefault("1000")
    long timeoutMillis();

    @WithDefault("0.1")
    double jitter();

    @WithDefault("https")
    String protocol();

    @WithName("prolong-path")
    @WithDefault("/api/front/auth/prolong")
    String prolongPath();
}