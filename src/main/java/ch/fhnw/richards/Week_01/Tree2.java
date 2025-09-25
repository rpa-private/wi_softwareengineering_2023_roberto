package ch.fhnw.richards.Week_01;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree2<T> {
    private record TreeNode<T> (T data, List<TreeNode<T>> children) {
        TreeNode(T data) {
            this(data, new ArrayList<TreeNode<T>>());
        }
    };

    private TreeNode<T> root = null;

    /** What data is at the root? */
    public T getRoot() { return root.data; }

    /** Set the root of the tree. Only allowed if the root is null. */
    public boolean setRoot(T data) {
        boolean success = (data != null && root == null);
        if (success) {
            this.root = new TreeNode<>(data);
        }
        return success;
    }

    /**
     * What are the children of the node containing this
     * data? Returns null, if there is no such node. Returns an empty list, if the
     * node exists, but has no children
     */
    public List<T> getChildren(T data) {
        TreeNode<T> node = findNode(data);
        if (node == null)
            return null;
        else {
            List<T> dataList = new ArrayList<>();
            for (TreeNode<T> n : node.children) {
                dataList.add(n.data);
            }
            return dataList;
        }
    }

    /**
     * Add child to a node. Returns true if successful.
     * Returns false if: (1) No such parent-node exists, (2) the new
     * child is null, (3) the new child already exists in the tree
     */
    public boolean addChild(T parent, T child) {
        boolean success = (child != null);
        if (success) {
            TreeNode<T> parentNode = findNode(parent);
            TreeNode<T> childNode = findNode(child);
            success = parentNode != null && childNode == null;
            if (success) {
                parentNode.children.add(new TreeNode<>(child));
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
        boolean success = (data != null);
        if (success) {
            // If this is the root node, immediate remove it
            if (root.data.equals(data)) {
                root = null;
            } else {
                success = recursiveRemove(root, data);
            }
        }
        return success;
    }

    private boolean recursiveRemove(TreeNode<T> parent, T data) {
        Iterator<TreeNode<T>> i = parent.children.iterator();
        while (i.hasNext()) {
            TreeNode<T> child = i.next();
            if (child.data.equals(data)) {
                i.remove();
                return true;
            } else if (recursiveRemove(child, data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Search the tree for the given data. Return true if found, otherwise false.
     */
    public boolean contains(T data) {
        return findNode(data) != null;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * This version of Tree does not provide an iterator, although we really should...
     */
//    public Iterator<T> iterator() {
//    }

    /**
     * Return the total number of nodes in the tree
     */
    public int size() {
        if (root == null)
            return 0;
        else
            return countNodes(root);
    }

    //---------- Internal helper methods ------------//

    /**
     * Count is 1 (for the current node) plus the count of all children
     * The base-case is implicit: when there are no children, there are
     * no recursive calls.
     * @param node
     * @return
     */
    private int countNodes(TreeNode<T> node) {
        int count = 1; // This node
        for (TreeNode<T> child : node.children) {
            count += countNodes(child);
        }
        return count;
    }

    /**
     * Uses a recursive find method.
     */
    private TreeNode<T> findNode(T data) {
        return recursiveFindNode(root, data);
    }

    private TreeNode<T> recursiveFindNode(TreeNode<T> node, T data) {
        if (node.data.equals(data)) {
            return node; // Base case
        } else if (node.children.isEmpty()) {
            return null; // Base case 2
        } else { // Complex recursion: does any child return a non-null result?
            TreeNode<T> result = null;
            for (TreeNode<T> child : node.children) {
                result = recursiveFindNode(child, data);
                if (result != null) break;
            }
            return result;
        }
    }
}
