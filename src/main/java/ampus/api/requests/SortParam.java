package ampus.api.requests;

public enum SortParam {

    TITLE("title"),
    DATE("remind"),
    DESCRIPTION("description");

    SortParam(String value) {
        this.value = value;
    }

    private final  String value;

    public final String value() {
        return this.value;
    }
}
