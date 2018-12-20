package cat.udl.eps.entsoftarch.textannot.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Tags with childs cannot be deleted")
public class TagDeleteException extends RuntimeException {

    public TagDeleteException() {
        super();
    }
}
