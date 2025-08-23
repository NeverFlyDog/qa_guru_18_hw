package com.demoqa.config;

public class BrowserConfig {
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String BROWSER_VERSION = System.getProperty("browserVersion", null);
    public static final String BROWSER_SIZE = System.getProperty("browserSize", "1920x1080");
    public static final String REMOTE_URL = System.getProperty("remoteUrl", null);
}