package com.example.demo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class CompressionAlgorithm
{
    public void addPathWeight(Node node,int weight){
        if( node==null )
            return;

        node.setEdgeValue(weight+"");
        addPathWeight(node.getLeftChild(),0);
        addPathWeight(node.getRightChild(),1);
    }

    public void viewPathWeight(Node node){
        if( node==null )
            return;

        viewPathWeight(node.getLeftChild());
        viewPathWeight(node.getRightChild());

    }

    public boolean getTotalPathWeight(Node node,Character targetCharacter,StringBuilder pathWeight){
        if( node==null ){
            return false;
        }



        if( node.getCharacter()!=null && node.getCharacter()==targetCharacter ){
            pathWeight.append(node.getEdgeValue());
            return true;

        }

        if( !node.getEdgeValue().equals("-1") ){
            pathWeight.append(node.getEdgeValue());
        }

        boolean leftChild = getTotalPathWeight(node.getLeftChild(),targetCharacter,pathWeight);
        if( leftChild )
            return true;
        boolean rightChild = getTotalPathWeight(node.getRightChild(),targetCharacter,pathWeight);
        if( rightChild )
            return true;
        pathWeight.deleteCharAt(pathWeight.length()-1);
        return false;


    }
    public Map<Character,String> compress(String contentsOfFile)
    {
        Map<Character,Integer> characterCountMap = new HashMap<>();
        for(Character ch:contentsOfFile.toCharArray()){
            if( ch>='a' && ch<='z' || ch>='A' && ch<='Z')
            characterCountMap.put(ch,characterCountMap.getOrDefault(ch,0)+1);
        }
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(a -> a.getCount())
        );
        for (Character character : characterCountMap.keySet()){
            int count = characterCountMap.get(character);
            priorityQueue.add(new Node(count,character));
        }

      while ( priorityQueue.size()>1 ){
          Node first = priorityQueue.poll();
          Node second = priorityQueue.poll();
          int totalSum = first.getCount()+second.getCount();
          Node third = new Node(totalSum);
          third.setLeftChild(first);
          third.setRightChild(second);
          priorityQueue.add(third);
      }

      Node node = priorityQueue.remove();
      addPathWeight(node,-1);
//      viewPathWeight(node);

      Map<Character,String> prefixCode = new HashMap<>();
      for ( Character currentChar : characterCountMap.keySet() ){
          StringBuilder pathWeightForCurrentCharacter = new StringBuilder();
          getTotalPathWeight(node,currentChar,pathWeightForCurrentCharacter);
          prefixCode.put(currentChar,pathWeightForCurrentCharacter.toString());
      }

        return prefixCode;


    }
}
