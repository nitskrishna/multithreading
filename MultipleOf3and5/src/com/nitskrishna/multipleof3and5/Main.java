package com.nitskrishna.multipleof3and5;

public class Main {
	public static void main(String[] args) {
		Program program = new Program();
		program.start();
		try {
			Thread.sleep(10000);
		}catch(InterruptedException e) {
			
		}
		program.stop();
	}
}
