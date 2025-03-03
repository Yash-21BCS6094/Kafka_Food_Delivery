package com.example.restraunt_service.dto;


public class RestaurantResponse {
    private RestaurantDto restaurantDto;
    private Integer responseCode;
    private String msg;

    public RestaurantDto getRestaurantDto() {
        return restaurantDto;
    }

    public void setRestaurantDto(RestaurantDto restaurantDto) {
        this.restaurantDto = restaurantDto;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
