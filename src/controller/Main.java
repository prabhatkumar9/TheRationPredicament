package controller;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {
   
    public static void main(String[] args) {
	    
	// test case 1
	    double packs[] = { 0.5, 1.0, 2.0, 5.0 };
	    int minLimits[] = { 50, 50, 50, 30 };
	    int topUps[] = { 10, 10, 10, 10 };
	    int totalSpace[] = { 63, 125, 40, 20};
	    int reminder = 123;
	    
//	 // test case 2
//	    double packs[] = { 0.5, 1.0, 2.0, 5.0 };
//	    int minLimits[] = { 50, 50, 50, 20 };
//	    int topUps[] = { 10, 10, 10, 10 };
//	    int totalSpace[] = { 103, 15,27, 20};
//	    int reminder = 146;
	    
//	 // test case 3
//	    double packs[] = { 0.5, 1.0, 2.0, 5.0 };
//	    int minLimits[] = { 50, 50, 50, 20 };
//	    int topUps[] = { 10, 10, 10, 10 };
//	    int totalSpace[] = { 60, 65, 55, 20};
//	    int reminder = 289;
	    
//	 // test case 4
//	    double packs[] = { 0.5, 1.0, 2.0, 5.0 };
//	    int minLimits[] = { 50, 50, 50, 20 };
//	    int topUps[] = { 10, 10, 10, 10 };
//	    int totalSpace[] = { 60, 65,55, 20};
//	    int reminder = 457;
	    
	    HashMap<String, String> result = new HashMap<String, String>(); 
	    // calling allocate function
	    result = allocate(reminder,packs,minLimits,topUps,totalSpace);
	    
	    // using for-each loop for iteration over Map.entrySet() 
	        for (Entry<String, String> entry : result.entrySet()) {
	            System.out.println(entry.getKey()+"  :  "+ entry.getValue()); 
	        }
	            

   }
    
    public static HashMap<String, String> allocate(int rem, double packs[], int minLimits[],   int topUps[],   int totalSpace[] ) {
	
	 HashMap<String, String> hash_map = new HashMap<String, String>(); 
	    // length of arrays
	    int arrLength = packs.length;
	    
	    // new allocation array
	    int allocations[]  = new int[arrLength]; ;
	    
	    // post allocation array
	    int postAllocationCapacity[] = new int[arrLength]; 
	    
	    // reminder or empty space to fill
	    int reminder = rem;
	    
	    // for loop start
	    for(int i=0;i<arrLength;i++) {
		// select packet's size  
		double packWeight = packs[i];
		// number of selected_packets needed to fill the reminder
		int packNeed = (int) (reminder/packWeight);
		// minimum-allocation-limit of packets 
		int limit = minLimits[i];
		// space vacant for particular packets based on their size or weight
		int vacantSpace = totalSpace[i];
		// top_up size
		int topUp = topUps[i];
		// quotient of minimum-limit/topUp 
		// it will ensure that topUp number will not equal or greater that max-allocation-limit of packets
		int limitQt = limit/topUp;
		// needed packets only one less than  , equal or greater than minimum-limit
		// vacant space must be greater than or equal to minimum-limit
		if(packNeed >= limit-1 && limit <= vacantSpace) {
		    // if minimum-limit , vacant space and needed packets are equal
		    if(limit == vacantSpace && vacantSpace == packNeed) {
						// weight of total packets
						int weight = (int) (limit * packWeight);
						reminder = reminder - weight;
						 // total packets after adding topUps
						 int totalPacks = limit;
						// push number of packets into allocations array
						allocations[i] = totalPacks;
						// left capacity 
						postAllocationCapacity[i] = vacantSpace-totalPacks;
		    }else {
			// check that extra packets can be added with minimum-limit
			int extraPacks = vacantSpace-limit;
			// extra packets are equal or greater than topUp size
			if(extraPacks >= topUp) {
			    // quotient of extraPacks/topUp
			    int qt = extraPacks/topUp;
			    // if extraPacks/topUp is greater than minimum-limit/topUp 
			    if(qt >= limitQt) {
				qt = limitQt -1;
			    }
			    // extra topUps needed 
			    int extraTop = qt * topUp;
			    // total packets after adding topUps
			    int totalPacks = limit + extraTop;
			    // total weight of packets
			    int weight = (int) (totalPacks * packWeight);
			    // left reminder 
			    reminder = reminder - weight;
			    // push allocated packets number in allocations array
			    allocations[i] = totalPacks;
			    // remaining space after allocation
			    postAllocationCapacity[i] = vacantSpace-totalPacks;
			}else {
			    // when vacant space is only 1 packet less than minimum-allocation size
			    int totalPacks = limit;
			    int weight = (int) (totalPacks * packWeight);
			    reminder = reminder - weight;
			    allocations[i] = totalPacks;
			    postAllocationCapacity[i] = vacantSpace - totalPacks;
			}
		    }		    
		}else {
		    // if packets not allocates
		    allocations[i] = 0;
		    postAllocationCapacity[i] = vacantSpace;
		}

//		    System.out.println("reminder : "+reminder);
//		    System.out.println("allocations : "+allocations[i]);
//		    System.out.println("postAllocationCapacity : "+postAllocationCapacity[i]);
		 
	    }// end of for loop
	    
	    // convert results into string
	   String variant =   "["+packs[0]+"," +packs[1]+"," +packs[2]+"," +packs[3]+"]";
	   String uoms=   "["+minLimits[0]+"," +minLimits[1]+"," +minLimits[2]+"," +minLimits[3]+"]";
	   String allocation=   "["+allocations[0]+"," +allocations[1]+"," +allocations[2]+"," +allocations[3]+"]";
	   String postAllocation=   "["+postAllocationCapacity[0]+"," +postAllocationCapacity[1]+"," +postAllocationCapacity[2]+"," +postAllocationCapacity[3]+"]";
	    
	    	hash_map.put("reminder", ""+reminder); 
	        hash_map.put("variant",variant ); 
	        hash_map.put("uoms",uoms); 
	        hash_map.put("allocations",allocation); 
	        hash_map.put("post_alloc_capacity",postAllocation); 

	        return hash_map;
//	        System.out.println("reminder : "+reminder);
//	        System.out.println("variant : "+variant);
//	        System.out.println("uoms : "+uoms);
//	        System.out.println("allocations : "+allocation);
//	        System.out.println("postAllocation : "+postAllocation);
    }

}
