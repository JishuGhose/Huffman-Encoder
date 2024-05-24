package com.example.demo;

public class Node
{
    private int count;
    private Node leftChild;
    private Node rightChild;
    private Character character;
    private String edgeValue;

    public Node(int count) {
        this.count = count;
        this.leftChild = null;
        this.rightChild = null;

    }

    public Node(int count, Character character) {
        this.count = count;
        this.character = character;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getEdgeValue() {
        return edgeValue;
    }

    public void setEdgeValue(String edgeValue) {
        this.edgeValue = edgeValue;
    }
}
