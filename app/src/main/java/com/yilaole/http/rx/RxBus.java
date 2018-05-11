package com.yilaole.http.rx;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 使用Rxbus跳转
 */
public class RxBus {

    private static volatile RxBus mDefaultInstance;
    private final Subject<Object, Object> _bus;


    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 根据code进行分发
     *
     * @param code 事件code
     * @param o
     */
    public void post(int code, Object o) {
        _bus.onNext(new RxBusBaseMessage(code, o));

    }


    /**
     * 根据传递的code和 eventType 类型返回特定类型(eventType)的 被观察者
     * 对于注册了code为0，class为voidMessage的观察者，那么就接收不到code为0之外的voidMessage。
     *
     * @param code      事件code
     * @param eventType 事件类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(final int code, final Class<T> eventType) {
        return _bus.ofType(RxBusBaseMessage.class)
                .filter(new Func1<RxBusBaseMessage, Boolean>() {
                    @Override
                    public Boolean call(RxBusBaseMessage o) {
                        //过滤code和eventType都相同的事件
                        return o.getCode() == code && eventType.isInstance(o.getObject());
                    }
                }).map(new Func1<RxBusBaseMessage, Object>() {
                    @Override
                    public Object call(RxBusBaseMessage o) {
                        return o.getObject();
                    }
                }).cast(eventType)

                ;
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return _bus.hasObservers();
    }

    /**
     * 实战项目证明，Rxbus在退出应用的时，并没有释放，需要手动调用释放，否则调用的地方UI会不更新，引起bug（手机直接杀死应用不影响）
     *
     * @return
     */
    public boolean release() {
        if (mDefaultInstance != null) {
            mDefaultInstance = null;
        }
        return true;
    }


}
