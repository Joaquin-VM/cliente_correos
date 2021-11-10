//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implements an AVL tree. Note that all "matching" is based on the compareTo method.
 *
 * @author Mark Allen Weiss
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {

  /**
   * The tree root.
   */
  private TreeNode<AnyType> root;

  private static final int ALLOWED_IMBALANCE = 1;

  /**
   * Construct the tree.
   */
  public AvlTree() {
    root = null;
  }

  public TreeNode getRoot() {
    return root;
  }

  /**
   * Insert into the tree; duplicates are ignored.
   *
   * @param x the item to insert.
   */
  public void insert(AnyType x) {
    root = insert(x, root);
  }

  /**
   * Remove from the tree. Nothing is done if x is not found.
   *
   * @param x the item to remove.
   */
  public void remove(AnyType x) {
    root = remove(x, root);
  }

  /**
   * Internal method to remove from a subtree.
   *
   * @param x the item to remove.
   * @param t the node that roots the subtree.
   * @return the new root of the subtree.
   */
  private TreeNode<AnyType> remove(AnyType x, TreeNode<AnyType> t) {
    if (t == null) {
      return t;   // Item not found; do nothing
    }

    int compareResult = x.compareTo(t.element);

    if (compareResult < 0) {
      t.left = remove(x, t.left);
    } else if (compareResult > 0) {
      t.right = remove(x, t.right);
    } else if (t.left != null && t.right != null) // Two children
    {
      t.element = findMin(t.right).element;
      t.right = remove(t.element, t.right);
    } else {
      t = (t.left != null) ? t.left : t.right;
    }
    return balance(t);
  }

  public TreeNode<AnyType> getNodo(AnyType x) throws Exception {
    return getNodo(x, root);
  }

  private TreeNode<AnyType> getNodo(AnyType x, TreeNode<AnyType> t) throws Exception {
    if (t == null) {
      throw new Exception("No se encuentra lo que busca en el arbol.");
    }

    int compareResult = x.compareTo(t.element);
    TreeNode<AnyType> nodoBuscado = new TreeNode<>();

    if (compareResult > 0) {
      nodoBuscado = getNodo(x, t.right);
    } else if (compareResult < 0) {
      nodoBuscado = getNodo(x, t.left);
    } else {
      return t;
    }

    return nodoBuscado;
  }

  public void inOrderR(TreeNode<AnyType> t) {
    if (t == null) {
      return;
    }

    inOrderR(t.left);
    System.out.print(t + " ");
    inOrderR(t.right);
  }

  public ArrayList<AnyType> inOrderNR(TreeNode<AnyType> n) throws Exception {
    Stack<TreeNode> stack = new ListStack<>();
    ArrayList<AnyType> arrayList = new ArrayList<>();
    int cantElementos = 0;
    try {
      while (!stack.isEmpty() | n != null) {
        if (n != null) {
          stack.push(n);
          n = n.left;
        } else {
          n = stack.topAndPop();
          arrayList.add(n.element);
          ++cantElementos;
          n = n.right;
        }
      }
    } catch (Exception e) {
      System.out.println("Error inOrderNR");
    }

    return arrayList;
  }

  public void inOrderPrintNR(TreeNode<AnyType> n) throws Exception {
    Stack<TreeNode> stack = new ListStack<>();
    ListStack<AnyType> stackContenedora = new ListStack<>();
    int cantElementos = 0;
    try {
      while (!stack.isEmpty() | n != null) {
        if (n != null) {
          stack.push(n);
          n = n.left;
        } else {
          n = stack.topAndPop();
          System.out.println(n.element);
          n = n.right;
        }
      }
    } catch (Exception e) {
      System.out.println("Error inOrderNR");
    }
  }

  /**
   * Find the smallest item in the tree.
   *
   * @return smallest item or null if empty.
   */

  public AnyType findMin() throws Exception {
    if (isEmpty()) {
      throw new Exception("Underflow");
    }
    return findMin(root).element;
  }

  /**
   * Find the largest item in the tree.
   *
   * @return the largest item of null if empty.
   */
  public AnyType findMax() throws Exception {
    if (isEmpty()) {
      throw new Exception("Underflow");
    }
    return findMax(root).element;
  }

  /**
   * Find an item in the tree.
   *
   * @param x the item to search for.
   * @return element if x is found.
   */
  public AnyType find(AnyType x) {
    return find(x, root);
  }

  public TreeNode<AnyType> findMasCercano(AnyType x) {
    return findMasCercano(x, root);
  }

  /**
   * Find an item in the tree.
   *
   * @param x the item to search for.
   * @return true if x is found.
   */
  public boolean contains(AnyType x) {
    return contains(x, root);
  }

  /**
   * Make the tree logically empty.
   */
  public void makeEmpty() {
    root = null;
  }

  /**
   * Test if the tree is logically empty.
   *
   * @return true if empty, false otherwise.
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Print the tree contents in sorted order.
   */
  public void printTree() {
    if (isEmpty()) {
      System.out.println("Empty tree");
    } else {
      printTree(root);
    }
  }

  // Assume t is either balanced or within one of being balanced
  private TreeNode<AnyType> balance(TreeNode<AnyType> t) {
    if (t == null) {
      return t;
    }

    if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
      if (height(t.left.left) >= height(t.left.right)) {
        t = rotateWithLeftChild(t);
      } else {
        t = doubleWithLeftChild(t);
      }
    } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
      if (height(t.right.right) >= height(t.right.left)) {
        t = rotateWithRightChild(t);
      } else {
        t = doubleWithRightChild(t);
      }
    }

    t.height = Math.max(height(t.left), height(t.right)) + 1;
    return t;
  }

  public void checkBalance() {
    checkBalance(root);
  }

  private int checkBalance(TreeNode<AnyType> t) {
    if (t == null) {
      return -1;
    }

    if (t != null) {
      int hl = checkBalance(t.left);
      int hr = checkBalance(t.right);
      if (Math.abs(height(t.left) - height(t.right)) > 1 ||
          height(t.left) != hl || height(t.right) != hr) {
        System.out.println("OOPS!!");
      }
    }

    return height(t);
  }

  /**
   * Internal method to insert into a subtree.
   *
   * @param x the item to insert.
   * @param t the node that roots the subtree.
   * @return the new root of the subtree.
   */
  private TreeNode<AnyType> insert(AnyType x, TreeNode<AnyType> t) {
    if (t == null) {
      return new TreeNode<>(x, null, null);
    }

    int compareResult = x.compareTo(t.element);

    if (compareResult < 0) {
      t.left = insert(x, t.left);
    } else if (compareResult > 0) {
      t.right = insert(x, t.right);
    } else
    //Duplicado: Reemplazar.
    {
      t.element = x;
    }
    return balance(t);
  }

  /**
   * Internal method to find the smallest item in a subtree.
   *
   * @param t the node that roots the tree.
   * @return node containing the smallest item.
   */
  private TreeNode<AnyType> findMin(TreeNode<AnyType> t) {
    if (t == null) {
      return t;
    }

    while (t.left != null) {
      t = t.left;
    }
    return t;
  }

  /**
   * Internal method to find the largest item in a subtree.
   *
   * @param t the node that roots the tree.
   * @return node containing the largest item.
   */
  private TreeNode<AnyType> findMax(TreeNode<AnyType> t) {
    if (t == null) {
      return t;
    }

    while (t.right != null) {
      t = t.right;
    }
    return t;
  }

  /**
   * Internal method to find an item in a subtree.
   *
   * @param x is item to search for.
   * @param t the node that roots the tree.
   * @return element if x is found in subtree.
   */
  private AnyType find(AnyType x, TreeNode<AnyType> t) {
    while (t != null) {
      int compareResult = x.compareTo(t.element);

      if (compareResult < 0) {
        t = t.left;
      } else if (compareResult > 0) {
        t = t.right;
      } else {
        return t.element;    // Match
      }
    }

    return null;   // No match
  }

  private TreeNode<AnyType> findMasCercano(AnyType x, TreeNode<AnyType> t) {
    while (t != null) {
      int compareResult = x.compareTo(t.element);

      if (compareResult < 0) {
        t = t.left;
      } else {
        return t;// Match
      }
    }

    return null;   // No match
  }

  /**
   * Internal method to find an item in a subtree.
   *
   * @param x is item to search for.
   * @param t the node that roots the tree.
   * @return true if x is found in subtree.
   */
  private boolean contains(AnyType x, TreeNode<AnyType> t) {
    while (t != null) {
      int compareResult = x.compareTo(t.element);

      if (compareResult < 0) {
        t = t.left;
      } else if (compareResult > 0) {
        t = t.right;
      } else {
        return true;    // Match
      }
    }

    return false;   // No match
  }


  /**
   * Internal method to print a subtree in sorted order.
   *
   * @param t the node that roots the tree.
   */
  private void printTree(TreeNode<AnyType> t) {
    if (t != null) {
      printTree(t.left);
      System.out.println(t.element);
      printTree(t.right);
    }
  }

  public void print() {
    print(false, "", root);
  }

  private void print(boolean esDerecho, String identacion, TreeNode<AnyType> r) {
    if (r.right != null) {
      print(true, identacion + (esDerecho ? "     " : "|    "), r.right);
    }
    System.out.print(identacion);
    if (esDerecho) {
      System.out.print(" /");
    } else {
      System.out.print(" \\");
    }
    System.out.print("-- ");
    System.out.println(r.element);
    if (r.left != null) {
      print(false, identacion + (esDerecho ? "|    " : "     "), r.left);
    }
  }

  /**
   * Return the height of node t, or -1, if null.
   */
  private int height(TreeNode<AnyType> t) {
    return t == null ? -1 : t.height;
  }

  /**
   * Rotate binary tree node with left child. For AVL trees, this is a single rotation for case 1.
   * Update heights, then return new root.
   */
  private TreeNode<AnyType> rotateWithLeftChild(TreeNode<AnyType> k2) {
    TreeNode<AnyType> k1 = k2.left;
    k2.left = k1.right;
    k1.right = k2;
    k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
    k1.height = Math.max(height(k1.left), k2.height) + 1;
    return k1;
  }

  /**
   * Rotate binary tree node with right child. For AVL trees, this is a single rotation for case 4.
   * Update heights, then return new root.
   */
  private TreeNode<AnyType> rotateWithRightChild(TreeNode<AnyType> k1) {
    TreeNode<AnyType> k2 = k1.right;
    k1.right = k2.left;
    k2.left = k1;
    k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
    k2.height = Math.max(height(k2.right), k1.height) + 1;
    return k2;
  }

  /**
   * Double rotate binary tree node: first left child with its right child; then node k3 with new
   * left child. For AVL trees, this is a double rotation for case 2. Update heights, then return
   * new root.
   */
  private TreeNode<AnyType> doubleWithLeftChild(TreeNode<AnyType> k3) {
    k3.left = rotateWithRightChild(k3.left);
    return rotateWithLeftChild(k3);
  }

  /**
   * Double rotate binary tree node: first right child with its left child; then node k1 with new
   * right child. For AVL trees, this is a double rotation for case 3. Update heights, then return
   * new root.
   */
  private TreeNode<AnyType> doubleWithRightChild(TreeNode<AnyType> k1) {
    k1.right = rotateWithLeftChild(k1.right);
    return rotateWithRightChild(k1);
  }
}
