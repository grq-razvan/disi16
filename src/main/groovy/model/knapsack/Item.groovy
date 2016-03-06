package model.knapsack

/**
 *  Created by stefangrecu on 25/02/16.
 */
class Item {
    Integer weight
    Integer value

    @Override
    public String toString() {
        return """${weight} ${value}"""
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Item)) return false

        Item item = (Item) o

        if (value != item.value) return false
        if (weight != item.weight) return false

        return true
    }

    int hashCode() {
        int result
        result = (weight?.hashCode() ?: 0)
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }
}
