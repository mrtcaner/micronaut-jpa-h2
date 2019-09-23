package micronaut.jpa.h2.util.error;

public enum ErrorEnum {

    INVALID_DATA("Invalid Data",1000);

    private String message;
    private Integer code;

    ErrorEnum(String message, int code){
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
