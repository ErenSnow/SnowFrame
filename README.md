# SnowFrame
SnowFrame框架，自用的一款快速开发的框架，其中包含了常用的三方框架：

    Fragmentation
    Glide
    BaseRecyclerViewAdapterHelper
    Retrofit
    Rxjava2
    okhttp
    Logger
    Butterknife
    Eventbus
    Autosize
    SmartRefresh
    AVLoadingIndicatorView

使用动态代理解决耦合问题，代替 Dagger2 ，关键类 InstanceUtil 如下所示：
```
public class InstanceUtil {

    /**
     * @param <T> 返回实例的泛型类型
     */
    public static <T> T getInstance(Object o, int i) {
        try {
            return (T) ((Class) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
