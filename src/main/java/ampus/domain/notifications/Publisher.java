package ampus.domain.notifications;

public interface Publisher<T> {

    void subscribe(Listener<T> listener);
}
