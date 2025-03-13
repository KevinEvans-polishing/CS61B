public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.left.isBlack = true;
        node.right.isBlack = true;
        node.isBlack = false;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // 重构指针
        var oldNode = node;
        node = node.left;
        oldNode.left = node.right;
        node.right = oldNode;

        // 互换节点颜色
        boolean color = node.isBlack;
        node.isBlack = oldNode.isBlack;
        oldNode.isBlack = color;

        // 更新parent节点的子节点
        var parent = findParent(oldNode.item);
        if (parent == null) {
            root = node;
        } else {
            if (parent.left != null && parent.left.item == oldNode.item) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        }
        return root;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        var oldNode  = node;
        node = oldNode.right;
        oldNode.right = node.left;
        node.left = oldNode;

        boolean color = node.isBlack;
        node.isBlack = oldNode.isBlack;
        oldNode.isBlack = color;

        var parent = findParent(oldNode.item);
        if (parent == null) {
            root = node;
        } else {
            if (parent.left != null && parent.left.item == oldNode.item) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        }
        return root;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        RBTreeNode<T> newNode = new RBTreeNode<>(false, item);
        // TODO: Handle normal binary search tree insertion.
        node = insertHelper(node, newNode);
        // TODO: Rotate left operation
        var parent = findParent(item);
        if (parent == null) {
            return newNode;
        }
        if ((parent.left == null || parent.left.isBlack) && parent.right != null && parent.right.item == item) {
            node = rotateLeft(parent);
            parent = findParent(parent.item);
        }
        // TODO: Rotate right operation
        if (isRed(parent) && isRed(parent.left)) {
            node = rotateRight(findParent(parent.item));
        }
        // TODO: Color flip
        while (parent != null && isRed(parent.left) && isRed(parent.right)) {
            flipColors(parent);
            parent = findParent(parent.item);
        }
        // 右转累积效应修正
        var current = root;
        while (current.left != null) {
            if (isRed(current) && isRed(current.left)) {
                node = rotateRight(findParent(current.item));
                break;
            }
            current = current.left;
        }
        while (parent != null && isRed(parent.left) && isRed(parent.right)) {
            flipColors(parent);
            parent = findParent(parent.item);
        }
        return node; //fix this return statement
    }

    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, RBTreeNode<T> newNode) {
        if (node == null) {
            return newNode;
        } else if (node.item.compareTo(newNode.item) > 0) {
            node.left = insertHelper(node.left, newNode);
        } else if (node.item.compareTo(newNode.item) < 0) {
            node.right = insertHelper(node.right, newNode);
        }
        return node;
    }
    private RBTreeNode<T> findParent(T item) {
        RBTreeNode<T> parent = null;
        var current = root;
        while (current != null) {
            if (current.item == item) {
                return parent;
            }
            parent = current;
            if (current.item.compareTo(item) > 0) {
                current = current.left;
            } else if (current.item.compareTo(item) < 0) {
                current = current.right;
            }
        }
        return null;
    }
}
