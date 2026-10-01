package es.upm.aled.lab2.kinematics;

import es.upm.aled.lab2.gui.Node;

/**
 * This class implements a forward kinematics algorithm using recursion. It
 * expects a tree of Segments (defined by its length and angle with respect to
 * the previous Segment in the tree) and returns a tree of Nodes (defined by
 * their absolute coordinates in a 2-dimensional space).
 * 
 * @author rgarciacarmona
 */
public class ForwardKinematics {

	/**
	 * Returns a tree of Nodes to be used by SkeletonPanel to draw the position of
	 * an exoskeleton. This method is the public facade to a recursive method that
	 * builds the result from a tree of Segments defined by their angle and length,
	 * and the relationship between them (which Segment is children of which).
	 * 
	 * @param root    The root of the tree of Segments.
	 * @param originX The X coordinate for the origin point of the tree.
	 * @param originY The Y coordinate for the origin point of the tree.
	 * @return The tree of Nodes that represent the exoskeleton position in absolute
	 *         coordinates.
	 */
	// Public method: returns the root of the position tree
	public static Node computePositions(Segment root, double originX, double originY) {
		double accumulatedAngle = 0;
		return computePositions(root, originX, originY,accumulatedAngle);
		 
	}

	// Private helper method that implements the recursive algorithm
	private static Node computePositions(Segment link, double baseX, double baseY, double accumulatedAngle) {
		long startTime = System.nanoTime();
		
	// 1. CÓDIGO GENERAL: Se ejecuta en todas las llamadas
		
		// Actualizamos el ángulo acumulado sumando el ángulo del segmento actual
		accumulatedAngle += link.getAngle();
		
		//Calculamos las cordenadas del nodo al final de este segmento X e Y
		double x1 = baseX + link.getLength() * Math.cos(accumulatedAngle);
		double y1 = baseY + link.getLength() * Math.sin(accumulatedAngle);
		
		//Construimos el nuevo nodo
		Node currentNode = new Node(x1,y1);
		
	// 2. CÓDIGO RECURSIVO / CASO BASE
		
		//Si la lista está vacía (sin hijos) no entra en el bucle - CASO BASE!!
		for(Segment childSegment : link.getChildren()) {
			
			//llama a la recursividad
			Node childNode = computePositions(childSegment, baseX, baseY, accumulatedAngle);
			// Añadimos el nodo a la lista de hijos de su nodoPadre
			currentNode.addChild(childNode);
		}
		
		long runningTime = System.nanoTime()- startTime;
		System.out.println("Tiempo de computePositions para un segmento con "
		+ link.getChildren().size() + " hijos: "
		+ runningTime + " nanosegundos");
		
	// Devolvemos el nodo (no la lista de hijos ni un Segment)
	return currentNode;	
		
	}
}