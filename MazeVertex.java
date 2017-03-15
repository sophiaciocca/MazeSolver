/*************************************************************************
  * Name         : Sophia Ciocca
  * PennKey      : sciocca
  * Recitation # : 211
  * Dependencies: ArrayList.java, and implements Vertex.java interface
  * 
  * Implements the Vertex.java interface, provides methods for what can be done
  * with a MazeVertex.
  * 
  *****************************************************************************/

import java.util.ArrayList; // we need the ArrayList class

public class MazeVertex implements Vertex {
    
    // INSTANCE VARIABLES:
    private String vertexName;
    private int vertexID;
    private int vertexXcoord;
    private int vertexYcoord;
    private ArrayList<Vertex> arrayListOfVertexEdges;
    

    // CONSTRUCTOR:
    public MazeVertex(String name, int id, int xcoord, int ycoord) {
        vertexName = name;
        vertexID = id;
        vertexXcoord = xcoord;
        vertexYcoord = ycoord;  
        arrayListOfVertexEdges = new ArrayList<Vertex>();
    }


    
 //**************************METHODS**************************//
    
    /**
     * METHOD: getName (returns string)
     * Returns the name of the vertex.
     * @return name of the vertex
     */
    public String getName() {
        //return the vertex's name
        return vertexName;
    }

    
    /**
     * METHOD: getID (returns int)
     * Returns the numeric ID of the vertex
     * @return numeric ID of the vertex
     */
    public int getID() {
        //return the vertex's ID
        return vertexID;
    }

    
    /**
     * METHOD: getX (returns int)
     * Returns the x coordinate of the vertex
     * @return x coordinate of the vertex
     */
    public int getX() {
        //return the vertex's X coordinate
        return vertexXcoord;
    }

    
    /**
     * METHOD: getY (returns int)
     * Returns the y coordinate of the vertex
     * @return y coordinate of the vertex
     */
    public int getY() {
        //return the vertex's Y coordinate
        return vertexYcoord;
    }

    
    /**
     * METHOD: addEdge (put in a vertex, returns boolean)
     * Add vertex v to the list of vertices adjacent to this one
     * @param v the adjacent vertex
     * @return true if the vertex was added successfully, false otherwise
     */
    public boolean addEdge(Vertex v) {   
        //call AND return the add method; will do true if added, false if not
        return arrayListOfVertexEdges.add(v);
    }

    
    /**
     * METHOD: getDegree (returns int)
     * Returns the number of edges leaving this vertex
     * @return the number of edges leaving this vertex
     */
    public int getDegree() { 
        //return size of the ArrayList (which is the # of edges leaving vertex)
        return arrayListOfVertexEdges.size();    
    }

    
    /**
     * METHOD: getAdjacent (returns ArrayList of vertices)
     * Returns the list of vertices directly reachable from this one
     * @return the list of vertices directly reachable from this one
     *         or an empty list if there are none
     */
    public ArrayList<Vertex> getAdjacent() {
        //just return the array list of vertex edges - all the adjacent vertices
        return arrayListOfVertexEdges;      
    }
    
    
    /**
     * METHOD: isAdjacentTo (put in vertex, returns boolean)
     * Returns true if there is an edge from this vertex to v
     * @param v the vertex we are looking for
     * @return true if there is an edge from this vertex to v
     */
    public boolean isAdjacentTo(Vertex v) {
        
        //use contains function - if list of edges contains the vertex v, return true
        if (arrayListOfVertexEdges.contains(v)) {
            return true;
        }
        
        //if list of edges doesn't contain the vertex v, return false
        return false;      
    }

    
    /**
     * METHOD: isAdjacentTo (put in ID, returns boolean)
     * Returns true if there is an edge from this vertex to v
     * @param id the id of the vertex we are looking for
     * @return true if there is an edge from to vertex id
     */
    public boolean isAdjacentTo(int id) {    
        
        //use foreach loop to access each element of array list in order
        for (Vertex v : arrayListOfVertexEdges) {
            
            //if the vertex's ID is the ID we're looking for, return true
            if (v.getID() == id) {
                return true;
            }

        }
        
        //if we never found the vertex ID we're looking for, return false;
        return false;
    }
    
}
