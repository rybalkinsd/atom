package accountserver.authDAO;

import jersey.repackaged.com.google.common.base.Joiner;
import accountserver.authInfo.Token;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 05.11.2016.
 */
public class TokenDAO implements AuthDAO<Token> {
    @Override
    public List<Token> getAll() {
        return DbConnection.selectTransaction("from Token");
    }

    @Override
    public List<Token> getAllWhere(String... hqlCondidtions) {
        String fullCondition = Joiner.on(" and ").join(Arrays.asList(hqlCondidtions));
        return DbConnection.selectTransaction("from Token where " + fullCondition);
    }

    @Override
    public boolean insert(Token token) {
         return DbConnection.insertTransaction(token);
    }

    @Override
    public boolean delete(Token token) {
        return DbConnection.deleteTransaction(token);
    }

    public boolean deleteByStringToken(String token){
        return delete(getAllWhere("number = \'" + token + "\'").get(0));
    }

    public int getUserIdByStringToken(String token){
        return getAllWhere("number = \'" + token + "\'").get(0).getUserId();
    }

    public Token getTokenByUserId(int id){
        List<Token> requestResult = getAllWhere("userId = \'" + id + "\'");
        return requestResult.isEmpty() ? null : requestResult.get(0);
    }

    public Token getTokenByStringToken(String stringToken){
        List<Token> requestResult = getAllWhere("number = \'" + stringToken + "\'");
        return requestResult.isEmpty() ? null : requestResult.get(0);
    }
}
