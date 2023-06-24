import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Chipo Chibbamulilo
 * @author Chikwanda Chisha
 * Sketch Class used for keeping track of changemade to the world
 */

public class Sketch {
    private TreeMap<Integer, Shape> shapeMap;
    private int ID = 0;

    public Sketch(){
        this.shapeMap = new TreeMap<>();
    }

    //only one user adds to the sketch map at a time

    /**
     * allows only one user adds to the sketch map at a time
     * @param shape
     */
    public synchronized void addShape(Shape shape){
        shapeMap.put(ID, shape);
        ID ++;
    }

    /**
     * allows only one user to remove from the sketch map at a time
     * @param id shape ID to be removed.
     */

    //only one user adds to the sketch map at a time
    public synchronized void removeShape(int id){
        shapeMap.remove(id);
    }

    /**
     * This returns the shape's (at the point p) ID number in the map
     * @param p point clicked on
     * @return ID at that point else return -1
     */
    public int getPointID(Point p) {
        for (int id : shapeMap.descendingKeySet()) {
            if (shapeMap.get(id).contains(p.x, p.y)) {
                int generalID = id;
                return generalID;
            }
        }
        return -1;
    }

    /**
     * Draws each of the shapes present in the map
     * @param g is graphics
     */
    public synchronized void draw(Graphics g) {
        // Loop over every shape in the Sketch
        for (Integer shapeID : shapeMap.keySet()) {
            shapeMap.get(shapeID).draw(g); // Draw every shape in Sketch from first put in map to last
        }
    }

    /**
     * Returns the class's shapeMap for usage elsewhere
     * @return shapemap
     */
    public Map<Integer, Shape> getShapeMap(){
        return shapeMap;
    }

    /**
     * returns a set of the shape IDs in ascending order
     * @return a set
     */
    public Set<Integer> getNaviKeySet(){
        return shapeMap.navigableKeySet();
    }
}
