import java.awt.*;
import java.util.ArrayList;

/**
 * @author Chikwanda Chisha
 * @author Chipo Chibbamulilo
 * Handles Editor's messages
 */

public class EditorMessages {
    ArrayList<Point> msgPoints;
    String command;
    String shapeType;
    int x1, y1, x2, y2;
    Shape shape;


    public void msgDecoder(String[] input, Editor editor) {
        command = input[0];

        //Draw command
        if (command.equals("DRAW")) {
            shapeType = input[1];

            if (!shapeType.equals("freehand")) {
                x1 = Integer.parseInt(input[2]);
                y1 = Integer.parseInt(input[3]);
                x2 = Integer.parseInt(input[4]);
                y2 = Integer.parseInt(input[5]);
            }

//          For a particular shapeType, do the action described
            switch (shapeType) {
                case "ellipse" ->{
                    Color color = new Color(Integer.parseInt(input[6]));
                    shape = new Ellipse(x1, y1, x2, y2, color);}

                case "rectangle" -> {
                    Color color = new Color(Integer.parseInt(input[6]));
                    shape = new Rectangle(x1, y1, x2, y2, color);
                }

                case "segment" -> {
                    Color color = new Color(Integer.parseInt(input[6]));
                    shape = new Segment(x1, y1, x2, y2, color);
                }

                case "freehand" -> shape = polyDecoder(input);
            }

            //Only add to map if shape is not null
            if (shape != null)
                editor.getSketch().addShape(shape);
        }

        //move command
        else if(command.equals("MOVE")){

        int ID = Integer.parseInt(input[1]);
        int dx = Integer.parseInt(input[2]);
        int dy = Integer.parseInt(input[3]);

        if (editor.getSketch().getShapeMap().get(ID) != null)
            editor.getSketch().getShapeMap().get(ID).moveBy(dx, dy); //moving shape
    }
        //recolor command
        else if(command.equals("RECOLOR")) {

        int ID = Integer.parseInt(input[1]);
        Color color = new Color(Integer.parseInt(input[2]));

        if (editor.getSketch().getShapeMap().get(ID) != null)
            editor.getSketch().getShapeMap().get(ID).setColor(color); //set shape color
        }

        //delete command
        else if(command.equals("DELETE")) {

        int ID = Integer.parseInt(input[1]);//shape's ID in map
        editor.getSketch().getShapeMap().remove(ID); // remove shape
    }

}
    public Shape polyDecoder(String[] input){
        // create a new polyline object
        shape = new Polyline(Integer.parseInt(input[2]), Integer.parseInt(input[3]), new Color(Integer.parseInt(input[input.length-1])));

        //loop through the string of coordinates to create points
        for (int i = 2; i < input.length - 2; i+=2){
            int x = Integer.parseInt(input[i]);
            int y = Integer.parseInt(input[i + 1]);
            ((Polyline) shape).points.add(new Point(x, y));
        }
        return shape;
    }

}
