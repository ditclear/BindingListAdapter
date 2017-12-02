

## BindingListAdapter

告别反复、冗余的自定义Adapter，让开发者的重点落在数据上，做到数据驱动UI

### demo

[demo.apk](demo.apk)

### gif：

![singletype](/Users/vienan/Desktop/github/BindingListAdapter/gif/singletype.gif)                                 ![multitype](/Users/vienan/Desktop/github/BindingListAdapter/gif/multitype.gif) 



## Usage

提供了SingleTypeAdapter 和 MultiTypeAdapter

- **SingleTypeAdapter**  单类型

```Kotlin
val dataSource=ObservableArrayList<T>()
val mAdapter by lazy {
    SingleTypeAdapter<T>(this, R.layout.list_item, dataSource)
}
```

- **MultiTypeAdapter** 多类型

```kotlin
val dataSource=ObservableArrayList<Any>()
val mAdapter by lazy {
    MultiTypeAdapter(this, dataSource, object : MultiTypeAdapter.MultiViewTyper {
        override fun getViewType(item: Any): Int =
            when(item){  //返回对应的Item View type
                is ItemWrapper -> item.type
                is String -> ItemType.TYPE_5
                else -> throw Resources.NotFoundException("${item::class} 找不到相应的ViewType")
            }

    }).apply {
    	//将布局类型与布局对应
        addViewTypeToLayoutMap(ItemType.TYPE_0, R.layout.multi_type_0)
        addViewTypeToLayoutMap(ItemType.TYPE_1, R.layout.multi_type_1)
        addViewTypeToLayoutMap(ItemType.TYPE_2, R.layout.multi_type_2)
        addViewTypeToLayoutMap(ItemType.TYPE_3, R.layout.multi_type_3)
        addViewTypeToLayoutMap(ItemType.TYPE_4, R.layout.multi_type_4)
        addViewTypeToLayoutMap(ItemType.TYPE_5, R.layout.multi_type_5)
        }
    }
```

#### 操作

改变数据源dataSoruce即可，通过回调在内部会调用Adapter相应的notify方法

因此方法同ArrayList相同

#### 监听

提供了ItemClickPresenter<T>来处理点击事件。

```kotlin
//单类型,可根据view的id进行相应的处理
override fun onItemClick(v: View?, item: String) {
     Toast.makeText(this,item,Toast.LENGTH_SHORT).show()
}
//多类型,根据view的id和item的类型进行区分处理
 override fun onItemClick(v: View?, item: Any) {
        when(item){
            is ItemWrapper -> Toast.makeText(this, item.bean, Toast.LENGTH_SHORT).show()
            is String -> Toast.makeText(this, item.split("").reversed().joinToString(""), Toast.LENGTH_SHORT).show()
        }
    }
```

#### 装饰器

如果以上不能满足需求，可以通过设置ItemDecorator来进行附加的设置

```kotlin
override fun decorator(holder: BindingViewHolder<ViewDataBinding>?, position: Int, viewType: Int) {
  	//可根据viewType进行区分
	//do sth..
}
```

### Thanks To

[markzhai](https://github.com/markzhai)/[DataBindingAdapter](https://github.com/markzhai/DataBindingAdapter)

### License

---

[MIT](LICENSE.txt)





