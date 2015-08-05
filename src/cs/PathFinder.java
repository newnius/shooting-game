package cs;

import java.util.ArrayList;
import java.util.List;
import cs.Location;

/**
 * 此类包含了核心算法-A*寻路算法
 * @author Super pan
 *
 */
public class PathFinder{
	
	//开启列表
	private List<Location> openList;
	//关闭列表
	private List<Location> closeList;
	
	public PathFinder(){
		openList = new ArrayList<Location>();
		closeList = new ArrayList<Location>();
	}
	
	public List<Location> findPath(Location start,Location dest,int[][] map){
		List<Location> path = new ArrayList<Location>();
		openList.add(start);
		Location current = null;
		do{
			current = getLowestF(openList);
			//System.out.println(current.toString());
			closeList.add(current);
			openList.remove(current);
			if(closeList.contains(dest)){
				break;
			}
			List<Location> adjacentLocations = getWalkableAdjacentLocations(current, map);
			for(Location lo : adjacentLocations){
				if(closeList.contains(lo)){
					continue;
				}
				if(!openList.contains(lo)){
					lo.setG(current.getG()+1);
					lo.setH(evalH(current,dest));
					lo.setTotalEvalSteps(evalH(current,dest)+lo.getG());
					openList.add(lo);
				}else{
					if(current.getG()+1 < lo.getG()){
						lo.setG(current.getG()+1 );
						lo.setPrevious(current);
					}
				}
			}
		
		}while(!openList.isEmpty());
		Location destination = null;
		if(closeList.contains(dest)){
			destination = current;
			
			path.add(destination);
			while(destination.getPrevious() != null){
				destination = destination.getPrevious();
				path.add(destination);
			}
		}
		
		return path;
	}
	
	/**找能到达的相邻的位置*/
	private List<Location> getWalkableAdjacentLocations(Location current,int[][] map){ 
		int i = current.getI();
		int j = current.getJ();
		List<Location> walkableLos = new ArrayList<Location>();
		Location lo = null;
		if(i+1 < map.length &&map[i+1][j]==0){
			lo = new Location(i+1,j);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		if(i-1>0 &&map[i-1][j]==0){
			lo = new Location(i-1,j);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		if(j+1 < map[0].length && map[i][j+1]==0){
			lo = new Location(i,j+1);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		if(j-1>0 &&map[i][j-1] ==0){
			lo = new Location(i,j-1);
			lo.setPrevious(current);
			walkableLos.add(lo);
		}
		return walkableLos;
	}
	
	/**
	 * 找到F值最低的位置
	 * @param openList
	 * @return
	 */
	private Location getLowestF(List<Location> openList){
		if(openList == null || openList.size() == 0){
			return null;
		}
		int minSteps = openList.get(0).getF();
		int tmpSteps = 0;
		Location lowestF= openList.get(0);
		for(Location lo : openList){
			tmpSteps = lo.getF();
			if(tmpSteps < minSteps){
				minSteps = tmpSteps;
				lowestF = lo;
			}
		}
		return lowestF;
	}
	
	/**
	 * 评估H值
	 * @param current
	 * @param dest
	 * @return
	 */
	private int evalH(Location current,Location dest){                                   // H function
		int distanceI = dest.getI() - current.getI();
		int distanceJ = dest.getJ() - current.getJ();
		if(distanceI < 0){
			distanceI = distanceI * -1;
		}
		if(distanceJ < 0){
			distanceJ = distanceJ * -1;
		}
		return distanceI + distanceJ;
	}
}
