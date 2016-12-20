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

    /**
     * Constructs QuadTree with given {@param region}
     *
     * @param region rectangular region where objects will be stored
     */
    public QuadTree(@NotNull Rectangle2D region) {
        this.root = new TreeNode<>(region, null);
    }

    /**
     * Assigns {@param point} to {@param node}
     * @param node node to store {@param point}
     * @param point point to store in {@param node}
     */
    private void setPointForNode(@NotNull TreeNode<T> node, @NotNull TreePoint<T> point) {
        if (node.getNodeType() == TreeNode.Type.POINTER) {
            throw new QuadTreeException("Can not set item for node type POINTER");
        }
        node.setNodeType(TreeNode.Type.LEAF);
        node.setPoint(point);
    }

    /**
     * Search quadrant for point at {@param coordinate}
     * Quadrant will be selected relatively to {@param parent} center
     * @param parent node where search quadrant (subnode)
     * @param coordinate coordinate for which quadrant will be searched
     * @return Found quadrant (subnode), may be null (if not defined in {@param parent})
     */
    @Nullable
    private TreeNode<T> getQuadrantForPoint(@NotNull TreeNode<T> parent, @NotNull Point2D coordinate) {
        double centerX = parent.getRegion().getCenterX();
        double centerY = parent.getRegion().getCenterY();
        if (coordinate.getX() < centerX) {
            return coordinate.getY() < centerY ? parent.getNw() : parent.getSw();
        } else {
            return coordinate.getY() < centerY ? parent.getNe() : parent.getSe();
        }
    }

    /**
     * Inserts {@param startPoint} to free quadrant.
     * Search starts from {@param parent}
     * @param parent start node for free quadrant search
     * @param startPoint point to insert
     * @throws QuadTreeException when finds LEAF node with null TreePoint
     */
    private void insert(@NotNull TreeNode<T> parent, @NotNull TreePoint<T> startPoint) {
        Stack<TreeNode<T>> insertStack = new Stack<>();
        insertStack.add(parent);
        Stack<TreePoint<T>> pointStack = new Stack<>();
        pointStack.add(startPoint);
        while (!insertStack.isEmpty()) {
            TreeNode<T> node = insertStack.pop();
            if (node == null) continue;
            TreePoint<T> point = pointStack.pop();
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
                        node.setSe(new TreeNode<>(new Rectangle2D.Double(x + w, y + h, w, h), node));

                        insertStack.add(node);
                        pointStack.add(point);
                        insertStack.add(node);
                        pointStack.add(oldPoint);
                    }
                    break;
                case POINTER:
                    pointStack.add(point);
                    insertStack.add(getQuadrantForPoint(node, point.getCoordinate()));
                    break;
            }
        }
    }

    /**
     * Place {@param item} at {@param coordinate}
     * @param coordinate where {@param item} will be placed
     * @param item item to place at {@param coordinate}
     * @throws QuadTreeException if coordinate not within defined region {@link QuadTree}
     */
    public synchronized void set(@NotNull Point2D coordinate, @NotNull T item) {
        if (!root.getRegion().contains(coordinate)) {
            throw new QuadTreeException("Out of bounds " + coordinate);
        }
        insert(root, new TreePoint<>(coordinate, item));
    }

    /**
     * Search {@link TreeNode} at {@param coordinate}.
     * Search starts from {@param start}
     * @return found {@link TreeNode} or null if not found.
     */
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

    /**
     * Search {@param T} at given {@param coordinate}
     * @return {@link Optional} object contains search result: empty if not found found but not contains point.
     */
    @NotNull
    public synchronized Optional<T> find(@NotNull Point2D coordinate) {
        TreeNode<T> result = find(root, coordinate);
        if (result==null || result.getPoint()==null) return Optional.empty();
        return result.getPoint().getItem();
    }

    /**
     * Balances tree after removing.
     * If in leaf {@link TreeNode} left only one busy quadrant, merge all quadrants to one.
     * If in leaf {@link TreeNode} no quadrants with items, mark it as empty.
     * @param start start node for balancing
     */
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

    /**
     * Remove object from tree by {@param coordinate}
     * @return removed object or null if object is not present at {@param coordinate}
     */
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

    /**
     * Traverse nodes, which intersects with {@param region} or {@param region} contains
     * Applies {@param navigateFunc} to leaf node
     * @param start start node for traversing
     */
    private void navigate(@NotNull TreeNode<T> start,
                          @NotNull Consumer<TreeNode<T>> navigateFunc,
                          @NotNull Rectangle2D region) {
        Stack<TreeNode<T>> navigateStack = new Stack<>();
        navigateStack.add(start);
        while (!navigateStack.isEmpty()) {
            TreeNode<T> node = navigateStack.pop();
            switch (node.getNodeType()) {
                case LEAF:
                    navigateFunc.accept(node);
                    break;
                case POINTER:
                    if (node.getNe() != null && (
                            region.contains(node.getNe().getRegion()) ||
                                    region.intersects(node.getNe().getRegion()))
                            ) {
                        navigateStack.add(node.getNe());
                    }
                    if (node.getNw() != null && (
                            region.contains(node.getNw().getRegion()) ||
                                    region.intersects(node.getNw().getRegion()))
                            ) {
                        navigateStack.add(node.getNw());
                    }
                    if (node.getSe() != null && (
                            region.contains(node.getSe().getRegion()) ||
                                    region.intersects(node.getSe().getRegion()))
                            ) {
                        navigateStack.add(node.getSe());
                    }
                    if (node.getSw() != null && (
                            region.contains(node.getSw().getRegion())) ||
                            region.intersects(node.getSw().getRegion())
                            ) {
                        navigateStack.add(node.getSw());
                    }
                    break;
            }
        }
    }

    /**
     * Search and return all {@link TreePoint} which are within {@param region}
     * @return list of found {@link TreePoint}
     */
    @NotNull
    public synchronized List<TreePoint<T>> searchWithin(@NotNull Rectangle2D region) {
        List<TreePoint<T>> ret = new ArrayList<>();
        navigate(root, node->{
            if (node.getPoint()!=null && region.contains(node.getPoint().getCoordinate())) {
                ret.add(node.getPoint());
            }
        },region);
        return ret;
    }

    /**
     * Traverse all tree starting from {@param start} and apply {@param traverseFunc} to leaf node
     */
    private void traverse(@NotNull TreeNode<T> start, @NotNull Consumer<TreeNode<T>> traverseFunc) {
        Stack<TreeNode<T>> traverseStack = new Stack<>();
        traverseStack.add(start);
        while(!traverseStack.isEmpty()) {
            TreeNode<T> node = traverseStack.pop();
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

    /**
     * @return all {@link TreePoint} stored in tree
     */
    @NotNull
    public synchronized List<TreePoint<T>> getAllPoints() {
        List<TreePoint<T>> ret = new ArrayList<>();
        traverse(root, node->{
            if (node.getPoint()!=null) ret.add(node.getPoint());
        });
        return ret;
    }

    /**
     * @return all {@link TreePoint} stored in tree and satisfies {@param predicate}
     */
    @NotNull
    public synchronized List<TreePoint<T>> getAllPointsWhere(@NotNull Predicate<TreePoint<T>> predicate) {
        List<TreePoint<T>> ret = new ArrayList<>();
        traverse(root, node->{
            if (node.getPoint()!=null && predicate.test(node.getPoint())) {
                ret.add(node.getPoint());
            }
        });
        return ret;
    }
}