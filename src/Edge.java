/**
 * Author: Jian Xie, 251036512
 * Date: December, 2019
 * Description: This is the Edge class.
 */
public class Edge {
	Node u,v;
	int type;
	Edge(Node u, Node v, int type){
		this.u = u;
		this.v = v;
		this.type = type;
	}
	Node firstEndpoint() {
		return u;
	}
	Node secondEndpoint() {
		return v;
	}
	int getType() {
		return type;
	}
}
