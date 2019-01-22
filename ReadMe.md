

## BindingListAdapter

![](https://img.shields.io/badge/minSdk-14-brightgreen.svg)    ![](https://img.shields.io/badge/version-1.0.3-green.svg)  ![](https://img.shields.io/badge/recyclerview_version-28.0.0-blue.svg)  ![](https://img.shields.io/badge/kotlin_version-1.3.11-blue.svg) ![](https://img.shields.io/badge/androidx_support-true-green.svg)

告别反复、冗余的自定义Adapter，让开发者的重点落在数据上，做到数据驱动UI

只需要关心Item，编写RecyclerView.Adapter竟然如此简单（重新定义）

[English Version](README_EN.md)

### demo

https://fabu.love/7me2fm

### 示例

![singletype](gif/singletype.gif)                       ![multitype](gif/multitype.gif)

#### 更多示例

PaoNet : [https://github.com/ditclear/paonet](https://github.com/ditclear/paonet)

### Download

```groovy

implementation 'com.ditclear:bindinglistadapter:1.0.1'

// if you use androidx
implementation 'com.ditclear:bindinglistadapterx:1.0.0'

```

### 快速开始

体验一下创建一个单类型RecyclerView.Adapter，只需要简单的3步

1. 为`Item`创建databinding形式的`list_item.xml`文件

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <layout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:app="http://schemas.android.com/apk/res-auto">

       <data>

           <variable
                   name="item"
                   type="String"/>
           <variable
                   name="presenter"
                   type="io.ditclear.bindingadapter.ItemClickPresenter"/>
       </data>

       <TextView
               android:layout_width="match_parent"
               android:layout_height="?actionBarSize"
               android:onClick="@{(v)->presenter.onItemClick(v,item)}"
               android:gravity="center"
               android:text="@{item}"/>
   </layout>
   ```

2. 在`Activity/Fragment`文件中创建我们的数据源`dataSource`和对应的`SingleTypeAdapter`

   ```kotlin
   val dataSource=ObservableArrayList<T>() //通常位于ViewModel层
   val mAdapter by lazy {
       SingleTypeAdapter<T>(this, R.layout.list_item, dataSource)
      .apply{
          itemPresenter=this@currentActity
      }
   }
   ```

3. 进行`adapter`和`recyclerView`的绑定

   ```kotlin
   recyclerView.apply{
       adapter = mAdapter
     	layoutManager = LinearLayoutManager(context)
   }
   ```

Over，大功告成。接下来只需改变数据源`DataSource`即可

```kotlin
//add item
dataSource.add(item)
//remove item
dataSource.remove(item) // or dataSource.removeAt(indexOfItem)
//setNewList
dataSource.clear()
dataSource.addAll(newList)
//batch remove 
dataSouce.rangeRemove(startIndex ,offset) //kotin extension

//and so on
```

#### Why?

Android的`databinding`框架提供了`ObservableArrayList`类，它提供了对应于RecyclerView.Adapter的几个回调。

```kotlin
        dataSource.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(contributorViewModels: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeChanged(i, i1)
            }

            override fun onItemRangeInserted(contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
                notifyItemRangeInserted(i, i1)
            }

            override fun onItemRangeMoved(contributorViewModels: ObservableList<T>, i: Int, i1: Int, i2: Int) {
                notifyItemMoved(i, i1)
            }

            override fun onItemRangeRemoved(contributorViewModels: ObservableList<T>, i: Int, i1: Int) {
                if (contributorViewModels.isEmpty()) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeRemoved(i,i1)
                }

            }
        })
```

所以只需要在对应的回调中调用Adapter对应的方法即可，再也不用手动调用adapter.notifyDataChanged()了。

具体代码见[SingleTypeAdapter.kt](library-kotlin/src/main/java/io/ditclear/bindingadapter/SingleTypeAdapter.kt)

而最Magic的地方在于使用了DataBinding技术之后我们不用再为Adapter的Item创建对应的ViewHolder类，当然也不需要再去`findView`然后设置什么`text`和`click`事件 。这也是为何在以前需要创建多个Adapter的原因。

在DataBinding中这些都不需要再考虑，开发者只需要注意数据对不对和在xml中绑定对应的值就好，具体到了单个Item。

#### 如何做到的？

仅需几行代码。。[BindingViewAdapter.kt](library-kotlin/src/main/java/io/ditclear/bindingadapter/BindingViewAdapter.kt)

```kotlin

override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val item = list[position]
        //pending binding itemModel
        holder.binding.setVariable(BR.item,item)
        //pending binding presenter
        holder.binding.setVariable(BR.presenter, itemPresenter)
        holder.binding.executePendingBindings()
        //set decorator
        itemDecorator?.decorator(holder, position, getItemViewType(position))
    }

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BindingViewHolder(DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutRes, parent, false))
```

用前面的`list_item.xml`来作为例子

`holder.binding` 即DataBinding框架为其生成的`ListItemBinding`对象

`setVariable(BR.item,item)`即将需要的对象给我们的binding文件，可以看一下`ListItemBinding`的`setVariable`方法

```java
@Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.item == variableId) {
            setItem((java.lang.String) variable);
        }
        else if (BR.presenter == variableId) {
            setPresenter((io.ditclear.bindingadapter.ItemClickPresenter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }
```

BR.item和BR.presenter 便是我们需要的Key值。

那它们又是从哪来的呢？了解DataBinding的已经知道了

```xml
   <data>

        <variable
                name="item"
                type="String"/>
        <variable
                name="presenter"
                type="io.ditclear.bindingadapter.ItemClickPresenter"/>
    </data>
```

在xml文件中，即指定了我们需要的key和需要的对象类型。

因为在`onBindViewHolder`方法已经指定了它们的key值为item和presenter。

所以任何相应的xx_item.xml文件中variable都需要是这两者，否则数据无法正常绑定。而且可以算得上是团队规范的一种。

##### 所以，我们只需要关心怎么在xml将数据绑定到对于的View上就完事了，写一个Adapter竟然如此简单。

### Usage

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

*the same as ArrayList*  具体见[SingleTypeListKotlin.kt](https://github.com/ditclear/BindingListAdapter/blob/0473dfe0150bd7049ac7f4f404eb12027298c8de/app/src/main/java/io/ditclear/app/singletype/SingleTypeListKotlin.kt)和[MultiTypeListKotlin.kt](https://github.com/ditclear/BindingListAdapter/blob/0473dfe0150bd7049ac7f4f404eb12027298c8de/app/src/main/java/io/ditclear/app/multitype/MultiTypeListKotlin.kt)

改变数据源dataSource即可，通过回调在内部会调用Adapter相应的notify方法

因此方法与ArrayList相同

```kotlin
//add item
dataSource.add(item)
//remove item
dataSource.remove(item) // or dataSource.removeAt(indexOfItem)
//setNewList
dataSource.clear()
dataSource.addAll(newList)
//batch remove 
dataSouce.rangeRemove(startIndex ,offset) //kotin extension

//and so on
```

#### 监听

提供了ItemClickPresenter<T>来处理点击事件。

```kotlin
//单类型,可根据view的id进行相应的处理
override fun onItemClick(v: View, item: String) {
     Toast.makeText(this,item,Toast.LENGTH_SHORT).show()
}
//多类型,根据view的id和item的类型进行区分处理或者获取到index然后更加ViewType来进行区分
 override fun onItemClick(v: View, item: Any) {
        when(item){
            is ItemWrapper -> Toast.makeText(this, item.bean, Toast.LENGTH_SHORT).show()
            is String -> Toast.makeText(this, item.split("").reversed().joinToString(""), Toast.LENGTH_SHORT).show()
        }
    }
```

#### 装饰器

如果以上不能满足需求，可以通过设置ItemDecorator来进行附加的设置

```kotlin
override fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int) {
  	//可根据viewType进行区分
	//do sth..
}
```


### Thanks To

[markzhai](https://github.com/markzhai)/[DataBindingAdapter](https://github.com/markzhai/DataBindingAdapter)

### License

[MIT](LICENSE.txt)





