package com.example.androidfirstlinecode.content_provider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androidfirstlinecode.R;

/**
 * 内容提供器
 *
 * 内容提供器的方法一般有两种
 *      一：使用现有的内容提供器来读取和操作相应程序中的数据
 *      二：创建自己的内容提供器给我们程序的数据提供外部访问接口
 *
 *   如果一个应用程序通过内容提供器对其数据提供了外部访问接口，那么任何其他的应用程序就都可以对这部分数据进行访问。
 * Android系统中自带的电话簿、短信、媒体库等程序都提供了类似的访问接口，这就使得第三方应用程序可以充分地利用这部
 * 分数据来实现更好的功能
 *
 * @author JinXin
 */
public class ContentResolverTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resolver_test);

        initContentResolver();
    }

    /**
     * ContentResolver的基本用法
     *
     *   对于每一个应用程序来说，如果想要访问内容提供器中共享的数据，就一定要借助ContentResolver类，
     * 可以通过Context中的getContentResolver()方法获取到该类的实例。ContentResolver中提供了一系
     * 列的方法用于对数据进行CRUD操作，其中 insert()方法用于添加数据，update()方法用于更新数据，
     * delete()方法用于删除数据，query()方法用于查询数据。
     *
     *   ContentResolver中的增删改查方法接收的参数使用一个 Uri代替，这个参数被称为内容URI。内容URI
     * 给内容提供器中的数据建立了唯一标识符，它主要有两部分组成：authority 和 path。authority是用于
     * 对不同的应用程序做区分的，一般为了避免冲突，都会采用程序包名的方式来进行命名。path则是用于对同
     * 一应用程序中不同的表做区分的，通常都会添加到authority的后面。因此，内容URI最标准的格式写法如下：
     *      content://com.example.app.provider/table1
     *      content://com.example.app.provider/table2
     *
     */
    private void initContentResolver() {
    }
}
