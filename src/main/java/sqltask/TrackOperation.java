package sqltask;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@SuppressWarnings("java:S106")
@Aspect
@Component
@EnableAspectJAutoProxy
public class TrackOperation {

    private final Logger logger = LoggerFactory.getLogger(TrackOperation.class);
    private static final String BEFORE_MESSAGE = "Calling {} with args ({})";
    private static final String AFTER_MESSAGE_OK = "{} was completed successfully";
    private static final String AFTER_MESSAGE_ERROR = "{} exited abnormally with exception:";

    @Pointcut(" execution(* sqltask.applicationmenu.DAO.saveAll(*))")
    public void saveAll(){}
    @Before("saveAll()")
    public void beforeSaveAll(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("saveAll()")
    public void afterSaveAllAdvice(JoinPoint jp){
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut="saveAll()", throwing="ex")
    public void afterThrowingSaveAll(JoinPoint jp, Exception ex) {
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut("execution(public void sqltask.applicationmenu.DAO.save(*))")
    public void save(){}
    @Before("save()")
    public void beforeSaveAdvice(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("save()")
    public void afterSaveAdvice(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut="save()", throwing="ex")
    public void afterThrowingSave(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.applicationmenu.DAO.deleteAll())")
    public void deleteAll(){}

    @Before("deleteAll()")
    public void beforeDeleteAll(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("deleteAll()")
    public void afterDeleteAll(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "deleteAll()", throwing = "ex")
    public void afterThrowingDeleteAll(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.applicationmenu.DAO.getById(*))")
    public void getById() {}
    @Before("getById()")
    public void beforeGetById(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("getById()")
    public void afterGetById(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "getById()", throwing = "ex")
    public void afterThrowingGetById(JoinPoint jp, Exception ex) {
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.applicationmenu.DAO.deleteById(*))")
    public void deleteByID(){}
    @Before("deleteByID()")
    public void beforeDeleteById(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("deleteByID()")
    public void afterDeleteById(JoinPoint jp){
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "deleteByID()", throwing = "ex")
    public void afterThrowingDeleteById(JoinPoint jp, Exception ex) {
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.applicationmenu.DAO.getAll())")
    public void getAll(){}

    @Before("getAll()")
    public void beforeGetAll(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("getAll()")
    public void afterGetAll(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "getAll()", throwing = "ex")
    public void afterThrowingGetAll(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }


    @Pointcut(" execution(* sqltask.InitializeService.run())")
    public void run() {}
    @Before("run()")
    public void beforeRun(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("run()")
    public void afterRun(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "run()", throwing = "ex")
    public void afterRun(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.students.StudentDAO.updateGroupIdByStudId(*,*))")
    public void updateGroupIdByStudId(){}
    @Before("updateGroupIdByStudId()")
    public void beforeUpdateGroupIdByStudId(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("updateGroupIdByStudId()")
    public void afterUpdateGroupIdByStudId(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "updateGroupIdByStudId()", throwing = "ex")
    public void afterUpdateGroupIdByStudId(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.groups.GroupDAO.compareGroups(*))")
    public void compareGroups(){}
    @Before("compareGroups()")
    public void beforeCompareGroups(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("compareGroups()")
    public void afterCompareGroups(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "compareGroups()", throwing = "ex")
    public void afterCompareGroups(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.courses.CourseDAO.getCourseMembers(*))")
    public void getCourseMembers(){}
    @Before("getCourseMembers()")
    public void beforeGetCourseMembers(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("getCourseMembers()")
    public void afterGetCourseMembers(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "getCourseMembers()", throwing = "ex")
    public void afterGetCourseMembers(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.courses.CourseDAO.unlinkCourse(*,*))")
    public void unlinkCourse(){}
    @Before("unlinkCourse()")
    public void beforeUnlinkCourse(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("unlinkCourse()")
    public void afterUnlinkCourse(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "unlinkCourse()", throwing = "ex")
    public void afterUnlinkCourse(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }

    @Pointcut(" execution(* sqltask.courses.CourseDAO.setNewCourse(*,*))")
    public void setNewCourse(){}
    @Before("setNewCourse()")
    public void beforeSetNewCourse(JoinPoint jp) {
        logger.debug(BEFORE_MESSAGE, jp.getSignature(), jp.getArgs());
    }
    @After("setNewCourse()")
    public void afterSetNewCourse(JoinPoint jp) {
        logger.debug(AFTER_MESSAGE_OK, jp.getSignature());
    }
    @AfterThrowing(pointcut = "setNewCourse()", throwing = "ex")
    public void afterSetNewCourse(JoinPoint jp, Exception ex){
        logger.error(AFTER_MESSAGE_ERROR, jp.getSignature(), ex);
    }
}
