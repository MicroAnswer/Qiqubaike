package com.microanswer.qiqubaike.other;

import android.util.Log;

import org.xutils.x;

public class Promise {

    private Object funParam = null; // 任务方法参数
    private Fun fun = null; // 当前任务执行方法。
    private Object returnData = null; // 方法返回值
    private Fun exception = null; // 报错执行方法

    private Promise then; // 指向下一个任务。

    public Promise(Fun fun) {
        this.fun = fun;
    }

    public Promise then(Fun fun) {
        if (this.then == null) {
            this.then = new Promise(fun);
            return this;
        } else {
            this.then.then = new Promise(fun);
            return this.then;
        }
    }

    public Promise param(Object funParam) {
        this.funParam = funParam;
        return this;
    }

    public Promise exception(Fun exception) {
        this.exception = exception;
        return this;
    }

    /**
     * 出发运行
     */
    public void promise() {
        Log.i("Promise", "开始运行");
        x.task().run(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                returnData = fun.d0(funParam);
                // Log.i("Promise - result - back", returnData.toString());
                if (then != null) {
                    x.task().post(runnableEnd);
                }
            } catch (final Throwable e) {
                // Log.w("Promise", e);
                if (exception != null) {
                    x.task().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                exception.d0(e);
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    };

    private Runnable runnableEnd = new Runnable() {
        @Override
        public void run() {
            if (then != null && then.then != null) {
                // 如果有下一个任务 且 下一个任务也有下一个任务， 将此下一个任务运行在后台
                then.promise();
            } else if (then != null) {
                // 如果有下一个任务 且 下一个任务没有下一个任务了， 那么下一个任务在主线程执行/
                try {
                    then.fun.d0(returnData);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            // Log.i("Promise - result - main", returnData.toString());
        }
    };

}

