package byAJ.Securex.configs;

import byAJ.Securex.repositories.PersonRepository;
import byAJ.Securex.service.SSUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private SSUserDetailsService userDetailsService;
    @Autowired
    private PersonRepository personRepository;
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception{
        return new SSUserDetailsService(personRepository);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/css/**","/js/**",
                "/img/**","/vendor/**","/scss/**","/register").permitAll()
                .antMatchers("/books/edit/**","/books/delete/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin().failureUrl("/login?error")
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .httpBasic();// this allows to login using the password and user in the console, it doesn't matter if we take it out

        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceBean());
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
    }
}
