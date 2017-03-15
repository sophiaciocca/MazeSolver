/*************************************************************************
  * Name         : Sophia Ciocca
  * PennKey      : sciocca
  * Recitation # : 211
  * Dependencies: MazeVertex.java, ArrayList.java, Vertex.java interface
  * 
  * Constructs a maze and includes a method to print out the maze in string form.
  * Also provides means for solving mazes.
  * 
  *****************************************************************************/

//import array list
import java.util.ArrayList;

public class Maze {
    
    //declare array of vertices & other variables
    private Vertex[] arrayOfVertices;
    private String name;
    private int N;
    private int xcoord;
    private int ycoord;
    private ArrayList<Vertex> solution;
    private boolean solutionCalculated = false;
    
    
    //MAZE CONSTRUCTOR
    public Maze(String filename) {
        
        //read in a .maze file
        //set up inStream
        In inStream = new In(filename);
        
        //read in # of vertices as N
        N = inStream.readInt();
        
        //initialize array of vertices
        arrayOfVertices = new Vertex[N];
        
        //1.) create for loop to go through & read in name, & X&Y coords
        for (int i = 0; i < N; i++) {
            
            //read in name, X/& coords
            name = inStream.readString();
            xcoord = inStream.readInt();
            ycoord = inStream.readInt();
            
            //create new Vertex w/ those values
            arrayOfVertices[i] = new MazeVertex(name, i, xcoord, ycoord);   
        }

        //2.) create while loop to read in hallways
        while (true) {
            //read in the two rooms between which there's a hallway
            int room1 = inStream.readInt();
            int room2 = inStream.readInt();
            
            //check if we're at the end; if at end of file, break
            if ((room1 == -1 && room2 == -1) || inStream.isEmpty()) {
                break;
            }
            
            //check to make sure room numbers are valid; 
            // if not, ignore, continue/start loop over w/ next values
            if (room1 < 0 || room2 < 0 || room1 > arrayOfVertices.length - 1 
                    || room2 > arrayOfVertices.length - 1) {
                continue;
            }
            
            //store room2 in room1's list of adjacent 
            if (room1 >= 0 && room1 < N && room2 >= 0 && room2 < N) {
                arrayOfVertices[room1].addEdge(arrayOfVertices[room2]);
            }
            
        }
        
    }
    
    
    //**************************METHODS**************************//
    
    
    /**
     * METHOD: getVertices (returns a vertex array)
     * return the array of vertices in the graph
     */
    public Vertex[] getVertices() {
        return arrayOfVertices;
    }
    
    
    /**
     * METHOD: toString (returns string)
     * print out the Maza data structure in same format as input files
     */
    public String toString() {
        
        //call vertex names & coordinates helper function
        String firstHalf = vertexNamesAndCoordsToString();
        
        //call edges helper function
        String secondHalf = edgesLeavingVertexToString();
        
        //combine the first and second halves
        String stringMazeTotal = firstHalf + "\n" + secondHalf;
        
        //return the result: the maze in string form
        return stringMazeTotal;
        
    } 
    
    
    /**
     * (PART 2)
     * METHOD: findPath (returns ArrayList of vertices)
     * --uses DFS to find a path through the maze
     * --only calculates the solution the first time it's called,
     *  but returns it every time
     * --return null if there is not path
     */
    public ArrayList<Vertex> findPath() {
        
        //if we've already been through findPath, then just return it
        if (solutionCalculated) {
            return solution;
        }
        
        //instantiate path
        solution = new ArrayList<Vertex>();
        
        //create boolean array of visited nodes (default: false)
        boolean[] visited = new boolean[arrayOfVertices.length];
        
        //(for testing)
        NavigateMaze.drawMaze(getVertices());
        
        //call dfs
        dfs(visited, arrayOfVertices[0], 
            arrayOfVertices[arrayOfVertices.length - 1]);
        
        //update solutionCalculated to true
        solutionCalculated = true;
        
        //return solution
        return solution;
        
    }
    
    
    //********************PRIVATE HELPER FUNCTIONS********************//
    
    
    /**
     * PRIVATE HELPER FUNCTION: vertexNamesAndCoordsToString (returns String)
     * --takes the first part of maze file -- the number of vertices & their names
     * & coordinates -- and converts it into a string
     */
    private String vertexNamesAndCoordsToString() {
        
        //make empty string
        String s = "";
        
        //add # of vertices & line break
        s += N + "\n";
        
        //for loop to add names/coords to it
        for (int i = 0; i < arrayOfVertices.length; i++) {
            
            //add vertex name, xcoord, & ycoord separated by spaces 
            s += arrayOfVertices[i].getName() + " " 
                + arrayOfVertices[i].getX() + " " 
                + arrayOfVertices[i].getY() + "\n";    
            
        }
        
        //return the string
        return s;
        
    }
    
    
    /**
     * PRIVATE HELPER FUNCTION: edgesLeavingVertexToString (returns String)
     * --takes the second part of maze file -- the edges -- 
     * and converts it into a string
     */
    private String edgesLeavingVertexToString() {
        
        //make empty string
        String s = "";
        
        //for loop to go through all vertices; 
        for (int j = 0; j < arrayOfVertices.length; j++) {
            
            //if it has any edges...
            if (arrayOfVertices[j].getDegree() > 0) {
                
                //go through its edges
                for (int k = 0; k < arrayOfVertices[j].getDegree(); k++) {
                    
                    //declare an array list
                    ArrayList<Vertex> newArrayList = 
                        arrayOfVertices[j].getAdjacent();
                    
                    //add to string: the vertex itself + space + edge + line break
                    s += j + " " + newArrayList.get(k).getID() + "\n";         
                    
                } 
            }   
        }
        
        //add -1, -1 after all the edges
        s += "-1 -1";
        
        return s;   
    }
    
    /**
     * (PART 2)
     * PRIVATE FUNCTION: dfs (takes boolean array, returns an ArrayList<Vertex>)
     * Recurses through a maze, using depth-first search to find a solution.
     */
    private ArrayList<Vertex> dfs(boolean[] visited, Vertex start, Vertex target) {
        
        //TEST VALUES: if start/end are out of bounds of array of vertices
        if (start.getID() < 0) {
            return null;
        }
        
        if (target.getID() > arrayOfVertices.length) {
            return null;
        }
        
        
        //base case: if start = target, return start vertex right there.
        if (start == target) {
            solution.add(0, start);
            return solution;
        }
        
        //has this vertex been visited before? yes? then just return. if no, 
        if (visited[start.getID()]) {
            return null;
        }
        
        //since it hasn't been visited before, mark it as visited
        visited[start.getID()] = true;
        
        //does it have kids? no? return.
        if (start.getDegree() == 0) {
            return null;
        }
        
        //create ArrayList of adjacent vertices to the given start
        ArrayList<Vertex> adjacents = start.getAdjacent();
        
        //go through its edges, for loop. to go through edges
        for (int i = 0; i < start.getDegree(); i++) {
            
            //(from testing)
            NavigateMaze.advance(start.getID(), adjacents.get(i).getID());
            
            //do the dfs; if it's not equal to null 
               //(i.e. we found the target, then add it to path
            if (dfs(visited, adjacents.get(i), target) != null) {
                //then add to path
                solution.add(0, start); 
                return solution;
            }
            
            //(from testing)
            else {
                NavigateMaze.backtrack(adjacents.get(i).getID(), start.getID());
            }
            
        }   
        
        //return null
        return null;
    }
    
    
    //**************************MAIN**************************//
    
    //MAIN (for testing)
    public static void main(String[] args) {  
        
        //read in filename from user
        String filenameInput = args[0];
        
        //create new maze/graph
        Maze newMaze = new Maze(filenameInput);
        
        //call toString
        //String stringMaze = newMaze.toString();
        
        //print the string maze
        //System.out.println(stringMaze);
        
        //call findPath
        //System.out.println(newMaze.findPath());
        
        for (Vertex v : newMaze.findPath()) System.out.print(v.getName());
        
        //print solution?
        //System.out.println(newMaze.solution);
        
        
    }
    
    
}