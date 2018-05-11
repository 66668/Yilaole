package lib.yilaole.takephoto.permission;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import lib.yilaole.takephoto.app.TakePhoto;
import lib.yilaole.takephoto.model.InvokeParam;

public class TakePhotoInvocationHandler implements InvocationHandler{
    private TakePhoto delegate;
    private InvokeListener listener;
    public static TakePhotoInvocationHandler of(InvokeListener listener){
        return new TakePhotoInvocationHandler(listener);
    }

    private TakePhotoInvocationHandler(InvokeListener listener) {
        this.listener=listener;
    }

    /**
     * 绑定委托对象并返回一个代理类
     * @param delegate
     * @return
     */
    public Object bind(TakePhoto delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        PermissionManager.TPermissionType type=listener.invoke(new InvokeParam(proxy,method,args));
        if(proxy instanceof TakePhoto){
            if(!PermissionManager.TPermissionType.NOT_NEED.equals(type)){
                ((TakePhoto)proxy).permissionNotify(type);
            }
        }
        return method.invoke(delegate, args);
    }
}