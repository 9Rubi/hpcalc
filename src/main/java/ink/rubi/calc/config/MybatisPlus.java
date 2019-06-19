package ink.rubi.calc.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Rubi
 * @since 2019-06-19 11:06
 */
public class MybatisPlus {
    private static MybatisPlus ourInstance = new MybatisPlus();

    //首先创建静态成员变量sqlSessionFactory，静态变量被所有的对象所共享。
    public static SqlSessionFactory sqlSessionFactory = null;


    //使用静态代码块保证线程安全问题
    static {
        MybatisConfiguration configuration = new MybatisConfiguration();
        TransactionFactory factory = new JdbcTransactionFactory();
        configuration.setLogImpl(Slf4jImpl.class);
        Environment environment = new Environment("dataSource", factory,
                new HikariDataSource(getConfig("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:/init.sql'", "root", "test")));
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        configuration.addMappers("ink.rubi.calc.dao");
        configuration.setEnvironment(environment);
        dbConfig.setKeyGenerator(new H2KeyGenerator());
        configuration.getGlobalConfig().setDbConfig(dbConfig);
        sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);

    }


    private static HikariConfig getConfig(String jdbc, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbc);
        config.setUsername(username);
        config.setPassword(password);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(10000);
        config.setMaximumPoolSize(30);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTestQuery("SELECT 1");
        return config;
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return sqlSessionFactory.getConfiguration().getMapper(type, sqlSession);
    }

    public Connection getConnection() throws SQLException {
        return sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection();
    }

    public SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }


    public SqlSession openSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }

    public SqlSession openSession(Connection connection) {
        return sqlSessionFactory.openSession(connection);
    }


    public static MybatisPlus getInstance() {
        return ourInstance;
    }

    private MybatisPlus() {
    }
}
