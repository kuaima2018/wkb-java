/**
 * 
 */
package com.heima.tree;

import java.util.Comparator;

/**
 * @author kevin
 * 
 */
public class NodeIDComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		int j1 = o1.id;
		int j2 = o2.id;
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}
