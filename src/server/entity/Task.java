package server.entity;

import java.io.Serializable;

public class Task implements Serializable, Printable {
    private String name;
    private int price;
    private Long duration;

    public Task() {

    }

    public Task(String name, int price, Long duration) {
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String getText() {
        return toString();
    }

    @Override
    public String toString() {
        return "Task: " + name + " price: " + price + " expected duration: " + duration;
    }

    @Override
    public String getTrimmedText() {
        return name;
    }
}
