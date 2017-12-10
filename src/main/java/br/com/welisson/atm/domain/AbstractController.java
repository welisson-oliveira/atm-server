package br.com.welisson.atm.domain;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public abstract class AbstractController {

    private final Logger logger = Logger.getLogger("filemanager");

    protected static final String CHARSET_SUFIX = ";charset=UTF-8";

    /**
     * Trata todas as excecoes que podem ocorrer na aplicacao. Caso a classe da
     * excecao contenha a anotacao <code>MvcException</code>, sera utilizado o
     * <code>HttpStatus</code> contido nela. Caso constrario, sera retornado
     * para o usuario o status 500.
     *
     * @param exception
     *            A excecao a ser tratada.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorData> handleContentManagementException(final Exception exception) {
        logger.error(exception.getMessage(), exception);
        Class<?> clazz = exception.getClass();
        Throwable throwable = exception.getCause();
        while (!clazz.isAnnotationPresent(MvcException.class)) {
            if (throwable == null || throwable.getClass().equals(clazz)) {
                break;
            }
            clazz = throwable.getClass();
            throwable = exception.getCause();
        }

        if (!clazz.isAnnotationPresent(MvcException.class)) {
            return new ResponseEntity<>(new ErrorData(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final MvcException mvcAnnotation = clazz.getAnnotation(MvcException.class);
        final HttpStatus status = mvcAnnotation.value();
        final String message = throwable != null ? throwable.getMessage() : exception.getMessage();
        final ErrorData data = new ErrorData(status.value(), message);
        return new ResponseEntity<>(data, status);
    }
}
