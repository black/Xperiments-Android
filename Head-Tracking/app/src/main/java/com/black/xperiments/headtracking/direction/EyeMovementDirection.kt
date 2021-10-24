package com.black.xperiments.headtracking.direction

import com.jins_jp.meme.MemeRealtimeData

class EyeMovementDirection(var data:MemeRealtimeData) {

    private var eyeSignal = EyeSignal()
    init{

    }

    private var rl_ep = 0
    fun getHorizontalDirection():Int{
        if (data.eyeMoveLeft>1 || data.eyeMoveRight>1) {
            eyeSignal.rl_ep++;
            var d1ma3 = 0
            if (data.eyeMoveLeft>1) {
                d1ma3 = -data.eyeMoveLeft
            } else if (data.eyeMoveRight>1) {
                d1ma3 = data.eyeMoveRight
            }
        }

        eyeSignal.rt_cnt_em += 1
        val em_min_th = 1

        //lalteral
        if (data.eyeMoveRight > em_min_th || data.eyeMoveLeft > em_min_th) {
            eyeSignal.em_lat_cnt += 1
            eyeSignal.em_lat_in_seq = 1 //in seqence flag
            eyeSignal.em_lat_fep_sign = if (data.eyeMoveRight > em_min_th) 1 else -1 //set sign
            if (eyeSignal.rt_cnt_em - eyeSignal.em_lat_fep_cnt <= eyeSignal.em_lat_seq_th) {
                eyeSignal.em_lat_seq_cnt += 1
            }
            eyeSignal.em_lat_fep_cnt = eyeSignal.rt_cnt_em //set former peak count
        }


        //finish seqencial peak detection
        if (eyeSignal.em_lat_in_seq == 1 && (eyeSignal.rt_cnt_em - eyeSignal.em_lat_fep_cnt > eyeSignal.em_lat_seq_th)) {
            var direction = eyeSignal.em_lat_fep_sign * ((eyeSignal.em_lat_seq_cnt % 2) * 2 - 1);
//            var event_em_lat = new CustomEvent('jmctrllib_eyemove_lat', {
//                detail: {
//                    direction: direction,
//                    count: em_lat_seq_cnt
//            }
//            });
            //document.dispatchEvent(event_em_lat);
            eyeSignal.em_lat_in_seq = 0
            eyeSignal.em_lat_seq_cnt = 1
        }
        return rl_ep
    }

    /*
    fun getVerticalDirection():Int{
        if (data.eyeMoveUp>1 || data.eyeMoveDown>1) {
            rl_ep++;
            var d1ma3 = 0
            if (data.eyeMoveUp>1) {
                d1ma3 = -data.eyeMoveUp
            } else if (data.eyeMoveDown>1) {
                d1ma3 = data.eyeMoveDown
            }
        }
        return rl_ep
    }
    */
}