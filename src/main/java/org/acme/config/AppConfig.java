package org.acme.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import java.util.List;
import java.util.Optional;

@ConfigMapping(prefix = "application")
public interface AppConfig {
    @WithDefault("false")
    @WithName("eoc-mode")
    boolean eocMode();

    @WithName("log-level")
    String logLevel();

    ProcessingConfig processing();

    ProlongConfig prolong();

    Optional<List<AuthConfig>> auth();

    default String stringify(){
        StringBuilder sb = new StringBuilder();
        sb.append("Application Configuration:\n");
        sb.append("EOC Mode: ").append(eocMode()).append("\n");
        sb.append("Log Level: ").append(logLevel()).append("\n");
        sb.append("Processing Config:\n");
        sb.append("  Always Success: ").append(processing().alwaysSuccess()).append("\n");
        sb.append("  Keep Token: ").append(processing().keepToken()).append("\n");
        sb.append("  Admin Paths: ").append(String.join(" || ", processing().adminPaths())).append("\n");
        sb.append("Prolong Config:\n");
        sb.append("  Retries: ").append(prolong().retries()).append("\n");
        sb.append("  Retry Delay: ").append(prolong().retryDelay()).append(" ms\n");
        sb.append("  Timeout Millis: ").append(prolong().timeoutMillis()).append(" ms\n");
        sb.append("  Jitter: ").append(prolong().jitter()).append("\n");
        sb.append("  Protocol: ").append(prolong().protocol()).append("\n");
        sb.append("  Prolong Path: ").append(prolong().prolongPath()).append("\n");
        
        if (auth().isPresent()) {
            sb.append("Auth Config:\n");
            auth().get().forEach(authConfig -> {
                sb.append("  Location: ").append(authConfig.location()).append("\n");
                sb.append("  Refresh: ").append(authConfig.refresh()).append(" ms\n");
                sb.append("  Hostnames: ").append(String.join(", ", authConfig.hostnames())).append("\n");
                sb.append("  Prolong URL: ").append(authConfig.prolongUrl()).append("\n");
            });
        } else {
            sb.append("Auth Config: Not configured\n");
        }

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

interface AuthConfig {
    String location();
    long refresh();
    List<String> hostnames();
    @WithName("prolong-url")
    String prolongUrl();
}