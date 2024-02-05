package uz.pikosolutions.myrestaurant.auth.error.exceptions;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
