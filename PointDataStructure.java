
public class PointDataStructure implements PDT
{
	
	MaxHeap maxh;
	MinHeap minh;
	Point mid;
	BST bst;
	Point[] pointsX;
	int indexPointsX;

	public PointDataStructure(Point[] points, Point initialYMedianPoint)
	{
		mid = initialYMedianPoint;
		maxh = new MaxHeap(points.length+10*(int)Math.log(points.length));
		minh = new MinHeap(points.length+10*(int)Math.log(points.length));
		bst = new BST();
		for(int i=0;i<points.length;i++)
		{
			if(mid.getY()<points[i].getY())
				minh.Insert(points[i]);
			else if(mid.getY()>points[i].getY())
				maxh.Insert(points[i]);
			else if(mid.getY()==points[i].getY())
			{
				if(mid.getX()<points[i].getX())
					minh.Insert(points[i]);
				else if(mid.getX()>points[i].getX())
					maxh.Insert(points[i]);
			}
		}

		boolean b=true;
		for(int i=1;i<points.length&&b;i++)
		{
			if(points[i].getX()<points[i-1].getX())
				b = false;
		}
		if(b)
			bst.SetRoot(bst.SortedArrayToBST(points, 0, points.length-1, null));
		else
		{
			Point[] p = new Point[points.length];
			for(int i=0;i<points.length;i++)
				p[points[i].getX()] = points[i];
			bst.SetRoot(bst.SortedArrayToBST(p, 0, p.length-1, null));
		}
	}

	@Override
	public void addPoint(Point point)
	{
		if(mid.getY()<point.getY())
			minh.Insert(point);
		else if(mid.getY()>point.getY())
			maxh.Insert(point);
		else if(mid.getY()==point.getY())
		{
			if(mid.getX()<point.getX())
				minh.Insert(point);
			else if(mid.getX()>point.getX())
				maxh.Insert(point);
		}
		
		if(minh.GetSize()-1==maxh.GetSize())
		{
			maxh.Insert(mid);
			mid = minh.ExtractMin();
		}
		if(minh.GetSize()==maxh.GetSize()-2)
		{
			minh.Insert(mid);
			mid = maxh.ExtractMax();
		}
		
		bst.insert(point);
		bst.UpdateParent(bst.search(point));
	}

	@Override
	public Point[] getPointsInRange(int XLeft, int XRight)
	{
		pointsX = new Point[maxh.GetHeap().length];
		indexPointsX = 0;
		BSTNode b = bst.GetRoot();
		getPointsInRange(b, XLeft, XRight);
		
		int i=0;
		while(pointsX[i]!=null)
		{
			i++;
		}
		Point[] p = new Point[i];
		for(int j=0;j<i;j++)
		{
			p[j] = pointsX[j];
		}
		return p;
	}
	private void getPointsInRange(BSTNode b,int XLeft, int XRight)
	{
		if(b!=null)
		{
			if(b.data.getX() == XLeft)
			{
				pointsX[indexPointsX++] = b.data;
				getPointsInRange(b.right, XLeft, XRight);
			}
			else if(b.data.getX() == XRight)
			{
				pointsX[indexPointsX++] = b.data;
				getPointsInRange(b.left, XLeft, XRight);
			}
			else if(b.data.getX() < XRight && b.data.getX() > XLeft)
			{
				pointsX[indexPointsX++] = b.data;
				getPointsInRange(b.left, XLeft, XRight);
				getPointsInRange(b.right, XLeft, XRight);
			}
			else if(b.data.getX() > XRight)
			{
				getPointsInRange(b.left, XLeft, XRight);
			}
			else if(b.data.getX() < XLeft)
			{
				getPointsInRange(b.right, XLeft, XRight);
			}
		}
	}

	@Override
	public int numOfPointsInRange(int XLeft, int XRight)
	{
		return GreaterCount(bst.GetRoot(), XLeft, true) - GreaterCount(bst.GetRoot(), XRight, false);
	}

	@Override
	public double averageHeightInRange(int XLeft, int XRight)
	{
		double ans = ((double)GreaterSum(bst.GetRoot(), XLeft, true) - (double)GreaterSum(bst.GetRoot(), XRight, false)) / ((double)GreaterCount(bst.GetRoot(), XLeft, true) - (double)GreaterCount(bst.GetRoot(), XRight, false));
		if (Double.isNaN(ans))
			return 0.0;
		return ans;
	}

	@Override
	public void removeMedianPoint()
	{
		bst.remove(mid.getX(), bst.GetRoot());
		
		if(minh.GetSize() == maxh.GetSize())
		{
			mid = minh.ExtractMin();
		}
		else if(minh.GetSize() == maxh.GetSize()-1)
		{
			mid = maxh.ExtractMax();
		}
	}

	@Override
	public Point[] getMedianPoints(int k)
	{
		Point [] ans = new Point[k];
		if(k==0)
		{
			return ans;
		}
		int curr = 1;//the index in ans array
		MaxHeap maxtemp = new MaxHeap(maxh.GetHeap().length);
		MinHeap mintemp = new MinHeap(minh.GetHeap().length);
		ans[0] = mid;
		Point outmin = null;
		Point outmax = null;
		if(k==1)
		{
			return ans;
		}
		maxtemp.Insert(new Point(maxh.GetHeap()[1].getX(),maxh.GetHeap()[1].getY(),"1"));//the name is the index in the heap
		outmax = new Point(maxtemp.ExtractMax());
	    ans[curr] = outmax;
	    curr++;
	    for(int j =1;j<Math.ceil((double)(k-1)/2);j++)
	    {
	    	if(maxh.GetSize()>=Integer.parseInt(outmax.getName())*2)
	    		maxtemp.Insert(new Point(maxh.GetHeap()[Integer.parseInt(outmax.getName())*2].getX(),maxh.GetHeap()[Integer.parseInt(outmax.getName())*2].getY(),Integer.toString(Integer.parseInt(outmax.getName())*2)));
	    	if(maxh.GetSize()>=Integer.parseInt(outmax.getName())*2+1)
	    		maxtemp.Insert(new Point(maxh.GetHeap()[Integer.parseInt(outmax.getName())*2+1].getX(),maxh.GetHeap()[Integer.parseInt(outmax.getName())*2+1].getY(),Integer.toString(Integer.parseInt(outmax.getName())*2+1)));
	    	outmax = maxtemp.ExtractMax();
	    	ans[curr] = outmax;
	    	curr++;
	    }
		if(minh.GetSize()!=0)
			mintemp.Insert(new Point(minh.GetHeap()[1].getX(),minh.GetHeap()[1].getY(),"1"));
		if(k>2 && mintemp.GetSize()!=0) 
		{
			outmin = new Point(mintemp.ExtractMin());
	        ans[curr] = outmin;
	        curr++;
		}
	    
	    for(int j =1;j<Math.floor((k-1)/2);j++)
	    {
	    	if(minh.GetSize()>=Integer.parseInt(outmin.getName())*2)
	    		mintemp.Insert(new Point(minh.GetHeap()[Integer.parseInt(outmin.getName())*2].getX(),minh.GetHeap()[Integer.parseInt(outmin.getName())*2].getY(),Integer.toString(Integer.parseInt(outmin.getName())*2)));
	    	if(minh.GetSize()>=Integer.parseInt(outmin.getName())*2+1)
	    		mintemp.Insert(new Point(minh.GetHeap()[Integer.parseInt(outmin.getName())*2+1].getX(),minh.GetHeap()[Integer.parseInt(outmin.getName())*2+1].getY(),Integer.toString(Integer.parseInt(outmin.getName())*2+1)));
	    	outmin = mintemp.ExtractMin();
	    	ans[curr] = outmin;
	    	curr++;
	    }
		
	    for(int i=0;i<ans.length;i++)
	    {
	    	Point tmp = new Point(ans[i].getX(),ans[i].getY());
	    	ans[i] = tmp;
	    }
	    
		return ans;
	}

	@Override
	public Point[] getAllPoints()
	{
		Point[] p = new Point[maxh.GetSize()+minh.GetSize()+1];
		int i,j;
		for(i=1;i<=maxh.GetSize();i++)
			p[i-1] = maxh.GetHeap()[i];
		for(j=1;j<=minh.GetSize();j++)
			p[i+j-2] = minh.GetHeap()[j];
		p[i+j-2] = mid;
		return p;
	}	
	
	int GreaterCount(BSTNode b,int k,boolean flag)
	{
		int ans = 0;
		while(b!=null)
		{
			if(k < b.data.getX())
			{
				if(b.right!=null)
					ans += b.right.GetCount()+1;
				else
					ans += 1;
				b = b.left;
			}
			else if(k > b.data.getX())
				b = b.right;
			else
			{
				if(b.right!=null && flag)
					ans += b.right.GetCount()+1;
				else if(b.right!=null && !flag)
					ans += b.right.GetCount();
				else if(flag)
					ans += 1;
				break;
			}
		}
		return ans;
	}
	
	int GreaterSum(BSTNode b,int k,boolean flag)
	{
		int ans = 0;
		while(b!=null)
		{
			if(k < b.data.getX())
			{
				if(b.right!=null)
					ans += b.right.GetSum()+b.data.getY();
				else
					ans += b.data.getY();
				b = b.left;
			}
			else if(k > b.data.getX())
				b = b.right;
			else
			{
				if(b.right!=null && flag)
					ans += b.right.GetSum() + b.data.getY();
				else if(b.right!=null && !flag)
					ans += b.right.GetSum();
				else if(flag)
					ans += b.data.getY();
				break;
			}
		}
		return ans;
	}
}