package com.heima.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Children {

	private List<Node> list = new ArrayList<Node>();

	public List<Node> getList() {
		return list;
	}

	public void setList(List<Node> list) {
		this.list = list;
	}

	public int getSize() {

		return list.size();
	}

	public void addChild(Node node) {
		list.add(node);
	}

	// 拼接孩子节点的JSON字符串
	public String toString() {
		String result = "[";
		for (Node node : list) {
			result += node.toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}

	// 孩子节点排序
	public void sortChildren() {
		// 对本层节点进行排序
		// 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
		Collections.sort(list, new NodeIDComparator());
		// 对每个节点的下一层节点进行排序
		for (Node node : list) {
			node.sortChildren();
		}
	}

}
