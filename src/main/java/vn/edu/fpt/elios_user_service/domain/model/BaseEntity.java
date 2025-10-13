package vn.edu.fpt.elios_user_service.domain.model;

public class BaseEntity<T> {
    private T id;

    public BaseEntity() {}

    public BaseEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
