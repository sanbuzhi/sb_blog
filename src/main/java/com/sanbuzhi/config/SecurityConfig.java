package com.sanbuzhi.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//采用链式编程
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            //首页所有人可以访问，功能页只有拥有对应权限的人才能访问
            http.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin/**").hasRole("role_admin");//需要拥有VIP1的角色，才能访问/level1/下的页面
            //没有权限则默认跳转到登录页面。
            //需要开启登录的页面
            http.formLogin().loginPage("/tologin").usernameParameter("user").passwordParameter("pwd").loginProcessingUrl("/login");

            //开启注销功能；注销成功返回首页
            http.logout().logoutSuccessUrl("/");

            http.rememberMe().rememberMeParameter("rememberMe");

            http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        //数据应该存储到数据库
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())  //确定密码加密的方法
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("role_admin")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("root")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("guest")).roles("/");
    }
}
