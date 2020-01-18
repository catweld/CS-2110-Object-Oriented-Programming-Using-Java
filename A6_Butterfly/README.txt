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

Then we switched to DFS in run and collected the flowers because it was much quicker run time.


