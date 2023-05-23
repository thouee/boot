package th.code.share.common.v3.common;

import java.util.Collection;

public class PageData<T> {

    private Integer total;
    private Integer pageNum;
    private Integer pageSize;
    private Collection<T> records;
}
