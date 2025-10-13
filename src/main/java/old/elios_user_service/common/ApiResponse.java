package old.elios_user_service.common;

public record ApiResponse<T>(int statusCode, String message, T data) {

    public ApiResponse(int status, T data) {
        this(status, "", data);
    }

    public ApiResponse {
        if (message == null) {
            message = "";
        }
    }
}