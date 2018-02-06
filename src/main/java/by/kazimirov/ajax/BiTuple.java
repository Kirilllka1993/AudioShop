package by.kazimirov.ajax;

import java.io.Serializable;

/**
 *
 */
public class BiTuple<L, R> implements Serializable {
    private L left;
    private R right;

    public BiTuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public static void main(String[] args) {
    }
}
