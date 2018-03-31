/**
 * 
 */
package com.heima.tree;

import java.io.Serializable;

/**
 * @author kevin
 * 
 */
public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5952550598118014115L;
	/**
	 * 节点编号
	 */
	public int id;
	/**
	 * 节点内容
	 */
	public String text;
	/**
	 * 父节点编号
	 */
	public int parentId;
	/**
	 * type:类型，0表示org,1表示岗位,2表示用户
	 */
	public int type;

	private Children children = new Children();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Children getChildren() {
		return children;
	}

	public void setChildren(Children children) {
		this.children = children;
	}

	public String toString() {
		String result = "{" + "id : '" + id + "'" + ", text : '" + text + "'"
				+ ", type:'" + type + "'";
		if (children != null && children.getSize() != 0) {
			result += ", children : " + children.toString();
		} else {
			result += ", leaf : true";
		}
		return result + "}";
	}

	public void sortChildren() {
		if (children != null && children.getSize() != 0) {
			children.sortChildren();
		}
	}

	// 添加孩子节点
	public void addChild(Node node) {
		this.children.addChild(node);
	}

}
