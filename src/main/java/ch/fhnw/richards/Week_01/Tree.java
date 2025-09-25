package ch.fhnw.richards.Week_01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A general purpose tree data structure. Nodes in the tree can have zero or
 * more children. For efficiency, we represent the tree using a HashMap, rather
 * than an explicit tree structure. The data-node is the key, and the node's
 * children are the value.
 * 
 * We maintain a reference to the special "root" node, so that we know where the
 * tree starts
 * 
 * The tree is a set, meaning that we do not allow duplicates. However, we
 * cannot implement the Set interface, because the operations are defined
 * incorrectly. For example, the "add" method from Set does not require the user
 * to specify a parent node.
 */
public class Tree<T> {
	private final HashMap<T, List<T>> allData = new HashMap<>();
	private T root = null;

	/**
	 * Default constructor - we don't really need to implement this
	 */
	public Tree() {
	}

	/** What data is at the root? */
	public T getRoot() {
		return root;
	}

	/** Set the root of the tree. Only allowed if the root is null. */
	public boolean setRoot(T data) {
		boolean success = (data != null && root == null);
		if (success) {
			this.root = data;
			allData.put(data, new ArrayList<>()); // New node in tree
		}
		return success;
	}

	/**
	 * What are the children of the node containing this
	 * data? Returns null, if there is no such node. Returns an empty list, if the
	 * node exists, but has no children
	 */
	public List<T> getChildren(T data) {
		return allData.get(data);
	}

	/**
	 * Add child to a node. Returns true if successful.
	 * Returns false if: (1) No such parent-node exists, (2) the new child is null,
	 * (3) the new child already exists in the tree
	 */
	public boolean addChild(T parent, T child) {
		boolean success = (child != null);
		if (success) {
			List<T> children = allData.get(parent); // Null if parent does not exist
			List<T> childChildren = allData.get(child); // Not-null if child already in tree
			success = children != null && childChildren == null;
			if (success) {
				children.add(child); // New child of parent
				allData.put(child, new ArrayList<>()); // New node in tree
			}
		}
		return success;
	}

	/**
	 * Remove node from the tree. Note that removing a node
	 * also removes all of its descendents! Returns true if the node exists. Returns
	 * false if the node does not exist or if the parameter is null.
	 */
	public boolean removeNode(T data) {
		boolean success = (data != null && this.contains(data));
		if (success) {
			// Remove this node and all of its descendents
			recursiveRemove(data);
			// If this was the root node, set root to null
			if (root.equals(data)) root = null;
		}
		return success;
	}

	/**
	 * Private method to remove all descendents of a given node. This is the only
	 * operation that would be more efficient, if we used an explicit tree-structure instead
	 * of a HashMap.
	 */
	private void recursiveRemove(T data) {
		List<T> children = this.getChildren(data);
		allData.remove(data);
		for (T child : children)
			recursiveRemove(child);
	}

	/**
	 * Search the tree for the given data. Return true if found, otherwise false.
	 */
	public boolean contains(T data) {
		return allData.containsKey(data);
	}

	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * We really ought to implement a tree-oriented iterator (preorder or postorder).
	 * For the moment, we just pass on an iterator directly on our internal HashMap
	 */	
	public Iterator<T> iterator() {
		return allData.keySet().iterator();
	}

	/**
	 * Return the total number of nodes in the tree
	 */
	public int size() {
		return allData.size();
	}
}
