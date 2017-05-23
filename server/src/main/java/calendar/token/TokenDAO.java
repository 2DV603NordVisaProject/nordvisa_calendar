package calendar.token;


interface TokenDAO {
    void add(Token token);
    Token get(String token);
    void update(Token token);
}
