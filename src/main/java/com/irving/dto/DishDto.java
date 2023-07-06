package com.irving.dto;


import com.irving.domain.Dish;
import com.irving.domain.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish implements Serializable {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName ;

    private Integer copies;
}
