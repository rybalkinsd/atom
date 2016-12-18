package utils.quadTree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.geom.Rectangle2D;

/**
 * Created by xakep666 on 16.12.16.
 * 
 * QuadTree node
 */
class TreeNode<T> {
    public enum Type {
        EMPTY,
        LEAF,
        POINTER
    }
    @NotNull
    private final Rectangle2D region;
    @Nullable
    private final TreeNode<T> parent;
    @NotNull
    private Type nodeType = Type.EMPTY;
    @Nullable
    private TreeNode<T> nw,ne,sw,se;
    @Nullable
    private TreePoint<T> point;

    /**
     * Constructs {@link QuadTree} node
     * 
     * @param region region occupied by node
     * @param parent optional parent node
     */
    TreeNode(@NotNull Rectangle2D region,@Nullable TreeNode<T> parent) {
        this.region=region;
        this.parent=parent;
    }

    @NotNull Rectangle2D getRegion() {
        return region;
    }

    @Nullable
    TreeNode<T> getParent() {
        return parent;
    }

    @NotNull
    Type getNodeType() {
        return nodeType;
    }

    void setNodeType(@NotNull Type nodeType) {
        this.nodeType = nodeType;
    }

    @Nullable
    TreeNode<T> getNw() {
        return nw;
    }

    void setNw(@Nullable TreeNode<T> nw) {
        this.nw = nw;
    }

    @Nullable
    TreeNode<T> getNe() {
        return ne;
    }

    void setNe(@Nullable TreeNode<T> ne) {
        this.ne = ne;
    }

    @Nullable
    TreeNode<T> getSw() {
        return sw;
    }

    void setSw(@Nullable TreeNode<T> sw) {
        this.sw = sw;
    }

    @Nullable
    TreeNode<T> getSe() {
        return se;
    }

    void setSe(@Nullable TreeNode<T> se) {
        this.se = se;
    }

    @Nullable
    TreePoint<T> getPoint() {
        return point;
    }

    void setPoint(@Nullable TreePoint<T> point) {
        this.point = point;
    }
}
