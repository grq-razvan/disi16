package application.tsp
/**
 * ... Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        def list = [1, 3, 5, 8, 4, 10]
        def fI = list.indices.first()
        def lI = list.indices.last()
        int i = 1, j = 4
        def result = list.subList(fI, i + 1) + list.subList(i + 1, j).reverse() + list.subList(j, lI + 1)
        println(result)
        def temp = list.collect()
        def length = 3
        def shift = 2

        def otherResult = list.subList(fI, i) + list.subList(i + length, i + length + shift) + list.subList(i, i + length) + list.subList(i + length + shift, lI + 1)
        println(otherResult)

        //expect: [1, 4, 3, 5, 8, 10]
        //expect s2: [1, 4, 10, 3, 5, 8]
        //if shift is bigger than remaining size, return list

    }
}
