// Basic node stored in a tree
// Note that this class is not accessible outside
// of package weiss.nonstandard

class TreeNode<AnyType> {

  public AnyType element;
  public TreeNode<AnyType> left;
  public TreeNode<AnyType> right;
  int height;

  public TreeNode() {
    this(null, null, null);
  }

  // Constructors
  public TreeNode(AnyType theElement) {
    this(theElement, null, null);
  }

  public TreeNode(AnyType theElement, TreeNode<AnyType> l, TreeNode<AnyType> r) {
    element = theElement;
    left = l;
    right = r;
    height = 0;
  }

}
