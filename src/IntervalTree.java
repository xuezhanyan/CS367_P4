/////////////////////////////////////////////////////////////////////////////
// Semester: CS367 Spring 2017
// PROJECT: Program 4
// FILE: GenerateData.java, Interval.java, IntervalADT.java, IntervalNode.java,
//       IntervalNotFoundException.java, IntervalTree.java, 
//       IntervalTreeADT.java, IntervalTreeGUI.java, IntervalTreeMain.java
//
// TEAM: Team 57a import teamName
// Authors: Matthew Schmude, Xuezhan Yan
// Author1: Matthew Schmude, schmude@wisc.edu, 9074395576, Lec 002
// Author2: Xuezhan Yan, xyan56@wisc.edu, 9074973794, Lec 002
//
// ---------------- OTHER ASSISTANCE CREDITS
// Persons: Identify persons by name, relationship to you, and email.
// Describe in detail the the ideas and help they provided.
//
// Online sources: avoid web searches to solve your problems, but if you do
// search, be sure to include Web URLs and description of
// of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {
	private IntervalNode<T> root;

	/** Returns the root node of this IntervalTree. */
	@Override
	public IntervalNode<T> getRoot() {
		return root;
	}

	/**
	 * Inserts an <code>Interval</code> in the tree.
	 * 
	 * <p>Each <code>Interval</code> is stored as the data item of an
	 * <code>IntervalNode</code>.  The position of the new IntervalNode 
	 * will be the position found using the binary search algorithm.
	 * This is the same algorithm presented in BST readings and lecture
	 * examples. 
	 * 
	 * <p>Tip: Call a recursive helper function with root node.
	 * In that call, traverse the tree using the binary search algorithm.
	 * Use the comparator defined in <code>Interval</code> and create a new
	 * IntervalNode to store the new <i>interval</i> item when you reach 
	 * the end of the tree.</p>
	 * 
	 * <p>This method must also check and possibly update the maxEnd 
	 * in the IntervalNode. Recall, that <b>maxEnd</b> of a node represents 
	 * the maximum end of current node and all descendant nodes.</p>
	 * 
	 * <p>Note: the key for comparison here will be the compareTo method defined
	 *  in interval class. You will use this for the interval stored in the node to
	 *  compare it with the input interval.</p>
	 * 
	 * <p>If the start and end of the given interval match an existing 
	 * interval, throw an IllegalArgumentException.</p>
	 *  
	 * @param interval the interval (item) to insert in the tree.
	 * @throws IllegalArgumentException if interval is null or is found 
	 * to be a duplicate of an existing interval in this tree.            
	 */
	@Override
	public void insert(IntervalADT<T> interval)
					throws IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		root = insertHelper(root, interval);
			
	}
	
	/**
	 * Recursive helper method for the insert operation. 
	 * 
	 * @param root
	 * @param interval
	 */
	private IntervalNode<T> insertHelper(IntervalNode<T> root, IntervalADT<T> interval) {
		// base case
		if (root == null)
			return new IntervalNode<T> (interval);
		
		if (root.getInterval().compareTo(interval) == 0){
			throw new IllegalArgumentException();
		} else if (root.getInterval().compareTo(interval) < 0){
			// search right
			IntervalNode<T> child = insertHelper(root.getRightNode(), interval);
			root.setRightNode(child);
			// whether need to update MaxEnd
			if (child.getMaxEnd().compareTo(root.getMaxEnd()) > 0)
				root.setMaxEnd(child.getMaxEnd());
		} else if (root.getInterval().compareTo(interval) > 0){
			// search left
			IntervalNode<T> child = insertHelper(root.getLeftNode(), interval);
			root.setLeftNode(child);
			// whether need to update MaxEnd
			if (child.getMaxEnd().compareTo(root.getMaxEnd()) > 0)
				root.setMaxEnd(child.getMaxEnd());
		}
		return root;
	}

	/**
	 * Delete the node containing the specified interval in the tree.
	 * Delete operations must also update the maxEnd of interval nodes
	 * that change as a result of deletion.  
	 *  
	 * <p>Tip: call <code>deleteHelper(root)</code> with the root node.</p>
	 * 
	 * @throws IllegalArgumentException if interval is null
	 * @throws IntervalNotFoundException if the interval does not exist.
	 */
	@Override
	public void delete(IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		root = deleteHelper(root, interval);
	}

	/** 
	 * Recursive helper method for the delete operation.  
	 * 
	 * <p>Note: the maxEnd of some interval nodes may also need to change
	 * as a result of an interval's deletion.</p>
	 * 
	 * <p>Note: the key for comparison here is the start of the interval
	 * stored at each IntervalNode.</p>
	 * 
	 * <p>Tip: write a non-recursive helper method that recalculates maxEnd for 
	 * any node based on the maxEnd of its child nodes</p>
	 * 
	 * <pre>      private T recalculateMaxEnd(IntervalNode&lt;T&gt; nodeToRecalculate)</pre>
	 * 
	 * <h3>Pseudo-code for this deleteHelper method:</h3>
	 *
 	 * <ul>
     * <li>If node is null, throw IntervalNotFoundException</li>
 	 * <li>If interval is found in this node, delete it and replace it 
 	 * with leftMost in right subtree.  There are two cases:
 	 * 
 	 * <ol><li>If right child exists
 	 *     <ol><li>Replace the node's interval with the in-order successor interval. 
 	 *     <br />Tip: Be sure to code the and use the <code>getSuccessor</code> method for <code>IntervalNode</code> class.</li>
 	 *         <li>Call deleteHelper() on the in-order successor node of the right subtree.</li>
	 *         <li>Update the new maxEnd.</li>
	 *         <li>Return the node.</li>
	 *     </ol>
	 *     </li>
	 *     
	 *     <li>If right child doesn't exist, return the left child</li>
	 * </ol>
     * 
	 * <li>If interval is in the right subtree,
	 *      <ol>
	 *	    <li>Set right child to result of calling deleteHelper on right child.</li>
	 *	    <li>Update the maxEnd if necessary. </li>
	 *      <li>Return the node.</li>
	 *      </ol>
	 *      </li>
	 *
	 * <li>If interval is in the left subtree.
	 *      <ol>
	 *	    <li>Set left child to result of calling deleteHelper on left child.</li>
	 *	    <li>Update the maxEnd if necessary. </li>
	 *      <li>Return the node.</li>
	 *      </ol>
	 *      </li>
	 *  </ul>
     *
	 * @param node the interval node that is currently being checked.
	 * 
	 * @param interval the interval to delete.
	 * 
	 * @throws IllegalArgumentException if the interval is null.
	 * 
	 * @throws IntervalNotFoundException
	 *             if the interval is not null, but is not found in the tree.
	 * 
	 * @return Root of the tree after deleting the specified interval.
	 */
	@Override
	public IntervalNode<T> deleteHelper(IntervalNode<T> node,
					IntervalADT<T> interval)
					throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		// base case
		if (node == null)
			throw new IntervalNotFoundException(interval.toString());
		if (node.getInterval().compareTo(interval) == 0){
			// delete this node
			if (node.getLeftNode() == null && node.getRightNode() == null){
				// case 1: this node have no child
				return null;
			} else if (node.getLeftNode() != null && node.getRightNode() == null){
				// case 2a: this node only have left child
				return node.getLeftNode();
			} else if (node.getLeftNode() == null && node.getRightNode() != null){
				// case 2b: this node only have right child
				return node.getRightNode();
			} else{
				// case 3: this node have two child
				// Replace the node's interval with the in-order successor interval. 
				node.setInterval(node.getSuccessor().getInterval());
				// Call deleteHelper() on the in-order successor node of the right subtree.
				node.setRightNode(deleteHelper(node.getRightNode(), node.getInterval()));
			}
		} else if (node.getInterval().compareTo(interval) > 0){
			IntervalNode child = deleteHelper(node.getLeftNode(),interval);
			node.setLeftNode(child);
		} else if (node.getInterval().compareTo(interval) < 0){
			IntervalNode child = deleteHelper(node.getRightNode(),interval);
			node.setRightNode(child);
		}
		// Update the maxEnd if necessary.
		if (node.getMaxEnd().compareTo(interval.getEnd()) == 0){
			T thisEnd = node.getInterval().getEnd();

			if (node.getLeftNode() == null && node.getRightNode() == null){
				node.setMaxEnd(thisEnd);
			} else if (node.getLeftNode() != null && node.getRightNode() == null){
				if (node.getLeftNode().getMaxEnd().compareTo(thisEnd) > 0)
					node.setMaxEnd(node.getLeftNode().getMaxEnd());
				else
					node.setMaxEnd(thisEnd);
			} else if (node.getLeftNode() == null && node.getRightNode() != null){
				if (node.getRightNode().getMaxEnd().compareTo(thisEnd) > 0)
					node.setMaxEnd(node.getRightNode().getMaxEnd());
				else
					node.setMaxEnd(thisEnd);
			} else{
				if (node.getLeftNode().getMaxEnd().compareTo(thisEnd) > 0)
					if (node.getLeftNode().getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) > 0)
						node.setMaxEnd(node.getLeftNode().getMaxEnd());
					else
						node.setMaxEnd(node.getRightNode().getMaxEnd());
				else
					if (node.getRightNode().getMaxEnd().compareTo(thisEnd) > 0)
						node.setMaxEnd(node.getRightNode().getMaxEnd());
					else
						node.setMaxEnd(thisEnd);
			}
			
			
			
			
			// alternative way
//			T[] temp = (T[]) new Object[]{thisEnd, node.getLeftNode().getMaxEnd(),node.getRightNode().getMaxEnd()};
//			// remove all null elements from array
//			ArrayList<T> temp2 = new ArrayList<>();
//			for (int i = 0; i < temp.length; i++)
//				if (temp[i] != null)
//					temp2.add(temp[i]);
//			Collections.sort(temp2, new Comparator<List<T>>());
//			@Override
//			public int compare(<List<T>> o1, <List<T>>o2){
//				return 0;
//			}
			
		}
		return node;
	}

	/**
	 * Find and return a list of all intervals that overlap with the given interval. 
	 * 
	 * <p>Tip: Define a helper method for the recursive call and call it with root, 
	 * the interval, and an empty list.  Then, return the list that has been built.</p>
	 * 
	 * <pre>   private void findOverlappingHelper(IntervalNode node, IntervalADT interval, List<IntervalADT<T>> result)</pre>
	 * 
	 * <p>Pseudo-code for such a recursive findingOverlappingHelper method.</p>
	 * 
	 * <ol>
	 * <li>if node is null, return</li>
	 * <li>if node interval overlaps with the given input interval, add it to the result.</li>
	 * <li>if left subtree's max is greater than or equal to the interval's start, call findOverlappingHelper in the left subtree.</li>
	 * <li>if right subtree's max is greater than or equal to the interval's start, call findOverlappingHelper in the rightSubtree.</li>
	 * </ol>
	 *  
	 * @param interval the interval to search for overlapping
	 * 
	 * @return list of intervals that overlap with the input interval.
	 */
	@Override
	public List<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		List<IntervalADT<T>> returnList = new LinkedList<IntervalADT<T>>();
		findOverlappingHelper(root, interval, returnList);
		return returnList;
	}

	/**
	 * Recursive helper method for the findOverlapping operation. 
	 * 
	 * @param root
	 * @param interval
	 * @param returnList
	 */
	private void findOverlappingHelper(IntervalNode<T> root, IntervalADT<T> interval,
			List<IntervalADT<T>> returnList) {
		// if node is null, return
		if (root == null)
			return;
		// if node interval overlaps with the given input interval, add it to the result.
		if (root.getInterval().overlaps(interval))
			returnList.add(root.getInterval());
		// if left subtree exist and it's max is greater than or equal to the interval's start, call
		// findOverlappingHelper in the left subtree.
		if (root.getLeftNode() != null && root.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(root.getLeftNode(), interval, returnList);
		// if right subtree exist and it's max is greater than or equal to the interval's start, call
		// findOverlappingHelper in the rightSubtree.
		if (root.getRightNode() != null && root.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(root.getRightNode(), interval, returnList);
	}
	
	/**
	 * Search and return a list of all intervals containing a given point. 
	 * This method may return an empty list. 
	 * 
	 * <p>For example: if the intervals stored in the tree are:</p>
	 * <pre>
	 * p1 [5, 10]
	 * p2 [2, 18]
	 * p3 [12, 30]</pre>
	 * 
	 * <p>and the input point is 16, it will return a list containing the intervals:</p>
	 * <pre>
	 * p2 [2, 18]
	 * p3 [12, 30]</pre>
	 * 
	 * @throws IllegalArgumentException if point is null
	 * 
	 * @param point
	 *            input point to search for.
	 * @return List of intervals containing the point.
	 */
	@Override
	public List<IntervalADT<T>> searchPoint(T point) {
		if (point == null)
			throw new IllegalArgumentException();
		List<IntervalADT<T>> returnList = new LinkedList<IntervalADT<T>>();
		searchPointHelper(root, point, returnList);
		return returnList;
	}

	/**
	 * Recursive helper method for the searchPoint operation. 
	 * This helper method will traverse the part of tree and add any interval that contains the point
	 * 
	 * @param root
	 * @param point
	 * @param returnList
	 */
	private void searchPointHelper(IntervalNode<T> root, T point, List<IntervalADT<T>> returnList) {
		// better approach
		if (root == null)
			return;
		/* 
		 * if point < root's start can no search root's right subtree but if point > root's end, you
		 * can no search root's left subtree bc there might be a huge internal on left subtree
		 * contain this point
		 */
		else{
		searchPointHelper(root.getLeftNode(),point,returnList);
		// no need to search right subtree if point less than root's start
		if (point.compareTo(root.getInterval().getStart()) >= 0)
			searchPointHelper(root.getRightNode(),point,returnList);
		// first traverse to leaf and then decide whether contains
		if (root.getInterval().contains(point))
			returnList.add(root.getInterval());
		return;
		}
		
//		naive approach need to traverse every node
//		if (root == null)
//			return;
//		else{
//			searchPointHelper(root.getLeftNode(),point,returnList);
//			searchPointHelper(root.getRightNode(),point,returnList);
//			// first traverse to leaf
//			// and then decide whether contains
//			if (root.getInterval().contains(point))
//				returnList.add(root.getInterval());
//			return;
//		}	
	}

	/**
	 * Get the size of the interval tree. The size is the total number of
	 * nodes present in the tree. 
	 * 
	 * <p>Tip: Define and call a recursive helper function to calculate this.</p>
	 * 
	 * @return int number of nodes in the tree.
	 */
	@Override
	public int getSize() {
		return getSizeHelper(root);
	}

	/**
	 * Recursive helper method for the getSize operation.  
	 * @param thisNode
	 * @return
	 */
	private int getSizeHelper(IntervalNode<T> thisNode) {
		if (thisNode == null)
			return 0;
		else
			return getSizeHelper(thisNode.getLeftNode()) + getSizeHelper(thisNode.getRightNode())
					+ 1;
	}

	/**
	 * Return the height of the interval tree at the root of the tree. 
	 * 
	 * <p>Tip: Define and call a recursive helper function to calculate this for a given node.</p>
	 * 
	 * @return the height of the interval tree
	 */
	@Override
	public int getHeight() {
		return getHeightHelper(root);
	}

	/**
	 * Recursive helper method for the getHeight operation.  
	 * @param thisNode
	 * @return
	 */
	private int getHeightHelper(IntervalNode<T> thisNode) {
		if (thisNode == null)
			return 0;
		else
			return Math.max(getHeightHelper(thisNode.getLeftNode()),
					getHeightHelper(thisNode.getRightNode())) + 1;
	}

	/**
	 * Returns true if the tree contains an exact match for the start and end of the given interval.
	 * The label is not considered for this operation.
	 *  
	 * <p>Tip: Define and call a recursive helper function to call with root node 
	 * and the target interval.</p>
	 * 
	 * @param interval
	 * 				target interval for which to search the tree for. 
	 * @return boolean 
	 * 			   	representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             	if interval is null.
	 * 
	 */
	@Override
	public boolean contains(IntervalADT<T> interval) {
		if (interval == null)
			throw new IllegalArgumentException();
		return containsHelper(root, interval);
	}

	/**
	 * Recursive helper method for the contains operation.  
	 * @param root
	 * @param interval
	 * @return
	 */
	private boolean containsHelper(IntervalNode<T> root, IntervalADT<T> interval) {
		if (root == null)
			return false;
		else
			return containsHelper(root.getLeftNode(), interval)
					|| containsHelper(root.getRightNode(), interval)
					|| root.getInterval().compareTo(interval) == 0;
	}

	/**
	 * Print the statistics of the tree in the below format
	 * <pre>
	 *	-----------------------------------------
	 *	Height: 2
	 *	Size: 3
	 *	-----------------------------------------
	 *	</pre> 
	 */
	@Override
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");
	}

}
