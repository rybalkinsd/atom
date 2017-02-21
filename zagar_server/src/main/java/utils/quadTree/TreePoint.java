package utils.quadTree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.geom.Point2D;
import java.util.Optional;

/**
 * Created by xakep666 on 16.12.16.
 */
public class TreePoint<T> implements Comparable<TreePoint<T>>{
    @NotNull
    private final Point2D coordinate;
    @Nullable
    private final T item;

    TreePoint(@NotNull Point2D coordinate, @Nullable T item) {
        this.coordinate = coordinate;
        this.item = item;
    }

    @NotNull
    public Point2D getCoordinate() {
        return coordinate;
    }

    @NotNull
    public Optional<T> getItem() {
        return Optional.ofNullable(item);
    }

    @Override
    public int compareTo(@NotNull TreePoint<T> tmp) {
        if (coordinate.getX()<tmp.coordinate.getX()) {
            return -1;
        } else if (coordinate.getX()>tmp.coordinate.getX()) {
            return 1;
        } else {
            if (coordinate.getY()<tmp.coordinate.getY()) {
                return -1;
            } else if (coordinate.getY()>tmp.coordinate.getY()) {
                return 1;
            }
            return 0;
        }
    }
}
