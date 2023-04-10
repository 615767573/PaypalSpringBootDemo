package com.example.paypalspringbootdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe :
 */
@Data
public class ProductsBO implements Serializable {
    private String id;
    private String name;
    private String description;
    private String type;
    private String category;
    private String image_url;
    private String home_url;
}

