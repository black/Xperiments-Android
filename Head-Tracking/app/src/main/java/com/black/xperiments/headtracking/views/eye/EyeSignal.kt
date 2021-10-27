package com.black.xperiments.headtracking.views.eye

data class EyeSignal(
    var rl_ep:Int = 0,
    var rt_cnt_em:Int = 0,
    var em_lat_cnt:Int = 0,
    var em_lat_in_seq:Int = 0,
    var em_lat_fep_sign:Int = 0,
    var em_lat_seq_cnt:Int = 0,
    var em_lat_fep_cnt:Int = 0,
    var em_lat_seq_th:Int = 0
)

/*
    private var rt_cnt_em = 0
    private var em_lat_cnt = 0
    private var em_lat_in_seq = 0
    private var em_lat_fep_sign = 0
    private var em_lat_seq_cnt = 0
    private var em_lat_fep_cnt = 0
    private var em_lat_seq_th = 0

*/