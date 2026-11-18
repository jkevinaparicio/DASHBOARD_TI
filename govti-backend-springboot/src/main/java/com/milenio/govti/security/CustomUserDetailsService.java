package com.milenio.govti.security;

import com.milenio.govti.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public CustomUserDetailsService(UserRepository users){this.users=users;}
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=users.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
            createAuthorityList("ROLE_"+user.getRole().name()));
    }
}
