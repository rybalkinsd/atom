package ru.atom.util;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by sergey on 2/3/17.
 */
public class VTest {
    private V v;
    @Before
    public void setUp() throws Exception {
        v = V.of(1, 2);
    }

    @Test
    public void serialize() throws Exception {
        Throwable ex = catchThrowable(() -> JsonHelper.toJson(v));
        assertThat(ex).isNull();
    }

    @Test
    public void deserialize() throws Exception {
        Throwable ex = catchThrowable(() -> JsonHelper.fromJson("{\"x\":1.0,\"y\":2.0}", V.class));
        assertThat(ex).isNull();
    }

}