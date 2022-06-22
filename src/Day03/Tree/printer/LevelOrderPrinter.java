package Day03.Tree.printer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**

   ��������381����������
   ��          ��
����12����     ����410����
��    ��     ��     ��
9  ����40���� 394 ����540����
   ��    ��     ��     ��
  35 ����190 ����476 ����760����
     ��     ��     ��     ��
    146   445   600   800
    
 * @author MJ Lee
 *
 */
public class LevelOrderPrinter extends Printer {
	/**
	 * �ڵ�֮���������С��ࣨ��Сֻ����1��
	 */
	private static final int MIN_SPACE = 1;
	private Node root;
	private int minX;
	private int maxWidth;

	public LevelOrderPrinter(BinaryTreeInfo tree) {
		super(tree);

		root = new Node(tree.root(), tree);
		maxWidth = root.width;
	}

	@Override
	public String printString() {
		// nodes����������еĽڵ�
		List<List<Node>> nodes = new ArrayList<>();
		fillNodes(nodes);
		cleanNodes(nodes);
		compressNodes(nodes);
		addLineNodes(nodes);
		
		int rowCount = nodes.size();

		// �����ַ���
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < rowCount; i++) {
			if (i != 0) {
				string.append("\n");
			}

			List<Node> rowNodes = nodes.get(i);
			StringBuilder rowSb = new StringBuilder();
			for (Node node : rowNodes) {
				int leftSpace = node.x - rowSb.length() - minX;
				rowSb.append(Strings.blank(leftSpace));
				rowSb.append(node.string);
			}

			string.append(rowSb);
		}

		return string.toString();
	}

	/**
	 * ���һ��Ԫ�ؽڵ�
	 */
	private Node addNode(List<Node> nodes, Object btNode) {
		Node node = null;
		if (btNode != null) {
			node = new Node(btNode, tree);
			maxWidth = Math.max(maxWidth, node.width);
			nodes.add(node);
		} else {
			nodes.add(null);
		}
		return node;
	}

	/**
	 * ��������������ʽ���ڵ�
	 */
	private void fillNodes(List<List<Node>> nodes) {
		if (nodes == null) return;
		// ��һ��
		List<Node> firstRowNodes = new ArrayList<>();
		firstRowNodes.add(root);
		nodes.add(firstRowNodes);

		// ������
		while (true) {
			List<Node> preRowNodes = nodes.get(nodes.size() - 1);
			List<Node> rowNodes = new ArrayList<>();

			boolean notNull = false;
			for (Node node : preRowNodes) {
				if (node == null) {
					rowNodes.add(null);
					rowNodes.add(null);
				} else {
					Node left = addNode(rowNodes, tree.left(node.btNode));
					if (left != null) {
						node.left = left;
						left.parent = node;
						notNull = true;
					}

					Node right = addNode(rowNodes, tree.right(node.btNode));
					if (right != null) {
						node.right = right;
						right.parent = node;
						notNull = true;
					}
				}
			}

			// ȫ��null�����˳�
			if (!notNull) break;
			nodes.add(rowNodes);
		}
	}

	/**
	 * ɾ��ȫ��null�����½ڵ������
	 */
	private void cleanNodes(List<List<Node>> nodes) {
		if (nodes == null) return;

		int rowCount = nodes.size();
		if (rowCount < 2) return;

		// ���һ�еĽڵ�����
		int lastRowNodeCount = nodes.get(rowCount - 1).size();

		// ÿ���ڵ�֮��ļ��
		int nodeSpace = maxWidth + 2;

		// ���һ�еĳ���
		int lastRowLength = lastRowNodeCount * maxWidth 
				+ nodeSpace * (lastRowNodeCount - 1);

		// �ռ���
		Collection<Object> nullSet = Collections.singleton(null);

		for (int i = 0; i < rowCount; i++) {
			List<Node> rowNodes = nodes.get(i);

			int rowNodeCount = rowNodes.size();
			// �ڵ��������ߵļ��
			int allSpace = lastRowLength - (rowNodeCount - 1) * nodeSpace;
			int cornerSpace = allSpace / rowNodeCount - maxWidth;
			cornerSpace >>= 1;

			int rowLength = 0;
			for (int j = 0; j < rowNodeCount; j++) {
				if (j != 0) {
					// ÿ���ڵ�֮��ļ��
					rowLength += nodeSpace;
				}
				rowLength += cornerSpace;
				Node node = rowNodes.get(j);
				if (node != null) {
					// ���У�������ż�������⣬������1�����ŵ���
					int deltaX = (maxWidth - node.width) >> 1;
					node.x = rowLength + deltaX;
					node.y = i;
				}
				rowLength += maxWidth;
				rowLength += cornerSpace;
			}
			// ɾ�����е�null
			rowNodes.removeAll(nullSet);
		}
	}

	/**
	 * ѹ���ո�
	 */
	private void compressNodes(List<List<Node>> nodes) {
		if (nodes == null) return;

		int rowCount = nodes.size();
		if (rowCount < 2) return;

		for (int i = rowCount - 2; i >= 0; i--) {
			List<Node> rowNodes = nodes.get(i);
			for (Node node : rowNodes) {
				Node left = node.left;
				Node right = node.right;
				if (left == null && right == null) continue;
				if (left != null && right != null) {
					// �����ҽڵ�Գ�
					node.balance(left, right);

					// left��right֮�����Ų������С���
					int leftEmpty = node.leftBoundEmptyLength();
					int rightEmpty = node.rightBoundEmptyLength();
					int empty = Math.min(leftEmpty, rightEmpty);
					empty = Math.min(empty, (right.x - left.rightX()) >> 1);

					// left��right���ӽڵ�֮�����Ų������С���
					int space = left.minLevelSpaceToRight(right) - MIN_SPACE;
					space = Math.min(space >> 1, empty);

					// left��right���м�Ų��
					if (space > 0) {
						left.translateX(space);
						right.translateX(-space);
					}

					// ����Ų��
					space = left.minLevelSpaceToRight(right) - MIN_SPACE;
					if (space < 1) continue;

					// ���Լ���Ų���ļ��
					leftEmpty = node.leftBoundEmptyLength();
					rightEmpty = node.rightBoundEmptyLength();
					if (leftEmpty < 1 && rightEmpty < 1) continue;

					if (leftEmpty > rightEmpty) {
						left.translateX(Math.min(leftEmpty, space));
					} else {
						right.translateX(-Math.min(rightEmpty, space));
					}
				} else if (left != null) {
					left.translateX(node.leftBoundEmptyLength());
				} else { // right != null
					right.translateX(-node.rightBoundEmptyLength());
				}
			}
		}
	}
	
	private void addXLineNode(List<Node> curRow, Node parent, int x) {
		Node line = new Node("��");
		line.x = x;
		line.y = parent.y;
		curRow.add(line);
	}

	private Node addLineNode(List<Node> curRow, List<Node> nextRow, Node parent, Node child) {
		if (child == null) return null;

		Node top = null;
		int topX = child.topLineX();
		if (child == parent.left) {
			top = new Node("��");
			curRow.add(top);

			for (int x = topX + 1; x < parent.x; x++) {
				addXLineNode(curRow, parent, x);
			}
		} else {
			for (int x = parent.rightX(); x < topX; x++) {
				addXLineNode(curRow, parent, x);
			}

			top = new Node("��");
			curRow.add(top);
		}

		// ����
		top.x = topX;
		top.y = parent.y;
		child.y = parent.y + 2;
		minX = Math.min(minX, child.x);

		// ����
		Node bottom = new Node("��");
		bottom.x = topX;
		bottom.y = parent.y + 1;
		nextRow.add(bottom);

		return top;
	}

	private void addLineNodes(List<List<Node>> nodes) {
		List<List<Node>> newNodes = new ArrayList<>();

		int rowCount = nodes.size();
		if (rowCount < 2) return;

		minX = root.x;

		for (int i = 0; i < rowCount; i++) {
			List<Node> rowNodes = nodes.get(i);
			if (i == rowCount - 1) {
				newNodes.add(rowNodes);
				continue;
			}

			List<Node> newRowNodes = new ArrayList<>();
			newNodes.add(newRowNodes);

			List<Node> lineNodes = new ArrayList<>();
			newNodes.add(lineNodes);
			for (Node node : rowNodes) {
				addLineNode(newRowNodes, lineNodes, node, node.left);
				newRowNodes.add(node);
				addLineNode(newRowNodes, lineNodes, node, node.right);
			}
		}

		nodes.clear();
		nodes.addAll(newNodes);
	}

	private static class Node {
		/**
		 * �������ž��븸�ڵ����С���루��С����0��
		 */
		private static final int TOP_LINE_SPACE = 1;

		Object btNode;
		Node left;
		Node right;
		Node parent;
		/**
		 * ���ַ���λ��
		 */
		int x;
		int y;
		int treeHeight;
		String string;
		int width;

		private void init(String string) {
			string = (string == null) ? "null" : string;
			string = string.isEmpty() ? " " : string;

			width = string.length();
			this.string = string;
		}

		public Node(String string) {
			init(string);
		}

		public Node(Object btNode, BinaryTreeInfo opetaion) {
			init(opetaion.string(btNode).toString());

			this.btNode = btNode;
		}

		/**
		 * ���������ַ���X��������Ҫ��
		 * 
		 * @return
		 */
		private int topLineX() {
			// ��ȵ�һ��
			int delta = width;
			if (delta % 2 == 0) {
				delta--;
			}
			delta >>= 1;

			if (parent != null && this == parent.left) {
				return rightX() - 1 - delta;
			} else {
				return x + delta;
			}
		}

		/**
		 * �ұ߽��λ�ã�rightX ���� ���ӽڵ�topLineX����һ��λ�ã���������Ҫ��
		 */
		private int rightBound() {
			if (right == null) return rightX();
			return right.topLineX() + 1;
		}

		/**
		 * ��߽��λ�ã�x ���� ���ӽڵ�topLineX����������Ҫ��
		 */
		private int leftBound() {
			if (left == null) return x;
			return left.topLineX();
		}

		/**
		 * x ~ ��߽�֮��ĳ��ȣ�������߽��ַ���
		 * 
		 * @return
		 */
		private int leftBoundLength() {
			return x - leftBound();
		}

		/**
		 * rightX ~ �ұ߽�֮��ĳ��ȣ������ұ߽��ַ���
		 * 
		 * @return
		 */
		private int rightBoundLength() {
			return rightBound() - rightX();
		}

		/**
		 * ��߽������յĳ���
		 * 
		 * @return
		 */
		private int leftBoundEmptyLength() {
			return leftBoundLength() - 1 - TOP_LINE_SPACE;
		}

		/**
		 * �ұ߽������յĳ���
		 * 
		 * @return
		 */
		private int rightBoundEmptyLength() {
			return rightBoundLength() - 1 - TOP_LINE_SPACE;
		}

		/**
		 * ��left��right����this�Գ�
		 */
		private void balance(Node left, Node right) {
			if (left == null || right == null)
				return;
			// ��left��β�ַ����롾this�����ַ���֮��ļ��
			int deltaLeft = x - left.rightX();
			// ��this��β�ַ����롾this�����ַ���֮��ļ��
			int deltaRight = right.x - rightX();

			int delta = Math.max(deltaLeft, deltaRight);
			int newRightX = rightX() + delta;
			right.translateX(newRightX - right.x);

			int newLeftX = x - delta - left.width;
			left.translateX(newLeftX - left.x);
		}

		private int treeHeight(Node node) {
			if (node == null) return 0;
			if (node.treeHeight != 0) return node.treeHeight;
			node.treeHeight = 1 + Math.max(
					treeHeight(node.left), treeHeight(node.right));
			return node.treeHeight;
		}

		/**
		 * ���ҽڵ�֮�����С�㼶����
		 */
		private int minLevelSpaceToRight(Node right) {
			int thisHeight = treeHeight(this);
			int rightHeight = treeHeight(right);
			int minSpace = Integer.MAX_VALUE;
			for (int i = 0; i < thisHeight && i < rightHeight; i++) {
				int space = right.levelInfo(i).leftX 
						- this.levelInfo(i).rightX;
				minSpace = Math.min(minSpace, space);
			}
			return minSpace;
		}

		private LevelInfo levelInfo(int level) {
			if (level < 0) return null;
			int levelY = y + level;
			if (level >= treeHeight(this)) return null;

			List<Node> list = new ArrayList<>();
			Queue<Node> queue = new LinkedList<>();
			queue.offer(this);

			// ��������ҳ���level�е����нڵ�
			while (!queue.isEmpty()) {
				Node node = queue.poll();
				if (levelY == node.y) {
					list.add(node);
				} else if (node.y > levelY) break;

				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}

			Node left = list.get(0);
			Node right = list.get(list.size() - 1);
			return new LevelInfo(left, right);
		}

		/**
		 * β�ַ�����һ��λ��
		 */
		public int rightX() {
			return x + width;
		}

		public void translateX(int deltaX) {
			if (deltaX == 0) return;
			x += deltaX;

			// �����LineNode
			if (btNode == null) return;

			if (left != null) {
				left.translateX(deltaX);
			}
			if (right != null) {
				right.translateX(deltaX);
			}
		}
	}

	private static class LevelInfo {
		int leftX;
		int rightX;

		public LevelInfo(Node left, Node right) {
			this.leftX = left.leftBound();
			this.rightX = right.rightBound();
		}
	}
}
