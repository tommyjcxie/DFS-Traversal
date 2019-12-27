/**
 * Author: Jian Xie, 251036512
 * Date: December, 2019
 * Description: This is the Graph class.
 */
import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT {
	private int numOfNodes;
	private Edge adjacentMat[][];
	private Node nodeArr[];

	public Graph(int n) {
		numOfNodes = n;
		adjacentMat = new Edge[n][n]; //initialize an adjacency matrix with size n
		nodeArr = new Node[n];

		for (int i = 0; i < n; i++) {
			nodeArr[i] = new Node(i); //intialize node's names from 1 to n=size
		}
	}

	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
		if (getNode(u.getName()) == null || getNode(v.getName()) == null) {  //if either node is empty than throw an exception
			throw new GraphException();
		} else {
			if (adjacentMat[u.getName()][v.getName()] == null) { //if edge does not already exist we create an edge
				adjacentMat[u.getName()][v.getName()] = new Edge(u, v, edgeType);
				adjacentMat[v.getName()][u.getName()] = new Edge(v, u, edgeType);
			} else {
				throw new GraphException();
			}
		}
	}

	public Node getNode(int n) throws GraphException {
		if (n < 0 || n >= numOfNodes) { //if index is greater than size or less than 0, there will be an error.
			throw new GraphException();   
		}
		else {
			return nodeArr[n];
		}
	}

	public Iterator incidentEdges(Node u) throws GraphException {
		if (getNode(u.getName()) != null) {
			Stack s = new Stack();
			for (int i = 0; i < numOfNodes; i++) { //iterate through adjacency matrix to find edges incident on Node u.
				if (adjacentMat[u.getName()][i] != null)
					s.push(adjacentMat[u.getName()][i]); //push onto stack containing edges incident.
			}
			return s.iterator();
		} else
			throw new GraphException();
	}

	public Edge getEdge(Node u, Node v) throws GraphException {
		//if nodes are empty there or the edge doesn't exist, there can't be an edge between them.
		if (getNode(u.getName()) == null || getNode(v.getName()) == null || adjacentMat[u.getName()][v.getName()] == null) { 
			throw new GraphException();
		}
		else {
			return adjacentMat[u.getName()][v.getName()];
		}	
	}

	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if (getNode(u.getName()) == null || getNode(v.getName()) == null) { //if nodes are empty there can't be an edge between them.
			throw new GraphException();
		}
		if (adjacentMat[u.getName()][v.getName()] == null) { 
			return false;
		}
		else {
			return true;
		}
	}
}