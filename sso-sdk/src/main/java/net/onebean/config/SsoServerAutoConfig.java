package net.onebean.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import net.onebean.core.extend.DynamicMapperSqlSessionFactoryBean;
import net.onebean.core.extend.LogSQLExcutionTimeInterceptor;
import net.onebean.core.extend.PaginationInterceptor;
import net.onebean.core.form.Parse;
import net.onebean.sso.sdk.core.SsoLoginFilter;
import net.onebean.util.PropUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
