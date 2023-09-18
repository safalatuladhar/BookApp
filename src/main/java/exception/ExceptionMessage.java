package exception;

public class ExceptionMessage extends RuntimeException{
    private int statusCode;
    private String message;

    public ExceptionMessage(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Exception{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
