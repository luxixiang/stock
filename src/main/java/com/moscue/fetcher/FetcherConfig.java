package com.moscue.fetcher;

import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FetcherConfig {

    public static String[] computerUserAgents = {"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT6.0)",
        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT5.2)", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT5.1)",
        "Mozilla/4.0 (compatible; MSIE 5.0; WindowsNT)",
        "Mozilla/5.0 (Windows; U; Windows NT 5.2)Gecko/2008070208 Firefox/3.0.1",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1)Gecko/20070309 Firefox/2.0.0.3",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1)Gecko/20070803 Firefox/1.5.0.12",
        "Opera/9.27 (Windows NT 5.2; U; zh-cn)", "Opera/8.0 (Macintosh; PPC Mac OS X; U; en)",
        "Mozilla/5.0 (Macintosh; PPC Mac OS X; U; en)Opera 8.0",
        "Mozilla/5.0 (Windows; U; Windows NT 5.2)AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1Safari/525.13",
        "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML,like Gecko) Chrome/0.2.149.27 Safari/525.13"};
    private String userAgentString = "Mozilla/4.0 (compatible; MSIE 5.0; WindowsNT)";
    private boolean includeHttpsPages = true;
    private int maxConnectionsPerHost = 100;
    private int maxTotalConnections = 100;
    private int socketTimeout = 25*1000;
    private int connectionTimeout = 65*1000;
    private boolean followRedirects = false;
    private String proxyHost = null;
    private int proxyPort = 80;
    private String proxyUsername = null;
    private String proxyPassword = null;

    public void randomComputerUserAgent() {
        this.userAgentString = computerUserAgents[new Random().nextInt(computerUserAgents.length)];
    }

    public FetcherConfig() {
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public boolean isIncludeHttpsPages() {
        return includeHttpsPages;
    }

    public void setIncludeHttpsPages(boolean includeHttpsPages) {
        this.includeHttpsPages = includeHttpsPages;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
