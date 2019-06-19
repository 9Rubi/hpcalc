package ink.rubi.calc.test;

import ink.rubi.calc.config.MybatisPlus;
import ink.rubi.calc.controller.calc.Calculator;
import ink.rubi.calc.controller.constant.ModuleFactory;
import ink.rubi.calc.controller.constant.Reality;
import ink.rubi.calc.controller.constant.ShieldFactory;
import ink.rubi.calc.dao.CombinationMapper;
import ink.rubi.calc.po.Combination;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * export：
 * call CSVWRITE ( 'C:/MyFolder/MyCSV.csv', 'SELECT * FROM MYTABLE' )
 * <p>
 * import：
 * CREATE TABLE TEST AS SELECT * FROM CSVREAD('test.csv');
 *
 * @author : Rubi
 * @version : 2019-06-18 01:12 上午
 */
@Slf4j
public class CSVPersistent {
    private static BlockingQueue<Combination> results = new LinkedBlockingDeque<>();
    public static volatile boolean wontPut = false;
    public static volatile boolean wontPersist = false;
    private static final String CSV = "wtf.csv";

    public static void main(String[] args) throws SQLException, InterruptedException {
//        writeCSV();//计算所有可能性并写入到数据库和csv
        readCSV();//从csv中读取数据到数据库
        doHandleSearch();//处理用户查询

    }

    private static void doHandleSearch() {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        MybatisPlus plus = MybatisPlus.getInstance();
        CombinationMapper mapper = plus.openSession().getMapper(CombinationMapper.class);
        List<Combination> list = mapper.selectList(null);
        list.forEach(combination -> {
            int rateMaxHp = ShieldFactory..getMaxHpDecrease() +
                    beta.getMaxHpDecrease() +
                    gamma.getMaxHpDecrease() +
                    reality.getMaxHpDecrease();
            BigDecimal maxHpDecreaseResult = Calculator.calcMaxHpDecrease(rateMaxHp, shieldLevel);
            BigDecimal maxHpIncreaseResult = Calculator.calcMaxHpIncrease(betaFactory.getMaxHpIncrease(), moduleLevel);
            Calculator.calcFinal(new BigDecimal(i),
                    Calculator.calcMaxHpDecrease(),
                    new BigDecimal(j),
                    Calculator.calcMaxHpIncrease()
            );
        });

    }

    private static void readCSV() throws SQLException {
        MybatisPlus mybatisPlus = MybatisPlus.getInstance();
        Connection connection = mybatisPlus.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS `combination`;CREATE TABLE `combination` AS SELECT * FROM CSVREAD('" + CSV + "');");
        statement.close();

        SqlSession session = mybatisPlus.openSession(connection);
        CombinationMapper mapper = session.getMapper(CombinationMapper.class);
        System.out.println(mapper.selectCount(null));
    }

    private static void writeCSV() throws SQLException {
        MybatisPlus mybatisPlus = MybatisPlus.getInstance();
        new Thread(new PutTask(results), "计算线程").start();
        new Thread(new PersistentTask(results, mybatisPlus), "持久化线程").start();
        new Thread(new ShowTask(mybatisPlus), "显示线程").start();
        for (; ; ) {
            if (wontPersist) {
                Connection connection = mybatisPlus.getConnection();
                Statement statement = null;
                try {
                    statement = connection.createStatement();
                    statement.execute("call CSVWRITE ( '" + CSV + "', 'SELECT * FROM `combination`' )");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        statement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
}

class ShowTask implements Runnable {
    private MybatisPlus mybatisPlus;
    private SqlSession session;
    private CombinationMapper mapper;

    public ShowTask(MybatisPlus mybatisPlus) {
        this.mybatisPlus = mybatisPlus;
        this.session = this.mybatisPlus.openSession();
        this.mapper = session.getMapper(CombinationMapper.class);
    }

    @Override
    public void run() {
        for (; ; ) {
            Integer count = mapper.selectCount(null);
            System.out.println("当前有" + count + "条记录");
            session.commit();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class PersistentTask implements Runnable {
    private BlockingQueue<Combination> results;
    private MybatisPlus mybatisPlus;
    private SqlSession session;

    public PersistentTask(BlockingQueue<Combination> results, MybatisPlus mybatisPlus) {
        this.results = results;
        this.mybatisPlus = mybatisPlus;
        this.session = this.mybatisPlus.openSession();

    }

    @Override
    public void run() {
        for (; ; ) {
            Combination poll = results.poll();
            if (poll != null) {
                CombinationMapper mapper = session.getMapper(CombinationMapper.class);
                mapper.insert(poll);
                session.commit();
            }
            if (poll == null && CSVPersistent.wontPut) {
                CSVPersistent.wontPersist = true;
                break;
            }
        }
    }
}

class PutTask implements Runnable {
    private BlockingQueue<Combination> results;

    public PutTask(BlockingQueue<Combination> results) {
        this.results = results;
    }


    @Override
    public void run() {
        try {
            putIn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void putIn() throws InterruptedException {
        for (int shieldLevel = 90; shieldLevel >= 1; shieldLevel--) {
            for (int moduleLevel = 90; moduleLevel >= 1; moduleLevel--) {
                loop:
                for (ModuleFactory.BetaFactory betaFactory : Calculator.BETA_FACTORIES) {
                    List<List<ShieldFactory>> lists = new ArrayList<>();
                    for (ShieldFactory alpha : Calculator.SHIELD_FACTORIES) {
                        for (ShieldFactory beta : Calculator.SHIELD_FACTORIES) {
                            for (ShieldFactory gamma : Calculator.SHIELD_FACTORIES) {
                                for (List<ShieldFactory> list : lists) {
                                    if (list.contains(alpha) && list.contains(beta) && list.contains(gamma)) {
                                        continue loop;
                                    }
                                }
                                lists.add(new ArrayList<ShieldFactory>() {{
                                    add(alpha);
                                    add(beta);
                                    add(gamma);
                                }});
                                for (Reality reality : Calculator.REALITIES) {
                                    Combination each = doForEach(shieldLevel, moduleLevel, alpha, beta, gamma, reality, betaFactory);
                                    results.put(each);
                                }
                            }
                        }
                    }
                }
            }
        }
        CSVPersistent.wontPut = true;
    }

    private static Combination doForEach(final int shieldLevel, final int moduleLevel,
                                         final ShieldFactory alpha, final ShieldFactory beta, final ShieldFactory gamma,
                                         final Reality reality, final ModuleFactory.BetaFactory betaFactory) {
        int rateMaxHp = alpha.getMaxHpDecrease() +
                beta.getMaxHpDecrease() +
                gamma.getMaxHpDecrease() +
                reality.getMaxHpDecrease();
        BigDecimal maxHpDecreaseResult = Calculator.calcMaxHpDecrease(rateMaxHp, shieldLevel);
        BigDecimal maxHpIncreaseResult = Calculator.calcMaxHpIncrease(betaFactory.getMaxHpIncrease(), moduleLevel);
        return Combination.builder().alpha(alpha.getName()).beta(beta.getName()).gamma(gamma.getName())
                .shieldLevel(shieldLevel).reality(reality.getName())
                .moduleLevel(moduleLevel).moduleBeta(betaFactory.getName())
                .maxHpDecrease(maxHpDecreaseResult).maxHpIncrease(maxHpIncreaseResult).build();
    }
}
