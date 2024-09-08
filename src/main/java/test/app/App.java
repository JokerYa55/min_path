package test.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Node {

    private int sum;
    private int i, j;
    private final List<Node> chailds = new ArrayList<>();
    private final Node parent;

    @Override
    public String toString() {
        return "Node{" + "sum=" + sum + ", i=" + i + ", j=" + j + ", chailds=" + chailds + '}';
    }

    public Node(int sum, int i, int j, Node parent) {
        this.sum = sum;
        this.i = i;
        this.j = j;
        this.parent = parent;
    }

    public int getSum() {
        return sum;
    }

    public List<Node> getChailds() {
        return chailds;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Node getParent() {
        return parent;
    }

}

/**
 *
 * @author vasil
 */
public class App {

    public static final int M = 3;
    public static final int N = 3;
    public static final List<Node> resultList = new ArrayList<>();

    public static int[][] initArray() {
        return new int[][]{new int[]{1, 1, 1, 1, 1},
        new int[]{3, 100, 100, 100, 100},
        new int[]{1, 1, 1, 1, 1},
        new int[]{2, 2, 2, 2, 1},
        new int[]{1, 1, 1, 1, 1}
        };
    }

    public static int[][] initArray1() {
        var random = new Random();
        int[][] result = new int[M][N];
        for (int i = 0; i < M - 1; i++) {
            for (int j = 0; j < N - 1; j++) {
                result[i][j] = random.nextInt(20);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] array = initArray1();
        printArray(array);
        var root = new Node(array[0][0], 0, 0, null);
        buildTree(root, 0, 0, array);
//        System.out.println(getPath(root, "", ""));
        resultList.sort((o1, o2) -> o1.getSum() - o2.getSum());
        System.out.println("res_all = " + resultList);
        System.out.println("res = " + resultList.get(0).getSum());
        var resPath = "";
        var currentNode = resultList.get(0);
        while (true) {
            resPath = resPath + "<- (" + String.join(",", currentNode.getI() + "", currentNode.getJ() + "") + ") ";
            if (currentNode.getParent() != null) {
                currentNode = currentNode.getParent();
            } else {
                break;
            }
        }
        System.out.println("res_path = " + resPath);
    }

    public static String getPath(Node root, String path, String prefix) {
        var result = path + String.join(",", root.getI() + "", root.getJ() + "") + ":" + root.getSum() + "\n";

        var iter = root.getChailds().iterator();
        while (iter.hasNext()) {
            Node next = iter.next();

            if (next.getChailds().size() == 0) {
                result = result + prefix + getPath(next, " |_", prefix + "  ");
            } else {
                result = result + prefix + getPath(next, " |-", prefix + "  ");
            }

        }

        return result;
    }

    public static void buildTree(Node node, final int i, final int j, final int[][] array) {
        //    System.out.println("i = " + i + " j = " + j + " nose = " + node);
        if (i == M - 1 && j == N - 1) {
            resultList.add(node);
        }
        // Движение вниз
        int k = i + 1;
        if (k < array.length) {
            {
                var resNode = new Node(node.getSum() + array[k][j], k, j, node);
                node.getChailds().add(resNode);
                buildTree(resNode, k, j, array);
            }
        }

        // движение налево
        k = j + 1;
        if (k < array[0].length) {
            var resNode = new Node(node.getSum() + array[i][k], i, k, node);
            node.getChailds().add(resNode);
            buildTree(resNode, i, k, array);
        }
    }

    public static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            var str = array[i];
            var s1 = IntStream.of(str).boxed().map(t -> t.toString()).collect(Collectors.joining(" | ", "| ", " |"));
            System.out.println(s1);
        }
    }
}
