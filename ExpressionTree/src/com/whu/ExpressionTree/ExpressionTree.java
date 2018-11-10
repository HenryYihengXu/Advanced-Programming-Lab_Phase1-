package com.whu.ExpressionTree;

import jdk.nashorn.internal.ir.EmptyNode;

import javax.xml.soap.Node;
import java.util.*;

/**
 * The main program for ExpressionTree.
 * Convert post-order, in-order, and pre-order expressions into expression trees.
 * And then convert the tree to expressions in pre-order, post-order, and in-order.
 *
 *  @author Henry Xu
 *  */

public class ExpressionTree {

    /** Root of the Expression tree. */
    ETNode root;

    /** Constructor of ExpressionTree. */
    public ExpressionTree(int i) {
        Scanner scanner = new Scanner(System.in);
        switch (i) {
            case 1: {
                System.out.println("请输入后缀表达式：运算符、操作数之间请用空格分隔，括号不用");
                String str = scanner.nextLine();
                String[] items = str.split(" ");
                root = postGenerate(items);
                break;
            }
            case 2: {
                System.out.println("请输入中缀表达式：运算符、操作数之间请用空格分隔，括号不用");
                String str = scanner.nextLine();
                String[] items = str.split(" ");
                root = inGenerate(items);
                break;
            }
        }
    }

    /** Tell if the string is a operatot.
     * @param item Item.
     * @return */
    public static boolean isOperator(String item) {
        return item.equals("+") || item.equals("*") || item.equals("-") || item.equals("/");
    }

    /** Generate the tree by a given post-order string array.
     * @param items The given string array.
     * @return The root of the generated tree. */
    public ETNode postGenerate(String[] items) {
        if (items == null) {
            return null;
        }
        Stack s = new Stack<ETNode>();
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (isOperator(item)) {
                ETNode right = (ETNode)s.pop();
                ETNode left = (ETNode)s.pop();
                ETNode node = new ETNode(item, left, right);
                s.push(node);
            } else {
                ETNode node = new ETNode(item, null, null);
                s.push(node);
            }
        }
        return  (ETNode)s.pop();
    }

    /** Generate the tree by a given in-order string array.
     * @param items The given string array.
     * @return The root of the generated tree. */
    public ETNode inGenerate(String[] items) {
        return postGenerate(inToPost(items));
    }

    /** Tell the relationship between two operator.
     * @param first The first operator.
     * @param second The second operator.
     * @return */
    public static boolean isNotLessThan(String first, String second) {
        if (first.equals("(")) {
            return false;
        }
        if (first.equals("*") || first.equals("/")) {
            return true;
        } else if (second.equals("*") || second.equals("/")) {
            return false;
        } else {
            return true;
        }
    }

    /** Convert an in-order array to a post-order array.
     * @param items in-order array.
     * @return post-order array after convert. */
    public static String[] inToPost(String[] items) {
        ArrayList result = new ArrayList<String>();
        Stack s = new Stack<String>();
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (!isOperator(item)) {
                if (item.charAt(0) == '(') {
                    while (item.charAt(0) == '(') {
                        s.push("(");
                        item = item.substring(1);
                    }
                    result.add(item);
                } else if (item.charAt(item.length() - 1) == ')') {
                    int count = 0;
                    while (item.charAt(item.length() - 1) == ')') {
                        count += 1;
                        item = item.substring(0, item.length() - 1);
                    }
                    result.add(item);
                    while (count > 0) {
                        if (!s.peek().equals("(")) {
                            result.add(s.pop());
                        } else {
                            s.pop();
                            count -= 1;
                        }
                    }
                } else {
                    result.add(item);
                }
            } else {
                if (s.isEmpty()) {
                    s.push(item);
                } else {
                    while (s.isEmpty() || isNotLessThan((String)s.peek(), item)) {
                        result.add(s.pop());
                    }
                    s.push(item);
                }
            }
        }
        while (!s.isEmpty()) {
            result.add(s.pop());
        }
        String[] rs = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            rs[i] = (String)result.remove(0);
        }
        return rs;
    }

    /** Print the in-order of the expression. */
    public void inExpression() {
        inExpression(root);
        System.out.println();
    }

    /** Print the in-order of the expression.
     * @param node The root of the tree*/
    public void inExpression(ETNode node) {
        if (node == null) {
            return;
        }
        if (isOperator(node.item)) {
            System.out.print("(");
        }
        inExpression(node.left);
        System.out.print(node.item + " ");
        inExpression(node.right);
        if (isOperator(node.item)) {
            System.out.print(")");
        }
    }

    /** Print the post-order of the expression. */
    public void postExpression() {
        postExpression(root);
        System.out.println();
    }

    /** Print the post-order of the expression.
     * @param node The root of the tree*/
    public void postExpression(ETNode node) {
        if (node == null) {
            return;
        }
        postExpression(node.left);
        postExpression(node.right);
        System.out.print(node.item + " ");
    }

    /** Print the pre-order of the expression. */
    public void preExpression() {
        preExpression(root);
        System.out.println();
    }

    /** Print the pre-order of the expression.
     * @param node The root of the tree*/
    public void preExpression(ETNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.item + " ");
        preExpression(node.left);
        preExpression(node.right);
    }

    public static void main(String[] args) throws Exception {

        System.out.println("请输入您要输入的表达式模式：");
        System.out.println("1：后缀表达式");
        System.out.println("2：中缀表达式");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        ExpressionTree tree = new ExpressionTree(i);
        System.out.println("后缀表达式：");
        tree.postExpression();
        System.out.println("前缀表达式：");
        tree.preExpression();
        System.out.println("中缀表达式：");
        tree.inExpression();
    }

    private class ETNode {
        /** Content of the node. */
        String item;
        /** Left child of the node. */
        ETNode left;
        /** Right child of the node. */
        ETNode right;

        /** Constructor of the node. */
        public ETNode(String item, ETNode left, ETNode right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }
}
