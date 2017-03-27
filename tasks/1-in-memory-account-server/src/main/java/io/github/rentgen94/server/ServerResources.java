package io.github.rentgen94.server;

import io.github.rentgen94.Token;
import io.github.rentgen94.TokenList;
import io.github.rentgen94.User;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

/**
 * Created by Western-Co on 27.03.2017.
 */
public class ServerResources {
    public static final ConcurrentArrayQueue<User> regUsers = new ConcurrentArrayQueue<>();
    public static final TokenList authUsers = new TokenList();
}
