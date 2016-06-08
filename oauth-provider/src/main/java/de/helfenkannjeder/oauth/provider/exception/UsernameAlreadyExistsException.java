package de.helfenkannjeder.oauth.provider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists.")
public class UsernameAlreadyExistsException extends RuntimeException {
}
