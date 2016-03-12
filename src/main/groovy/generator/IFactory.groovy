package generator

/**
 * Created by stefangrecu on 12/03/16.
 */
interface IFactory<T> {

    T create(Map<String, Object> params)

}