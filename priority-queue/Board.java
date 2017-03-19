/**
 * Created by tangmin on 17/3/19.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {
    private int[][] tiles;

    public Board(int[][] blocks) {
        tiles = copy(blocks);
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][];
        for (int r = 0; r < blocks.length; r++) {
            copy[r] = blocks[r].clone();
        }
        return copy;
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return tiles.length;
    }
    public int hamming() {
        int value = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (i == tiles.length - 1 && j == tiles.length - 1) {
                    continue;
                }
                if (tiles[i][j] != (i * tiles.length + j + 1)) {
                    value++;
                }
            }
        }
        return value;
    }
    public int manhattan() {
        int value = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int expected = i * tiles.length + j + 1;
                if (tiles[i][j] != expected && tiles[i][j] != 0) {
                    int actual = tiles[i][j];
                    actual--;
                    int col = actual % tiles.length;
                    int row = actual / tiles.length;
                    value += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return value;
    }
    public boolean isGoal() {
        return hamming() == 0;
    }
    public Board twin() {
        int[][] twinBlocks = copy(tiles);
        int i = 0;
        int j = 0;
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            i++;
            if (i >= twinBlocks.length) {
                j++;
                i = 0;
            }
        }

        exchange(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);
    }
    private void exchange(int[][] blocks, int i, int j, int x, int y) {
        int firstValue = blocks[i][j];
        blocks[i][j] = blocks[x][y];
        blocks[x][y] = firstValue;
    }
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) return false;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new NeighborIterator();
            }
        };
    }
    private Board[] findNeighbors() {
        List<Board> founded = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (tiles[i][j] != 0) {  // locate the empty element;
            i++;
            if (i >= dimension()) {
                i = 0;
                j++;
            }
        }
        if (i > 0) {
            int[][] temp = copy(tiles);
            exchange(temp, i - 1, j, i, j);
            founded.add(new Board(temp));
        }
        if (i < dimension() - 1) {
            int[][] temp = copy(tiles);
            exchange(temp, i + 1, j, i, j);
            founded.add(new Board(temp));
        }
        if (j > 0) {
            int[][] temp = copy(tiles);
            exchange(temp, i, j - 1, i, j);
            founded.add(new Board(temp));
        }
        if (j < dimension() - 1) {
            int[][] temp = copy(tiles);
            exchange(temp, i, j + 1, i, j);
            founded.add(new Board(temp));
        }

        return founded.toArray(new Board[founded.size()]);
    }
    private class NeighborIterator implements Iterator<Board> {
        private int index = 0;
        private Board[] neighbors = findNeighbors();
        public boolean hasNext() {
            return index < neighbors.length;
        }
        public Board next() {
            if (hasNext()) {
                return neighbors[index++];
            }
            else {
                throw new NoSuchElementException("no next neighbor");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("removal not supported");
        }

    }
    public String toString() {
        StringBuilder boardStringBuilder = new StringBuilder(tiles.length + "\n");
        for (int[] row: tiles) {
            for (int block : row) {
                boardStringBuilder.append(" ");
                boardStringBuilder.append(block);
            }
            boardStringBuilder.append("\n");
        }
        return boardStringBuilder.toString();
    }

    public static void main(String[] args) {

    }
}
