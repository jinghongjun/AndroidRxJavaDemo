package rxjava.nothingoneday.com.androidrxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 基本使用精简晋级
         */
        useRxJava01();
        useRxJava02();
        useRxJava03();
        useRxJava04();

        /**
         * 操作符使用
         * 操作符可以在传递的途中对数据进行修改
         */
        useRxJava05();
        useRxJava06();

    }

    public void useRxJava01() {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {

            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("useRxJava01: hello jinghongjun!");
                e.onComplete();
            }

        }, BackpressureStrategy.BUFFER);

        Subscriber subscriber = new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("useRxJava01: onSubscribe: ");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println("useRxJava01: onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("useRxJava01: onError: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("useRxJava01: onComplete: ");
            }
        };
        flowable.subscribe(subscriber);
    }

    public void useRxJava02() {
        Flowable<String> flowable = Flowable.just("useRxJava02: hello jinghongjun!");
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        };
        flowable.subscribe(consumer);
    }

    private void useRxJava03() {
        Flowable.just("useRxJava03: hello jinghongjun!").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    private void useRxJava04() {
        Flowable.just("useRxJava04: hello jinghongjun!")
                .subscribe(s -> System.out.println(s));
    }

    private void useRxJava05() {
        Flowable.just("useRxJava05: hello").map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + " jinghongjun";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    private void useRxJava06() {
        Flowable.just("useRxJava06: hello")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }


}
