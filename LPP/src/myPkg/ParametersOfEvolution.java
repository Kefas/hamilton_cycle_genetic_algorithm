package myPkg;

public class ParametersOfEvolution {
	int numberOfIterations = 5000;
	double sizeOfPopulation = 0.5; //that is in relation to the number of vertexes of graph
	int methodOfMutation = 1;
	int methodOfCrossing = 1;
	int methodOfBreeding = 1;
	public int getMethodOfBreeding() {
		return methodOfBreeding;
	}
	public void setMethodOfBreeding(int methodOfBreeding) {
		this.methodOfBreeding = methodOfBreeding;
	}
	int methodToFinish = 1;
	double similiarityInTest = 0.98;
	ParamsOfIndividual individualParams = new ParamsOfIndividual();
	
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
	long correctResult = 0;
	
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
