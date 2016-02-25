package model.knapsack

/**
 * Created by stefangrecu on 25/02/16.
 */
class Knapsack {
    Integer maxWeight
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

    @Override
    public String toString() {
        return "Knapsack{" +
                "maxWeight=" + maxWeight +
                ", items=" + items +
                '}';
    }
}
