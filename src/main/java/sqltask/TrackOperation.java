package sqltask;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@SuppressWarnings("java:S106")
@Aspect
public class TrackOperation {

    @Pointcut("within(sqltask.applicationmenu.DAO) && execution(* saveAll(*))")
    public void saveAll(){}

    @Before("saveAll()")
    public void beforeAdvice() {
        System.out.println("Data is going to be save");
    }

    @Pointcut("execution(* sqltask.applicationmenu.DAO.save())")
    public void save(){}

    @Before("save()")
    public void beforeSaveAdvice() {
        System.out.println("Entity is going to be saved");
    }
    @After("save()")
    public void afterSaveAdvice() {
        System.out.println("Entity was saved");
    }

    @Pointcut("execution(* sqltask.InitializeService.run())")
    public void run() {}
    @Before("run()")
    public void beforeRun() {
        System.out.println("IS GOING TO BE RUN");
    }
}
