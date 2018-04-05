import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Screen extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    public static boolean outlines = true;
    public static double yView = -0.9,
            xView = 0,
            reticle = 4,
            xRotSpeed = 900,
            yRotSpeed = 2200,
            sunPos = 1,
            zoom = 1000,
            minZoom = 500,
            maxZoom = 2500,
            mouseX = 0,
            mouseY = 0,
            movementSpeed = 0.1,
            drawFPS = 0,
            MaxFPS = 1000,
            SleepTime = 1000.0 / MaxFPS,
            LastRefresh = 0,
            StartTime = System.currentTimeMillis(),
            LastFPSCheck = 0,
            Checks = 0;
    public static double[] viewFrom = {15, 5, 10},
            viewTo = {0, 0, 0},
            light = {1, 1, 1};
    public static int x,y,z,TotalFrames,CurrentFrame;
    public static int[] newOrder;
    public static boolean[] cues = new boolean[4];
    public static ArrayList<DDDgon> dddgons = new ArrayList<DDDgon>();
    public static ArrayList<Cube> cubes = new ArrayList<Cube>();
    public static PolygonObject selectedPolygon = null;
    public static Robot r;
    public static int twinkleCount = 0;
    public static boolean twinkleState = true;
    public static double sunSpeed = .005;

    public Screen(int x, int y, int z, int t) {
        this.addKeyListener(this);
        setFocusable(true);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        invisibleMouse();

        TotalFrames = t;
        CurrentFrame = 0;
        this.x = x;
        this.y = y;
        this.z = z;

        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++)
                for (int k = 0; k < z; k++)
                    cubes.add(new Cube(i*3+18, j*3-5, k*3, 2, 2, 2, Color.GRAY, 0, false, i, j, k, true));

        for(int i = 0; i < cubes.size(); i++)
            System.out.println(i + 1 + ": " + cubes.get(i).x + ", " + cubes.get(i).y + ", " + cubes.get(i).z);
    }

    public void jumpToFrame(int f){
        int index = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if(HyperSweeper.win == null || HyperSweeper.win != null && !(HyperSweeper.hyperBoard.board[i][j][k][CurrentFrame] instanceof Bomb)) {
                        Block testBlock = HyperSweeper.hyperBoard.board[i][j][k][f];
                        cubes.get(index).remove();
                        cubes.add(index, new Cube(i * 3 + 18, j * 3 - 5, k * 3, 2, 2, 2, testBlock.isFlag() ? Color.red : testBlock.isQuestion() ? Color.yellow : Color.gray, testBlock.isFlag() ? 1 : testBlock.isQuestion() ? 2 : 0, !testBlock.isHidden(), i, j, k, testBlock.getClass() == Bomb.class));
                    }
                    index++;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.blue.lightGray);
        g.fillRect(0, 0, (int)HyperSweeper.screenSize.getWidth(), (int)HyperSweeper.screenSize.getHeight());

        controlCamera();

        Calculator.setPredeterminedInfo();

        controlSunAndLight();

        for (int i = 0; i < dddgons.size(); i++) {
            dddgons.get(i).updatePolygon();
        }

        setOrder();

        setSelectedPolygon();

        for (int i = 0; i < newOrder.length; i++) {
            dddgons.get(newOrder[i]).drawable.draw(g);
        }

        drawReticle(g);

        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        g.drawString("Frame " + (CurrentFrame+1),10,35);
        g.drawString((HyperSweeper.hyperBoard.getBombs()) + " bombs",10,HyperSweeper.screenSize.height-10);

        if(HyperSweeper.win != null && !HyperSweeper.win) {
            if(twinkleCount == 75) {
                twinkleState = !twinkleState;
                int index = 0;
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        for (int k = 0; k < z; k++) {
                            if(HyperSweeper.hyperBoard.board[i][j][k][CurrentFrame] instanceof Bomb) {
                                cubes.get(index).remove();
                                cubes.add(index, new Cube(i * 3 + 18, j * 3 - 5, k * 3, 2, 2, 2, Math.random() > .75 ? Color.ORANGE : index % 2 == (twinkleState ? 1 : 0) ? Color.RED : Color.YELLOW, 0, true, i, j, k, true));
                            }
                            index++;
                        }
                    }
                }
                twinkleCount = 0;
            }
            else{
                twinkleCount++;
            }
        }

        sleepRefresh();
    }

    public void drawReticle(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int)(HyperSweeper.screenSize.getWidth() / 2 - reticle),
                (int)(HyperSweeper.screenSize.getHeight() / 2),
                (int)(HyperSweeper.screenSize.getWidth() / 2 + reticle),
                (int)(HyperSweeper.screenSize.getHeight() / 2));

        g.drawLine((int)(HyperSweeper.screenSize.getWidth() / 2),
                (int)(HyperSweeper.screenSize.getHeight() / 2 - reticle),
                (int)(HyperSweeper.screenSize.getWidth() / 2),
                (int)(HyperSweeper.screenSize.getHeight() / 2 + reticle));
    }

    public void setSelectedPolygon() {
        selectedPolygon = null;
        for (int i = newOrder.length - 1; i >= 0; i--) {
            if (dddgons.get(newOrder[i]).drawable.mouseOver() &&
                    dddgons.get(newOrder[i]).draw &&
                    dddgons.get(newOrder[i]).drawable.visible &&
                    !dddgons.get(newOrder[i]).associatedCube.seeThru) {

                selectedPolygon = dddgons.get(newOrder[i]).drawable;
                break;
            }
        }
    }

    public void controlSunAndLight() {
        sunPos += sunSpeed;
        double mapSize = 50 * 2;
        light[0] = mapSize / 2 - (mapSize / 2 + Math.cos(sunPos) * mapSize * 10);
        light[1] = mapSize / 2 - (mapSize / 2 + Math.sin(sunPos) * mapSize * 10);
        light[2] = -200;
    }

    public void invisibleMouse() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");
        setCursor(invisibleCursor);
    }

    public void updateCamera() {
        double r = Math.sqrt(1 - (yView * yView));
        viewTo[0] = viewFrom[0] + r * Math.cos(xView);
        viewTo[1] = viewFrom[1] + r * Math.sin(xView);
        viewTo[2] = viewFrom[2] + yView;
    }

    public void moveTo(double x, double y, double z) {
        viewFrom[0] = x;
        viewFrom[1] = y;
        viewFrom[2] = z;
        updateCamera();
    }

    public void setOrder() {
        double[] k = new double[dddgons.size()];
        newOrder = new int[dddgons.size()];

        for (int i = 0; i < dddgons.size(); i++) {
            k[i] = dddgons.get(i).avgDist;
            newOrder[i] = i;
        }

        double temp1;
        int temp2;

        for (int i = 0; i < k.length - 1; i++) {
            for (int j = 0; j < k.length - 1; j++) {
                if (k[j] < k[j + 1]) {
                    temp1 = k[j];
                    temp2 = newOrder[j];
                    newOrder[j] = newOrder[j + 1];
                    k[j] = k[j + 1];

                    newOrder[j + 1] = temp2;
                    k[j + 1] = temp1;
                }
            }
        }
    }

    public void sleepRefresh() {
        long timeSLU = (long)(System.currentTimeMillis() - LastRefresh);

        Checks++;
        if (Checks >= 15) {
            drawFPS = Checks / ((System.currentTimeMillis() - LastFPSCheck) / 1000.0);
            LastFPSCheck = System.currentTimeMillis();
            Checks = 0;
        }

        if (timeSLU < 1000.0 / MaxFPS) {
            try {
                Thread.sleep((long)(1000.0 / MaxFPS - timeSLU));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LastRefresh = System.currentTimeMillis();

        repaint();
    }

    public void controlCamera() {
        Vector view = new Vector(viewTo[0] - viewFrom[0], viewTo[1] - viewFrom[1], viewTo[2] - viewFrom[2]);
        double xMove = 0, yMove = 0, zMove = 0;
        Vector vertical = new Vector(0, 0, 1);
        Vector sideView = view.cross(vertical);

        if (cues[0]) {
            xMove += view.x;
            yMove += view.y;
            zMove += view.z;
        }
        if (cues[1]) {
            xMove += sideView.x;
            yMove += sideView.y;
            zMove += sideView.z;
        }
        if (cues[2]) {
            xMove -= view.x;
            yMove -= view.y;
            zMove -= view.z;
        }
        if (cues[3]) {
            xMove -= sideView.x;
            yMove -= sideView.y;
            zMove -= sideView.z;
        }

        Vector moveVector = new Vector(xMove, yMove, zMove);
        moveTo(viewFrom[0] + moveVector.x * movementSpeed, viewFrom[1] + moveVector.y * movementSpeed, viewFrom[2] + moveVector.z * movementSpeed);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            cues[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            cues[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            cues[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            cues[3] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if(CurrentFrame < TotalFrames-1){
                CurrentFrame++;
                jumpToFrame(CurrentFrame);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(CurrentFrame > 0){
                CurrentFrame--;
                jumpToFrame(CurrentFrame);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_O) {
            outlines = !outlines;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            cues[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            cues[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            cues[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            cues[3] = false;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getUnitsToScroll() > 0) {
            if (zoom > minZoom) {
                zoom -= 25 * e.getUnitsToScroll();
            }
        } else {
            if (zoom < maxZoom) {
                zoom -= 25 * e.getUnitsToScroll();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMovement(e.getX(), e.getY());
        mouseX = e.getX();
        mouseY = e.getY();
        centerMouse();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMovement(e.getX(), e.getY());
        mouseX = e.getX();
        mouseY = e.getY();
        centerMouse();
    }

    public void centerMouse() {
        try {
            r = new Robot();
            r.mouseMove((int)HyperSweeper.screenSize.getWidth() / 2, (int)HyperSweeper.screenSize.getHeight() / 2);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void mouseMovement(int newMouseX, int newMouseY) {
        double difX = (newMouseX - HyperSweeper.screenSize.getWidth() / 2);
        double difY = (newMouseY - HyperSweeper.screenSize.getHeight() / 2);
        difY *= 6 - Math.abs(yView) * 5;
        yView -= difY / yRotSpeed;
        xView += difX / xRotSpeed;

        if (yView > 0.999) {
            yView = 0.999;
        }

        if (yView < -0.999) {
            yView = -0.999;
        }

        updateCamera();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && HyperSweeper.win == null) {
            if (selectedPolygon != null && selectedPolygon.getAssociatedCube().c != Color.red) {
                Cube temp = selectedPolygon.getAssociatedCube();
                Boolean gameStatus = HyperSweeper.hyperBoard.reveal(temp.relativeCoords[0],temp.relativeCoords[1],temp.relativeCoords[2],CurrentFrame);
                if(gameStatus == null) {
                    jumpToFrame(CurrentFrame);
                }
                else if(gameStatus){
                    HyperSweeper.win = true;

                    int index = 0;
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            for (int k = 0; k < z; k++) {
                                cubes.get(index).remove();
                                cubes.add(index , new Cube(i*3+18, j*3-5, k*3, 2, 2, 2, Color.GREEN, 0,false,i,j,k,false));
                                index++;
                            }
                        }
                    }

                    System.out.println("You Win!");
                    jumpToFrame(CurrentFrame);
                }
                else{
                    HyperSweeper.win = false;
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            for (int k = 0; k < z; k++) {
                                if(HyperSweeper.hyperBoard.board[i][j][k][CurrentFrame] instanceof Bomb)
                                    HyperSweeper.hyperBoard.board[i][j][k][CurrentFrame].setHidden(false);
                            }
                        }
                    }

                    System.out.println("\n\nYou Suck!");
                    jumpToFrame(CurrentFrame);
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON3 && HyperSweeper.win == null) {
            Cube temp = selectedPolygon.getAssociatedCube();
            if (selectedPolygon.getAssociatedCube().clickCount % 3 == 0) {
                HyperSweeper.hyperBoard.flag(temp.relativeCoords[0],temp.relativeCoords[1],temp.relativeCoords[2],CurrentFrame);
                selectedPolygon.getAssociatedCube().changeColor(Color.RED);
            }
            if (selectedPolygon.getAssociatedCube().clickCount % 3 == 1) {
                HyperSweeper.hyperBoard.question(temp.relativeCoords[0],temp.relativeCoords[1],temp.relativeCoords[2],CurrentFrame);
                selectedPolygon.getAssociatedCube().changeColor(Color.YELLOW);
            }
            if (selectedPolygon.getAssociatedCube().clickCount % 3 == 2) {
                HyperSweeper.hyperBoard.question(temp.relativeCoords[0],temp.relativeCoords[1],temp.relativeCoords[2],CurrentFrame);
                selectedPolygon.getAssociatedCube().changeColor(Color.GRAY);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}
