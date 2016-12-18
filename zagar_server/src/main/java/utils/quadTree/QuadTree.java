package utils.quadTree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by xakep666 on 15.12.16.
 * <p>
 * Thread-safe iterative quandrants tree implementation to store items.
 * Simplifies collision detection.
 */
public class QuadTree<T> {
    @NotNull
    private final TreeNode<T> root;

    public QuadTree(@NotNull Rectangle2D region) {
        assert (region.getWidth()==region.getHeight()); //make sure if region is square
        this.root = new TreeNode<>(region, null);
    }

    private void setPointForNode(@NotNull TreeNode<T> node, @NotNull TreePoint<T> point) {
        if (node.getNodeType() == TreeNode.Type.POINTER) {
            throw new QuadTreeException("Can not set item for node type POINTER");
        }
        node.setNodeType(TreeNode.Type.LEAF);
        node.setPoint(point);
    }

    @Nullable
    private TreeNode<T> getQuadrantForPoint(@NotNull TreeNode<T> parent, @NotNull Point2D coordinate) {
        Point2D mPoint = new Point2D.Double(
                parent.getRegion().getX() + parent.getRegion().getWidth() / 2,
                parent.getRegion().getY() + parent.getRegion().getHeight() / 2
        );
        if (coordinate.getX() < mPoint.getX()) {
            return coordinate.getY() < mPoint.getY() ? parent.getNw() : parent.getSw();
        } else {
            return coordinate.getY() < mPoint.getY() ? parent.getNe() : parent.getSe();
        }
    }

    private void insert(@NotNull TreeNode<T> parent, @NotNull TreePoint<T> startPoint) {
        Stack<TreeNode<T>> insertStack = new Stack<>();
        insertStack.add(parent);
        Stack<TreePoint<T>> pointStack = new Stack<>();
        pointStack.add(startPoint);
        while (!insertStack.isEmpty()) {
            TreeNode<T> node = insertStack.pop();
            TreePoint<T> point = pointStack.pop();
            if (node == null) continue;
            switch (node.getNodeType()) {
                case EMPTY:
                    setPointForNode(node, point);
                    break;
                case LEAF:
                    if (node.getPoint() == null) {
                        throw new QuadTreeException("Got null TreePoint for LEAF node");
                    }
                    if (point.getCoordinate().equals(node.getPoint().getCoordinate())) {
                        setPointForNode(node, point);
                    } else {
                        //split: convert to pointer node, insert with correct children
                        TreePoint<T> oldPoint = node.getPoint();
                        node.setPoint(null);
                        node.setNodeType(TreeNode.Type.POINTER);

                        //insert children: 4 rectangles
                        Rectangle2D oldRegion = node.getRegion();
                        double x = oldRegion.getX();
                        double y = oldRegion.getY();
                        double w = oldRegion.getWidth() / 2;
                        double h = oldRegion.getHeight() / 2;
                        node.setNw(new TreeNode<>(new Rectangle2D.Double(x, y, w, h), node));
                        node.setNe(new TreeNode<>(new Rectangle2D.Double(x + w, y, w, h), node));
                        node.setSw(new TreeNode<>(new Rectangle2D.Double(x, y + h, w, h), node));
                        node.setNe(new TreeNode<>(new Rectangle2D.Double(x + w, y + h, w, h), node));

                        pointStack.add(oldPoint);
                        insertStack.add(node);
                    }
                    break;
                case POINTER:
                    pointStack.add(point);
                    insertStack.add(getQuadrantForPoint(node, point.getCoordinate()));
                    break;
            }
        }
    }

    public synchronized void set(@NotNull Point2D coordinate, @NotNull T item) {
        if (!root.getRegion().contains(coordinate)) {
            throw new QuadTreeException("Out of bounds " + coordinate);
        }
        insert(root, new TreePoint<>(coordinate, item));
    }

    @Nullable
    private TreeNode<T> find(@NotNull TreeNode<T> start, @NotNull Point2D coordinate) {
        TreeNode<T> response = null;
        Stack<TreeNode<T>> findStack = new Stack<>();
        findStack.add(start);
        while (!findStack.isEmpty()) {
            TreeNode<T> node = findStack.pop();
            switch (node.getNodeType()) {
                case EMPTY:
                    break;
                case LEAF:
                    if (node.getPoint() == null) {
                        throw new QuadTreeException("Got null point in LEAF node");
                    }
                    response = node.getPoint().getCoordinate().equals(coordinate) ? node : null;
                    break;
                case POINTER:
                    findStack.add(getQuadrantForPoint(node, coordinate));
                    break;
            }
        }
        return response;
    }

    @NotNull
    public synchronized Optional<T> find(@NotNull Point2D coordinate) {
        TreeNode<T> result = find(root, coordinate);
        if (result==null || result.getPoint()==null) return Optional.empty();
        return result.getPoint().getItem();
    }

    private void balance(@NotNull TreeNode<T> start) {
        Stack<TreeNode<T>> balanceStack = new Stack<>();
        balanceStack.add(start);
        while (!balanceStack.isEmpty()) {
            TreeNode<T> node = balanceStack.pop();
            if (node == null) continue;
            switch (node.getNodeType()) {
                case EMPTY:
                case LEAF:
                    if (node.getParent() != null) {
                        balanceStack.add(node.getParent());
                    }
                    break;
                case POINTER:
                    TreeNode<T> nw = node.getNw();
                    TreeNode<T> ne = node.getNe();
                    TreeNode<T> sw = node.getSw();
                    TreeNode<T> se = node.getSe();
                    TreeNode<T> firstLeaf = null;

                    //Look for first non-empty child. If there is more than one than we
                    //break as this node can`t be balanced
                    if (nw != null && nw.getNodeType() != TreeNode.Type.EMPTY) {
                        firstLeaf = nw;
                    }
                    if (ne != null && ne.getNodeType() != TreeNode.Type.EMPTY) {
                        if (firstLeaf != null) {
                            return;
                        }
                        firstLeaf = ne;
                    }
                    if (sw != null && sw.getNodeType() != TreeNode.Type.EMPTY) {
                        if (firstLeaf != null) {
                            return;
                        }
                        firstLeaf = sw;
                    }
                    if (se != null && se.getNodeType() != TreeNode.Type.EMPTY) {
                        if (firstLeaf != null) {
                            return;
                        }
                        firstLeaf = se;
                    }

                    if (firstLeaf == null) {
                        //all child nodes are empty, so make this node empty
                        node.setNodeType(TreeNode.Type.EMPTY);
                        node.setNw(null);
                        node.setNe(null);
                        node.setSw(null);
                        node.setSe(null);
                    } else if (firstLeaf.getNodeType() == TreeNode.Type.POINTER) {
                        //only child was a pointer, therefore we can`t rebalance
                        return;
                    } else {
                        //only child was a leaf, so update node a point and make it a leaf
                        node.setNodeType(TreeNode.Type.LEAF);
                        node.setNw(null);
                        node.setNe(null);
                        node.setSw(null);
                        node.setSe(null);
                        node.setPoint(firstLeaf.getPoint());
                    }
                    break;
            }

            //try to balance the parent as well
            if (node.getParent() != null) {
                balanceStack.add(node.getParent());
            }
        }
    }

    @Nullable
    public synchronized T remove(@NotNull Point2D coordinate) {
        TreeNode<T> node = find(root, coordinate);
        if (node != null && node.getPoint() != null) {
            if (!node.getPoint().getItem().isPresent()) return null;
            T item = node.getPoint().getItem().get();
            node.setPoint(null);
            node.setNodeType(TreeNode.Type.EMPTY);
            balance(node);
            return item;
        } else {
            return null;
        }
    }

    private void navigate(@NotNull TreeNode<T> start,
                          @NotNull Consumer<TreeNode<T>> navigateFunc,
                          @NotNull Rectangle2D region) {
        Stack<TreeNode<T>> navigateStack = new Stack<>();
        navigateStack.add(start);
        while (!navigateStack.isEmpty()) {
            TreeNode<T> node = navigateStack.pop();
            if (node == null) continue;
            switch (node.getNodeType()) {
                case LEAF:
                    navigateFunc.accept(node);
                    break;
                case POINTER:
                    if (node.getNe() != null &&
                            node.getNe().getPoint() != null &&
                            region.contains(node.getNe().getPoint().getCoordinate())) {
                        navigateStack.add(node.getNe());
                    }
                    if (node.getNw() != null &&
                            node.getNw().getPoint() != null &&
                            region.contains(node.getNw().getPoint().getCoordinate())) {
                        navigateStack.add(node.getNw());
                    }
                    if (node.getSe() != null &&
                            node.getSe().getPoint() != null &&
                            region.contains(node.getSe().getPoint().getCoordinate())) {
                        navigateStack.add(node.getSe());
                    }
                    if (node.getSw() != null &&
                            node.getSw().getPoint() != null &&
                            region.contains(node.getSw().getPoint().getCoordinate())) {
                        navigateStack.add(node.getSw());
                    }
                    break;
            }
        }
    }

    @NotNull
    public synchronized List<TreePoint<T>> searchWithin(@NotNull Rectangle2D region) {
        List<TreePoint<T>> ret = new ArrayList<>();
        navigate(root,node->{
            if (node.getPoint()!=null && region.contains(node.getPoint().getCoordinate())) {
                ret.add(node.getPoint());
            }
        },region);
        return ret;
    }

    private void traverse(@NotNull TreeNode<T> start, @NotNull Consumer<TreeNode<T>> traverseFunc) {
        Stack<TreeNode<T>> traverseStack = new Stack<>();
        traverseStack.add(start);
        while(!traverseStack.isEmpty()) {
            TreeNode<T> node = traverseStack.pop();
            if (node == null) continue;
            switch (node.getNodeType()) {
                case LEAF:
                    traverseFunc.accept(node);
                    break;
                case POINTER:
                    if (node.getNe() != null) traverseStack.add(node.getNe());
                    if (node.getNw() != null) traverseStack.add(node.getNw());
                    if (node.getSe() != null) traverseStack.add(node.getSe());
                    if (node.getSw() != null) traverseStack.add(node.getSw());
                    break;
            }
        }
    }

    @NotNull
    public synchronized List<TreePoint<T>> getAllPoints() {
        List<TreePoint<T>> ret = new ArrayList<>();
        traverse(root,node->{
            if (node.getPoint()!=null) ret.add(node.getPoint());
        });
        return ret;
    }

    @NotNull
    public synchronized List<TreePoint<T>> getAllPointsWhere(@NotNull Predicate<TreePoint<T>> predicate) {
        List<TreePoint<T>> ret = new ArrayList<>();
        traverse(root,node->{
            if (node.getPoint()!=null && predicate.test(node.getPoint())) {
                ret.add(node.getPoint());
            }
        });
        return ret;
    }
}