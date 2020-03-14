package cn.tedu.straw.portal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("fastdfs")
public class FastDfsConfig {

    private String hostUrl;
    //private String defaultImgurl;


//	public String getDefaultImgurl() {
//		return defaultImgurl;
//	}
//
//	public void setDefaultImgurl(String defaultImgurl) {
//		this.defaultImgurl = defaultImgurl;
//	}

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }


}
