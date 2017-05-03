
public class Planet {
	int pid;
	int	distance;
	Planet	prev;
	Planet	next;

	//constructor
	public Planet ( ) {
		this.prev=null;
		this.next=null;
	}
	public Planet (int pid, int	distance, Planet prev, Planet next) {
		this.pid=pid;
		this.distance=distance;
		this.prev=prev;
		this.next=next;
	}
	
	
	//various functions
	int getPid()		{ return this.pid; }
	int getDistance()	{ return this.distance; }
	Planet getNext()	{ return this.next; }
	Planet getPrev()	{ return this.prev; }
	
	void setPid(int pid)			{   this.pid= pid; }
	void setDistance(int distance)	{   this.distance=distance; }
	void setNext(Planet	next)		{   this.next=next; }
	void setPrev(Planet	prev)		{   this.prev=prev; }
	
	
	
}