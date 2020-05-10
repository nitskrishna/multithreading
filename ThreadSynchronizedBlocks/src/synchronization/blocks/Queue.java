package synchronization.blocks;

import java.util.Stack;

/**
 * Queue Implementation with Stack as underlying data-structure and with
 * parallel (almost) enqueue and dequeue
 * 
 * @author krishna_k
 *
 */
public class Queue {

	private Stack<Integer> addStack = new Stack<>();
	private Stack<Integer> removeStack = new Stack<>();
	private final Object addLock = new Object();
	private final Object removeLock = new Object();

	public void enqueue(int item) {
		synchronized (addLock) {
			addStack.push(item);
			addLock.notifyAll();
		}
	}

	public int dequeue() {
		int value = 0;
		synchronized (removeLock) {
			if (removeStack.isEmpty()) {
				try {
					synchronized (addLock) {
						while (addStack.isEmpty()) {
							addLock.wait();
						}
						while (!addStack.isEmpty()) {
							removeStack.push(addStack.pop());
						}
					}
				} catch (InterruptedException e) {
					// code for logging the exception
				}
			}
			value = removeStack.pop(); // within synchronized block is needed so as if other thread already removed an
										// element same is not removed again
		}
		return value;
	}
}
