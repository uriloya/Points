
public class MinHeap {

    private Point[] Heap;
    private int size;
    private int minsize;
    
 
    private static final int FRONT = 1;
 
    public MinHeap(int minsize)
    {
        this.minsize = minsize;
        this.size = 0;
        Heap = new Point[this.minsize + 1];
        Point min= new Point(-1,-1); 
        Heap[0] = min;
    }
 
    private int parent(int pos)
    {
        return pos / 2;
    }
 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 
    private boolean isLeaf(int pos)
    {
        if (pos*2 > size  &&  pos <= size)
        {
            return true;
        }
        return false;
    }
 
    private void swap(int fpos,int spos)
    {
        Point tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
 
    private void MinHeapify(int pos)
    {
            if (!isLeaf(pos))
            { 
                if (size>=leftChild(pos) && Heap[pos].getY() > Heap[leftChild(pos)].getY() || size>=rightChild(pos) && Heap[pos].getY() > Heap[rightChild(pos)].getY())
                {
                    if (Heap[leftChild(pos)].getY() < Heap[rightChild(pos)].getY())
                    {
                        swap(pos, leftChild(pos));
                        MinHeapify(leftChild(pos));
                    }
                    else if (Heap[leftChild(pos)].getY() > Heap[rightChild(pos)].getY())
                    {
                        swap(pos, rightChild(pos));
                        MinHeapify(rightChild(pos));
                    }
                    else
                    {
                    	if(Heap[rightChild(pos)].getX()<Heap[leftChild(pos)].getX())
    	        		{
    	                    swap(pos, rightChild(pos));
    	                    MinHeapify(rightChild(pos));
    	        		}
                    	else
                    	{
                    		swap(pos, leftChild(pos));
    	                    MinHeapify(leftChild(pos));
                    	}
                    }
                }
                else if (size>=rightChild(pos) && Heap[pos].getY() == Heap[rightChild(pos)].getY() && size>=leftChild(pos) &&  Heap[rightChild(pos)].getY() == Heap[leftChild(pos)].getY())
                {
     	        	 if( Math.min(Heap[rightChild(pos)].getX(), Heap[leftChild(pos)].getX())<Heap[pos].getX())
     	        	 {
     	        		if(Heap[rightChild(pos)].getX()>Heap[leftChild(pos)].getX())
     	        		{
	     	                 swap(pos, leftChild(pos));
	     	                 MinHeapify(leftChild(pos));
     	        		}
	     	            else
	     	            {
	     	                 swap(pos, rightChild(pos));
	     	                 MinHeapify(rightChild(pos));
	     	            }
                }
                }
                else if (size>=leftChild(pos) &&  Heap[pos].getY() == Heap[leftChild(pos)].getY()) 
                {
                    if (Heap[leftChild(pos)].getX() < Heap[pos].getX())
                    {
                        swap(pos, leftChild(pos));
                        MinHeapify(leftChild(pos));
                    }
                }
                else if (size>=rightChild(pos) && Heap[pos].getY() == Heap[rightChild(pos)].getY())
               {
            	   if (Heap[rightChild(pos)].getX() < Heap[pos].getX())
            	   {
                       swap(pos, rightChild(pos));
                       MinHeapify(rightChild(pos));
            	   }
               }

            }
    }
 
    public void Insert(Point element)
    {
        Heap[++size] = element;
        int current = size;
 
        while((Heap[current].getY() < Heap[parent(current)].getY()) || (Heap[current].getY() == Heap[parent(current)].getY() && Heap[current].getX() < Heap[parent(current)].getX()))
        {
            swap(current,parent(current));
            current = parent(current);
        }
    }

    public int GetSize()
    {
        return size;
    }
 
    public Point ExtractMin()
    {
        Point popped = Heap[FRONT];
        if(size>1)
        {
        	Heap[FRONT] = Heap[size--];
        	MinHeapify(FRONT);
        }
        else if(size == 1)
        {
        	size--;
        }
        return popped;
    }
    public Point[] GetHeap()
    {
       return Heap;  
    }
}