# disi16
**DISI lab assignments**

 - Params are configurable from  `application.<problem-name>.RuntimeConfiguration`
 - IO module consisting of reading/writing and data conversion. `io.**.implementation.<problem-name>`
 - Generator module used to create test data. `generator.implementation.<problem-name>`
 - Solution module used to actually solve the problems. `solution.**.implementation.<problem-name>` 

>Lab assignment 1 - Knapsack problem
  - Classic methods -> *Exhaustive search*, *Greedy Search*, *Random Search*.
  - Hill-climbing methods -> *Random Hill Climbing*, *Steepest Hill Climbing*, *Experiment Hill Climbing*.
  
  	Adaptive iterations/restarts by certainty factor.
  	Binary representation of knapsack contents.
  	Neighborhood defined by K-Flip operations.
  	Experiment is defined as :multiple restarts, multiple hilltops, variable degree, parallelized steepest ascent.
>>Possible improvements:
 - Use Next-Ascent in Experiment HC.
 - Instead of binary strings, use bitsets of item count length.
 - Create an iterative approach to getting neighbors of degree N.
		
