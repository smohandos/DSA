import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Dijikstra {

	public static void main(String[] args) {
		/*
		 * 
Example Input
Input 1:

A = 6
B = [   [0, 4, 9]
        [3, 4, 6] 
        [1, 2, 1] 
        [2, 5, 1] 
        [2, 4, 5] 
        [0, 3, 7] 
        [0, 1, 1] 
        [4, 5, 7] 
        [0, 5, 1] ] 
C = 4


		 */
		List<WeightedNode> nodeList=new ArrayList<>();
		WeightedNode src=new WeightedNode("E", 4);
		nodeList.add(src);
		
		nodeList.add(new WeightedNode("A", 0));
		nodeList.add(new WeightedNode("B", 1));
		nodeList.add(new WeightedNode("D", 3));
		nodeList.add(new WeightedNode("C", 2));
		nodeList.add(new WeightedNode("F", 5));

		
		WeightedGraph graph=new WeightedGraph(nodeList);
	
		graph.addWeightedEdge(0, 4, 9);
		graph.addWeightedEdge(3, 4, 6);
		graph.addWeightedEdge(1, 2, 1);
		graph.addWeightedEdge(2, 5, 1);
		graph.addWeightedEdge(2, 4, 5);
		graph.addWeightedEdge(0, 3, 7);
		graph.addWeightedEdge(0, 1, 1);
		graph.addWeightedEdge(4, 5, 7);
		graph.addWeightedEdge(0, 5, 1);

		graph.dijikstra(src);
		//graph.bellmanFord(src);
	}

}

class WeightedNode implements Comparable<WeightedNode>
{
	String name;
	WeightedNode parent;
	ArrayList<WeightedNode> neighbors=new ArrayList<>();
	Map<WeightedNode, Integer> weightMap=new TreeMap<>();
	int index;
	int distance;

	
	public WeightedNode(String name, int index) {
		this.name = name;
		this.index = index;
		this.distance=Integer.MAX_VALUE;
	}

	
	@Override
	public int compareTo(WeightedNode o) {
		return this.distance-o.distance;
	}
	
}

class WeightedGraph
{
	List<WeightedNode> nodeList;

	public WeightedGraph(List<WeightedNode> nodeList) {
		super();
		this.nodeList = nodeList;
	}
	
	void dijikstra(WeightedNode src)
	{
		PriorityQueue<WeightedNode> pq=new PriorityQueue<>();
		
		pq.add(src);
		
		src.distance=0;
		
		while(!pq.isEmpty())
		{
			WeightedNode currNode=pq.remove();
			
			for(WeightedNode neighbor:currNode.neighbors)
			{
			   int newDistance=currNode.distance+currNode.weightMap.get(neighbor);
			   
			   if(newDistance<neighbor.distance)
			   {
				   neighbor.distance=newDistance;
				   
				   neighbor.parent=currNode;
				   
				   pq.add(neighbor);
						   
			   }
			}
			
		}
	}
	
	void PrintPath(WeightedNode node)
	{
		if(node.parent!=null)
			PrintPath(node.parent);
		
		System.out.println(node.name);
	}
	
	void addWeightedEdge(int i,int j, int d)
	{
		WeightedNode first=nodeList.get(i);
		WeightedNode second=nodeList.get(j);
		
		first.neighbors.add(second);
		second.neighbors.add(first);

		first.weightMap.put(second,d);
		second.weightMap.put(first,d);

	}
	
	 // Bellman Ford Algorithm
	   void bellmanFord(WeightedNode sourceNode) {
	     sourceNode.distance = 0;
	     for (int i=0; i<nodeList.size(); i++) {
	       for (WeightedNode currentNode : nodeList) {
	         for (WeightedNode neighbor : currentNode.neighbors) {
	           if(neighbor.distance > currentNode.distance + currentNode.weightMap.get(neighbor)) {
	             neighbor.distance = (currentNode.distance + currentNode.weightMap.get(neighbor));
	             neighbor.parent = currentNode;
	           }
	         }
	       }
	     }
	     System.out.println("Checking for Negative Cycle..");
	     for (WeightedNode currentNode : nodeList) {
	       for (WeightedNode neighbor : currentNode.neighbors ) {
	         if(neighbor.distance > currentNode.distance + currentNode.weightMap.get(neighbor)) {
	             System.out.println("Negative cycle found: \n");
	             System.out.println("Vertex name: " + neighbor.name);
	             System.out.println("Old cost: " + neighbor.distance);
	             int newDistance = currentNode.distance + currentNode.weightMap.get(neighbor);
	             System.out.println("new cost: " + newDistance);
	             return;
	           }
	       }
	     }
	     System.out.println("Negative Cycle not found");
	   }
}
