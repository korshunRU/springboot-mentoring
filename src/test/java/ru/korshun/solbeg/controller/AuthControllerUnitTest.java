package ru.korshun.solbeg.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import ru.korshun.solbeg.dto.request.AuthRequestDto;
import ru.korshun.solbeg.dto.response.AuthResponseDto;
import ru.korshun.solbeg.service.AuthService;
import ru.korshun.solbeg.utils.BaseResponse;
import ru.korshun.solbeg.utils.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerUnitTest {

  private MessageConverter messageConverter;
  private AuthRequestDto authRequestDto;
  private AuthService authService;
  private AuthController authController;

  @BeforeEach
  public void setup() {
    messageConverter = new MessageConverter();
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
    BaseResponse<Void> response = new BaseResponse<>(HttpStatus.BAD_REQUEST, "Bad email or password");
    assertEquals(messageConverter.toJson(response), messageConverter.toJson(authController.signIn(authRequestDto)));
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
    BaseResponse<AuthResponseDto> response = new BaseResponse<>(authResponseDto);
    assertThat(response)
            .usingRecursiveComparison()
            .ignoringFields("token")
            .isEqualTo(authController.signIn(authRequestDto));
    // assertEquals(messageConverter.toJson(response), messageConverter.toJson(authController.signIn(authRequestDto)));
  }
}