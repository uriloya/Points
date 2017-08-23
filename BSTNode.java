/* Class BSTNode */
class BSTNode
{
    BSTNode left, right, parent;
    Point data;
    int sum, count;

    /* Constructor */
    public BSTNode()
    {
        left = null;
        right = null;
        parent = null;
        data = null;
        sum = 0;
        count = 0;
    }
    /* Constructor */
    public BSTNode(Point n, BSTNode p)
    {
        left = null;
        right = null;
        parent = p;
        data = n;
        sum = data.getY();
        count = 1;
    }
    public void SetData(Point p)
    {
    	data = p;
    }
    public void SetName(String name)
    {
    	data = new Point(data.getX(),data.getY(),name);
    }
    public void SetData(int x,int y)
    {
    	data = new Point(x,y);
    }
    public void SetParent(BSTNode b)
    {
    	parent = b;
    }
    public BSTNode GetParent()
    {
    	return parent;
    }
    public void AddToCount(int a)
    {
    	count += a;
    }
    public void AddToSum(int a)
    {
    	sum += a;
    }
    public int GetSum()
    {
    	return sum;
    }
    public int GetCount()
    {
    	return count;
    }
}
 