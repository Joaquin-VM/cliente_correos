public class TreeIterator<T> implements Iterator<T> {

  protected TreeNode<T> root;
  protected ListStack<TreeNode<T>> visiting;
  protected ListStack<Boolean> visitingRightChild;

  public TreeIterator(TreeNode<T> root) {
    this.root = root;
    visiting = new ListStack<>();
    visitingRightChild = new ListStack<>();
  }

  public boolean hasNext() {
    return (root != null);
  }

  public T next() throws Exception {
    if (!hasNext()) {
      throw new java.util.NoSuchElementException("no more elements");
    }

    T nextElement = inorderNext();

    return nextElement;
  }

  private void pushLeftMostNode(TreeNode<T> node) {
    if (node != null) {
      visiting.push(node);
      pushLeftMostNode(node.left);
    }
  }

  private T inorderNext() throws Exception {
    if (visiting.isEmpty()) {
      pushLeftMostNode(root);
    }
    TreeNode<T> node = visiting.topAndPop();
    T result = node.element;

    if (node.right != null) {
      TreeNode<T> right = node.right;
      pushLeftMostNode(right);
    }

    if (visiting.isEmpty()) {
      root = null;
    }

    return result;
  }

  public String toString() {
    return "in: " + toString(root) + "\n" + visiting + "\n";
  }

  private String toString(TreeNode<T> node) {
    if (node == null) {
      return "";
    } else {
      return node + "(" + toString(node.left) + ", " +
          toString(node.right) + ")";
    }
  }

}