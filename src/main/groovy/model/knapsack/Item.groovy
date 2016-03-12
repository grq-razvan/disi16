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

}
