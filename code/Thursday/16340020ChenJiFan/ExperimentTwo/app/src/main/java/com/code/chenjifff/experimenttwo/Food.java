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

    public Food(String name, String typeSimple, String type, String nutrient, String backgroundColor) {
        this.name = name;
        this.typeSimple = typeSimple;
        this.type = type;
        this.nutrient = nutrient;
        this.backgroundColor = Color.parseColor(backgroundColor);
    }

    public Food(String name, String typeSimple, String type, String nutrient, int backgroundColor) {
        this.name = name;
        this.typeSimple = typeSimple;
        this.type = type;
        this.nutrient = nutrient;
        this.backgroundColor = backgroundColor;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) { this.name = name; }

    public String getTypeSimple() {
        return typeSimple;
    }

    public void setTypeSimple(String typeSimple) { this.typeSimple = typeSimple; }

    public String getType () {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public String getNutrient () { return nutrient; }

    public void setNutrient(String nutrient) { this.nutrient = nutrient; }

    public int getBackgroundColor() { return backgroundColor; }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = Color.parseColor(backgroundColor);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
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
