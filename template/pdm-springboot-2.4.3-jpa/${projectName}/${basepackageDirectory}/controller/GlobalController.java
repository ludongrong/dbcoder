package ${basepackage}.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.onemysoft.common.exception.BusinessException;
import com.onemysoft.common.exception.ResourceNotFoundException;
import com.onemysoft.common.web.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalController {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public Result error(Exception e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message("后台异常，请联系管理员！");
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public Result error(BusinessException e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message(e.getMsg());
	}

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public Result handleAuthorizationException(AccessDeniedException e) {
//        log.error(e.getMessage());
//        return Result.error().code(HttpServletResponse.SC_UNAUTHORIZED).message("没有权限，请联系管理员授权");
//    }

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public Result validatedBindException(BindException e) {

		log.error(e.getMessage());
		String message = e.getAllErrors().get(0).getDefaultMessage();
		return Result.error().message(message);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result validExceptionHandler(MethodArgumentNotValidException e) {
		
		log.error(e.getMessage());
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		return Result.error().message(message);
	}

//    @ExceptionHandler(BadCredentialsException.class)
//    public Result userNotFound(BadCredentialsException e) {
//        log.error(e.getMessage());
//        return Result.error().code(HttpServletResponse.SC_BAD_REQUEST).message("用户名或者密码错误");
//    }
//
//    @ExceptionHandler(LockedException.class)
//    public Result userLocked(LockedException e) {
//        log.error(e.getMessage());
//        return Result.error().code(HttpServletResponse.SC_FORBIDDEN).message(e.getMessage());
//    }
//
//    @ExceptionHandler(AuthenticationServiceException.class)
//    public Result handleAuthenticationServiceException(AuthenticationServiceException e) {
//        log.error(e.getMessage());
//        return Result.error().message("验证码错误");
//    }

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Result handleSQLIntegrityConstraintViolationException(DataIntegrityViolationException e) {

		log.error(e.getMessage());
		e.printStackTrace();
		return Result.error().message("违反数据完整性约束");
	}

}
