


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @file    Main.java
 * @author  GV
 *
 */

public class Main {
	public final static int N = 100;
	
	
	public static Galaxy[] Galaxies;
	public static int GalaxySize=0;
	
	
	static int ht_upper_g; /**< The upper limit of the chains'
	                        * size for the rehash */
	static int ht_lower_g; /**< The lower limit of the chains'
	                        * size for the rehash */
	static int phashtable_size_g; /**< The size of the orphan planets
	                               * hashtable, parsed from the command
	                               * line (>0) */
	static int max_id_g; /**< The maximum planet ID */
 
	// This is a very conservative progress on the hashtable. Our purpose
	// is to force many rehashes to check the stability of the code.
	static int primes_g[] = { 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
	                          41, 43, 47, 53, 59, 61, 67, 71, 73, 79,
	                          83, 89, 97, 101, 103, 107, 109, 113, 127, 131,
	                          137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
	                          191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
	                          241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
	                          307, 311, 313, 317, 331, 337, 347, 349, 353, 359,
	                          367, 373, 379, 383, 389, 397, 401, 409, 419, 421,
	                          431, 433, 439, 443, 449, 457, 461, 463, 467, 479,
	                          487, 491, 499, 503, 509, 521, 523, 541, 547, 557,
	                          563, 569, 571, 577, 587, 593, 599, 601, 607, 613,
	                          617, 619, 631, 641, 643, 647, 653, 659, 661, 673,
	                          677, 683, 691, 701, 709, 719, 727, 733, 739, 743,
	                          751, 757, 761, 769, 773, 787, 797, 809, 811, 821,
	                          823, 827, 829, 839, 853, 857, 859, 863, 877, 881,
	                          883, 887, 907, 911, 919, 929, 937, 941, 947, 953};

	public static boolean DEBUG = true;

    /**
     * @brief Initializes the system.
     *
     * @return true on success
     *         false on failure
     */
    public static boolean big_bang() {
    	
    	Galaxies=new Galaxy[N];
    	
    	for(int i=0;i<N;i++){
    		Galaxies[i]=new Galaxy();
    	}
        return true;
    }

    /**
    * @brief Create a new galaxy and add it to the universe
    *
    * @param gid The new galaxy's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean galaxy_creation(int gid) {
    	Galaxies[GalaxySize].GalaxyInsert(gid);
    	if (GalaxySize<N) GalaxySize++;
        return true;
    }
    
    public static void printGalaxy()
    {	
    	System.out.println("G <gid> ");
    	System.out.println("   Galaxies = ");
    	int flag=0;
    	for(int i=0;i<N;i++){
    		
    		if (flag%15==0 && flag!=0 && Galaxies[i].getGid()!=2147483647)
    			System.out.println(Galaxies[i].getGid()+" ,");
    		else{
    			if ( Galaxies[i].getGid()!=2147483647)
    			System.out.print(Galaxies[i].getGid()+" ,");
    			}
    		flag++;
    		}
    	System.out.println("\nDONE");
    }
    
    
    
    
    

    /**
    * @brief Create a new solar system and add it to the galaxy with id gid
    *
    * @param sid The new solar system's star id
    * @param gid The galaxy's id to add the new solar system
    *
    * @return true on success
    *         false on failure
    */
public static boolean star_birth(int sid, int gid) {
    	
    	boolean found=false;
    	int i;
    	
    	for( i=0;i<N;i++){
    		
    		if (Galaxies[i].getGid()==gid){
    			found=true;
    			break;
    			}
    		}
    	if (found){
    		Galaxies[i].star_birth(sid);
    		return true;
    	}else
    		return false;
    }
    
    public static void printSolar(int sid,int gid)
    {	
    	boolean found=false;
    	int i;
    	
    	for( i=0;i<N;i++){
    		
    		if (Galaxies[i].getGid()==gid){
    			found=true;
    			break;
    			}
    		}
    	
    	
    	if(!found) return;
    	
    	System.out.println("\nS    "+sid+"    "+gid);
    	System.out.println("   Solars = ");
    	
    	int flag=0;
    	Solar tmp=Galaxies[i].getSolar();
    	while(tmp!=null && tmp!=Galaxies[i]. getSSentinel()){
    		
    		if (flag%15==0 && flag!=0)
    			System.out.print("\n\t"+tmp.getSid()+" ,");
    		else
    			System.out.print("\t"+tmp.getSid()+" ,");
    		
    		flag++;
    		tmp=tmp.getNext();
    		}
    	System.out.println("\nDONE\n\n");
    }

   /**
    * @brief Create a new planet in the solar system with id sid
    *
    * @param pid      The new planet's id
    * @param distance The new planet's distance from the solar system's star
    * @param sid      The solar system's id to add the new planet
    *
    * @return true on success
    *         false on failure
    */
    public static boolean planet_creation(int pid, int distance, int sid) {
    	Solar tmpSolar=findSolar(sid);
    	if (tmpSolar==null) return false;
    	
    	tmpSolar.insertPlanet(pid, distance);
    	
    	System.out.println("P  "+pid+"  "+distance+"  "+sid);
    	printInorder(tmpSolar.getPlanets());
    	System.out.println("DONE\n\n");
    	
        return true;   
        }

    public static Solar  findSolar(int sid){
		boolean found=false;
		int i;
		Solar foundg=null;
		for(i=0;i<N;i++){
			
			Solar tmp= Galaxies[i].getSolar();
			
			while (tmp!=null && tmp!=Galaxies[i].getSSentinel()){
				
				if (tmp.getSid() == sid){
					found=true;
					foundg=tmp;
					break;
				}
				tmp=tmp.getNext();
			}
			if (found)break;
		}
		 
		if (found)
			return foundg;
		else
			return null;
	}
    
    
     static void printInorder(Planet root)
    {
    	if (root==null)
    		return;
    	 
    	
    	printInorder(root.getLc());
    	System.out.print("\t"+root.getPid()+"  :  "+root.getDistance()+ "\n");
    	printInorder(root.getRc());

    }

    /**
    * @brief Delete a solar system and delete all planets in range
    * distance from the solar system's star-sun. The remaining planets
    * form a new orphan planets cluster in the galaxy.
    *
    * @param sid      The solar system's id
    * @param distance The range in which to delete the planets
    *
    * @return true on success
    *         false on failure
    */
    public static boolean star_death(int sid, int distance) {
    	Solar tmpSolar=findSolar(sid);
    	if (tmpSolar==null) return false;
    	
    	int galaxyIndex=findSolarGalaxy(sid);
    	if (galaxyIndex==-1) return false;
    	
    	postOrder(tmpSolar.getPlanets(),distance,galaxyIndex);
    	Galaxies[galaxyIndex].deleteSolar(sid);
    	
    	printStarDeath(sid,distance,galaxyIndex);
        return true;
    }
    
    public static void postOrder(Planet root,int distance,int galaxyIndex)
    {
    	if(root==null) return;
    	postOrder(root.getLc(), distance,galaxyIndex);
    	postOrder(root.getRc(), distance,galaxyIndex);
    	
    	if (root.getDistance()<distance)
    		root=new Planet();
    	else
    		Galaxies[galaxyIndex].unsortedTreeInsert(root.getPid());
    		
    }
    
    
    public static int findSolarGalaxy(int sid){
		boolean found=false;
		int i;
		int foundg=-1;
		for(i=0;i<N;i++){
			
			Solar tmp= Galaxies[i].getSolar();
			
			while (tmp!=null && tmp!=Galaxies[i].getSSentinel()){
				
				if (tmp.getSid() == sid){
					found=true;
					foundg=i; 
					break;
				}
				tmp=tmp.getNext();
			}
			if (found)break;
		}
		 
		if (found)
			return foundg;
		else
			return -1;
	} 
    
    public static void printStarDeath(int sid,int distance,int galaxyIndex){
    	System.out.println("D   "+sid+ "  "+distance+"   ");
    	System.out.println("   Solars = ");
    	System.out.print( "\t");
    	Solar tmp= Galaxies[galaxyIndex].getSolar();
		while (tmp!=null && tmp!=Galaxies[galaxyIndex].getSSentinel()){
			System.out.print(tmp.getSid()+",");		
			tmp=tmp.getNext();
		}
		
		System.out.println();
		System.out.println("   Orpahns = ");System.out.print("\t");
		Galaxies[galaxyIndex].printoclusters();
			System.out.print("\nDONE\n\n");

    }

   /**
    * @brief Delete the orphan planet with id oid and the planet with id
    * pid
    *
    * @param oid The orphan planet's id
    * @param pid The planet's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean planet_orphan_crash(int oid, int pid) {
    	Solar current,tmp=null;
    	int gtmp=-1;
    	for(int i=0;i<N;i++)
    	{
    		current=Galaxies[i].getSolar();
    		
    		if (current==null)
    		 continue;
    		
    		while(current!=null && current!=Galaxies[i].getSSentinel())
    		{
    			if(Galaxies[i].getSolar().planetLookupNdelete(pid))
    			{
    				 
    				tmp=current;
    				gtmp=i;
    				break;
    			}
    			current=current.getNext();
    		}
    		
    		
    		
    		if(Galaxies[i].yolo(oid))
    		{
    			System.out.println("   Planets = ");
    	    	System.out.print( "\t");
    			Galaxies[i].printoclusters();
    			System.out.println("");
    		} 
    		
    		
    		
    	}
    	
    	if(gtmp==-1 || tmp==null)
    		return false;
    	
    	System.out.println("   Solars = ");
    	System.out.print( "\t");

		while (tmp!=null )
		{
			System.out.print(tmp.getSid()+",");		
			tmp=tmp.getNext();
		}
		System.out.println("\n");
    	return true;
    }
    	

   /**
    * @brief Binary star creation
    *
    * @param sid1 The, to split, solar system
    * @param distance
    * @param sid2 The id of the first new solar system
    * @param sid3 The id of the second new solar system
    *
    * @return true on success
    *         false on failure
    */
    public static boolean binary_star_creation(int sid1, int distance, int sid2, int sid3) {
        return true;
    }

   /**
    * @brief Find a planet in the universe
    *
    * @param pid The planet's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean lookup_planet(int pid) {
    	Solar current;
    	for(int i=0;i<N;i++)
    	{
    		current=Galaxies[i].getSolar();
    		
    		if (current==null)
    		 continue;
    		
    		while(current!=null && current!=Galaxies[i].getSSentinel())
    		{
    			if(Galaxies[i].getSolar().planetLookupNdelete(pid))
    			{
    				//System.out.println("YOYOYO FOUND :D");
    				return true;
    				
    			}
    			current=current.getNext();
    		}}
    	
    	
        return false;
    }

    /**
    * @brief Find an orphan in the universe
    *
    * @param oid The orphan planet's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean lookup_orphan(int oid) {
    	
    	
    	
    	for(int i=0;i<N;i++)
    	{
    		if(Galaxies[i].LUP(oid))
    			return true;
    	}
    	
    	
    	
        return false;
    }

    /**
    * @brief Print a solar system
    *
    * @param sid The solar system's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_solar(int sid) {
    	
    	   Solar tmp=findSolar(sid);
    	   printInorder(tmp.getPlanets());
    	
        return true;
    }

    /**
    * @brief Print orphan planets
    *
    * @param gid The id of galaxy
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_orphans(int gid) {
    	for(int i=0;i<N;i++)
    	{
    		if(Galaxies[i].getGid()==gid)
    		{
    			Galaxies[i].printoclusters();
    			return true;
    		}
    	}
        return true;
    }

   /**
    * @brief Print a galaxy
    *
    * @param gid The galaxy's id
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_galaxy(int gid) {
        return true;
   }

   /**
    * @brief Print the universe
    *
    * @return true on success
    *         false on failure
    */
    public static boolean print_universe() {
        return true;
    }

   /**
    * @brief Finalize the system, empty and free all the data structures
    *
    * @return true on success
    *         false on failure
    */
    public static boolean end_of_the_world() {
        return true;
    }

////////////////////////////////////////////////////////////////////////////////
    public static void DPRINT(String s) {
        if (DEBUG) { System.out.println(s); }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader inputFile;
        String line;
        String [] params;

        /* Check command buff arguments */
        if (args.length != 1 ) {//|| args.length != 2
            System.err.println("Usage: <executable-name> <input_file>");
            System.err.println("   or  <executable-name> <input_file> <hashtable_size>");
            System.exit(0);
        }

        /*phashtable_size_g = Integer.parseInt(args[0]);
        if( phashtable_size_g < 1){
	        System.out.println("Error: The hashtable_size must be larger than 0");
	        System.exit(0);
        }*/

        /* Open input file */
        inputFile = new BufferedReader(new FileReader(args[0]));

        /* Read input file buff-by-buff and handle the events */
        while ( (line = inputFile.readLine()) != null ) {

            //System.out.println("Event: " + line);
            params = line.split(" ");
            char eventID = line.charAt(0);

            switch(eventID) {

            /* Comment */
            case '#':
                break;

            /* HT_LOWER */
            case '1': {
                ht_lower_g = Integer.parseInt(params[1]);
                //System.out.println("ht_lower_g = " + ht_lower_g);
                break;
            }

            /* HT_UPPER */
            case '2': {
                ht_upper_g = Integer.parseInt(params[1]);
                //System.out.println("ht_upper_g = " + ht_upper_g);
                break;
            }

            /* MAX ID */
            case '3': {
                max_id_g = Integer.parseInt(params[1]);
                //System.out.println("max_id_g = " + max_id_g);
                if ( 1 == 0 ) {
                    System.err.println("Wrong testfile format!");
                    System.exit(-1);
                }
                break;
            }

            /* Big Bang
             * B */
            case 'B':
            {
                if ( big_bang() ) {
                    DPRINT("B succeeded\n");
                } else {
                    System.out.println("B failed");
                }

                break;
            }

            /* Create a new Galaxy
             * G <gid> */
            case 'G':
            {
                int gid = Integer.parseInt(params[1]);
                assert(gid > 0);
                //DPRINT(eventID + " " + gid);

                if ( galaxy_creation(gid) ) {
                    //DPRINT(eventID + " " + gid + " succeeded");
                } else {
                    System.out.println(eventID + " " + gid + " failed");
                }
                printGalaxy();
                break;
            }

            /* Create a new Star
             * S <sid> <gid> */
            case 'S':
            {
                int sid = Integer.parseInt(params[1]);
                int gid = Integer.parseInt(params[2]);
                assert(sid > 0);
                assert(gid > 0);
                //DPRINT(eventID + " " + sid + " " + gid);

                if ( star_birth(sid, gid) ) {
                  //  DPRINT(eventID + " " + sid + " " + gid + " succeeded");
                } else {
                    System.out.println(eventID + " " + sid + " " + gid + " failed");
                }
                printSolar(sid,gid);
                break;
            }

            /* Create a new Planet
             * P <pid> <distance> <sid> */
            case 'P':
            {
                int pid = Integer.parseInt(params[1]);
                int distance = Integer.parseInt(params[2]);
                int sid = Integer.parseInt(params[3]);
                assert(pid > 0);
                assert(sid > 0);
                //DPRINT(eventID + " " + pid + " " + distance + " " + sid);

                if ( planet_creation(pid, distance, sid) ) {
                    //DPRINT(eventID + " " + pid + " " + distance + " " + sid + " succeeded");
                } else {
                    System.out.println(eventID + " " + pid + " " + distance + " " + sid + " failed");
                }

                break;
            }

            /* Delete a solar system
             * D <sid> <distance> */
            case 'D':
            {
                int sid = Integer.parseInt(params[1]);
                int distance = Integer.parseInt(params[2]);
                assert(sid > 0);
                //DPRINT(eventID + " " + sid + " " + distance);

                if ( star_death(sid, distance) ) {
                   // DPRINT(eventID + " " + sid + " " + distance + " succeeded");
                } else {
                    System.out.println(eventID + " " + sid + " " + distance + " failed");
                }

                break;
            }

            /* Trigger an orphan planet crash with a planet
             * O <oid> <pid> */
            case 'O':
            {
                int oid = Integer.parseInt(params[1]);
                int pid = Integer.parseInt(params[2]);
                assert(oid > 0);
                assert(pid > 0);
                DPRINT(eventID + " " + oid + " " + pid);

                if ( planet_orphan_crash(oid, pid) ) {
                    DPRINT(eventID + " " + oid + " " + pid + " succeed");
                } else {
                    System.out.println(eventID + " " + oid + " " + pid + " failed");
                }

                break;
            }

            /* Trigger an Binary Star Creation
             * C <sid1> <distance> <sid2> <sid3> */
            case 'C':
            {
                int [] sid = new int[3];
                sid[0] = Integer.parseInt(params[1]);
                int distance = Integer.parseInt(params[2]);
                sid[1] = Integer.parseInt(params[3]);
                sid[2] = Integer.parseInt(params[4]);
                assert(sid[0] > 0);
                assert(sid[1] > 0);
                assert(sid[2] > 0);
                DPRINT(eventID + " " + sid[0] + " " + sid[1] + " " + sid[2]);

                if ( binary_star_creation(sid[0], distance, sid[1], sid[2]) ) {
                    DPRINT(eventID + " " + sid[0] + " " + distance + " " +
                           sid[1] + " " + sid[2] + " succeeded");
                } else {
                    System.out.println(eventID + " " + sid[0] + " " +
                                       distance + " " + sid[1] + " " + sid[2] + " failed");
                }

                break;
            }

            /* Lookup a planet
             * L <pid> */
            case 'L':
            {
                int pid = Integer.parseInt(params[1]);
                assert(pid > 0);
                DPRINT(eventID + " " + pid);

                if ( lookup_planet(pid) ) {
                    DPRINT(eventID + " " + pid + " succeeded");
                } else {
                    System.out.println(eventID + " " + pid + " failed");
                }

                break;
            }

                /* Lookup orphan planet
                 * K <oid>  */
                case 'K':
                {
                    int oid = Integer.parseInt(params[1]);
                    assert(oid > 0);

                    DPRINT(eventID + " " + oid);

                    if ( lookup_orphan(oid) ) {
                        DPRINT(eventID + " " + oid + " succeeded");
                    } else {
                        System.out.println(eventID + " " + oid + " failed");
                    }

                    break;

                }

		/* Print a solar system
		 * H <sid> */
		case 'H':
		{
                    int sid = Integer.parseInt(params[1]);
                    assert(sid > 0);
                    DPRINT(eventID + " " + sid);

                    if ( print_solar(sid) ) {
                        DPRINT(eventID + " " + sid + " succeeded");
                    } else {
                        System.out.println(eventID + " " + sid + " failed");
                    }

                    break;
		}

		/* Print a orphan planets
		 * I <gid> */
		case 'I':
		{
                    int gid = Integer.parseInt(params[1]);
                    assert(gid > 0);
                    DPRINT(eventID + " " + gid);

                    if ( print_orphans(gid) ) {
                        DPRINT(eventID + " " + gid + " succeeded");
                    } else {
                        System.out.println(eventID + " " + gid + " failed");
                    }

                    break;
		}

		/* Print a galaxy
		 * J <gid> */
		case 'J':
		{
                    int gid = Integer.parseInt(params[1]);
                    assert(gid > 0);
                    DPRINT(eventID + " " + gid);

                    if ( print_galaxy(gid) ) {
                        DPRINT(eventID + " " + gid + " succeeded");
                    } else {
                        System.out.println(eventID + " " + gid + " failed");
                    }

                    break;
		}

		/* Print universe
		 * U */
		case 'U':
		{
                    if ( print_universe() ) {
			DPRINT("U succeeded\n");
                    } else {
                        System.out.println("U failed");
                    }

                    break;
		}

		/* End of the World (Optional)
		 * E */
		case 'E':
		{
                    if ( end_of_the_world() ) {
			DPRINT("E succeeded\n");
                    } else {
                        System.out.println("E failed");
                    }

                    break;
		}

		/* Empty line */
		case '\n':
			break;

		/* Ignore everything else */
		default:
                    DPRINT("Ignoring " + line);

                    break;
		}
	}

    }

}
