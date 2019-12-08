package io.github.javafaktura.s02e05.demo5;

import io.github.javafaktura.s02e05.demo5.lock.LoginAttemptsCounter;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class JavafakturaUserDetailsService extends InMemoryUserDetailsManager {

    private final LoginAttemptsCounter loginAttemptsCounter;

    public JavafakturaUserDetailsService(LoginAttemptsCounter loginAttemptsCounter) {
        this.loginAttemptsCounter = loginAttemptsCounter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptsCounter.isBlocked(username)) {
            throw new LockedException("You account has been locked!");
        }
        return super.loadUserByUsername(username);
    }
}
