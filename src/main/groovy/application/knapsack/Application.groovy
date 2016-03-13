package application.knapsack
/**
 *  Created by stefangrecu on 25/02/16.
 */
class Application {

    private final ExecutionDataHolder executionDataHolder


    Application() {
        this.executionDataHolder = new ExecutionDataHolder()
    }

    void generateFiles() {
        executionDataHolder.generateFiles()
    }

    void executeClassicMethods() {
        executionDataHolder.executeClassicMethodsBatchTest()
    }

    void executeHillClimbing() {
        executionDataHolder.executeHillClimbingBatchTest()
    }

    public static void main(String[] args) {
        Application application = new Application()
        //application.generateFiles()
        application.executeClassicMethods()
        //application.executeHillClimbing()
    }
}