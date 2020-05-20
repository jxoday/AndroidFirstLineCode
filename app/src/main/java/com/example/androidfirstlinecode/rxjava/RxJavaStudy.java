package com.example.androidfirstlinecode.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * https://blog.csdn.net/u012527802/article/details/81117684
 * @author JinXin 2020/4/7
 */
public class RxJavaStudy {

    private static final String TAG = "RxJavaStudy";

    public static void main(String[] args) {

//        helloRxJava();

        // RxJava操作符概念
        // 事件流：通过发射器发射的事件，从发射事件到结束事件的过程，这一过程称为事件流
        // 数据流：通过发射器发射的数据，从数据输入到数据输出的过程，这一过程称为数据流
        // 被观察者：事件流的上游，即Observable(被观察者)，事件流开始的地方和数据流发射的地方
        // 观察者：事件流的下游，即Observer(观察者)，事件流结束的地方和数据流接收的地方

        // 操作符分类
        operatorCategory();
    }

    private static void helloRxJava() {
        // 创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 使用Emitter事件发射器发射事件
                emitter.onNext("这是事件1");
                emitter.onNext("这是事件2");
//                emitter.onError(new Exception("这里事件发生了异常。"));
                emitter.onNext("这是事件3");
                emitter.onNext("这是事件4");
                emitter.onComplete();
            }
        });

        // 创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                // 订阅成功回调该方法，返回控制对象
//                 d.dispose();
                System.out.print("onSubscribe: " + d);
            }

            @Override
            public void onNext(String s) {
                // 这里接收被观察者发出的事件
                System.out.print("\n onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                // 错误事件
                System.out.print("\n onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                // 完成事件
                System.out.print("\n onComplete: ");
            }
        };

        // 订阅事件 观察者订阅被观察者
        observable.subscribe(observer);
    }

    /**
     * 操作符分类
     */
    private static void operatorCategory() {

        // Creating Observables（创建Observable）
        createOperator();

        // Transforming Observables（转换Observable）

        // Filtering Observables（过滤Observable）

        // Combining Observables（组合Observable）

        // Error Handling Operators（处理错误）

    }

    /**
     * 创建型操作符
     */
    private static void createOperator() {

        // Create
        CreateOperatorStudy.getInstance().create();

        // Just
        CreateOperatorStudy.getInstance().just();

        // From

        // Defer

        // Empty/Never/Throw

        // Interval

        // Range

        // Repeat

        // Start

        // Timer
    }

}
