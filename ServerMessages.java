import java.awt.*;

/**
 * @author Chikwanda Chisha
 * @author Chipo Chibbamulilo
 * Decoding Server's received Messages
 */
public class ServerMessages {
    private String command;
    private String shapeType;
    private int x1, y1, x2, y2;
    private Shape shape;

    /**
     * Decodes the message received and makes appropriate changes to sketch map
     * @param input
     * @param server
     */
    public void msgDecoder(String[] input, SketchServer server){
        command = input[0];

        //draw command
        switch (command) {
            case "DRAW" -> {
                shapeType = input[1];
                if (!shapeType.equals("freehand")) {
                    x1 = Integer.parseInt(input[2]);
                    y1 = Integer.parseInt(input[3]);
                    x2 = Integer.parseInt(input[4]);
                    y2 = Integer.parseInt(input[5]);
                }

                // For particular shapeType, do action described.
                switch (shapeType) {

                    case "ellipse" -> {
                        Color color = new Color(Integer.parseInt(input[6]));
                        shape = new Ellipse(x1, y1, x2, y2, color);
                        //           System.out.println(shape);//debugging statement
                    }
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

                //only add to map if shape is not null
                if (shape != null)
                    server.getSketch().addShape(shape);
            }

            // move command
            case "MOVE" -> {
                int ID = Integer.parseInt(input[1]);
                int dx = Integer.parseInt(input[2]);
                int dy = Integer.parseInt(input[3]);

                if (server.getSketch().getShapeMap().get(ID) != null)
                    server.getSketch().getShapeMap().get(ID).moveBy(dx, dy); // move the shape

            }


            // recolor command
            case "RECOLOR" -> {
                int ID = Integer.parseInt(input[1]);
                Color color = new Color(Integer.parseInt(input[2]));
                if (server.getSketch().getShapeMap().get(ID) != null)
                    server.getSketch().getShapeMap().get(ID).setColor(color); //set shape color

            }


            // delete command
            case "DELETE" -> {
                int ID = Integer.parseInt(input[1]);
                server.getSketch().getShapeMap().remove(ID); // remove shape

            }
        }

    }

    /**
     * decoders the points in the polyline and creates a new shape of type polyline
     * @param input is the already split line read
     * @return shape
     */
    public Shape polyDecoder(String[] input){
        // create new polyline shape
        shape = new Polyline(Integer.parseInt(input[2]), Integer.parseInt(input[3]), new Color(Integer.parseInt(input[input.length-1])));

        // loop over string of coordinates and create points
        for (int i = 2; i < input.length - 2; i+=2){
            int x = Integer.parseInt(input[i]);
            int y = Integer.parseInt(input[i + 1]);
            ((Polyline) shape).points.add(new Point(x, y));
        }
        return shape;
    }


}
