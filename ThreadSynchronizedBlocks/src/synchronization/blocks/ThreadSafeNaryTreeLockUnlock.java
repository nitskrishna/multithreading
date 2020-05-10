package synchronization.blocks;

/**
 * URL :
 * https://www.geeksforgeeks.org/locking-and-unlocking-of-resources-in-the-form-of-n-ary-tree/
 * 
 * 
 * Given an n-ary tree of resources arranged hierarchically such that height of
 * tree is O(Log N) where N is total number of nodes (or resources). A process
 * needs to lock a resource node in order to use it. But a node cannot be locked
 * if any of its descendant or ancestor is locked.
 * 
 * Following operations are required for a given resource node:
 * 
 * islock()- returns true if a given node is locked and false if it is not. A
 * node is locked if lock() has successfully executed for the node.
 * 
 * Lock()- locks the given node if possible and updates lock information. Lock
 * is possible only if ancestors and descendants of current node are not locked.
 * 
 * 
 * unLock()- unlocks the node and updates information. How design resource nodes
 * and implement above operations such that following time complexities are
 * achieved.
 * 
 * islock() O(1)
 * 
 * Lock() O(log N)
 * 
 * unLock() O(log N)
 * 
 * @author krishna_k
 *
 */
public class ThreadSafeNaryTreeLockUnlock {

	public static class Node {
		int id;
		boolean isLocked;
		Node parent;
		int lockedChildCount;
	}
	
	public synchronized boolean isLocked(Node node) {
		return node.isLocked;
	}

	/**
	 * 
	 * @param node
	 * @throws InValidOperationException in case node can not be locked (itself or
	 *                                   any of its ancestor or any of its children
	 *                                   is locked)
	 * 
	 * @throws NullPointerException      in case node is null
	 */
	public synchronized void lock(Node node) throws InValidOperationException {
		// self or if any of node's children is locked
		String exceptionMessage = "Node with id " + node.id + " can not be locked";
		if (node.isLocked || node.lockedChildCount > 0) {
			throw new InValidOperationException(exceptionMessage);
		}

		// check if any of its parent is locked
		Node ancestor = node;
		while (ancestor != null) {
			if (ancestor.isLocked) {
				throw new InValidOperationException(exceptionMessage);
			}
			ancestor = ancestor.parent;
		}
		// control reached here => Lock is possible
		ancestor = node;
		while (ancestor != null) {
			ancestor.lockedChildCount++;
			ancestor = ancestor.parent;
		}
		node.isLocked = true;
	}

	/**
	 * 
	 * @param node
	 * @throws InValidOperationException in case node is already unlocked
	 * @throws NullPointerException      in case node is null
	 */
	public synchronized void unlock(Node node) throws InValidOperationException {
		String exceptionMessage = "Node with id " + node.id + " can not be unlocked as it is already unlocked";
		if (!node.isLocked) {
			throw new InValidOperationException(exceptionMessage);
		}

		Node ancestor = node.parent;
		while (ancestor != null) {
			ancestor.lockedChildCount--;
			ancestor = ancestor.parent;
		}
		node.isLocked = false;
	}

	public static class InValidOperationException extends Exception {
		public InValidOperationException(String message) {
			super(message);
		}
	}
}
