package com.example.oauth.infrastructure.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * 鉴权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // 数据源
    @Autowired
    private DataSource dataSource;

    //jwt令牌转换器
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    // springSecurity用户授权
    @Resource
    private UserDetailsService userDetailsService;

    // 授权认证管理
    @Resource
    AuthenticationManager authenticationManager;

    //令牌持久化存储接口
    @Autowired
    TokenStore tokenStore;

    // 自定义转换器
    @Autowired
    private CustomUserAuthenticationConverter customUserAuthenticationConverter;

    /**
     * 客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).clients(clientDetails());
    }


    /**
     * 服务端配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter) // token转换器
                 .authenticationManager(authenticationManager)  // 认证管理器
                 .tokenStore(tokenStore) // 令牌存储
                 .userDetailsService(userDetailsService); // 用户信息
    }


    /**
     * 授权服务器安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .passwordEncoder(new BCryptPasswordEncoder())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }




    /**
     * 客户端配置
     */
    @Bean
    public ClientDetailsService clientDetails(){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Autowired
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }


    /**
     * 读取密钥配置
     */
    @Bean("keyProp")
    public KeyProperties keyProperties(){
        return new KeyProperties();
    }

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    /**
     * jwt令牌转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(CustomUserAuthenticationConverter customUserAuthenticationConverter){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 读取公私钥匙
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties.getKeyStore().getLocation(), // 读取证书的路径
                keyProperties.getKeyStore().getSecret().toCharArray()) //读取密钥访问密码
                .getKeyPair(
                        keyProperties.getKeyStore().getAlias(), // 读取别名
                        keyProperties.getKeyStore().getPassword().toCharArray()); // 读取证书的密码
                converter.setKeyPair(keyPair);

                // 配置自定义的鉴权转换器
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter)converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
        return converter;

    }





}
