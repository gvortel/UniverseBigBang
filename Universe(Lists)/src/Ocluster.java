
public class Ocluster {
	int cid;
	Planet orphans;
	

	
	
	//constructor
	public Ocluster(){
		this.cid=Integer.MAX_VALUE;
		orphans=null;
	}
	
	
	
	//various functions
	void setCid(int cid)			{ this.cid=cid;	}
	int getCid( )					{ return this.cid;	}
	void setOrphans(Planet planet)	{ if(planet!=null) this.orphans=planet;}
	Planet getOrphans()				{ return this.orphans;}
	
	
	boolean isEmpty(){
		if (this.orphans==null)
			return true;
		return false;
	}
	
	
	void  insertPlanet (int pid)
    {
        Planet nptr = new Planet(pid , 0, null, null);
        Planet tmp, ptr,start=this.orphans;        
        boolean ins = false;
        
        if( isEmpty())
            this.orphans = nptr;
        
        else if (pid <= start.getPid())
        {
            nptr.setNext(start);
            start.setPrev(nptr);
            start = nptr;
        }
        else
        {
            tmp = start;
            ptr = start.getNext();
            while(ptr != null)
            {
                if(pid >= tmp.getPid() && pid <= ptr.getPid())
                {
                    tmp.setNext(nptr);
                    nptr.setPrev(tmp);
                    nptr.setNext(ptr);
                    ptr.setPrev(nptr);
                    ins = true;
                    break;
                }
                else
                {
                    tmp = ptr;
                    ptr = ptr.getNext();
                }
            }
            if(!ins)
            {
                tmp.setNext(nptr);
                nptr.setPrev(tmp);
 
            }
        }
        //this.size++;
    }
	
	public void printAllOrphans(){
		Planet tmp=orphans;
		
		while (tmp!=null)
		{
			System.out.print(tmp.getPid()+",");
			tmp=tmp.getNext();
		}
	}
	
	
	
	
}
