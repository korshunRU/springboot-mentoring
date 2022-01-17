package ru.korshun.solbeg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korshun.solbeg.config.SecurityConfig;
import ru.korshun.solbeg.config.exception.UserExistException;
import ru.korshun.solbeg.dto.UserDto;
import ru.korshun.solbeg.dto.request.AuthRequestDto;
import ru.korshun.solbeg.dto.request.SignUpRequestDto;
import ru.korshun.solbeg.dto.response.AuthResponseDto;
import ru.korshun.solbeg.entity.RoleEntity;
import ru.korshun.solbeg.entity.UserEntity;
import ru.korshun.solbeg.mapper.UserMapper;
import ru.korshun.solbeg.repository.RoleRepository;
import ru.korshun.solbeg.repository.UserRepository;
import ru.korshun.solbeg.security.CustomTokenProvider;
import ru.korshun.solbeg.security.CustomUserDetails;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final CustomTokenProvider customTokenProvider;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final SecurityConfig securityConfig;


  public AuthResponseDto getUserDataOnSignIn(AuthRequestDto authRequest) {

    String email = authRequest.getEmail();
    String password = authRequest.getPassword();
    Authentication authentication;

    try {
      authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(email, password));
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Bad email or password");
    }

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    String token = customTokenProvider.generateToken(email, customUserDetails.getRoles());

    return new AuthResponseDto(customUserDetails.getId(), email, token);
  }


  @Transactional
  public void saveUser(SignUpRequestDto signUpRequest) {
    Optional<UserEntity> user = userRepository.findByEmail(signUpRequest.getEmail());

    if (user.isPresent()) {
      throw new UserExistException(String.format("User with email %s already exist", signUpRequest.getEmail()));
    }

    RoleEntity role = roleRepository.findByName("user");
    Set<RoleEntity> roleEntities = new HashSet<>();
    roleEntities.add(role);

    UserDto userDto = new UserDto();
    userDto.setFirstName(signUpRequest.getFirstName());
    userDto.setLastName(signUpRequest.getLastName());
    userDto.setEmail(signUpRequest.getEmail());
    userDto.setPassword(securityConfig.getPasswordEncoder().encode(signUpRequest.getPassword()));
    userDto.setCreatedAt(new Date().getTime());
    userDto.setRoles(roleEntities);

    UserEntity userEntity = userMapper.toEntity(userDto);

    userRepository.save(userEntity);

  }

}
