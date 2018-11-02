package com.code.chenjifff.experimenttwo;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Food implements Serializable {
    private String name;
    private String typeSimple;
    private String type;
    private String nutrient;
    private int backgroundColor;

    public Food(String _name, String _typeSimple, String _type, String _nutrient, String _backgroundColor) {
        name = _name;
        typeSimple = _typeSimple;
        type = _type;
        nutrient = _nutrient;
        backgroundColor = Color.parseColor(_backgroundColor);
    }

    public String getName () {
        return name;
    }

    public String getTypeSimple() {
        return typeSimple;
    }

    public String getType () {
        return type;
    }

    public String getNutrient () {
        return nutrient;
    }

    public int getBackgroundColor () {
        return backgroundColor;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Food)) {
            return false;
        }
        Food food = (Food) o;
        return Objects.equals(name, food.name) &&
                Objects.equals(typeSimple, food.typeSimple) &&
                Objects.equals(type, food.type) &&
                Objects.equals(nutrient, food.nutrient) &&
                (backgroundColor == food.backgroundColor);
    }
}
