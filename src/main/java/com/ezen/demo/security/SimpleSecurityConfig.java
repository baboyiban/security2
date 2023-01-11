package com.ezen.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
//@EnableWebSecurity
public class SimpleSecurityConfig
{
   //@Bean
   BCryptPasswordEncoder  passwordEncoder()
   {
      BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
      System.out.println("employee->" + enc.encode("employee"));
      System.out.println("imadmin->" + enc.encode("imadmin"));
      System.out.println("guest->" + enc.encode("guest"));
       return enc;
   } 

    //@Bean
    WebSecurityCustomizer webSecurityCustomizer() 
    {
       return (webSecurity) -> webSecurity.ignoring().antMatchers("/resources/**", "/ignore2");
    }

    //@Bean
   SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
      System.out.println("접근제한 설정");
      return http.authorizeHttpRequests()/* 권한에 따른 인가(Authorization) */
            .antMatchers("/", "/sec/", "/sec/loginForm", "/sec/denied", "/logout").permitAll()
              .antMatchers("/sec/hello").hasAnyRole("USER", "ADMIN")
               .antMatchers("/sec/getemps").hasAnyRole("USER", "ADMIN")
               .antMatchers("/sec/addemp").hasAnyRole("ADMIN")
               .antMatchers("/sec/menu").hasAnyRole("USER","GUEST","ADMIN")
               .antMatchers("/sec/sample").hasAnyRole("GUEST", "ADMIN")
            //.anyRequest().authenticated()  // 위의 설정 이외의 모든 요청은 인증을 거쳐야 한다
               .anyRequest().permitAll()        // 위의 설정 이외의 모든 요청은 인증 요구하지 않음
               
            .and()
            .csrf().disable()    //csrf 기능을 사용하지 않을 때
            //.csrf().ignoringAntMatchers("/logout") //요청시 'POST' not supported 에러 방지
            //.ignoringAntMatchers("/sec/loginForm")
            //.ignoringAntMatchers("/doLogin")

            .formLogin().loginPage("/sec/loginForm")   // 지정된 위치에 로그인 폼이 준비되어야 함
               .loginProcessingUrl("/doLogin")            // 컨트롤러 메소드 불필요, 폼 action과 일치해야 함
               .failureUrl("/sec/loginForm?error=T")      // 로그인 실패시 다시 로그인 폼으로
               //.failureForwardUrl("/login?error=Y")  //실패시 다른 곳으로 forward
               .defaultSuccessUrl("/sec/menu", true)
               .usernameParameter("id")  // 로그인 폼에서 이용자 ID 필드 이름, 디폴트는 username
               .passwordParameter("pw")  // 로그인 폼에서 이용자 암호 필트 이름, 디폴트는 password
               .permitAll()
               
               .and()   // 디폴트 로그아웃 URL = /logout
               .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 요청시 URL
             .logoutSuccessUrl("/sec/loginForm?logout=T")
             .invalidateHttpSession(true) 
             .deleteCookies("JSESSIONID")
             .permitAll()
             
             .and()
             .exceptionHandling().accessDeniedPage("/sec/denied")
             .and().build();
    }

    //@Autowired 
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception 
    {
        authenticationMgr.inMemoryAuthentication() /* 메모리 기반 인증(Authentication) */
        .withUser("employee").password("$2a$10$MZ2ANCUXIj5mrAVbytojruvzrPv9B3v9CXh8qI9qP13kU8E.mq7yO")
            .authorities("ROLE_USER")
        .and()
        .withUser("imadmin").password("$2a$10$FA8kEOhdRwE7OOxnsJXx0uYQGKaS8nsHzOXuqYCFggtwOSGRCwbcK")
            .authorities("ROLE_USER", "ROLE_ADMIN")
        .and()
        .withUser("guest").password("$2a$10$ABxeHaOiDbdnLaWLPZuAVuPzU3rpZ30fl3IKfNXybkOG2uZM4fCPq")
            .authorities("ROLE_GUEST");
    }
}