package application.tsp
/**
 * ... Created by stefangrecu on 24/03/16.
 */
class Application {
    public static void main(String[] args) {
        ExecutionDataHolder holder = new ExecutionDataHolder()
        //holder.generateFiles()
        holder.executeSingleTest([dimension: 5])
    }
}
