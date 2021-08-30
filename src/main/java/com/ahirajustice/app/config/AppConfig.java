package com.ahirajustice.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${app.config.accesstokenexpireminutes}")
    public int ACCESS_TOKEN_EXPIRE_MINUTES;

    @Value("${app.config.secretkey}")
    public String SECRET_KEY;
}
