package com.ulluna;

/**
 * Created by tomaszczernuszenko on 09/03/2017.
 */
public class MazeSolving {

    Square[][] array;
    int[][] graph;
    int[] distances;
    int[] sources;

    public MazeSolving() {
        array = new Square[4][4];
        graph = new int[16][16];
        distances = new int[16];
        sources = new int[16];


        array[0][0] = new Square("0101");
        array[0][1] = new Square("0011");
        array[0][2] = new Square("0111");
        array[0][3] = new Square("0010");
        array[1][0] = new Square("1100");
        array[1][1] = new Square("0101");
        array[1][2] = new Square("1110");
        array[1][3] = new Square("0100");
        array[2][0] = new Square("1001");
        array[2][1] = new Square("1110");
        array[2][2] = new Square("1000");
        array[2][3] = new Square("1100");
        array[3][0] = new Square("0001");
        array[3][1] = new Square("1011");
        array[3][2] = new Square("0011");
        array[3][3] = new Square("1010");

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                //For every point in the Square array iterate through every element in the whole Square array and update value
                if (array[i][j] == null)
                    continue;
                if (array[i][j].backward == 1) {
                    graph[getIndex(i, j)][getIndex(i + 1, j)] = 1;
                }
                if (array[i][j].forward == 1) {
                    graph[getIndex(i, j)][getIndex(i - 1, j)] = 1;
                }
                if (array[i][j].right == 1) {
                    graph[getIndex(i, j)][getIndex(i, j + 1)] = 1;
                }
                if (array[i][j].left == 1) {
                    graph[getIndex(i, j)][getIndex(i, j - 1)] = 1;
                }
            }
        }

        for (int i = 0; i < distances.length; i++) {
            distances[i] = 30;
        }

        distances[getIndex(3, 0)] = 0;
        sources[getIndex(3, 0)] = getIndex(3, 0);
        BFS(getIndex(3, 0));
        for (int i = 0; i < distances.length; i++) {
            System.out.print(distances[i] + " ");
            if (i % 4 == 3)
                System.out.println();
        }
        System.out.println();

        for (int i = 0; i < sources.length; i++) {
            System.out.print(sources[i] + " ");
            if (sources[i] < 10)
                System.out.print(" ");
            if (i % 4 == 3)
                System.out.println();
        }

        //work your way back and find the path
        int path[] = new int[distances[getIndex(0, 3)] + 1];
        int next = getIndex(0, 3), k = path.length - 1;
        path[k] = next;
        while (next != getIndex(3, 0)) {
            k--;
            if (k < 0) {
                System.out.println("Error: No Path found");
                System.exit(0);
            }
            next = sources[next];
            path[k] = next;
        }

        //FIX path - somethimes there are a few 0 elements in the front
        int j = 0;
        while (path[j] == 0) {
            j++;
        }

        int displacements[] = new int[path.length - j - 1];
        for (int i = 0; i < displacements.length; i++) {
            displacements[i] = displacementType(path[i + j], path[i + j + 1]);
        }

        //0 - up, 1 - right, 2 - down, 3 - left
        int orientation = 0;

        int movesCounter = 0;
        int moves[] = new int[displacements.length * 2];
        int DISTANCE = 50, LEFT_TURN = -90, RIGHT_TURN = 90;

        moves[0] = DISTANCE;
        for (int i = 0; i < displacements.length; i++) {
            if (orientation == (displacements[i] - 1) % 4) {
                System.out.print("TURN RIGHT ");
                movesCounter++;
                moves[movesCounter] = RIGHT_TURN;
                orientation++;
                orientation %= 4;
            } else if (orientation == (displacements[i] + 1) % 4) {
                System.out.print("TURN LEFT ");
                movesCounter++;
                moves[movesCounter] = LEFT_TURN;
                orientation--;
                orientation %= 4;
            }

            if (moves[movesCounter] % DISTANCE == 0) {
                System.out.println("STRAIGHT " + orientation);
                moves[movesCounter] += DISTANCE;
            } else {
                System.out.println("STRAIGHT " + orientation);
                movesCounter++;
                moves[movesCounter] = DISTANCE;
            }
        }
        movesCounter++;

        for (int i = 0; i < movesCounter; i++) {
            System.out.print(moves[i] + " ");
        }
        System.out.println();

    }

    public int getIndex(int row, int column) {
        return row * 4 + column;
    }

    public void BFS(int x) {
        for (int i = 0; i < graph[x].length; i++) {
            if (graph[x][i] == 1) {

                int cost = 1;
                if (abs(sources[x] - i) != 2 && (sources[x] - i) % 4 != 0) //are all 3 points on the same line? If not - increase the cost
                    cost++;

                if (distances[i] > distances[x] + cost) {
                    distances[i] = distances[x] + cost;
                    sources[i] = x;
                    BFS(i);
                }
            }
        }
    }

    //0 - up, 1 - right, 2 - down, 3 - left
    public int displacementType(int source, int goal) {
        if (goal == source + 1)
            return 1;
        if (goal == source - 1)
            return 3;
        if (goal == source + 4)
            return 2;
        if (goal == source - 4)
            return 0;
        return -1;
    }

    public int abs(int x) {
        if (x < 0)
            return -x;
        return x;
    }

    class Square {
        int forward, backward, left, right;

        public Square(int f, int b, int l, int r) {
            forward = f;
            backward = b;
            left = l;
            right = r;
        }

        public Square(String s) {
            this(s.charAt(0) - '0', s.charAt(1) - '0', s.charAt(2) - '0', s.charAt(3) - '0');
        }

    }

}
