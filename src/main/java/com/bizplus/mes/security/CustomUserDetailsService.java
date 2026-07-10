package com.bizplus.mes.security;

import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserIdAndDeletedAtIsNull(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found [ID: " + username + "]"));

        return new CustomUserDetails(user);
    }
}
