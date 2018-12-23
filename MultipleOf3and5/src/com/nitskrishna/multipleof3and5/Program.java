package com.multipleof3and5;

public class Program {
	private int counter = 3;
	private Thread t1;
	private Thread t2;
	private final Object synchronizer = new Object();
	private void print(String msg) throws InterruptedException {
		System.out.println(msg);
		Thread.sleep(500);
	}
	public void start() {
		t1 = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (synchronizer) {
						while(!isInterrupted()) {
							if(counter % 3 == 0) {
								print("1-"+counter);
								counter++;
							}else if(counter % 5 != 0) {
								counter++;
							}
							// do not increment for multiple of 5
							synchronizer.notifyAll();
							synchronizer.wait();
						}
					}
				}catch(InterruptedException e) {
					
				}
			};
		};

		t2 = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (synchronizer) {
						while(!isInterrupted()) {
							if(counter % 3 != 0 && counter % 5 == 0) {
								print("2-"+counter);
								counter++;
							}else if(counter % 3 != 0) {
								counter++;
							}
							synchronizer.notifyAll();
							synchronizer.wait();
						}
					}
				}catch (InterruptedException e) {

				}
			};
		};
		t1.start();
		t2.start();
	}
	public void stop() {
		if(t1 != null) {
			t1.interrupt();
		}
		if(t2 != null) {
			t2.interrupt();
		}
	}
}
