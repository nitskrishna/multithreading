package synchronization.blocks.basics;

/**
 * 
 * @author krishna_k
 * 
 *         1. If a thread holding lock invokes a method which is not
 *         synchronized (not synchronized on the same lock , neither has any
 *         synchronized block on the same lock), IT DOES NOT RELEASE THE LOCK,
 *         IT STILL HOLDS IT
 * 
 *         2. If a thread holding lock CAN INVOKE (any number of times) a method which is synchronized
 *         on the same lock. In between lock is not released
 *
 */
public class Tester {
	private final Object lock = new Object();
	Thread t1;
	Thread t2;

	public void init() {
		t1 = new Thread("Thread_1") {
			public void run() {
				try {
					method1();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException Caught T1");
				}

			};
		};
		t2 = new Thread("Thread_2") {
			public void run() {
				try {
					method1();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException Caught T2");
				}
			};
		};

		t1.start();
		t2.start();
	}

	public void method1() throws InterruptedException {
		System.out.println(
				"current thread:: " + Thread.currentThread().getName() + ", Method:: method1 before Synchronized, "
						+ "T1 State " + t1.getState() + ", T2 State:: " + t2.getState());
		synchronized (lock) {
			System.out.println(
					"current thread:: " + Thread.currentThread().getName() + ", Method:: method1 inside Synchronized, "
							+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
			Thread.sleep(1000);
			method2();
		}
		System.out.println(
				"current thread:: " + Thread.currentThread().getName() + ", Method:: method1 after Synchronized, "
						+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
	}

	public void method2() throws InterruptedException {
		System.out.println(
				"current thread:: " + Thread.currentThread().getName() + ", Method:: method2 before Synchronized, "
						+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
		synchronized (lock) {
			System.out.println(
					"current thread:: " + Thread.currentThread().getName() + ", Method:: method2 inside Synchronized, "
							+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
			method3();
			Thread.sleep(2500);
		}
		System.out.println(
				"current thread:: " + Thread.currentThread().getName() + ", Method:: method2 after Synchronized, "
						+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
		Thread.sleep(1000);
	}

	public void method3() throws InterruptedException {
		System.out.println("current thread:: " + Thread.currentThread().getName() + ", Method:: method3 Entry, "
				+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
		Thread.sleep(2500);
		System.out.println("current thread:: " + Thread.currentThread().getName() + ", Method:: method3 Exit, "
				+ "T1 State:: " + t1.getState() + ", T2 State:: " + t2.getState());
	}

}
