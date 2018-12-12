package com.alevel;

public class Node {

    private int value;
    private int weight;
    private Node left;
    private Node right;


    private Node(int value, int weight, Node left, Node right) {
        this.value = value;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }


    public Node(int value, int weight) {
        this.value = value;
        this.weight = weight;

    }


    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public static NodeBuilder builder() {
        return new NodeBuilder();
    }

    public boolean isLeaf() {
        if (left == null && right == null) {
            return true;
        }
        return false;
    }

    public static class NodeBuilder {
        public int value;
        public int weight;
        public Node left;
        public Node right;


        public NodeBuilder setValueNode(int value) {
            this.value = value;
            return this;
        }

        public NodeBuilder setWeightNode(int weight) {
            this.weight = weight;
            return this;
        }

        public NodeBuilder setLeftNode(Node left) {
            this.left = left;
            return this;
        }

        public NodeBuilder setRightNode(Node right) {
            this.right = right;
            return this;
        }

        public Node biuldNode() {
            return new Node(value, weight, left, right);
        }


    }
}
