package ru.korshun.solbeg.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.korshun.solbeg.entity.UserEntity;
import ru.korshun.solbeg.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserEntity user = userRepository.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException(
                    String.format("User not found with email %s", email)
            ));

    return new CustomUserDetails(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword(),
            user.getCreatedAt(),
            user.getRoles(),
            true
    );
  }
}
