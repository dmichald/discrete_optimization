package utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Knapsack {
    int totalValue;
    int capacity;
    Map<Item, Integer> items;


    public Knapsack(int capacity) {
        this.capacity = capacity;
        items = new HashMap<>();

    }


    public void add(Item item, int amount) {
        items.put(item, amount);
        capacity = capacity - (item.getWeight() * amount);
        totalValue += amount * item.getPrice();
    }

}
