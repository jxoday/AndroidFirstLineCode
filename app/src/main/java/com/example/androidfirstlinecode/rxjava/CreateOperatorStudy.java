package com.example.androidfirstlinecode.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author JinXin 2020/4/8
 */
public class CreateOperatorStudy {

    private static CreateOperatorStudy instance = new CreateOperatorStudy();

    public static CreateOperatorStudy getInstance() {
        return instance;
    }

    /**
     * Create操作符
     * 创建一个被观察者，同时定义并发送事件，手动维护事件的发送和结束
     * 解释来源 https://blog.csdn.net/u012527802/article/details/81117684
     */
    public void create() {
        // 创建被观察者
        Observable.create(new ObservableOnSubscribe<String>() {

            //默认在主线程里执行该方法
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("学习Create操作符");
            }
        })
        // 创建观察者并订阅
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.print("create  onSubscribe: " + d);
            }

            @Override
            public void onNext(String s) {
                System.out.print("\n" + "create  onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.print("\n" + "create  onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.print("\n" + "create  onComplete: ");
            }
        });
    }

    /**
     * Just操作符
     * 创建一个被观察者，并发送事件，发送的事件不可以超过10个
     * 解释来源 https://blog.csdn.net/u012527802/article/details/81117684
     */
    public void just() {
        // 创建被观察者
        Observable.just("学习Just操作符").subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print("\n" + "just  onSubscribe: " + d);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.print("\n" + "just  onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print("\n" + "just  onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.print("\n" + "just  onComplete: ");
                    }
                });
    }

}
