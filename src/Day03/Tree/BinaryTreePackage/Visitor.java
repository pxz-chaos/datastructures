package Day03.Tree.BinaryTreePackage;

/**
 * @ClassName jj
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/17 16:06
 * @Version 1.0
 */
//�Ż������߼����������һ���ӿڣ����ⲿʹ�����Զ�������ķ�ʽ���������Ǵ�ӡ


/**
 * �����࣬�����Զ��������֮ǰʹ�õ��Ǻ���ʽ�ӿ�
 *
 * @param <E>
 */
public abstract class Visitor<E> {
    //void visit(E e);//����ֻ��һ�����󷽷������Կ���ʹ�ú���ʽ�ӿ�
    boolean stop = false;//���ڼ�¼����ʲôʱ����ֹ

    public abstract boolean visit(E element);//�ٴθĽ����Ľ���Ч���ǣ����������Ԫ�أ�����Ҫȫ�����������ж�����
}

