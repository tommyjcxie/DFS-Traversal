
/**
* Author: Jian Xie, 251036512
 * Date: December, 2019
 * Description: This is the RoadMap class.
 */
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.io.FileInputStream;
import java.util.ArrayList;

public class RoadMap {
	private Graph graph;
	private int destination; // 3
	private int initialMoney; // 6
	private int start;// 2
	private int width; // 4
	private int length;// 5
	private int toll; // 7
	private int gain; // 8
	Stack<Node> nodes = new Stack<Node>();
	// + are nodes
	// ’T’: denotes a private road (Toll required)
	// 'F’: denotes a public road (Free to use)
	// ’C’: denotes a reward road (Cash received from using it)
	// ’X’: denotes a block of houses

	public RoadMap(String inputFile) throws MapException {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			input.readLine();
			start = Integer.parseInt(input.readLine());
			destination = Integer.parseInt(input.readLine());
			width = Integer.parseInt(input.readLine());
			length = Integer.parseInt(input.readLine());
			initialMoney = Integer.parseInt(input.readLine());
			toll = Integer.parseInt(input.readLine());
			gain = Integer.parseInt(input.readLine());
			graph = new Graph((length) * (width));
			ArrayList<String> arrayLine = new ArrayList();
			String line;
			int x = 0;
			int i = 0;

			while ((line = input.readLine()) != null) {
				// adding elements to an arraylist of type string for easier processing.
				arrayLine.add(x, line);
				x++;
			}

			// creating the edges, loops till the end of the arraylist.
			while (i < arrayLine.size()) {
				for (int j = 0; j < width - 1; j++) {
					if (i % 2 == 0) { // connecting horizontally
						char symbol = arrayLine.get(i).charAt((j * 2 + 1));
						if (symbol != 'X') { // check that if the symbol is X we don't do anything with it.
							Node firstVertice = graph.getNode((i / 2) * (width) + j);
							Node secondVertice = graph.getNode((i / 2) * (width) + j + 1);
							if (symbol == 'C') { // if symbol C
								graph.insertEdge(firstVertice, secondVertice, -1);
							} else if (symbol == 'T') { // if symbol T
								graph.insertEdge(firstVertice, secondVertice, 1);
							} else { // if symbol F
								graph.insertEdge(firstVertice, secondVertice, 0);
							}
						}
					} else { // connecting vertically
						char symbol = arrayLine.get(i).charAt((j * 2));
						if (symbol != 'X') { // check that if the symbol is X we don't do anything with it.
							Node firstVertice = graph.getNode((i / 2) * (width) + j);
							Node secondVertice = graph.getNode(((i / 2) + 1) * (width) + j);
							if (symbol == 'C') { // if symbol C
								graph.insertEdge(firstVertice, secondVertice, -1);
							} else if (symbol == 'T') { // if symbol T
								graph.insertEdge(firstVertice, secondVertice, 1);
							} else { // if symbol F
								graph.insertEdge(firstVertice, secondVertice, 0);
							}
						}
					}
				}
				i++;
			}
			input.close();
		} catch (Exception e) {
			throw new MapException();
		}
	}

	Graph getGraph() {
		return graph;
	}

	int getStartingNode() {
		return start;
	}

	int getDestinationNode() {
		return destination;
	}

	int getInitialMoney() {
		return initialMoney;
	}

	public boolean find(int start, int destination, int initialMoney) {
		Node startNode = graph.getNode(start);
		int money = initialMoney;
		nodes.push(startNode);
		startNode.setMark(true);
		// base case
		if (startNode.getName() == destination) {
			return true;
		}
		Iterator<Edge> edges = graph.incidentEdges(startNode);
		// loop for all edges incident on the node.
		while (edges.hasNext()) {
			Edge currEdge = edges.next();
			// if it is not marked we can go there so we check.
			if (currEdge.secondEndpoint().getMark() == false) {
				if (currEdge.getType() == 1) {
					money = initialMoney - toll;
				} else if (currEdge.getType() == -1) {
					money = initialMoney + gain;
				} else {
					money = initialMoney;
				}
				// if we have money we can keep looking for a path.
				if (!(money < 0)) {
					if (find(currEdge.secondEndpoint().getName(), destination, money))
						return true;
				}
			}
		}
		// unmark and pop the node.
		startNode.setMark(false);
		nodes.pop();
		return false;
	}

	// Use the recursive method above to find the path
	public Iterator findPath(int start, int destination, int initialMoney) throws GraphException {
		if (find(start, destination, initialMoney) == false) { // if it returns false there is no solution.
			return null;
		} else {
			return nodes.iterator(); // returns an iterator for a stack of nodes if a solution exists.
		}
	}
}
