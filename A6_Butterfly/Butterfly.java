/* Time spent:  32 hours and 30 minutes
 * Name: Marc Choueiri, Catherine Weldon (aka cwelds)
 * NetID: mnc27, cfw56
 * PLEASE GRADE THIS QUICKLY AS WE ARE CONSIDERING OPTING OUT OF THE FINAL
 * ALSO SEE README, it explains why we took so much time.
 */

package student;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
import java.util.List;
//import java.util.PriorityQueue;

import danaus.*;

public class Butterfly extends AbstractButterfly {
	private TileState[][] ts; // records explored tileStates
	private boolean [][] viewed; // records viewed tiles
	private List<Flower> fList; // list of flowers in map
	
	
	
	
	/**
	 * Returns a two-dimensional array of TileStates that represents the map the
	 * butterfly is on.
	 * 
	 * During the learning phase of a simulation, butterflies are tasked with
	 * learning the map in preparation for the running phase of a simulation. 
	 * A butterfly should traverse the entire map and generate a two-dimensional
	 * array of TileStates in which each TileState corresponds to the
	 * appropriate in the map.
	 *
	 * The returned array is graded based on the percentage of correctly 
	 * identified TileStates. It is recommended that a butterfly save the 
	 * TileState array to use during the running phase of a simulation.
	 *
	 * For more information, refer to Danaus' documentation.
	 * 
	 * @return A two-dimensional array of TileStates that represents the map 
	 * 		    the butterfly is on.
	 */
	public @Override TileState[][] learn() {
		ts = new TileState[getMapHeight()][getMapWidth()]; //tileState array
		viewed = new boolean[getMapHeight()][getMapWidth()]; //viewed array
		recordGoodTile(); // helper function that records data of current tile
		dfs(); //first call of recursive function
		fList=flowerList();
		return ts;
	}
	
	/**
	 * This is the recursive function, dfs() (depth first search). 
	 * Precondition: I am at some tile t. Mark t as visited. Store TileState. 
	 * For all unvisited neighbors, t', of t try to fly to t'. If successful 
	 * dfs(), then return to t. Otherwise, mark obstacle.
	 * Postcondition: I am at t
	 */
	private void dfs() {
		for (Direction d: Direction.values()) {
			try{
				int row = Common.mod(location.row + d.dRow, getMapHeight());
				int col = Common.mod(location.col + d.dCol, getMapWidth());
				if (viewed[row][col] == false) { 
					fly(d, danaus.Speed.FAST);
					recordGoodTile();
					dfs();
					fly(danaus.Direction.opposite(d), danaus.Speed.FAST);}
			}
			catch(danaus.CliffCollisionException e){
				viewed[location.row][location.col] = true; //cliff, mark visited
			}
			catch(danaus.WaterCollisionException e){
				viewed[location.row][location.col] = true; //water, mark visited
			}
		}
		return;
	}
	
	/**
	 * This is a helper function. It refreshes state, records the tile in the
	 * tile in the tileState array, and changes the corresponding cell in the
	 * visited array to true.
	 */
	private void recordGoodTile() {
		refreshState();
		ts[location.row][location.col] = state;
		viewed[location.row][location.col] = true;
		return;
	}
	
	/*******************************************************************************/
	
    /**
     * Simulates the butterfly's flight.
     * <br>
     * During the transition from the learning phase to the running phase, new 
     * flowers are planted on the map. Everything else remains the same. A
     * butterfly must navigate to and collect all the flowers in 
     * <em>flowerIds<em> in any order to successfully complete a simulation. 
     * You must also not collect extraneous flowers. That is, only collect the 
     * flowers represented by flowerIds.
     * 
     * @param flowers A vector of flowers which the butterfly has to collect
     * @see danaus.AbstractButterfly#collect(Flower)
     */
	public @Override void run(List<Long> flowerIds) {
		ts = new TileState[getMapHeight()][getMapWidth()];
		viewed = new boolean[getMapHeight()][getMapWidth()]; //viewed array
		recordGoodTile(); // helper function that records data of current tile
		List<Flower> flowers=state.getFlowers();
		for (Flower f:flowers) {
			if (flowerIds.contains(f.getFlowerId())) {
				flowerIds.remove(f.getFlowerId());
				collect(f);
			}
		}
		dfs2(flowerIds);
	}
	
	/**
	 * This is the recursive function, dfs2() (depth first search). 
	 * Precondition: I am at some tile t. Mark t as visited. If t has a flower
	 * that we want to collect, collect that flower. Store TileState. 
	 * For all unvisited neighbors, t', of t try to fly to t'. If successful 
	 * dfs2(), then return to t. Otherwise, mark obstacle.
	 * Postcondition: I am at t
	 */
	private void dfs2(List<Long> flowerIds) {
		for (Direction d: Direction.values()) {
			try{
				int row = Common.mod(location.row + d.dRow, getMapHeight());
				int col = Common.mod(location.col + d.dCol, getMapWidth());
				if (viewed[row][col] == false) { 
					fly(d, danaus.Speed.FAST);
					recordGoodTile();
					List<Flower> flowers=state.getFlowers();
					for (Flower f:flowers) {
						if (flowerIds.contains(f.getFlowerId())) {
							flowerIds.remove(f.getFlowerId());
							collect(f);
						}
					}
					dfs2(flowerIds);
					fly(danaus.Direction.opposite(d), danaus.Speed.FAST);}
			}
			catch(danaus.CliffCollisionException e){
				viewed[location.row][location.col] = true; //cliff, mark visited
			}
			catch(danaus.WaterCollisionException e){
				viewed[location.row][location.col] = true; //water, mark visited
			}
		}
		return;
	}

	/**
	 * Return a list of all the flowers seen by this butterfly. More formally,
	 * return the union of the sets of flowers of all visited tiles. If no
	 * flowers have been found, the empty list should be returned.
	 * 
	 * @return A list of the discovered flowers, or empty if no flowers have
	 * 		been discovered.
	 */
	public @Override List<Flower> flowerList() {
		List<Flower> flowers = new ArrayList<Flower>();
		for (int row = 0; row<getMapHeight(); row++) {
			for (int col = 0; col<getMapWidth(); col++) {
				if (ts[row][col] != null && ts[row][col].getFlowers() != null) {
					flowers.addAll(ts[row][col].getFlowers());
					}
			}
		}
		return flowers;
	}
	
	/**
	 * If f is in the list produced by flowerList(), return the location of f.
	 * Otherwise, return null. If f is null, return null. Note that null will be
	 * returned if the flower is not present on the map or if the flower is
	 * present on the map but has not yet been discovered.
	 * 
	 * Note that flowers are equal if and only if their flowerId's are equal. 
	 * Thus, we could pass you a Flower instance with a null location field. 
	 * Therefore, returning f.getLocation() is not always guaranteed to work. 
	 * 
	 * @param f A flower.
	 * @return The Location of f if f has been discovered. null 
	 * 		otherwise.
	 */
	public @Override Location flowerLocation(Flower f) {
		if (f == null) return null;
		for (Flower fInList:fList) {
			if (fInList.getFlowerId() == f.getFlowerId()) {
				Location fLocation= fInList.getLocation();
				return fLocation;
				}
		}
		return null;
	}
	
	/**
	 * If there exists a flower <f> with the flower id <flowerId> in the list of
	 * flowers returned by flowerList(), then return the location of <f>. If 
	 * there does not exist a flower <f>, then return null. 
	 * 
	 * @param flowerId A flower id.
	 * @return The location of the flower with flower id <flowerId>. null
	 * 		otherwise.  
	 */
	public @Override Location flowerLocation(long flowerId) {
		for (Flower fInList:fList) {
			if (fInList.getFlowerId() == flowerId) {
				Location fLocation= fInList.getLocation();
				return fLocation;
				}
		}
		return null;
	}
}
