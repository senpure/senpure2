package com.senpure.base.configuration;

import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.filter.encoding.EncodingConvertFilter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallFilter;
import com.senpure.base.util.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.*;

/**
 * copy from DruidDataSourceAutoConfigure
 *
 * @author 罗中正
 * @date 2017/12/7 0007
 */
@Configuration
@AutoConfigureBefore(DruidDataSourceAutoConfigure.class)
@ConditionalOnClass(DruidDataSource.class)
public class DataSourceAutoConfiguration extends BaseConfiguration {

    @Autowired
    private DataSourceProperties prop;

    @Value("${druid.view.account:senpure}")
    private String account;
    @Value("${druid.view.passwrod:senpure}")
    private String passwrod;
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {

        String fullurl = prop.getUrl();
        String user = prop.getUsername();
        String password = prop.getPassword();
        int index = StringUtil.indexOf(fullurl, "/", 1, true);
        String url = fullurl.substring(0, index);
        String database = "";
        int j = fullurl.indexOf("?");
        if (j < 0) {
            database = fullurl.substring(index + 1);
        } else {
            database = fullurl.substring(index + 1, j);
        }
        fullurl = fullurl.toLowerCase();
        index = fullurl.indexOf("encoding");
        String charSet = null;
        if (index > 0) {
            int i = fullurl.indexOf("&amp;", index);
            if (i < 0) {
                i = fullurl.indexOf("&", index);
            }
            if (i < 0) {
                charSet = fullurl.substring(index + 9);
            } else {
                charSet = fullurl.substring(index + 9, i);
            }
        }
        Connection connection = null;
        try {
            String checkSql = "SELECT information_schema.SCHEMATA.SCHEMA_NAME FROM information_schema.SCHEMATA where SCHEMA_NAME=?";
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setString(1, database);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.debug("[{}]数据库存在", database);
            } else {
                logger.debug("[{}]数据库不存在，准备创建数据库", database);
                StringBuilder sb = new StringBuilder();
                sb.append("create DATABASE ");
                sb.append("`");
                sb.append(database);
                sb.append("`");
                if (charSet == null) {
                    sb.append(" default character set utf8 collate utf8_general_ci");
                } else {
                    sb.append(" default character set");
                    sb.append(charSet.replace("_", ""));
                }
                String createSql = sb.toString();
                logger.debug("创建数据库sql:{}", createSql);
                preparedStatement = connection.prepareStatement(checkSql);
                int update = preparedStatement.executeUpdate(createSql);
                if (update == 1) {
                    logger.debug("创建数据库[{}]成功", database);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DruidDataSourceWrapper();
    }

    @Bean
    public ServletRegistrationBean DruidStatViewServle(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        servletRegistrationBean.setLoadOnStartup(-2);
        //添加初始化参数：initParams

        //白名单：
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //servletRegistrationBean.addInitParameter("deny","192.168.1.129");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername",account);
        servletRegistrationBean.addInitParameter("loginPassword",passwrod);
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }


    @Bean
    public  Log4j2Filter log4j2Filter()
    {

        Log4j2Filter log4j2Filter=new Log4j2Filter();
        log4j2Filter.setStatementExecutableSqlLogEnable(true);

        return log4j2Filter;
    }
    @ConfigurationProperties("spring.datasource.druid")
    class DruidDataSourceWrapper extends DruidDataSource implements InitializingBean {
        @Autowired
        private DataSourceProperties sourceProperties;

        DruidDataSourceWrapper() {
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            if (super.getUsername() == null) {
                super.setUsername(this.sourceProperties.determineUsername());
            }

            if (super.getPassword() == null) {
                super.setPassword(this.sourceProperties.determinePassword());
            }

            if (super.getUrl() == null) {
                super.setUrl(this.sourceProperties.determineUrl());
            }

            if (super.getDriverClassName() == null) {
                super.setDriverClassName(this.sourceProperties.determineDriverClassName());
            }

        }

        @Autowired(
                required = false
        )
        public void addStatFilter(StatFilter statFilter) {
            super.filters.add(statFilter);
        }

        @Autowired(
                required = false
        )
        public void addConfigFilter(ConfigFilter configFilter) {
            super.filters.add(configFilter);
        }

        @Autowired(
                required = false
        )
        public void addEncodingConvertFilter(EncodingConvertFilter encodingConvertFilter) {
            super.filters.add(encodingConvertFilter);
        }

        @Autowired(
                required = false
        )
        public void addSlf4jLogFilter(Slf4jLogFilter slf4jLogFilter) {
            super.filters.add(slf4jLogFilter);
        }

        @Autowired(
                required = false
        )
        public void addLog4jFilter(Log4jFilter log4jFilter) {
            super.filters.add(log4jFilter);
        }

        @Autowired(
                required = false
        )
        public void addLog4j2Filter(Log4j2Filter log4j2Filter) {
            super.filters.add(log4j2Filter);
        }

        @Autowired(
                required = false
        )
        public void addCommonsLogFilter(CommonsLogFilter commonsLogFilter) {
            super.filters.add(commonsLogFilter);
        }

        @Autowired(
                required = false
        )
        public void addWallFilter(WallFilter wallFilter) {
            super.filters.add(wallFilter);
        }
    }
}
