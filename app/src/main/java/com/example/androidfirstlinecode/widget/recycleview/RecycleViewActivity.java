package com.example.androidfirstlinecode.widget.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


import com.example.androidfirstlinecode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 注：在项目的build.gradle中添加相应的依赖库
 * @author Administrator
 */
public class RecycleViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(getStaggeredGridLayoutManager());
        FruitAdapter fruitAdapter = new FruitAdapter(getStaggeredGridLayoutData());
        recyclerView.setAdapter(fruitAdapter);
    }

    private RecyclerView.LayoutManager getLinearLayoutManager(){
        // 线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // LinearLayoutManager.VERTICAL 设置水平排列
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // LinearLayoutManager.HORIZONTAL 设置垂直排列
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;
    }

    private List<Fruit> getLinearLayoutData() {
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit("test1",1));
        fruitList.add(new Fruit("test2",1));
        fruitList.add(new Fruit("test3",1));
        fruitList.add(new Fruit("test4",1));
        fruitList.add(new Fruit("test5",1));
        fruitList.add(new Fruit("test6",1));
        fruitList.add(new Fruit("test7",1));
        return fruitList;
    }

    private RecyclerView.LayoutManager getStaggeredGridLayoutManager(){
        // 瀑布流布局
        // StaggeredGridLayoutManager 的构造函数接受两个参数
        // 第一个参数用于指定布局的列数，传入2表示会把布局分为2列；
        // 第二个参数用于指定布局的排列方向，传入StaggeredGridLayoutManager.VERTICAL表示会让布局纵向排列
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        return layoutManager;
    }

    private List<Fruit> getStaggeredGridLayoutData() {
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit("窗透初晓日照西桥云自摇窗透初晓日照西桥云自摇窗透初晓日照西桥云自摇",1));
        fruitList.add(new Fruit("想你当年荷风微摆的衣角想你当年荷风微摆的衣角",1));
        fruitList.add(new Fruit("木雕流金岁月涟漪七年前封笔木雕流金岁月涟漪七年前封笔木雕流金岁月涟漪七年前封笔木雕流金岁月涟漪七年前封笔",1));
        fruitList.add(new Fruit("最怕不觉泪已拆两行",1));
        fruitList.add(new Fruit("我在人间彷徨寻不到你的天堂，东瓶西镜放恨不能遗忘我在人间彷徨寻不到你的天堂，东瓶西镜放恨不能遗忘我在人间彷徨寻不到你的天堂，东瓶西镜放恨不能遗忘",1));
        fruitList.add(new Fruit("又是清明雨上折菊寄到你身旁，把你最爱的歌来轻轻唱",1));
        fruitList.add(new Fruit("想你在每个夜晚想你在每个夜晚想你在每个夜晚想你在每个夜晚想你在每个夜晚",1));
        return fruitList;
    }

}
