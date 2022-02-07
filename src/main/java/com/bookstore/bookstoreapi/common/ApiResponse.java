package com.bookstore.bookstoreapi.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> { // wild card : 어떤 클래스던 받을 수 있다! 를 선언
    private final boolean success;
    private String message = null;
    private T data;

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
