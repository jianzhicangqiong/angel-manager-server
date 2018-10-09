package com.hero.angel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "init")
public class InitDataConfig {

    /**
     * 初始用户的角色Id
     */
    private Long initRoleId;

    public Long getInitRoleId() {
        return initRoleId;
    }

    public void setInitRoleId(Long initRoleId) {
        this.initRoleId = initRoleId;
    }
}
