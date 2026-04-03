package lzh.common;

import lombok.Data;

@Data
public class Error {
    private Integer code;
    private String message;
    private Object data;

    public static Error success() {
        Error error = new Error();
        error.setCode(200);
        return error;
    }

    public static Error error() {
        Error error = new Error();
        error.setCode(500);
        return error;
    }

    public static Error error(Integer code,String message) {
        Error error = new Error();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }

    public static Error success(String message,Object data) {
        Error error = new Error();
        error.setCode(200);
        error.setMessage(message);
        error.setData(data);
        return error;
    }
}
