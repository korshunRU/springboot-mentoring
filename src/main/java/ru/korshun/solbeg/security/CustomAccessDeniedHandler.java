package ru.korshun.solbeg.security;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.korshun.solbeg.utils.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                     AccessDeniedException e) throws IOException {

    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

    BaseResponse<Void> baseResponse = new BaseResponse<>(HttpStatus.FORBIDDEN,
            "You don't have required permissions to perform this action");
    JSONObject jsonObject = new JSONObject(baseResponse);

    httpServletResponse.getOutputStream().println(jsonObject.toString());

  }
}
