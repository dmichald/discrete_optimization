package algorithms;

import lombok.Getter;
import utils.Item;
import utils.Knapsack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KnapsackProblem {
    private final int knapsackCapacity;
    @Getter
    private List<Item> items;
    @Getter
    private int[][] P;

    @Getter
    private int[][] Q;

    public KnapsackProblem(ArrayList<Item> items, int knapsackCapacity) {
        this.items = items;
        this.knapsackCapacity = knapsackCapacity;
        this.P = new int[items.size() + 1][knapsackCapacity + 1];
        this.Q = new int[items.size() + 1][knapsackCapacity + 1];
    }

    private Item getItemWithTheHighestRatio(List<Item> items) {
        return items.stream().max(Comparator.comparingDouble(Item::getRatio)).get();
    }

    private Item getTheLightestItem(List<Item> items) {
        return items.stream().min(Comparator.comparingInt(Item::getWeight)).get();
    }

    public Knapsack greedy() {

        List<Item> copiedList = new ArrayList<>(items);
        Knapsack knapsack = new Knapsack(knapsackCapacity);
        while (hasEnoughSpace(knapsack, copiedList)) {
            Item item = getItemWithTheHighestRatio(copiedList);
            if (item.getWeight() <= knapsack.getCapacity()) {
                int amountToAdd = knapsack.getCapacity() / item.getWeight();
                knapsack.add(item, amountToAdd);
                copiedList.remove(item);
            } else {
                copiedList.remove(item);
            }
        }
        return knapsack;
    }

    private boolean hasEnoughSpace(Knapsack knapsack, List<Item> items) {
        return knapsack.getCapacity() != 0 && knapsack.getCapacity() >= getTheLightestItem(items).getWeight();
    }

    public void fillMatrixP_Q() {
        for (int i = 1; i < P.length; i++) {
            for (int j = 1; j < knapsackCapacity + 1; j++) {
                if (items.get(i - 1).getWeight() <= j && P[i - 1][j] < P[i][j - items.get(i - 1).getWeight()] + items.get(i - 1).getPrice()) {
                    P[i][j] = P[i][j - items.get(i - 1).getWeight()] + items.get(i - 1).getPrice();
                    Q[i][j] = i;
                } else {
                    P[i][j] = P[i - 1][j];
                    Q[i][j] = Q[i - 1][j];
                }
            }
        }
    }

    public Knapsack getDynamicKnapsack() {
        int[] lastRowQ = Q[Q.length - 1];
        Knapsack knapsack = new Knapsack(knapsackCapacity);
        int itemWeight;

        for (int i = lastRowQ.length - 1; i > 1; i = i - itemWeight) {
            if (lastRowQ[i] - 1 <= 0) {
                break;
            }
            Item item = items.get(lastRowQ[i] - 1);
            if (knapsack.getItems().containsKey(item)) {
                knapsack.getItems().put(item, knapsack.getItems().get(item) + 1);
                knapsack.setCapacity(knapsack.getCapacity() - (item.getWeight()));
                knapsack.setTotalValue(knapsack.getTotalValue() + item.getPrice());
            } else {
                knapsack.add(item, 1);
            }
            itemWeight = items.get(lastRowQ[i] - 1).getWeight();
        }
        return knapsack;
    }
}