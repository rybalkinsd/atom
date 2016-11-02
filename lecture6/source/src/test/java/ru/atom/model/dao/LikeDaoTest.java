package ru.atom.model.dao;

import org.junit.Test;
import ru.atom.model.data.Like;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LikeDaoTest {
    private LikeDao likeDao = new LikeDao();

    @Test
    public void testInsert() throws Exception {
        int before = likeDao.getAll().size();
        likeDao.insert(new Like(2, 16));
        assertEquals(before + 1, likeDao.getAll().size());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Like> likes = likeDao.getAll();
        System.out.println(likes);
        assertTrue(likes.size() > 0);
    }

    @Test
    public void testGetAllWhere() throws Exception {
        List<Like> likeInRange = likeDao.getAllWhere("target between 10 and 22");
        assertTrue(likeInRange.size() > 0);
    }

    @Test
    public void testGetAllWhere1() throws Exception {
        List<Like> likeInRange = likeDao.getAllWhere("target between 10 and 22", "source = target");
        assertTrue(likeInRange.isEmpty());

    }

}