package com.ahirajustice.app.constants;

import java.util.Arrays;
import java.util.List;

public class SecurityConstants {

    // Auth
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    // URLs
    public static final String LOGIN_URL = "/api/auth/login";
    public static final String[] EXCLUDE_FROM_AUTH_URLS = new String[] { "/api/auth/login, POST", "/api/users, POST" };

    /**
     * List of endpoints that should not be logged.
     */
    public static final List<String> SENSITIVE_ENDPOINTS = Arrays.asList(
            "/api/auth/login"
    );

}
