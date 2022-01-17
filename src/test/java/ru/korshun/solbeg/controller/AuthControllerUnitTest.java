package ru.korshun.solbeg.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import ru.korshun.solbeg.dto.request.AuthRequestDto;
import ru.korshun.solbeg.dto.response.AuthResponseDto;
import ru.korshun.solbeg.service.AuthService;
import ru.korshun.solbeg.utils.BaseResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthControllerUnitTest {

  private AuthRequestDto authRequestDto;
  private AuthService authService;
  private AuthController authController;

  @BeforeEach
  public void setup() {
    authRequestDto = new AuthRequestDto(
            "test@test.ru",
            "123456"
    );
    authService = Mockito.mock(AuthService.class);
    authController = new AuthController(authService);
  }

  @Test
  void authFailedTest() {
    Mockito
            .when(authService.getUserDataOnSignIn(authRequestDto))
            .thenThrow(new BadCredentialsException("Bad email or password"));
    assertThatThrownBy(() -> authController.signIn(authRequestDto)).isInstanceOf(BadCredentialsException.class);
  }

  @Test
  void authCompliteTest() {
    AuthResponseDto authResponseDto = new AuthResponseDto(
            1L,
            authRequestDto.getEmail(),
            "123"
    );
    Mockito
            .when(authService.getUserDataOnSignIn(authRequestDto))
            .thenReturn(authResponseDto);
    ResponseEntity<BaseResponse<AuthResponseDto>> response =
            ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(authResponseDto));
    assertThat(response)
            .usingRecursiveComparison()
            .ignoringFields("token")
            .isEqualTo(authController.signIn(authRequestDto));
  }
}