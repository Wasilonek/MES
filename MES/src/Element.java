import java.util.Arrays;

/**
 * Created by Kamil on 2017-10-27.
 */
public class Element {
    private Node ID[] = new Node[4];



    public Element(Node node, Node node1, Node node2, Node node3) {
        ID[0] = node;
        ID[1] = node1;
        ID[2] = node2;
        ID[3] = node3;
    }

    public Node[] getID() {
        return ID;
    }

    public void setID(Node[] ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Element{" +
                "ID=" + Arrays.toString(ID) +
                '}';
    }
}

