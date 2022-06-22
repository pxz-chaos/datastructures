package Day03.Tree.BinaryTreePackage;

/**
 * @ClassName jj
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/17 16:06
 * @Version 1.0
 */
//优化遍历逻辑，这里采用一个接口，让外部使用者自定义遍历的方式，不仅仅是打印


/**
 * 抽象类，用于自定义遍历，之前使用的是函数式接口
 *
 * @param <E>
 */
public abstract class Visitor<E> {
    //void visit(E e);//这里只有一个抽象方法，所以可以使用函数式接口
    boolean stop = false;//用于记录遍历什么时候终止

    public abstract boolean visit(E element);//再次改进，改进的效果是，按需求遍历元素，不需要全部遍历完所有二叉树
}

