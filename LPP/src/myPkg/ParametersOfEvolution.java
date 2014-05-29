package myPkg;

public class ParametersOfEvolution {
	int numberOfIterations = 5000;
	double sizeOfPopulation = 0.5; //that is in relation to the number of vertexes of graph
	int methodOfMutation = 1;
	int methodOfCrossing = 1;
	int methodOfBreeding = 1;
	
	int methodToFinish = 2;
	//parameters for the 2nd method
	int maxAmountOfAncestors = 2000; //how many ancestors do I check to identify end of evolution
	double similarityToAncestors = 0.98; //how much must the best individual be similar to the best ancestor from the latest 'amountOfAncestors'
	//parameters for the 3rd method
	double similiarityInTest = 0.98;
	long correctResult = 0;
	
	
	ParamsOfIndividual individualParams = new ParamsOfIndividual();
	
	
	public int getMaxAmountOfAncestors() {
		return maxAmountOfAncestors;
	}
	public void setMaxAmountOfAncestors(int amountOfAncestors) {
		this.maxAmountOfAncestors = amountOfAncestors;
	}
	public double getSimilarityToAncestors() {
		return similarityToAncestors;
	}
	public void setSimilarityToAncestors(double similarityToAncestors) {
		this.similarityToAncestors = similarityToAncestors;
	}
		
	public int getMethodOfBreeding() {
		return methodOfBreeding;
	}
	public void setMethodOfBreeding(int methodOfBreeding) {
		this.methodOfBreeding = methodOfBreeding;
	}
	
	public double getSimiliarityInTest() {
		return similiarityInTest;
	}
	public void setSimiliarityInTest(double similiarityInTest) {
		this.similiarityInTest = similiarityInTest;
	}
	public int getMethodToFinish() {
		return methodToFinish;
	}
	public void setMethodToFinish(int methodToFinish) {
		this.methodToFinish = methodToFinish;
	}
	public long getCorrectResult() {
		return correctResult;
	}
	public void setCorrectResult(long correctResult) {
		this.correctResult = correctResult;
	}
	
	public ParamsOfIndividual getIndividualParams() {
		return individualParams;
	}
	public int getNumberOfIterations() {
		return numberOfIterations;
	}
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}
	public double getSizeOfPopulation() {
		return sizeOfPopulation;
	}
	public void setSizeOfPopulation(double sizeOfPopulation) {
		this.sizeOfPopulation = sizeOfPopulation;
	}
	public int getMethodOfMutation() {
		return methodOfMutation;
	}
	public void setMethodOfMutation(int methodOfMutation) {
		this.methodOfMutation = methodOfMutation;
	}
	public int getMethodOfCrossing() {
		return methodOfCrossing;
	}
	public void setMethodOfCrossing(int methodOfCrossing) {
		this.methodOfCrossing = methodOfCrossing;
	}
}
