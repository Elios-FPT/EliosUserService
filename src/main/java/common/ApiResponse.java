package common;

public record ApiResponse<T>(int status, String message, T data) {

    public ApiResponse(int status, T data) {
        this(status, "", data);
    }

    public ApiResponse {
        if (message == null) {
            message = "";
        }
    }
}

