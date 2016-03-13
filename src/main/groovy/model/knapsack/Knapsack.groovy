package model.knapsack
/**
 *  Created by stefangrecu on 25/02/16.
 */
class Knapsack {
    Integer maxWeight
    Integer currentWeight = 0
    Integer totalValue = 0
    List<Item> items = []

    public boolean validate() {
        return currentWeight <= maxWeight
    }

    public void addItem(Item item) {
        items.add(item)
        currentWeight += item.weight
        totalValue += item.value
    }

    public void clearKnapsack() {
        if (!items.empty) {
            items = []
            currentWeight = 0
            totalValue = 0
        }
        System.gc()
    }

    @Override
    public String toString() {
        return "Knapsack{" +
                "maxWeight=" + maxWeight +
                ", currentWeight=" + currentWeight +
                ", totalValue=" + totalValue +
                ", items=" + items +
                '}';
    }

}
