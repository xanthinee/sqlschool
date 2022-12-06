package sqltask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

@SuppressWarnings("java:S106")
@Aspect
@Component
public class TrackOperation {

    private final Logger logger = LogManager.getLogger(TrackOperation.class);
    private static final String BEFORE_MESSAGE = "Calling {} with args ({})";
    private static final String AFTER_MESSAGE_OK = "{} was completed successfully";
    private static final String AFTER_MESSAGE_ERROR = "{} exited abnormally with exception:";

    @Pointcut(" execution(* sqltask.applicationmenu.DAO.*())")
    public void daoMethod(){}
    @Before("daoMethod()")
    public void beforeAdvice(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("daoMethod()")
    public void afterAdvice(JoinPoint jp){
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut="daoMethod()", throwing="ex")
    public void afterThrowingAdvice(JoinPoint jp, Exception ex) {
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }
}
