import java.util.LinkedList;
import java.util.Queue;


public class Galaxy {
	
	
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public Solar getSolar() {
		return solars;
	}
	public void setSolars(Solar solars) {
		this.solars = solars;
	}
	public Solar getSSentinel() {
		return ssentinel;
	}
	public void setSsentinel(Solar ssentinel) {
		this.ssentinel = ssentinel;
	}
	public Planet getOclusters() {
		return oclusters;
	}
	public void setOclusters(Planet oclusters) {
		this.oclusters = oclusters;
	}

	
	
	//oclusters
	public final static int M = 50; 
	
	int gid;
	Solar solars;
	Solar ssentinel;
	Planet oclusters;
	
	


	//constructor
	public Galaxy(){
		this.gid=Integer.MAX_VALUE;
		this.oclusters=null;
		this.solars=null;
		this.ssentinel=null;
		
	}
	
	
	
	public void GalaxyInsert(int gid){
		this.gid=gid;
		this.solars=new Solar();
		this.solars.SolarInit();
		this.ssentinel=new Solar();
		this.ssentinel.setNext(ssentinel);
		this.oclusters=null; 
	
	}
	
	
public void star_birth(int sid){
		
		Solar newSolar=new Solar();
		
		newSolar.sid=sid;
		newSolar.planets=new Planet();
		
		if (ssentinel.getNext()==ssentinel)
			{
				newSolar.setNext(ssentinel);
				ssentinel.setNext(newSolar);
			}
		else
			{newSolar.setNext(this.solars);}
		//
		this.solars= newSolar;
		
			
	}







void unsortedTreeInsert(int pid) { 
	Queue<Planet> Q = new LinkedList<Planet>();
	Planet neworphan=new Planet(pid,0);
	
	if(oclusters==null)
	{
		oclusters=neworphan;
		return;
	}
	

	Q.add(oclusters);
	while( !Q.isEmpty() )
	{
		Planet tmp=Q.remove();
		
		if(tmp.getLc()==null)
		{
			tmp.setLc(neworphan);
			return;
		}
		
		
		else if(tmp.getRc()==null)
		{
			tmp.setRc(neworphan);
			return;
		}
		
		
		
		else if(tmp.getLc()!=null)
			Q.add( tmp.getLc() );
		else if(tmp.getLc()!=null)
			Q.add( tmp.getLc() );
		
	}

}
	







void deleteSolar(int sid){
	Solar current;
	Solar prev=null;
	
	if (getSolar()!=null)
		 current=getSolar();
	else 
		return;
	
	while(current!=null && current!=getSSentinel()){
		
		if(current.getSid()==sid)
		{
			// if is the last element
			if (current.getNext()==null){ 
				prev.setNext(null);
				current=null;
				}
				
			// if is head
			else if (prev==null){ 
				solars=solars.getNext();
				}
			else{
				prev.setNext(current.getNext()); //prev's next=current's next
				current=null;
				}
			break;
		}
		prev=current;
		current=current.getNext();
	
	}

}
	
public void printoclusters() { printoclusters(oclusters); }
public void printoclusters(Planet root) { 
	if (root==null) return;
	System.out.print(root.getPid()+ " , ");
	 printoclusters(root.getLc());
	 printoclusters(root.getRc());
}





public boolean LUP(int pid) { 
	orphanfound=false;
	LUP1(oclusters,pid,null); 
	return orphanfound;
	}

public void LUP1(Planet root,int pid,Planet parent) { 
	if (root==null) return;
	
	
	
	 LUP1(root.getLc(),pid,root);
	 LUP1(root.getRc(),pid,root);
	 
	 
	 if (root.getPid()==pid)
		orphanfound=true;
	
}






boolean orphanfound;
///////////////////////
public boolean yolo(int pid) { 
	orphanfound=false;
	yolo1(oclusters,pid,null); 
	return orphanfound;
	}

public void yolo1(Planet root,int pid,Planet parent) { 
	if (root==null) return;
	
	
	
	 yolo1(root.getLc(),pid,root);
	 yolo1(root.getRc(),pid,root);
	 
	 if (root.getPid()==pid)
	 {
		orphanfound=true;
		orphanDelete(root,parent);
	}

	 
	 
}




boolean orphanDelete(Planet current,Planet p){ 
	Planet tmp;
	if( current==null)//empty 
		return false; 
		
	// if has 2 children
	if( current.getLc()!=null && current.getRc()!=null  )
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
	if( current.getLc()==null && current.getRc()==null)
	{
		// if is empty tree
		if(p==null)
			oclusters=null; 
		
		if(p.getLc()==current)
			p.setLc(null);
		else if(p.getRc()==current)
			p.setRc(null);
		else
			oclusters=null;
	}
	
	// if has only right child
	if( current.getLc()==null && current.getRc()!=null) 
	{
		if(p.getLc()==current)
			p.setLc( current.getRc());
		else if(p.getRc()==current)
			p.setRc(current.getRc());
		else
			oclusters=current.getRc();
	}
	
	//if has only left child
	if( current.getRc()==null && current.getLc()!=null) 
	{
		if(p.getLc()==current)
			p.setLc( current.getLc());
		else if(p.getRc()==current)
			p.setRc(current.getLc());
		else
			oclusters=current.getLc();
	}
	
	return true;
}

}
