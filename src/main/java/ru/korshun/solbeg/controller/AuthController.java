package ru.korshun.solbeg.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.korshun.solbeg.config.exception.UserExistException;
import ru.korshun.solbeg.dto.request.AuthRequestDto;
import ru.korshun.solbeg.dto.request.SignUpRequestDto;
import ru.korshun.solbeg.dto.response.AuthResponseDto;
import ru.korshun.solbeg.utils.BaseResponse;
import ru.korshun.solbeg.service.AuthService;


@Slf4j
@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @Operation(
          summary = "User login",
          description = "User login"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Success",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = AuthResponseDto.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "400",
          description = "Bad Request"
  )
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
          value = "/signin",
          method = RequestMethod.POST,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public BaseResponse<AuthResponseDto> signIn(@RequestBody AuthRequestDto authRequest) {

    try {
      AuthResponseDto response = authService.getUserDataOnSignIn(authRequest);
      log.info(String.format("Signin from %s success", authRequest.getEmail()));
      return new BaseResponse<>(response);
    } catch (BadCredentialsException e) {
      log.error(e.getMessage());
      return new BaseResponse<>(
              HttpStatus.BAD_REQUEST,
              e.getMessage());
    }

  }

  @Operation(
          summary = "Create new user",
          description = "Create new user"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Success",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "409",
          description = "CONFLICT",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(
          value = "/signup",
          method = RequestMethod.POST,
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public BaseResponse<Void> signUp(@RequestBody SignUpRequestDto signUpRequest) {

    try {
      authService.saveUser(signUpRequest);
    } catch (UserExistException e) {
      log.error(e.getMessage());
      return new BaseResponse<>(
              HttpStatus.CONFLICT,
              e.getMessage()
      );
    }

    log.info(String.format("Registration successfully: %s", signUpRequest.getEmail()));

    return new BaseResponse<>(
            HttpStatus.CREATED,
            "Registration successfully");
  }

}
