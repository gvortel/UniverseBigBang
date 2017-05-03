
public class Solar {
	int sid;
	Planet planets;
	Solar next;
	
	
	//constructor
	public Solar(){ // 
		this.planets=null;
		this.next=null;
	}
	
	
	public void SolarInit(){ //+init Gnode
		this.planets=new Planet();
		this.next=null;
	}
	
	
	//various functions
	public Solar getNext()				{ return this.next; }
	public int  getSid()				{ return this.sid;}
	public void setNext(Solar next)		{ this.next=next ;}
	public Planet getPlanet()			{ return this.planets;}
	//public boolean isEmpty()			{ true; }
	
	void  insertPlanet (int pid, int distance)
    {
        Planet nptr = new Planet(pid , distance, null, null);
        Planet tmp, ptr,start=this.planets;        
        boolean ins = false;
        
        if( this.planets ==null)
            {this.planets = nptr; }
        
        else if (distance < start.getDistance())
        {
            nptr.setNext(start);
            start.setPrev(nptr);
            start.setDistance(start.getDistance()-distance);
            start = nptr;}
        else
        {
            tmp = start;
            ptr = start.getNext();
            int counter=tmp.getDistance();
            
            while(ptr != null)
            {
            	counter=counter+start.getDistance();
                //if(distance >= tmp.getDistance() && distance <= ptr.getDistance())
            	if(distance >= counter && distance <= ptr.getDistance())
                {
                    tmp.setNext(nptr);
                    nptr.setPrev(tmp);
                    nptr.setNext(ptr);
                    ptr.setPrev(nptr);
                    nptr.setDistance(distance-counter);
                    ins = true;//System.out.println("Y  O L  O  ");
                    break;
                }
                else
                {
                    tmp = ptr;
                    ptr = ptr.getNext();
                }
                
            }
            if(!ins)
            {//System.out.println("bob   "+size);
                tmp.setNext(nptr);
                nptr.setPrev(tmp);
                nptr.setDistance(distance-counter);
 
            }
        }
        //this.size++;
    }
	
	
	
	
	public boolean  deletePlanet(Planet planet)
	{
		if (planet==null) return false;
		
		if(planet.getNext()==null && planet.getPrev()==null)//one elem list
		{planet=null; //System.out.println("======"+planet+"SWAG");
		return true;}
	
		
		if (planet.getNext()==null){ //if last elem
			Planet yotmp =planet.getPrev();
			yotmp.setNext(null);
			planet=null;
			}
		
		else if (planet.getPrev()==null){ //if head
			this.planets=planet.getNext();
			planet=null;
		}
		else{
			planet.getPrev().setNext(planet.getNext()); //prev's next=planet's next
			planet.getNext().setPrev(planet.getPrev()); //next's prev=planet's prev
			planet=null;
		}	
		
		return true;
	}
	
	

}
