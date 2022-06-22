package Day06Heap;

import Day06Heap.printer.BinaryTrees;

import java.util.Comparator;

public class Main {
    static void test01() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        int[] data = {11, 22, 21, 84, 83, 85, 99, 3, 23, 9, 48, 4, 13, 24, 71, 30, 54, 50, 35, 72};
        for (int i = 0; i < data.length; i++) {
            heap.add(data[i]);
        }
        BinaryTrees.println(heap);

    }

    static void test02() {

        Integer[] data = {11, 22, 21, 84, 83, 85, 99, 3, 23, 9, 48, 4, 13, 24, 71, 30, 54, 50, 35, 72};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        heap.heapify();
        BinaryTrees.println(heap);

    }

    static void test03() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(72);
        heap.add(68);
        heap.add(50);
        heap.add(43);
        heap.add(38);
        heap.add(47);
        heap.add(21);
        heap.add(14);
        heap.add(40);
        heap.add(80);
        BinaryTrees.println(heap);
//        heap.remove();
//        BinaryTrees.println(heap);
        System.out.println(heap.get());
        Integer replace = heap.replace(70);
        System.out.println("-----------");
        BinaryTrees.println(heap);
        System.out.println(replace);
    }

    static void test04() {
        Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93,
                91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
                90, 6, 65, 49, 3, 26, 61, 21, 48};
        topK(5, data);
    }


    public static void main(String[] args) {
//       test01();
//       test02();
        test04();

    }


    /**
     * topK问题
     *
     * @param k    找出最大的前K个数
     * @param data 给定一个数组
     */
    public static void topK(int k, Integer[] data) {
        //新建一个小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;//注意：这是一个新建小顶堆的写法，因为将小的放在了堆顶
            }

        });
        for (int i = 0; i < data.length; i++) {
            if (heap.size() < k) { // 前k个数添加到小顶堆
                heap.add(data[i]); // logk
            } else if (data[i] > heap.get()) { // 如果是第k + 1个数，并且大于堆顶元素
                heap.replace(data[i]); // logk//替换掉堆顶最小的元素，最后残留下来的就是最大的元素了
            }
        }
        BinaryTrees.println(heap);

    }
}
