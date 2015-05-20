package edu.purdue.cs.tornado.helper;

import java.util.Random;

public class RandomGenerator extends Random{
	int seed;
	
	public RandomGenerator(int seed) {
		super(seed);
	}
	public double nextDouble(double min,double max){
		return min+(max-min)*nextDouble();
	}

}
