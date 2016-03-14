package application.knapsack
/**
 *  Created by stefangrecu on 25/02/16.
 */
class Application {

    /**
     * The ExecutionDataHolder is a top-level wrapper
     * for file generation and application of algorithms
     * on test data.
     */
    private final ExecutionDataHolder executionDataHolder


    Application() {
        this.executionDataHolder = new ExecutionDataHolder()
    }
    /**
     *  Generates test data files. Data definition
     *  and configuration can be done in the
     *  RuntimeConfiguration interface under the
     *  ITEM_PARAMS list.
     */
    void generateFiles() {
        executionDataHolder.generateFiles()
    }

    /**
     * Applies classic knapsack algorithms to
     * previously generated test data files.
     * Data configuration can be done from the
     * RuntimeConfiguration interface, under the
     * CLASSIC_TEST_CASES list and CLASSIC_SOLUTIONS list.
     */
    void executeClassicMethods() {
        executionDataHolder.executeClassicMethodsBatchTest()
    }

    /**
     * Applies hill climbing methods to solve
     * the knapsack problem. Data is taken from
     * the previously generated test files. Data
     * configuration can be done from the
     * RuntimeConfiguration interface, under the
     * HILL_CLIMBING_TEST_CASES list and the
     * HILL_CLIMBING_SOLUTIONS list.
     */
    void executeHillClimbing() {
        executionDataHolder.executeHillClimbingBatchTest()
    }

    public static void main(String[] args) {
        Application application = new Application()

        //application.generateFiles()
        //application.executeClassicMethods()
        //application.executeHillClimbing()

    }
}