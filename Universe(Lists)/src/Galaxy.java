
public class Galaxy {
	public final static int M = 50; //oclusters
	
	int gid;
	Solar solars;
	Solar ssentinel;
	Ocluster[] oclusters;
	
	int oclustersSize=0;
	public int getoclustersSize(){return this.oclustersSize;}


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
		this.oclusters=new Ocluster[M];
		for (int i=0;i<M;i++) this.oclusters[i]=new Ocluster();
		//this.size++;
	}
	
	//various functions
	public int getGid()					{ return this.gid; }
	public Solar getSolar() 			{ return this.solars;}
	public Solar getSSentinel()			{ return this.ssentinel;}
	public Ocluster getOcluster(int i)	{ return this.oclusters[i];}
	
	
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



public int findOcluster(int cid){
	boolean found=false;
	int i;
	
	for (i=0;i<M;i++)
	{
		if (this.oclusters[i].getCid( )==cid){
			found=true;
			break;
			}
		
	}
	if (found) return i;
	else return -1;
}


public void insertOcluster(int cid,Planet planet){
	if (planet==null) return;
	 
	Planet tmp = planet;
	while (tmp!=null){
		tmp.setDistance(0);
		tmp=tmp.getNext();
	}
	
	for (int i=0;i<M;i++)
	{
		if (this.oclusters[i].getCid( )==cid)
		{
			this.oclusters[i].insertPlanet(planet.getPid());
			return;
		}
		
	}
	this.oclusters[oclustersSize].insertPlanet( planet.getPid() );
	this.oclusters[oclustersSize].setCid(cid);
	oclustersSize++;
	
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
			if (current.getNext()==null){ //if last elem
				prev.setNext(null);
				current=null;
				}
			
			else if (prev==null){ //if head
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



Planet findOrphan(int oid){
	Planet tmp=null;
	
	for (int i=0;i<oclustersSize;i++)
	{
		if(oclusters[i]!=null)
			tmp=oclusters[i].getOrphans();
		else continue;
		
		while(tmp!=null && tmp.getPid()<=oid)
		{
			if (tmp.getPid()==oid)
				return tmp;
			tmp=tmp.getNext();
		}
		
	}
	
	
	return tmp;
}

int findCluster(int cid){
	Ocluster tmp=oclusters[0];
	int i=0;
	
	while(tmp.getCid()!=Integer.MAX_VALUE && i<M)
	{
		if (tmp.getCid()==cid)
			return i;
		i++;
	}
	return 0;
}


Planet findPlanet(int pid){
	Planet tmp=null;
	Solar tmpsolar=solars;
	
	while(tmpsolar!=null && tmpsolar!=ssentinel)
	{
		tmp=tmpsolar.getPlanet();
		
		while(tmp!=null)
		{
			if(tmp.getPid()==pid)
				return tmp;
			else
				tmp=tmp.getNext();
		}
		
		tmpsolar=tmpsolar.getNext();
	}

	
	return null;
}

 
	
}
