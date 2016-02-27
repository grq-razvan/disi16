package model.knapsack

/**
 * Created by stefangrecu on 25/02/16.
 */
class Knapsack {
    Integer maxWeight
    Integer currentWeight = 0
    Integer totalValue = 0
    List<Item> items

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Knapsack)) return false

        Knapsack knapsack = (Knapsack) o

        if (items != knapsack.items) return false
        if (maxWeight != knapsack.maxWeight) return false

        return true
    }

    int hashCode() {
        int result
        result = (maxWeight != null ? maxWeight.hashCode() : 0)
        result = 31 * result + (items != null ? items.hashCode() : 0)
        return result
    }

    private boolean canAdd() {
        return currentWeight < maxWeight
    }

    public void addItem(Item item) {
        items.add(item)
        currentWeight += item.weight
        totalValue += item.value
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
