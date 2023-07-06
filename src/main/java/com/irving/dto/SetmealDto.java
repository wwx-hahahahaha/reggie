package com.irving.dto;


import com.irving.domain.Setmeal;
import com.irving.domain.SetmealDish;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
//@Builder
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
