
public class Planet {
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public Planet getParent() {
		return parent;
	}
	public void setParent(Planet parent) {
		this.parent = parent;
	}
	public Planet getLc() {
		return lc;
	}
	public void setLc(Planet lc) {
		this.lc = lc;
	}
	public Planet getRc() {
		return rc;
	}
	public void setRc(Planet rc) {
		this.rc = rc;
	}
	
	
	int pid;
	int	distance;
	Planet	parent;
	Planet	lc;
	Planet	rc;
	
	
	public Planet(int pid, int distance){
		this.pid=pid;
		this.distance=distance;
		this.rc = null;
		this.lc = null;
		this.parent = null;
	}
	
	public Planet ( ) {
		this.lc=null;
		this.rc=null;
		this.parent=null;
	}
	
	
	/*public Planet(int distance,Planet rc){ 		// FOR OCLUSTER QUEUE ONLY !!!
													// DISTANCE = LEVEL
		this.distance=distance;						// RC = PTR
		this.rc = rc;
		this.lc = null;
		this.parent = null;
	}*/
}
