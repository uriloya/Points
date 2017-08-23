/* Class BST */
class BST
{	
    private BSTNode root;

    /* Constructor */
    public BST()
    {
        root = null;
    }
    public void SetRoot(BSTNode root)
    {
    	this.root = root;
    }
    public BSTNode GetRoot()
    {
    	return root;
    }
    /* Function to check if tree is empty */
    public boolean isEmpty()
    {
    	return root == null;
    }
    /* Make the tree logically empty */
    public void makeEmpty()
    {
        root = null;
    }
    /* Function to insert data */
    public void insert(Point data)
    {
    	if(root == null)
    		root = insert(data,root,null);
    	else 
    		root = insert(data,root,root);
    }
    
    private BSTNode insert(Point p, BSTNode t, BSTNode parent)// Function to insert data and keys array in to a tree
    {
        if(t == null)
            t = new BSTNode(p, parent);
        else if(p.getX() < t.data.getX())
            t.left = insert(p, t.left, t);
        else if(p.getX() > t.data.getX())
            t.right = insert(p, t.right, t);
        return t;
    }

    /* Functions to count number of nodes */
    public int countNodes()
    {
        return countNodes(root);
    }
    
    private int countNodes(BSTNode r)
    {
        if (r == null)
            return 0;
        else
        {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }
    /* Functions to search for an element */
    public BSTNode search(Point val)
    {
        return search(root,val);
    }
    
    private BSTNode search(BSTNode r, Point val)
    {
    	while( r != null )
    	{
            if( val.getX() < r.data.getX() )
                r = r.left;
            else if( val.getX() > r.data.getX() )
                r = r.right;
            else
                return r;
    	}
        return null; 
    }
    public void remove(int x, BSTNode b)
    {
    	remove(x,b,new Point(-1,-1));
    }
    
    private void remove(int x, BSTNode b,Point prev)
    {
        if (b == null) return;
        if (x < b.data.getX())
            remove (x, b.left, prev);
        else if (x > b.data.getX())
            remove (x, b.right, prev);
        else
        {
            if (b.left != null && b.right != null)
            {
                /* b has two children */
                BSTNode maxFromLeft = findMax(b.left);
                prev = b.data;
                b.SetData(new Point(maxFromLeft.data.getX(),maxFromLeft.data.getY(),""+prev.getX()));
                remove (maxFromLeft.data.getX(), b.left, prev);
            }
            else if(b.left != null)
            {
            	b.SetData(-b.data.getX(),-b.data.getY());
            	UpdateParent(b,prev);
            	if(search(new Point(-b.data.getX(),-b.data.getY()))!=null)
            		search(new Point(-b.data.getX(),-b.data.getY())).SetName("");
                b = b.left;
            }
            else if(b.right != null)
            {
            	b.SetData(-b.data.getX(),-b.data.getY());
                UpdateParent(b,prev);
                if(search(new Point(-b.data.getX(),-b.data.getY()))!=null)
                	search(new Point(-b.data.getX(),-b.data.getY())).SetName("");
                b = b.right;
            }
            else
            {
            	b.SetData(-b.data.getX(),-b.data.getY());
            	UpdateParent(b,prev);
            	if(search(new Point(-b.data.getX(),-b.data.getY()))!=null)
            		search(new Point(-b.data.getX(),-b.data.getY())).SetName("");
            	b = null;
            }
        }
    }
    
    private BSTNode findMax(BSTNode b)
    {
    	if(b.right == null)
    		return b;
    	else
    		return findMax(b.right);
    }
    
    public BSTNode SortedArrayToBST(Point arr[], int start, int end, BSTNode parent)
    {
    	if (start > end) return null;
		int mid = start + (end - start) / 2;
		BSTNode node = new BSTNode(arr[mid], parent);
		UpdateParent(node);
		node.left = SortedArrayToBST(arr, start, mid-1, node);
		node.right = SortedArrayToBST(arr, mid+1, end, node);
		return node;
    }
    
    public void UpdateParent(BSTNode b)
    {
    	int y = b.data.getY();
    	int c = 1;
    	while(b.GetParent()!=null)//while is not root
    	{
    		b.parent.AddToCount(c);
    		b.parent.AddToSum(y);
    		b = b.parent;
    	}
    }
    
    public void UpdateParent(BSTNode b, Point p)
    {
    	int y = b.data.getY();
    	int c = -1;
    	while(b.GetParent()!=null)
    	{
        	if(b.parent.data.getName().equals(""+p.getX()))
        		y = -p.getY();
    		b.parent.AddToCount(c);
    		b.parent.AddToSum(y);
    		b = b.parent;
    	}
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}