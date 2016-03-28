package RecursiveBST;

public class RecursiveBST<T extends Comparable<T>> {

    // root of the tree
    private Node<T> root;

    // default constructor
    public RecursiveBST() {
        root = null;
    }

    // constructor with data
    public RecursiveBST(T data) {
        root = new Node<>(data);
    }


    // insert data into the BST
    public void insert(T data) {

        if (root == null) {             // new root
            root = new Node<>(data);
            return;
        }

        // find a parent for new data (recursive)
        Node<T> parent = findParent(data, root);

        // node with data already exists
        if (parent == null)
            return;

        // create new leaf with given data
        parent.createLeaf( (data.compareTo(parent.data) < 0) , data);
    }


    // recursive method to find a parent node to insert data
    private Node<T> findParent(T data, Node<T> parent) {

        // compareTo result
        int compRes = (data.compareTo(parent.data));

        // node with data exists -> return null reference
        if (compRes == 0)
            return null;

        // choose the next child node
        Node<T> tmp = (( compRes < 0 ) ? parent.left : parent.right);

        // recursive call if tmp node is not null
        return ( (tmp == null) ? parent : findParent(data, tmp) );
    }


    // return true if node with data exists; false otherwise
    public boolean findNode(T data, Node<T> parent) {

        // compareTo result
        int compareResult = data.compareTo(parent.data);

        // equality
        if (compareResult == 0)
            return true;

        // next node
        Node<T> tmp = ( (data.compareTo(parent.data) < 0) ? parent.left : parent.right);

        // check if the node with data exists
        return ( (tmp != null) && findNode(data, tmp) );
    }


    // print the sorted tree
    public void printTree() {

        if (root == null) {
            System.out.println("The tree is empty!");
            return;
        }

        // recursive display for all nodes (sorted data)
        dispNode(root);
    }


    // display data for the node and its children
    private void dispNode(Node<T> parent) {

        if (parent != null) {
            dispNode(parent.left);                          // left first (recursive)
            System.out.println(parent.data.toString());     // current node
            dispNode(parent.right);                         // right (recursive)
        }
    }


    // print minimum value from the tree
    public void printMin() {

        if (root == null) {
            System.out.println("The tree is empty!");
            return;
        }

        printNode(recursiveMinMax(root, true));     // finds and prints min node
    }


    // print maximum value from the tree
    public void printMax() {

        if (root == null) {
            System.out.println("The tree is empty!");
            return;
        }

        printNode(recursiveMinMax(root, false));    // finds and prints max node
    }


    // min == true -> finds minimum; min == false -> finds maximum
    private Node<T> recursiveMinMax(Node<T> parent, boolean min) {

        Node<T> tmp = (min ? parent.left : parent.right);                   // min/max node

        return ( (tmp == null) ? parent : recursiveMinMax(tmp, min) );      // recursive call

    }

    // print the data from particular node
    private void printNode(Node<T> node) {
        System.out.println(node.data.toString());
    }


    public void removeNode(T data) {

        if (root == null) {
            System.out.println("The tree is empty!");
            return;
        }

        if (!findNode(data, root)) {
            System.out.println("The node does not exist!");
            return;
        }

        // parent node - recursive search
        Node<T> parent = findParentNodeWithData(data, root);

        // help flag (of parent is also root)
        boolean rootOk = true;
        if (parent == root && root.data.compareTo(data) == 0)
            rootOk = false;

        // left or right?
        boolean flag = (data.compareTo(parent.data) < 0);

        // current node with data
        Node<T> current;
        if (rootOk)
            current = ( flag ? parent.left : parent.right );
        else
            current = parent;

        // number of children
        int children = getChildNumber(current);

        // switch children cases
        switch (children) {
            case 0:             // there are no children -> just remove node
                // the easiest case - just delete the node

                if (!rootOk) {
                    root = null;
                    break;
                }

                if (flag)
                    parent.left = null;
                else
                    parent.right = null;

                break;
            case 1:

                if (!rootOk) {
                    root = ( (current.left != null) ? current.left : current.right);
                    break;
                }

                // change node with its children
                if (flag)
                    parent.left = ( (current.left != null) ? current.left : current.right);
                else
                    parent.right = ( (current.left != null) ? current.left : current.right);

                break;
            case 2:

                // minimum node from right subtree
                Node<T> rightMin = recursiveMinMax(current.right, true);

                // remove rightMin from original tree (recursive call)
                removeNode(rightMin.data);

                // replace node children (from current)
                rightMin.left = current.left;
                rightMin.right = current.right;

                // root case
                if (!rootOk) {
                    root = rightMin;
                    break;
                }

                // move minimum right node to the current position
                if (flag)
                    parent.left = rightMin;
                else
                    parent.right = rightMin;

                break;
        }
    }


    // return number of children of particular node
    private int getChildNumber(Node<T> node) {
        return (node.left == null ? 0 : 1) + (node.right == null ? 0 : 1);
    }


    // recursive method to find a parent node (one of the children has given data)
    // to use only if the node with data exists !
    private Node<T> findParentNodeWithData(T data, Node<T> parent) {

        // one special situation: parent has data -> stop searching
        if (parent.data.equals(data))
            return parent;

        // choose the next child node
        Node<T> tmp = ( (data.compareTo(parent.data) < 0) ? parent.left : parent.right);

        // recursive call if tmp node is not null
        return ( (tmp.data == data) ? parent : findParentNodeWithData(data, tmp) );
    }





    // node nested class
    private static class Node<T extends Comparable<T>> {         // node class

        private T data;             // data stored inside the node
        private Node<T> left;       // left node
        private Node<T> right;      // right node

        // constructor
        Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }

        // create new leaf (branch?) left or right
        private void createLeaf(boolean left, T data) {

            if (left)
                this.left = new Node<>(data);
            else
                this.right = new Node<>(data);
        }
    }



    
    // test with some Integers
    public static void main(String[] args) {

        Integer[] tab = {50, 30, 25, 35, 70, 80, 90, 75, 60, 65};

        // new tree
        RecursiveBST<Integer> obj = new RecursiveBST<Integer>();

        //  insert data
        for (Integer i : tab)
            obj.insert(i);

        // print
        obj.printTree();

        System.out.println("=================");

        // extreme values
        obj.printMax();
        obj.printMin();

        System.out.println("=================");

        // remove root
        obj.removeNode(50);

        // print modified tree
        obj.printTree();
    }
}
