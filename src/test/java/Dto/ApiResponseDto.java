package Dto;

public class ApiResponseDto<T> {
    public String statusCode, version;
    public T data;
}
