package net.onebean.config;

import net.onebean.sso.sdk.core.SsoLoginFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "sso.uag.app.id")
public class SsoServerAutoConfig {

    /**
     * 配置监控服务器
     * @return 返回监控注册的servlet对象
     */
    @Bean
    public SsoLoginFilter ssoLoginFilter() {
        return new SsoLoginFilter();
    }


}
