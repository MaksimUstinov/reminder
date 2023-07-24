package ampus.domain.notifications;

public interface Listener<T> {

    void update(T t);
}
