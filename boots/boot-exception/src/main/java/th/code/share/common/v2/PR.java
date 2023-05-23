package th.code.share.common.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PageResponse
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PR<T> extends R<PageData<T>> {

    public PR(PageData<T> aPage) {
        super(aPage);
    }
}
