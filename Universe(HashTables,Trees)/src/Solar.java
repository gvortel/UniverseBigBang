
public class Solar {
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public Planet getPlanets() {
		return planets;
	}
	public void setPlanets(Planet planets) {
		this.planets = planets;
	}
	public Planet getPsentinel() {
		return psentinel;
	}
	public void setPsentinel(Planet psentinel) {
		this.psentinel = psentinel;
	}
	public Solar getNext() {
		return next;
	}
	public void setNext(Solar next) {
		this.next = next;
	}
	public final static int N = 100;
	
	
	int sid;
	Planet planets;
	Planet psentinel;
	Solar next;
	
	
	public void SolarInit(){ 
		// init sentinel node
		this.planets=new Planet();
		this.planets.setLc(psentinel);
		this.planets.setRc(psentinel);
		
		this.next=null;
		this.psentinel=new Planet(0,0);
		this.psentinel=null;
	}
	
	

    
	
	public boolean insertPlanet(int pid,int distance){
		Planet temp,current;
		int wasChild=-1;
		
		temp=new Planet(pid,distance);
		current=planets;
		
		
		if(current.getDistance()==0)
		{
			planets=temp;
			temp.setLc(getPsentinel());
			temp.setRc(getPsentinel());
			return true;
		}
		else
		{
		
		while(current!=null)
		{
			if( current.getDistance() > temp.getDistance() )
			{
				wasChild=1;
				temp.setParent(current);
				current=current.getLc();
			}
			else if ( current.getDistance() < temp.getDistance() )
			{
				wasChild=2;
				temp.setParent(current);
				current=current.getRc();
			}
			else
			{
				wasChild=0;
				temp.setParent(current);
				// element already exists
				break;
			}
		}
		
		}
		
		if(wasChild==0 || wasChild==-1)
			{System.out.print("\n\nSUCCESS\n");return false;}
		
		if(wasChild==2)
		{
			temp.setLc(getPsentinel());
			temp.setRc(getPsentinel());
			temp.getParent().setRc(temp);
		}
		else if(wasChild==1)
		{
			temp.setLc(getPsentinel());
			temp.setRc(getPsentinel());
			temp.getParent().setLc(temp);
		}
		
		
		
		return true;
	}
	
	
	
	boolean found ;
	////////////////////////////
	//==========DELETE======////
	////////////////////////////
	boolean planetLookupNdelete(int pid){
			found=false;
		  planetLookupNdelete(planets,pid);
		  return found;
	}
	
	boolean planetLookupNdelete(Planet root,int pid){
		
		if(root==null || root==getPsentinel())
			return false;
		
		planetLookupNdelete(root.getLc(), pid);
		planetLookupNdelete(root.getRc(), pid);
		
		if(root.getPid()==pid)
		{
			planetDelete(root);
			found=true;
			return true;
			
		}
		else
		 return false;
		
	}
	
	
	boolean planetDelete(Planet current){
		Planet tmp,p=current.getParent();
		// if empty 
		if( current.getDistance()==0 || current==null)
			return false;

		
		// if has 2 children
		if( current.getLc()!=getPsentinel() && current.getRc()!=getPsentinel())
		{
			p=current;
			tmp=current.getRc();
			
			while(tmp.getLc()!=null)
			{
				p=tmp;
				tmp=tmp.getLc();
			}
			
			current=tmp;
		}
		
		// if has no children
		if( current.getLc()==getPsentinel() && current.getRc()==getPsentinel())
		{	
			//empty tree
			if(p==null)
				planets=new Planet(); 
			
			if(p.getLc()==current)
				p.setLc(getPsentinel());
			else if(p.getRc()==current)
				p.setRc(getPsentinel());
			else
				planets=new Planet();
		}
		
		//if has only right child
		if( current.getLc()==getPsentinel() && current.getRc()!=getPsentinel()) 
		{
			if(p.getLc()==current)
				p.setLc( current.getRc());
			else if(p.getRc()==current)
				p.setRc(current.getRc());
			else
				planets=current.getRc();
		}
		
		//if has only left child
		if( current.getRc()==getPsentinel() && current.getLc()!=getPsentinel())  
		{
			if(p.getLc()==current)
				p.setLc( current.getLc());
			else if(p.getRc()==current)
				p.setRc(current.getLc());
			else
				planets=current.getLc();
		}
		
		return true;
	}

	
	
	
	
	
}
