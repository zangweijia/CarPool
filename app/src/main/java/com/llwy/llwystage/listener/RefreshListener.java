package com.llwy.llwystage.listener;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by ZWJ on 2018/3/13.
 */

public interface RefreshListener {

    void  OnRefreshListener(RefreshLayout refreshlayout);

    void  OnLoadmoreListener(RefreshLayout refreshlayout);
}
