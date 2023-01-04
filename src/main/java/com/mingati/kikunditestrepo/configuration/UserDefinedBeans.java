package com.mingati.kikunditestrepo.configuration;

//package com.mingati.kikunditestrepo.configuration;
//
//import com.mingati.kikunditestrepo.security.UserAuthenticationProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class UserDefinedBeans {
//  private UserAuthenticationProvider userAuthenticationProvider;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new MyDatabaseUserDetailsService();
//    }
//
//
////    @Bean
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class UserDefinedBeans implements WebMvcConfigurer {


}