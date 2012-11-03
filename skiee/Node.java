package skiee;

import java.util.HashMap;
import java.util.Map;

public class Node {
int height;
int x;
int y;

int tag = 0; //标记是否以该节点为root构造过有向图，默认为0
static int maxpath = 1;  //最大路劲永远只有一个
static Map<String,Node> map = null;

Node child1 = null; // up
Node child2 = null; // right
Node child3 = null; // down
Node child4 = null; // left

//根据整型数组构造对象map
public static Map<String,Node> buildGround(int[][] ground,int x,int y){
	Map<String,Node> mg = new HashMap<String,Node>();
	for (int i = 0; i < x;  i++) {
		for (int j = 0; j < y; j++) {
			Node n = new Node();
			n.height = ground[i][j];
			n.x = i;
			n.y = j;
			mg.put(i+":"+j, n);
		}
	}
	return mg;
}

//构造有向图
public static void buildGraph(Map<String,Node> gmap, int maxx,int maxy) {  
	for (int x = 0; x < maxx; x++) {
		for (int y = 0; y < maxy; y++) {
			Node root = gmap.get(x+":"+y);
			if (x-1 >= 0 && root.height > (gmap.get((x-1)+":"+y)).height) {
				root.child1 = gmap.get((x-1)+":"+y);
			}
			if (y+1 < maxy && root.height > (gmap.get(x+":"+(y+1))).height) {
				root.child2 = gmap.get(x+":"+(y+1));
			}
			if (x+1 < maxx && root.height > (gmap.get((x+1)+":"+y)).height) {
				root.child3 = gmap.get((x+1)+":"+y);
			}
			if (y-1 >= 0 && root.height > (gmap.get(x+":"+(y-1))).height) {
				root.child4 = gmap.get(x+":"+(y-1));
			}
		}
	}
}

//搜索某个节点为根的所有子树路径最大长度
public static void searchTree(Node root,int path){
	int pathtemp = 0;
	if (root.child1 == null && root.child2 == null && root.child3 == null && root.child4 == null) {    //当路劲走到最末端时，将得到的路劲值与全局最大值比较
		if (path > maxpath) {
			maxpath = path;       //更新全局变量maxpath
		}
		return;
	}
	if (root.child1 != null) {
		pathtemp = path;
		pathtemp = pathtemp + 1;
		searchTree(root.child1,pathtemp);
	}
	if (root.child2 != null) {
		pathtemp = path;
		pathtemp = pathtemp + 1;
		searchTree(root.child2,pathtemp);
	}
	if (root.child3 != null) {
		pathtemp = path;
		pathtemp = pathtemp + 1;
		searchTree(root.child3,pathtemp);
	}
	if (root.child4 != null) {
		pathtemp = path;
		pathtemp = pathtemp + 1;
		searchTree(root.child4,pathtemp);
	}
}

public static void main(String[] args) {
	int[][] rawground ={{1,2,3,4,5},{16,17,18,19,6},{15,24,25,20,7},{14,23,22,21,8},{13,12,11,10,9}};
	map = buildGround(rawground,5,5);
	
//	for (int i = 0; i < 5; i++) {
//		for (int j = 0; j < 5; j++) {
//			System.out.print(map.get(i+":"+j).height+"   ");
//		}
//		System.out.println("\n");
//	}
	
	buildGraph(map,5,5);     //构建有向关系
//	for (int i = 0; i < 5; i++) {
//		for (int j = 0; j < 5; j++) {
//			System.out.print(map.get(i+":"+j).height+"|"+map.get(i+":"+j).child1+"|"+map.get(i+":"+j).child2+"|"+map.get(i+":"+j).child3+"|"+map.get(i+":"+j).child4+"|"+"   ");
//		}
//		System.out.println("\n");
//	}
	
	for (int i = 0; i <5; i++) {
		for (int j = 0; j < 5; j++) {
			searchTree(map.get(i+":"+j),1);     //默认path=1，因为开始就有1个节点
		}
	}
	System.out.println(maxpath);
}

}




