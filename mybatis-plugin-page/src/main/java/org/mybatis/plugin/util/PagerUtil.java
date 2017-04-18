package org.mybatis.plugin.util;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.model.QueryParam;

public class PagerUtil {
    public static <T> Pager<T> getPager(List<T> rows, QueryParam queryParam) {
        Pager<T> pagerModel = new Pager<T>();
        pagerModel.setList(rows);
        pagerModel.setPage(queryParam.getPage());
        pagerModel.setTotal(queryParam.getCount());
        int totalPage = getTotalPage(queryParam.getLimit(), pagerModel.getTotal());
        pagerModel.setTotalPage(totalPage);
        return pagerModel;
    }

    private static int getTotalPage(int limit, int records) {
        return records % limit == 0 ? records / limit : records / limit + 1;
    }
}
