import java.awt.*;

public class PolygonObject {
    Polygon p;
    Color c;
    Cube associatedCube;
    DDDgon associatedGon;
    boolean draw = true, visible = true, seeThrough;
    double lighting = 1;

    public PolygonObject(double[] x, double[] y, Color color, int n, boolean sT, Cube associated, DDDgon associate) {
        p = new Polygon();
        for (int i = 0; i < x.length; i++) {
            p.addPoint((int)x[i], (int)y[i]);
        }
        c = color;
        seeThrough = sT;
        associatedCube = associated;
        associatedGon = associate;
    }

    public Cube getAssociatedCube() {
        return associatedCube;
    }

    public void updatePolygon(double[] x, double[] y) {
        p.reset();
        for (int i = 0; i < x.length; i++) {
            p.xpoints[i] = (int)x[i];
            p.ypoints[i] = (int)y[i];
            p.npoints = x.length;
        }
    }

    public void draw(Graphics g) {
        if (draw && visible) {
            g.setColor(new Color((int)(c.getRed() * lighting), (int)(c.getGreen() * lighting), (int)(c.getBlue() * lighting)));
            if (!seeThrough)
                g.fillPolygon(p);
            else if(!associatedGon.notDummy){
                g.setFont(new Font("Comic Sans MS", Font.PLAIN, (int)(600 / associatedGon.getDist())));
                g.setColor(Color.blue);
                int bCount = HyperSweeper.hyperBoard.findBombNumber(associatedCube.relativeCoords[0], associatedCube.relativeCoords[1], associatedCube.relativeCoords[2], Screen.CurrentFrame);
                if(bCount != 0)
                    g.drawString("" + bCount, Calculator.avgArr(p.xpoints), Calculator.avgArr(p.ypoints));
            }
            if (Screen.outlines)
                g.setColor(new Color(0, 0, 0));


            if (Screen.selectedPolygon == this) {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillPolygon(p);
            }
        }
    }

    public boolean mouseOver() {
        return p.contains(HyperSweeper.screenSize.getWidth() / 2, HyperSweeper.screenSize.getHeight() / 2);
    }
}
