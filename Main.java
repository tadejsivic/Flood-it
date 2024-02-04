import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class Main {
    public static Tile tiles[][] = new Tile[15][15];

    public static HashSet<Tile> activeTiles = new HashSet<>();

    public static int offset = 36;
    public static void main(String[] args) {
        
        createTileBoard();

        Frame f = new Frame("Flood It");   
        f.add(new MyCanvas());    
        f.setLayout(null);    
        f.setSize(600, 600 + offset);    
        f.setVisible(true);    
        
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });
    }

    static void createTileBoard() {
        for(int i=0; i<tiles.length; i++){
            for (int j=0; j<tiles[0].length; j++){
                int result = (int)(Math.random()*5);
                tiles[i][j] = new Tile();
                switch (result){
                    case 0 : tiles[i][j].color = Color.RED; break;
                    case 1 : tiles[i][j].color = Color.GREEN; break;
                    case 2 : tiles[i][j].color = Color.BLUE; break;
                    case 3 : tiles[i][j].color = Color.YELLOW; break;
                    case 4 : tiles[i][j].color = Color.PINK; break;
                }
                tiles[i][j].x = j; 
                tiles[i][j].y = i;     
            }
        }
    }
}

class MyCanvas extends Canvas implements MouseListener
{
    public MyCanvas() {    
        setBackground (Color.GRAY);    
        setSize(600, 600);    
        setLocation(0, Main.offset);
        addMouseListener(this);

        Main.activeTiles.add(Main.tiles[0][0]);
        findNeighbors(Main.tiles[0][0], Main.tiles[0][0].color);
    }    

    public void paint(Graphics g) {
        for(int i=0; i<Main.tiles.length; i++){
            for (int j=0; j<Main.tiles[0].length; j++){
                g.setColor(Main.tiles[i][j].color);
                g.fillRect(j*600/15, i*600/15, 600/15, 600/15);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int i = y/40;
        int j = x/40;
        if (!Main.activeTiles.contains(Main.tiles[i][j]) && Main.tiles[i][j].color != Main.tiles[0][0].color)
            findNeighbors(Main.tiles[i][j], Main.tiles[i][j].color);
    }

    public void findNeighbors(Tile t, Color color){
        HashSet<Tile> neighbors = new HashSet<>();
        for (Tile tile : Main.activeTiles) {
            findNeighborsRecurse(tile, color, neighbors);
        }
        Main.activeTiles.addAll(neighbors);    
        for(Tile tile : Main.activeTiles){
            tile.color = color;
            this.repaint();
        }
    }

    public void findNeighborsRecurse(Tile tile, Color color, HashSet<Tile>neighs){
        if (!Main.activeTiles.contains(tile))
            neighs.add(tile);
        if (tile.x != 14 && Main.tiles[tile.y][tile.x+1].color == color && !neighs.contains(Main.tiles[tile.y][tile.x+1]))
                findNeighborsRecurse(Main.tiles[tile.y][tile.x+1], Main.tiles[tile.y][tile.x+1].color, neighs);
        if (tile.x != 0 && Main.tiles[tile.y][tile.x-1].color == color && !neighs.contains(Main.tiles[tile.y][tile.x-1]))
                findNeighborsRecurse(Main.tiles[tile.y][tile.x-1], Main.tiles[tile.y][tile.x-1].color, neighs);
        if (tile.y != 14 && Main.tiles[tile.y+1][tile.x].color == color && !neighs.contains(Main.tiles[tile.y+1][tile.x]))
                findNeighborsRecurse(Main.tiles[tile.y+1][tile.x], Main.tiles[tile.y+1][tile.x].color, neighs);
        if (tile.y != 0 && Main.tiles[tile.y-1][tile.x].color == color && !neighs.contains(Main.tiles[tile.y-1][tile.x]))
                findNeighborsRecurse(Main.tiles[tile.y-1][tile.x], Main.tiles[tile.y-1][tile.x].color, neighs);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}


class Tile{
    int x;
    int y;
    Color color;

    public String toString(){
        return String.format("Tile(%d, %d): %s", x, y, color.toString());
    }

    @Override
    public boolean equals(Object obj) {
        Tile oth = (Tile) obj;
        return this.x == oth.x && this.y == oth.y;
    }
}


