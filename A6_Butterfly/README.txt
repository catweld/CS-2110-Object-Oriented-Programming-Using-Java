Originally we wanted to minimize steps so we opted for a dijsktra method to find the known flowers and then to grab the unknown flowers along the way based on the aromas. After about 30 hours debugging our method we determined it was not as efficient time-wise as a depth search method and implemented that instead. 
Our original method functioned as follows: 
The learn() method initializes by creating a TileState array and boolean array of equal dimensions to the map. The TileState array will be used to store tileStates of the tiles we visit and the boolean array is to keep track of what tiles have been visited. The learn method has two helper functions.

The first helper function, recordGoodTile(), refreshes state, records the tile in the tile in the tileState array, and changes the corresponding cell in the visited array to true.

The second helper function is the recursive function dfs(). This is essentially how it works:
	•Precondition: I am at some tile t. Mark t as visited. Store TileState. For all unvisited neighbors, t', of t try to fly to t'. If successful dfs(), then return to t. Otherwise, mark obstacle.
	•Postcondition: I am at t flowerList() returns a List of type Flower. It iterates 		through every tile on the map and checks if there is a flower there. If there is, it adds it to the list.

flowerLocation(Flower f) iterates through every tile on the map searching for Flower f. Once it is found, it returns the location of that flower. If not found, returns null.

RUN
1)	Convert list of flower ids into hash map and array list
	a.	Hash map is for flower ids with known location (key fID, value location)
	b.	ArrayList is for flower ids with unknown location
2)	Find the closest tile with a known flower location from current tile w/ dkistra’s
3)	Get the distance (int) to that tile with helper pathWeight (sum of path)
4)	Find the distance (int) to the closest unknown flower
5)	Whatever distance is shorter fly to that flower
	a.	If it’s a known tile, fly following the path
	b.	If it’s the unkownn flower, fly using the hot and cold method
6)	Once at flower, collect flower, remover from HashMap/ArrayList and repeat from step 2

Then we switched to DFS in run and collected the flowers because it was much quicker run time. Below is the code we spent 32+ hours on:
CODE:

//private MyTileState[][] ms; // records explored tileStates
	
//	private class MyTileState implements Comparable<MyTileState> {
//		private TileState current;
//		private int val;
//		private MyTileState backPointer;
//		
//		private MyTileState (TileState cTS, int n, MyTileState bP) {
//			current= cTS;
//			val= n;
//			backPointer= bP;
//		}
//
//		@Override
//		public int compareTo(MyTileState o) {
//			return val-o.val;
//		}
		
//	}

//ms = new MyTileState[getMapHeight()][getMapWidth()]; //MyTileState array

//		for (int r=0; r<getMapHeight(); r++) {
//			for (int c=0; c<getMapWidth(); c++) {
//				ms[r][c]= new MyTileState(ts[r][c], Integer.MAX_VALUE, null);
//			}
//		}


//ms = new MyTileState[getMapHeight()][getMapWidth()]; //MyTileState array




//		HashMap<Long, Location> fLocsKnown = new HashMap<Long, Location>();
//		ArrayList<Long> fLocsUnknown = new ArrayList<Long>();
//		MyTileState tileOfCKF = null; LinkedList<MyTileState> pathCKF= null;
//		for (long fId:flowerIds) { //splits flowerIDs into 2 HashMaps
//			if (flowerLocation(fId)==null) {
//				fLocsUnknown.add(fId);
//			}
//			else {
//				fLocsKnown.put(fId, flowerLocation(fId));
//			}
//		}
//		int dist=0; int lengthOfPath=0;
//		while (!(fLocsKnown.isEmpty() && fLocsUnknown.isEmpty())) {
//			refreshState(); //get current location
//			int rCurrent = location.row;
//			int cCurrent = location.col;
//			if (!fLocsKnown.isEmpty()) {
//				tileOfCKF=closestKnownFlower(fLocsKnown, rCurrent, cCurrent);
//				pathCKF=dijkstras(ms[rCurrent][cCurrent], tileOfCKF);//returns closest
//				lengthOfPath=pathWeight(pathCKF);
//			}
//			else {
//				lengthOfPath=Integer.MAX_VALUE;
//			}
//			long cUF=closestUnknownFlower(fLocsUnknown);
//			if (fLocsUnknown.size()!=0) { 
//				dist= distanceCUF(cUF, rCurrent, cCurrent);
//			}
//			else {dist=Integer.MAX_VALUE;}
//				
//			if (lengthOfPath<=dist) {
//				flyToCKF(pathCKF);
//				System.out.println("Step 5");
//				long cKFID=0;
//				refreshState();
//				List<Flower> cKF=state.getFlowers();
//				for (Flower f:cKF) {
//					if (fLocsKnown.containsKey(f.getFlowerId())) {
//						cKFID=f.getFlowerId();
//						collect(f);
//						}
//				}
//				System.out.println("KnownPre:" +fLocsKnown.size());
//				fLocsKnown.remove(cKFID);
//				System.out.println("KnownPost:" +fLocsKnown.size());
//			}
//			else if (fLocsUnknown.size()!=0) {
//				flyHotAndCold(cUF);
//				refreshState();
//				List<Flower> cUKF=state.getFlowers();
//				for (Flower f:cUKF) {
//					if (f.getFlowerId()==cUF) {collect(f);}
//				}
//				System.out.println("UnknownPre:" +fLocsUnknown.size());
//				fLocsUnknown.remove(cUF);
//				System.out.println("UnknownPost:" +fLocsUnknown.size());
//			}
//		}
//		System.out.println("FINISHED!");





//	
//	private MyTileState closestKnownFlower(HashMap<Long, Location> fLocsKnown,
//			int rCurrent, int cCurrent) {
//		//if (fLocsKnown.isEmpty()) return null;
//		int shortest=Integer.MAX_VALUE;
//		MyTileState fClosestTile = null;
//		for (long key: fLocsKnown.keySet()) { //fly to the closet flower
//			int r=fLocsKnown.get(key).row;
//			int c=fLocsKnown.get(key).col;
//			int pWeight= pathWeight(dijkstras(ms[rCurrent][cCurrent], ms[r][c]));
//			if (shortest>=pWeight) {
//				shortest=pWeight;
//				fClosestTile= ms[r][c];
//			}
//		}
//		return fClosestTile;
//	}
//	
	
//	/**
//	 * This is a helper function to determine the closet flower that has an
//	 * unknown location by picking the one that has the strongest aroma.
//	 */
//	private long closestUnknownFlower(ArrayList<Long> fLocsUnknown) {
//		refreshState();
//		List<Aroma> tAroma= state.getAromas();
//		double maxIntensity = 0; long fClosest=0;
//		for (Aroma aroma:tAroma) {
//			for (long fID: fLocsUnknown) {
////				System.out.println("aroma:" +aroma);
////				System.out.println("aromaIntensity:" +aroma.intensity);
////				System.out.println("aID:" +aroma.getFlowerId());
////				System.out.println("fID:" +fID);
//				if (aroma.getFlowerId()==fID && maxIntensity<aroma.intensity) {
//					maxIntensity=aroma.intensity;
//					fClosest=fID; //FlowerID of closest unknown flower
//					//System.out.println("WORKS!");
//
//				}
//				//else {
//				//}
//			}
//		}
//		return fClosest;
//	}
//	
//
//	/**
//	 * This determines the distance from the current tile to the flower with
//	 * unknown location via aroma.
//	 */
//	private int distanceCUF(long fID, int rCurrent, int cCurrent) {
//		int aIntensity= aromaIntensity(fID);
//		double dist= Math.sqrt((10000000)/(aIntensity));
//		dist= Math.ceil(dist-1);
//		int distance = (int) dist;
//		return distance;
//			}
//	
//	
//	/**
//	 * This is a helper function to determine the total cost of the path. It
//	 * is the sum of all the weights.
//	 */
//	private int pathWeight (LinkedList<MyTileState> path) {
//		int tVal=0;
//		if (!path.isEmpty()) {
//			for (MyTileState mts:path) {tVal= tVal+ mts.val;}
//			}
//		return tVal;
//	}
//	
//	
//	/**
//	 * This is the flying method to reach the tile of the known flower.
//	 */
//	private void flyToCKF(LinkedList<MyTileState> path) {
//		path.poll();
//		while (!path.isEmpty()) {
//			MyTileState s=path.poll();
//			refreshState();
//			int r= s.current.location.row - location.row;
//			int c= s.current.location.col - location.col;
//			Direction d=danaus.Direction.N;
//			if ((r==0 || r==1 || r==-1) && (c==0 || c==1 || c==-1)) {
//				if (r==-1 && c==0) {d= danaus.Direction.N;}
//				else if (r==-1 && c==1) {d= danaus.Direction.NE;}
//				else if (r==0 && c==1) {d= danaus.Direction.E;}
//				else if (r==1 && c==1) {d= danaus.Direction.SE;}
//				else if (r==1 && c==0) {d= danaus.Direction.S;}
//				else if (r==1 && c==-1) {d= danaus.Direction.SW;}
//				else if (r==0 && c==-1) {d= danaus.Direction.W;}				
//				else if (r==-1 && c==-1) {d= danaus.Direction.NW;}
//			}
//			int row = Common.mod(location.row + d.dRow, getMapHeight());
//			int col = Common.mod(location.col + d.dCol, getMapWidth());
//			if (ts[row][col]!=null) {
//				fly(d, danaus.Speed.FAST);
//				}
//			}
//		}
//
//	
//	
//	/**
//	 * This is the flying method to reach unknown tile.
//	 */
//	private void flyHotAndCold(long cUF) {
//		refreshState();
//		Direction d=null; int oldAroma=0; int k=-1;
//		int currentAroma=aromaIntensity(cUF);
//		while (currentAroma!=1000000) {
//			//System.out.println("Step 6.1, aroma="+currentAroma);
//			boolean foundWorseAroma = false;
//			k=(k+1)%8;
//			d=Direction.values()[k];
//			while (!foundWorseAroma && currentAroma!=1000000) {
//				try {
//					fly(d, danaus.Speed.FAST);
//					refreshState();
//					oldAroma=currentAroma;
//					//System.out.println("oldaroma="+oldAroma);
//					currentAroma=aromaIntensity(cUF);
//					//System.out.println("Step 6.3, aroma="+currentAroma);
//					if (currentAroma<=oldAroma) {
//						foundWorseAroma = true;
//						oldAroma=currentAroma;
//						fly(danaus.Direction.opposite(d), danaus.Speed.FAST);
//						currentAroma=aromaIntensity(cUF);
//						refreshState();
//					}
//				}
//				catch(danaus.CliffCollisionException e){foundWorseAroma = true;}
//				catch(danaus.WaterCollisionException e){foundWorseAroma = true;}
//				}
//			}
//		}
//				
//			
//	public int aromaIntensity(long flowerId) {
//		refreshState();
//		List<Aroma> tAroma= state.getAromas();
//		double aIntensity = 0;
//		for (Aroma aroma:tAroma) {
//			if (aroma.getFlowerId()==flowerId) {
//				aIntensity= aroma.intensity;
//				int a=(int) aIntensity;
//				return a;
//			}
//		}
//		return 0;
//	}
//	
//	/**
//	 * This is a helper method to determine the 'nearest' flower with a known
//	 * location. By nearest it means the fewest turns away.
//	 */
//	private LinkedList<MyTileState> dijkstras(MyTileState A, MyTileState B) {
//		//System.out.println("Starting tile: " + A.current.location + 
//				//" | Target tile: " + B.current.location);
//		//clean
//		for (int r=0; r<getMapHeight(); r++) {
//			for (int c=0; c<getMapWidth(); c++) {
//				ms[r][c].val=Integer.MAX_VALUE;
//				ms[r][c].backPointer=null;
//			}
//		}
//		//initialize
//		A.val=0;
//		PriorityQueue<MyTileState> frontier = new PriorityQueue<MyTileState>();
//		frontier.add(A);
//		//loop
//		while(!(frontier.peek().equals(B))) {
//			MyTileState n= frontier.poll();
//			TileState t= n.current;
//			//System.out.println("Step 3.143");
//			if (t==null) {
//				return null; //possible error?
//			}
//			int r=t.location.row;
//			int c=t.location.col;
//			for (Direction d:Direction.values()) {
//				int newR= Common.mod(r+ d.dRow, getMapHeight());
//				int newC= Common.mod(c+ d.dCol, getMapWidth());
//				MyTileState child= ms[newR][newC];
//				int weight=1;
//				if (child.current==null) weight= Integer.MAX_VALUE;
//				else if (child.current.type== danaus.TileType.FOREST) weight=2;
//				if (n.val + weight < child.val) {
//					child.val=n.val+ weight;
//					child.backPointer=n;
//					if(!frontier.contains(child))
//						frontier.add(child);
//				}
//			}
//		}
//			//back trace path and return
//			LinkedList<MyTileState> path= new LinkedList<MyTileState>();
//			MyTileState current= B;
//			while (current.backPointer != null) {
//				path.push(current);
//				current=current.backPointer;
//			}
//			path.push(A);
//		return path;
//	}
//	
//	


