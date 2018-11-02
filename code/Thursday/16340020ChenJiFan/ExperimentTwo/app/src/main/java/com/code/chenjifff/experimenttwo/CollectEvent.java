package com.code.chenjifff.experimenttwo;

public class CollectEvent {
    private Food food;
    private Boolean ifCollect;

    public CollectEvent(Food food, Boolean ifCollect) {
        this.food = food;
        this.ifCollect = ifCollect;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    public void setIfCollect(Boolean ifCollect) {
        this.ifCollect = ifCollect;
    }

    public Boolean getIfCollect() {
        return ifCollect;
    }
}
