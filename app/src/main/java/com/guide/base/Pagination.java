package com.guide.base;

public interface Pagination {

    String OFFSET = "offset";

    String LIMIT = "limit";

    void setOffset(int offset);

    void setLimit(int limit);


}
