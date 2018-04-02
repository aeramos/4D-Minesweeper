import java.awt.*;
import java.util.ArrayList;

public class Cube {
    int clickCount = 0;
    double x, y, z, width, length, height, rotation = Math.PI * 0.75;
    double[] rotAdd = new double[4];
    Color c;
    double x1, x2, x3, x4, y1, y2, y3, y4;
    public boolean seeThru, mine;
    ArrayList<DDDgon> polys = new ArrayList<DDDgon>();
    double[] angle;
    int[] relativeCoords = new int[3];

    public Cube(double x, double y, double z, double width, double length, double height, Color c, int cc, boolean transparent, int i, int j, int k, boolean isMine) {
        polys.add(new DDDgon(new double[]{x, x + width, x + width, x}, new double[]{y, y, y + length, y + length}, new double[]{z, z, z, z}, c, transparent, this));
        Screen.dddgons.add(polys.get(0));
        polys.add(new DDDgon(new double[]{x, x + width, x + width, x}, new double[]{y, y, y + length, y + length}, new double[]{z + height, z + height, z + height, z + height}, c, transparent, this));
        Screen.dddgons.add(polys.get(1));
        polys.add(new DDDgon(new double[]{x, x, x + width, x + width}, new double[]{y, y, y, y}, new double[]{z, z + height, z + height, z}, c, transparent, this));
        Screen.dddgons.add(polys.get(2));
        polys.add(new DDDgon(new double[]{x + width, x + width, x + width, x + width}, new double[]{y, y, y + length, y + length}, new double[]{z, z + height, z + height, z}, c, transparent, this));
        Screen.dddgons.add(polys.get(3));
        polys.add(new DDDgon(new double[]{x, x, x + width, x + width}, new double[]{y + length, y + length, y + length, y + length}, new double[]{z, z + height, z + height, z}, c, transparent, this));
        Screen.dddgons.add(polys.get(4));
        polys.add(new DDDgon(new double[]{x, x, x, x}, new double[]{y, y, y + length, y + length}, new double[]{z, z + height, z + height, z}, c, transparent, this));
        Screen.dddgons.add(polys.get(5));
        mine = isMine;
        if(mine){
            polys.add(new DDDgon(new double[]{x + .5, x + width - .5, x + width - .5}, new double[]{y + .5, y + length - .5, y + .5}, new double[]{z + .5, z + .5, z + height - .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(6));
            polys.add(new DDDgon(new double[]{x + .5, x + width - .5, x + .5}, new double[]{y + .5, y + length - .5, y + length - .5}, new double[]{z + .5, z + .5, z + height - .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(7));
            polys.add(new DDDgon(new double[]{x + .5, x + .5, x + width - .5}, new double[]{y + .5, y + length - .5, y + .5}, new double[]{z + .5, z + height - .5, z + height - .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(8));
            polys.add(new DDDgon(new double[]{x + .5, x + width - .5, x + width - .5}, new double[]{y + length - .5, y + length - .5, y + .5}, new double[]{z + height - .5, z + .5, z + height - .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(9));
            polys.add(new DDDgon(new double[]{x + width - .5, x + .5, x + .5}, new double[]{y + length - .5, y + .5, y + length - .5}, new double[]{z + height - .5, z + height - .5, z + .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(10));
            polys.add(new DDDgon(new double[]{x + width - .5, x + .5, x + width - .5}, new double[]{y + length - .5, y + .5, y + .5}, new double[]{z + height - .5, z + height - .5, z + .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(11));
            polys.add(new DDDgon(new double[]{x + width - .5, x + width - .5, x + .5}, new double[]{y + length - .5, y + .5, y + length - .5}, new double[]{z + height - .5, z + .5, z + .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(12));
            polys.add(new DDDgon(new double[]{x + width - .5, x + .5, x + .5}, new double[]{y + .5, y + .5, y + length - .5}, new double[]{z + .5, z + height - .5, z + .5}, Color.RED, false, this));
            Screen.dddgons.add(polys.get(13));
        }

        seeThru = transparent;
        relativeCoords[0] = i;
        relativeCoords[1] = j;
        relativeCoords[2] = k;

        this.c = c;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.length = length;
        this.height = height;

        clickCount = cc;

        setRotAdd();
        updatePoly();
    }

    void setRotAdd() {
        angle = new double[4];

        double xdif = -width / 2 + 0.00001;
        double ydif = -length / 2 + 0.00001;

        angle[0] = Math.atan(ydif / xdif);

        if (xdif < 0) {
            angle[0] += Math.PI;
        }



        xdif = width / 2 + 0.00001;
        ydif = -length / 2 + 0.00001;

        angle[1] = Math.atan(ydif / xdif);

        if (xdif < 0) {
            angle[1] += Math.PI;
        }




        xdif = width / 2 + 0.00001;
        ydif = length / 2 + 0.00001;

        angle[2] = Math.atan(ydif / xdif);

        if (xdif < 0) {
            angle[2] += Math.PI;
        }





        xdif = -width / 2 + 0.00001;
        ydif = length / 2 + 0.00001;

        angle[3] = Math.atan(ydif / xdif);

        if (xdif < 0) {
            angle[3] += Math.PI;
        }




        rotAdd[0] = angle[0] + 0.25 * Math.PI;
        rotAdd[1] = angle[1] + 0.25 * Math.PI;
        rotAdd[2] = angle[2] + 0.25 * Math.PI;
        rotAdd[3] = angle[3] + 0.25 * Math.PI;
    }

    void updateDirection(double toX, double toY) {
        double xdif = toX - (x + width / 2) + 0.00001;
        double ydif = toY - (y + length / 2) + 0.00001;

        double anglet = Math.atan(ydif / xdif) + 0.75 * Math.PI;

        if (xdif < 0) {
            anglet += Math.PI;
        }

        rotation = anglet;
        updatePoly();
    }

    void updatePoly() {
        for (int i = 0; i < polys.size(); i++) {
            Screen.dddgons.add(polys.get(i));
            Screen.dddgons.remove(polys.get(i));
        }

        double radius = Math.sqrt(width * width + length * length);

        x1 = x + width * 0.5 + radius * 0.5 * Math.cos(rotation + rotAdd[0]);
        x2 = x + width * 0.5 + radius * 0.5 * Math.cos(rotation + rotAdd[1]);
        x3 = x + width * 0.5 + radius * 0.5 * Math.cos(rotation + rotAdd[2]);
        x4 = x + width * 0.5 + radius * 0.5 * Math.cos(rotation + rotAdd[3]);

        y1 = y + length * 0.5 + radius * 0.5 * Math.sin(rotation + rotAdd[0]);
        y2 = y + length * 0.5 + radius * 0.5 * Math.sin(rotation + rotAdd[1]);
        y3 = y + length * 0.5 + radius * 0.5 * Math.sin(rotation + rotAdd[2]);
        y4 = y + length * 0.5 + radius * 0.5 * Math.sin(rotation + rotAdd[3]);

        polys.get(0).x = new double[]{x1, x2, x3, x4};
        polys.get(0).y = new double[]{y1, y2, y3, y4};
        polys.get(0).z = new double[]{z, z, z, z};

        polys.get(1).x = new double[]{x4, x3, x2, x1};
        polys.get(1).y = new double[]{y4, y3, y2, y1};
        polys.get(1).z = new double[]{z + height, z + height, z + height, z + height};

        polys.get(2).x = new double[]{x1, x1, x2, x2};
        polys.get(2).y = new double[]{y1, y1, y2, y2};
        polys.get(2).z = new double[]{z, z + height, z + height, z};

        polys.get(3).x = new double[]{x2, x2, x3, x3};
        polys.get(3).y = new double[]{y2, y2, y3, y3};
        polys.get(3).z = new double[]{z, z + height, z + height, z};

        polys.get(4).x = new double[]{x3, x3, x4, x4};
        polys.get(4).y = new double[]{y3, y3, y4, y4};
        polys.get(4).z = new double[]{z, z + height, z + height, z};

        polys.get(5).x = new double[]{x4, x4, x1, x1};
        polys.get(5).y = new double[]{y4, y4, y1, y1};
        polys.get(5).z = new double[]{z, z + height, z + height, z};
    }

    public void changeColor(Color c) {
        int prevIndex = Screen.cubes.indexOf(this);
        remove();
        Screen.cubes.add(prevIndex, new Cube(x, y, z, width, length, height, c, clickCount + 1, false, relativeCoords[0], relativeCoords[1], relativeCoords[2], mine));
    }

    public void remove(){
        for (int i = 0; i < polys.size(); i++)
            Screen.dddgons.remove(polys.get(i));
        Screen.cubes.remove(this);
    }
}