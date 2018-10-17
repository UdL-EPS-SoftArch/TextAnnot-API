package cat.udl.eps.entsoftarch.textannot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="TagHierarchy Object must contains defined name and root.")
public class TagHierarchyValidationException extends RuntimeException {
}
