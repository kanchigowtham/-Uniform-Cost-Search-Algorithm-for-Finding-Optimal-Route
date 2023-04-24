/*
 * CSE 5360-003 Artificial Intelligence - I
 * Kanchi Gowtham Kumar
 * 1002044003
 *
 */
import java.util.*;

public class node{

	String name;
	int depth;
	int price;
	node parent;
	ArrayList<node> children = new ArrayList<node>();

	public node(String name, int depth, int price, node parent, ArrayList<node>children){
		this.name=name;
		this.depth=depth;
		this.price=price;
		this.parent=parent;
		this.children=children;
	}

	public node(String name, int depth, int price, node parent){
		this.name=name;
		this.depth=depth;
		this.price=price;
		this.parent=parent;
	}

	String getName(){
		return this.name;
	}

	int getDepth(){
		return this.depth;
	}

	int getCost(){
		return this.price;
	}

	node getParent(){
		return this.parent;
	}

	ArrayList<node> getChildren(){
		return this.children;
	}

	void setChildren(ArrayList<node>children){
		 this.children=children;

	}
}
