package Day03.Tree.BST;


import Day03.Tree.printer.BinaryTrees;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/*
形象的二叉树网站
http://520it.com/binarytrees/
http://btv.melezinek.cz/binary-search-tree.html
https://www.cs.usfca.edu/~galles/visualization/Algorithms.html
https://yangez.github.io/btree-js
 */

/**
 * @ClassName Main
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/30 10:25
 * @Version 2.0
 */
public class Main {
    static void test1() {
        Integer[] data = {7, 4, 9, 2/*5, 8, 11, 1, 12*/};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst/*, BinaryTrees.PrintStyle.INORDER*/);
        System.out.println(bst.isComplete());
    }

    static void test2() {
        BinarySearchTree<Person> bst2 = new BinarySearchTree<>();
        bst2.add(new Person(12));
        bst2.add(new Person(18));
        bst2.add(new Person(15));
        bst2.add(new Person(2));
        BinaryTrees.println(bst2);
    }

    static void test3() {
        BinarySearchTree<Person> bst3 = new BinarySearchTree<>((p1, p2) -> p2.age - p1.age);//小的为右子树
        bst3.add(new Person(12));
        bst3.add(new Person(18));
        bst3.add(new Person(15));
        bst3.add(new Person(2));
        BinaryTrees.println(bst3);
        /*  String s = BinaryTrees.printString(bst3);*/
    }

    /**
     * @param num 随机二叉树的节点
     */
    static void test4(int num) {
        //打印随机二叉树
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < num; i++) {
            bst.add((int) (Math.random() * 100));
        }
        BinaryTrees.println(bst);
        // printToDir(BinaryTrees.printString(bst)+"\n");
        // Files.writeToFile("F:\\MyEclipse 2017 CI\\DataStructures_mj\\src\\binarySearchTree.txt",BinaryTrees.printString(bst));
    }

    static void test5() {
        BinarySearchTree<Person> bst3 = new BinarySearchTree<>((p1, p2) -> p2.age - p1.age);//小的为右子树
        bst3.add(new Person(12, "jack"));
        bst3.add(new Person(18, "rose"));
        bst3.add(new Person(15, "micheal"));
        bst3.add(new Person(12, "dogSum"));//实现了覆盖，因为在add的else中实现了覆盖原内容
        BinaryTrees.println(bst3);
    }

    static void test6() {
        Integer[] data = {7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst/*, BinaryTrees.PrintStyle.INORDER*/);
        //前序遍历，根左右
        bst.preorderTraversal();
        //bst.preorderTraversal((e)-> System.out.println(e*10));//使用函数式接口来实现
       /* bst.preorderTraversal(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer integer) {
                System.out.println(integer);
                return false;
            }
        });*/
    }

    static void test7() {
        Integer[] data = {7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst/*, BinaryTrees.PrintStyle.INORDER*/);
        //在中序遍历,左根右，特点是从小到大排序，或从大到小，仅仅针对于二叉搜索树
        bst.inorderTraversal();
    }

    static void test8() {
        Integer[] data = {7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst/*, BinaryTrees.PrintStyle.INORDER*/);
        //在后序遍历，左->右->根
        bst.postorderTraversal();
    }

    static void test9() {
        Integer[] data = {7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst/*, BinaryTrees.PrintStyle.INORDER*/);
        //层序遍历
        bst.levelOrderTraversal();
        //bst.levelOrderTraversal((e)-> System.out.print(e*10+"\t"));
       /* bst.levelOrderTraversal((param) -> {
            System.out.print(param+" ");
            return param == 2 ? true : false;//遍历到值为2时就停止打印
        });*/
        bst.levelOrderTraversal(new Visitor<Integer>() {
            @Override
            public boolean visit(Integer integer) {
                System.out.println(integer);
                return integer == 5 ? true : false;
            }
        });
    }

    static void test10() {
        Integer[] data = {7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }
        System.out.println("层序遍历：");
        bst.levelOrderTraversal(new Visitor<Integer>() {
            @Override
            public boolean visit(Integer integer) {
                System.out.print(integer + " ");
                return integer == 5 ? true : false;
            }
        });
        System.out.println();
        System.out.println("前序遍历：");
        bst.preorderTraversal(new Visitor<Integer>() {
            @Override
            public boolean visit(Integer integer) {
                System.out.print(integer+" ");
                return integer == 9 ? true : false;
            }
        });
        System.out.println();
        System.out.println("中序遍历：");
        bst.inorderTraversal(new Visitor<Integer>() {
            @Override
            public boolean visit(Integer integer) {
                System.out.print(integer + " ");
                return integer == 8 ? true : false;
            }
        });
        System.out.println();
        System.out.println("后序遍历：");
        bst.postorderTraversal(new Visitor<Integer>() {
            @Override
            public boolean visit(Integer integer) {
                System.out.print(integer + " ");
                return integer == 11 ? true : false;
            }
        });
        System.out.println();
        System.out.println("打印二叉树的高度：");
        System.out.println(bst.height1());//打印二叉树的高度
    }

    static void test11() {
        Integer[] data = {7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst/*, BinaryTrees.PrintStyle.INORDER*/);
        //在后序遍历，左->右->根
       /* bst.postorderTraversal();*/
      /*  System.out.println(bst.predecessor(bst.getRoot()));*/
    }

    //删除节点
    static void test12(){

        Integer[] data = {/*9,11,10,12*/7, 4, 9, 2, 5, 8, 11, 1, 12};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
        bst.remove(4);
        BinaryTrees.println(bst);
        System.out.println(bst.contains(11));


    }

    //打印到硬盘中
    public static void printToDir(String str) {
        BufferedWriter bw = null;
        try {
            String filePath = "F:\\MyEclipse 2017 CI\\DataStructures_mj\\src\\binarySearchTree.txt";
            bw = new BufferedWriter(new FileWriter(filePath, true));
            bw.write(str);
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
//        test1();
//        test2();//默认排序，左小右大
//        test3();
//        test4(30);
//        test5();
//        test6();
//        test7();
//        test8();
//        test9();
//        test10();
//        test11();
        test12();
    }
}
