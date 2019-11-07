package com.example.androidfirstlinecode.widget.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

import java.util.List;

/**
 * 为RecyclerView准备一个适配器，继承自RecycleView.Adapter，
 * 并将泛型指定为FruitAdapter.viewHolder
 * FruitAdapter.viewHolder是在FruitAdapter中定义的内部类
 * @author Administrator
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> fruitList;

    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    /**
     * 定义一个内部类ViewHolder，ViewHolder要继承自RecycleView.viewHolder
     * 然后ViewHolder的构造函数中要传入一个View参数，这个参数通常是RecycleView子项的最外层布局
     * 完成以上就可以通过finViewById()方法获取布局的控件实例
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;
        TextView fruitName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.fruit_image);
            fruitName = itemView.findViewById(R.id.fruit_name);
        }
    }

    /**
     * 创建ViewHolder实例，加载item布局
     * 并把加载的布局传入到构造函数中，将ViewHolder实例返回
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate三个参数
        // 第一个是resource ID，指明了当前的Fragment对应的资源文件；
        // 第二个参数是父容器控件；
        // 第三个参数指定成false，表示只让我们在父布局中声明的layout属性生效，但不为这个View添加父布局，因为一旦view有了父布局之后，它就不能再添加到ListView中
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit, parent, false);

        // 换成瀑布流布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fruit_staggered, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.fruitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                Fruit fruit = fruitList.get(adapterPosition);
                Toast.makeText(v.getContext(), fruit.getName(), Toast.LENGTH_LONG).show();
            }
        });

        return holder;
    }

    /**
     * 用于对RecycleView子项的数据进入赋值
     * 通过position参数或去当前项的实例
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
//        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    /**
     * 告诉RecycleView一共有多少子项
     * @return
     */
    @Override
    public int getItemCount() {
        return fruitList.size();
    }

}
