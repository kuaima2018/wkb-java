package com.chinadrtv.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-6
 */
@Aspect
public class ControllerAroundTracer {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerAroundTracer.class);

    private ObjectMapper objectMapper;

    public Long getTimeThresholdInSeconds() {
        return timeThresholdInSeconds;
    }

    public void setTimeThresholdInSeconds(Long timeThresholdInSeconds) {
        this.timeThresholdInSeconds = timeThresholdInSeconds;
    }

    protected Long timeThresholdInSeconds;

    public ControllerAroundTracer()
    {
        objectMapper=new ObjectMapper();
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            objectMapper.setDateFormat(simpleDateFormat);
        }catch (Exception exp)
        {
            logger.error("init controller log error:",exp);
        }
    }


    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerBean() {}

    @Pointcut("execution(* com..*.controller.*.*(..))")
    public void methodPointcut() {}

    @Around("controllerBean() && methodPointcut() ")
    public Object aroundMethodInControllerClass(ProceedingJoinPoint pjp) throws Throwable{
        long startTimeMillis=0;
        String methodName="";
        Object result;
        if(logger!=null&&logger.isDebugEnabled())
        {
            try{
                if(pjp!=null&&(pjp.getSignature() instanceof MethodSignature))
                {
                    MethodSignature methodSignature=(MethodSignature)pjp.getSignature();
                    log(methodSignature,pjp.getArgs());
                    methodName=methodSignature.getDeclaringType()+"."+methodSignature.getName();
                }
            }catch (Exception e)
            {
                logger.error("log unkown error:", e);
            }

            startTimeMillis=System.currentTimeMillis();
        }


        try{
            result=pjp.proceed();
        }
        finally {
            if(logger!=null&&logger.isDebugEnabled())
            {
                long lastTime = System.currentTimeMillis() - startTimeMillis;
                logger.debug(methodName+":"+lastTime);
                if(timeThresholdInSeconds!=null&&timeThresholdInSeconds.longValue()>0)
                {
                    long temp=lastTime/1000;
                    if(temp>=timeThresholdInSeconds)
                    {
                        logger.error("time threshold:"+methodName);
                    }
                }
            }
        }
        return result;
    }

    public List<String> getPrefixInludeTypeList() {
        return prefixInludeTypeList;
    }

    public void setPrefixInludeTypeList(List<String> prefixInludeTypeList) {
        this.prefixInludeTypeList = prefixInludeTypeList;
    }

    private List<String> prefixInludeTypeList;

    private boolean isLogType(Class clazz)
    {
        if(clazz.isPrimitive())
            return true;
        if(clazz.getName().startsWith("java.lang."))
            return true;

        if(clazz.getName().startsWith("java.math."))
            return true;
        if(clazz.getName().startsWith("com.heima"))
            return true;

        //配置中读取的类型
        if(prefixInludeTypeList!=null)
        {
            for(String item :prefixInludeTypeList)
            {
                if(clazz.getName().startsWith(item))
                    return true;
            }
        }

        //如果是数组，那么获取类型并判断
        if(clazz.isArray()&&clazz.getComponentType()!=null)
        {
            return isLogType(clazz.getComponentType());
        }
        return false;
    }

    /**
     * 记录有业务参数的方法以及业务数据
     * 目前只记录chinadrtv包里面的类型数据以及基本类型数据
     * @param methodSignature
     * @param datas
     */
    private void log(MethodSignature methodSignature,Object[] datas)
    {
        if(methodSignature.getParameterTypes()!=null&&methodSignature.getParameterTypes().length>0)
        {
            boolean bLog=false;
            for(int i=0;i<methodSignature.getParameterTypes().length;i++)
            {
                Class paramterClass=methodSignature.getParameterTypes()[i];
                if(this.isLogType(paramterClass))
                {
                    if(bLog==false)
                    {
                        bLog=true;
                        logger.debug(methodSignature.getDeclaringType()+"."+methodSignature.getName());
                    }
                    //
                    if(datas[i]==null)
                    {
                        logger.debug(methodSignature.getParameterNames()[i]+ ":<null>");
                    }
                    else
                    {
                        if(paramterClass.isPrimitive())
                        {
                            logger.debug(methodSignature.getParameterNames()[i]+ ":"+String.valueOf(datas[i]));
                        }
                        else
                        {
                            try{
                                logger.debug(methodSignature.getParameterNames()[i]+ ":"+objectMapper.writeValueAsString(datas[i]));
                            }catch (Exception exp)
                            {
                                logger.error("controller log error:",exp);
                            }
                        }
                    }
                }
            }
        }
    }
}
