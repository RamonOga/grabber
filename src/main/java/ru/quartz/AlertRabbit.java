package ru.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.PropertiesCreator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            List<Long> store = new ArrayList<>();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            try (Connection conn = getConnection()) {
                data.put("store", conn);
                JobDetail job = newJob(Rabbit.class)
                        .usingJobData(data)
                        .build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(Integer.parseInt(PropertiesCreator
                                .getProperties("rabbit.properties")
                                .getProperty("rabbit.interval")))
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);
                Thread.sleep(10000);
            }
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection conn = (Connection) context.getJobDetail().getJobDataMap().get("store");
            try(Statement stat = conn.createStatement()) {

                String query = String.format("insert into rabbit (create_date) values ('%s')",
                        System.currentTimeMillis());
                stat.execute(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        Connection rsl = null;
        try (InputStream is = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            Properties prop = new Properties();
            prop.load(is);
            Class.forName(prop.getProperty("driver"));
            rsl = DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("login"),
                    prop.getProperty("password"));

        } catch (Exception is) {
            is.fillInStackTrace();
            System.out.println(is.getMessage());
        }
        return rsl;
    }
}